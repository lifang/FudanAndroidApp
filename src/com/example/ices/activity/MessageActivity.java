package com.example.ices.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.examlpe.ices.util.DialogUtil;
import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.DialogUtil.CallBackChange;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.EventAdapter;
import com.example.ices.adapter.MessageAdapter;
import com.example.ices.entity.EventlistEntity;
import com.example.ices.entity.NotifiEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MessageActivity extends BaseActivity implements IXListViewListener {
	private LinearLayout showdilog;
	private XListView event_listview;
	private int title;
	private int page = 1;
	private int rows = Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private boolean isFirstCreate;
	private MessageAdapter myAdapter;
	private Dialog loadingDialog;
	private int type = 2;// 新消息1、最近2、所有3
	private TextView suer, titleback_text_title;
	private PopupWindow menuWindow;
	List<NotifiEntity> myList = new ArrayList<NotifiEntity>();
	List<NotifiEntity> moreList = new ArrayList<NotifiEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				System.out.println(myList.size() + "===myList.size()");

				if (type == 1) {

					if (myList.size() == 0) {
						showdilog.setVisibility(View.GONE);
					} else {
						showdilog.setVisibility(View.VISIBLE);
					}
					titleback_text_title.setText(myList.size() + "   " + "unread");
				} else {
					showdilog.setVisibility(View.GONE);
				}
				if (myList.size() == 0) {
					// norecord_text_to.setText("您没有相关的商品");
					event_listview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				} else {
					eva_nodata.setVisibility(View.GONE);
					event_listview.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true;
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(),
						"no 3g or wifi content", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		isFirstCreate=true;
		innitView();
		title = getIntent().getIntExtra("code", 0);
		new TitleMenuUtil(MessageActivity.this, "").show();
		if (title > 0) {
			titleback_text_title.setText(title + "   " + "unread");
			type = 1;
			suer.setText("New");
			showdilog.setVisibility(View.VISIBLE);
		} else {
			suer.setText("Recent");
			titleback_text_title.setText("");
			type = 2;
			showdilog.setVisibility(View.GONE);
		}

		getData();
	}
	@Override
	protected void onResume() {
		super.onResume();

		if(!isFirstCreate){
			if (type == 1) {
				page = 1;
				myList.clear();
				getData();
			}
		}else {
			isFirstCreate=false;
		}

	}
	private void innitView() {
		// TODO Auto-generated method stub
		suer = (TextView) findViewById(R.id.next_sure);

		titleback_text_title = (TextView) findViewById(R.id.titleback_text_title);
		suer.setVisibility(View.VISIBLE);
		suer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if(menuWindow.isShowing()){
				// menuWindow.dismiss();
				// }else{
				//
				// }
				menu_press();
			}
		});
		showdilog = (LinearLayout) findViewById(R.id.showdilog);
		showdilog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog ddd = new DialogUtil(MessageActivity.this,
						"Mark all as read?").getCheck(new CallBackChange() {

							@Override
							public void change() {
								// TODO Auto-generated method stub
								// Toast.makeText(getApplication(), "DOOOO",
								// 1000).show();
								readAll();
							}

						});
				ddd.show();
			}
		});
		myAdapter = new MessageAdapter(MessageActivity.this, myList);

		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		event_listview = (XListView) findViewById(R.id.event_listview);
		// refund_listview.getmFooterView().getmHintView().setText("已经没有数据了");
		event_listview.setPullLoadEnable(true);
		event_listview.setXListViewListener(this);
		event_listview.setDivider(null);

		event_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MessageActivity.this, MessageDetail.class);

				// System.out.println("detail``id"+myList.get(position-1).getId());
				i.putExtra("id", myList.get(position - 1).getNotificationId());
				System.out.println("id---" + myList.get(position - 1).getId());
				startActivityForResult(i, 10);
			}
		});
		event_listview.setAdapter(myAdapter);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		myList.clear();
		getData();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (onRefresh_number) {
			page = page + 1;
			if (Tools.isConnect(getApplicationContext())) {
				onRefresh_number = false;
				getData();
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}
	}

	private void onLoad() {
		event_listview.stopRefresh();
		event_listview.stopLoadMore();
		event_listview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		page = 1;
		myList.clear();
		getData();
	}

	private void readAll() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();

		RequestParams params = new RequestParams();

		params.put("studentId", MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken());


		params.setUseJsonStreamer(true);
		System.out.println("MyApplication.currentUser.getStudentId()---"
				+ MyApplication.currentUser.getStudentId());

		System.out.println("MyApplication.getToken()---"
				+ MyApplication.getToken());
		System.out.println("params--"
				+ MyApplication.currentUser.getStudentId() + "---"
				+ MyApplication.getToken());

		MyApplication
		.getInstance()
		.getClient()
		.post(Config.readAllNotification, params,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					Header[] headers, byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String userMsg = new String(responseBody)
				.toString();
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if (code == -2) {
						Intent i = new Intent(getApplication(),
								LoginActivity.class);
						startActivity(i);
						finish();
					} else if (code == 0) {
						Toast.makeText(
								getApplicationContext(),
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
						titleback_text_title.setText(0 + "   " + "unread");
						//	type = 2;
						showdilog.setVisibility(View.GONE);
						eva_nodata.setVisibility(View.VISIBLE);

					} else {
						Toast.makeText(
								getApplicationContext(),
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode,
					Header[] headers, byte[] responseBody,
					Throwable error) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}

			}
		});
	}

	public void menu_press() {
		View view = getLayoutInflater().inflate(R.layout.popwindow, null);
		// view.findViewById(R.id.todayorder_ordernumber).setOnClickListener(this);
		// view.findViewById(R.id.todayorder_mobile).setOnClickListener(this);
		menuWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView tv_new = (TextView) view.findViewById(R.id.tv_new);
		TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
		TextView tv_re = (TextView) view.findViewById(R.id.tv_re);
		if (type == 1) {
			tv_new.setTextColor(getResources().getColor(R.color.C0075FF));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.black));

		}
		if (type == 3) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.C0075FF));
			tv_re.setTextColor(getResources().getColor(R.color.black));
			titleback_text_title.setText("");
		}
		if (type == 2) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.C0075FF));
		}
		tv_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 1;
				suer.setText("New");
				menuWindow.dismiss();
				myList.clear();
				getData();
				titleback_text_title.setText("");
			}
		});
		tv_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 3;
				suer.setText("All");
				titleback_text_title.setText("");
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		tv_re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 2;
				suer.setText("Recent");
				titleback_text_title.setText("");
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		menuWindow.setFocusable(true);
		menuWindow.setOutsideTouchable(true);
		menuWindow.update();
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置layout在PopupWindow中显示的位置
		// int hight = findViewById(R.id.main_top).getHeight()
		// + Tools.getStatusBarHeight(getApplicationContext());
		menuWindow.showAsDropDown(this.findViewById(R.id.next_sure),0,10);
		//		menuWindow.showAtLocation(this.findViewById(R.id.next_sure),
		//				Gravity.TOP | Gravity.RIGHT, 50, 100);
	}

	private void getData() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象

		RequestParams params = new RequestParams();

		params.put("studentId", MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken());
		params.put("type", type);
		params.put("rows", Config.ROWS);
		params.put("page", page);

		params.setUseJsonStreamer(true);
		System.out.println("MyApplication.currentUser.getStudentId()---"
				+ MyApplication.currentUser.getStudentId());

		System.out.println("MyApplication.getToken()---"
				+ MyApplication.getToken());
		System.out.println("params--"
				+ MyApplication.currentUser.getStudentId() + "---"
				+ MyApplication.getToken());

		MyApplication
		.getInstance()
		.getClient()
		.post(Config.getNotifications, params,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					Header[] headers, byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String userMsg = new String(responseBody)
				.toString();
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if (code == -2) {
						Intent i = new Intent(getApplication(),
								LoginActivity.class);
						startActivity(i);
						finish();
					} else if (code == 0) {
						moreList.clear();
						moreList = gson.fromJson(
								jsonobject.getString("result"),
								new TypeToken<List<NotifiEntity>>() {
								}.getType());

						if (moreList.size() == 0) {
							Toast.makeText(
									getApplicationContext(),
									"no more data",
									Toast.LENGTH_SHORT).show();
							event_listview.getmFooterView()
							.setState2(2);
						}
						myList.addAll(moreList);
						handler.sendEmptyMessage(0);
					} else {
						Toast.makeText(
								getApplicationContext(),
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode,
					Header[] headers, byte[] responseBody,
					Throwable error) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}

			}
		});
	}
}
