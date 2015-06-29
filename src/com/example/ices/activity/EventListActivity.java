package com.example.ices.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.EventAdapter;
import com.example.ices.baidu.NewEventDetail;
import com.example.ices.entity.EventlistEntity;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class EventListActivity extends BaseActivity implements  IXListViewListener{
	private XListView event_listview;
	private String title;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private EventAdapter myAdapter;
	private int type=1;

	List<EventlistEntity>  myList = new ArrayList<EventlistEntity>();
	List<EventlistEntity>  moreList = new ArrayList<EventlistEntity>();
    private Dialog loadingDialog;
	private boolean isRefresh = true;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );

				if(myList.size()==0){
					//	norecord_text_to.setText("您没有相关的商品");
					event_listview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true; 
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),  " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);
		title=getIntent().getStringExtra("title");
		myAdapter=new EventAdapter(EventListActivity.this, myList);
		//			lecture:1、raveling:2、sports:3、ceremony:4、
		//			school club:5、forum:6、literature and art:7、entertainment:8、other:

		System.out.println("title``"+title);
		initView();
		getData();
	}
	private void initView() {
		// TODO Auto-generated method stub

		new TitleMenuUtil(EventListActivity.this, title).show();
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		event_listview=(XListView) findViewById(R.id.event_listview);
		// refund_listview.getmFooterView().getmHintView().setText("已经没有数据了");
		event_listview.setPullLoadEnable(true);
		event_listview.setXListViewListener(this);
		event_listview.setDivider(null);

		event_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EventListActivity.this,EventDetail.class);
				//					i.putExtra("Status", myList.get(position-1).getStatus());
				//					i.putExtra("refundId",myList.get(position-1).getId()+"" );
				//					System.out.println("detail``id"+myList.get(position-1).getId());
				i.putExtra("id", myList.get(position-1).getId());
				System.out.println("id---"+myList.get(position-1).getId());
				startActivityForResult(i, 10);
			}
		});
		event_listview.setAdapter(myAdapter);

		if(title.endsWith("Tourism")){
			type=2;
		}
		if(title.endsWith("Sports")){
			type=3;
		}
		if(title.endsWith("Ceremonies")){
			type=4;
		}
		if(title.endsWith("School Clubs")){
			type=5;
		}
		if(title.endsWith("Forums")){
			type=6;
		}
		if(title.endsWith("Movies and Shows")){
			type=7;
		}
		if(title.endsWith("Recreation")){
			type=8;
		}
		if(title.endsWith("Others")){
			type=9;
		}
		if(title.endsWith("Lectures")){
			type=1;
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (isRefresh == true) {
			page = 1;
			myList.clear();
			getData();
		}
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (onRefresh_number) {
			page = page+1;
			if (Tools.isConnect(getApplicationContext())) {
				onRefresh_number = false;
				getData();
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		}
		else {
			handler.sendEmptyMessage(3);
		}
	}
	private void onLoad() {
		event_listview.stopRefresh();
		event_listview.stopLoadMore();
		event_listview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		if (isRefresh == true) {
			page = 1;
			myList.clear();
			getData();
		}
	}
	private void getData() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
		isRefresh = false;
		// TODO Auto-generated method stub
		//AsyncHttpClient client = new rows(); //  
		RequestParams params = new RequestParams();
		params.put("rows",rows);
		params.put("page",page); 
		params.put("type", type); 
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		System.out.println("type"+type);

		params.setUseJsonStreamer(true);


		MyApplication.getInstance().getClient().post(Config.getEvents, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
				isRefresh = true;

				Gson gson = new Gson();
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(responseMsg);


					code = jsonobject.getInt("code");

					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){


						moreList.clear();

						moreList = gson.fromJson(jsonobject.getString("result") ,
								new TypeToken<List<EventlistEntity>>() {
						}.getType());

						if (moreList.size()==0) {
							Toast.makeText(getApplicationContext(),
									"The end", Toast.LENGTH_SHORT).show();
							event_listview.getmFooterView().setState2(2);

						} 

						myList.addAll(moreList);
						handler.sendEmptyMessage(0);



					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				isRefresh = true;
				Toast.makeText(getApplicationContext(), "请检查网络问题",
						Toast.LENGTH_SHORT).show();
			}
		});

	}
}
