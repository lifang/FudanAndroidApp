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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.AroundAdapter;
import com.example.ices.entity.AroundEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AroundList  extends BaseActivity implements  IXListViewListener{

	private XListView event_listview;
	private String title;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private AroundAdapter myAdapter;
	private int type=1;
    private Dialog loadingDialog;
	 
	List<AroundEntity>  myList = new ArrayList<AroundEntity>();
	List<AroundEntity>  moreList = new ArrayList<AroundEntity>();
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
		myAdapter=new AroundAdapter(AroundList.this, myList);
//		lecture:1、raveling:2、sports:3、ceremony:4、
//		school club:5、forum:6、literature and art:7、entertainment:8、other:
 
		System.out.println("title``"+title);
		initView();
		getData();
	}
	private void initView() {
		// TODO Auto-generated method stub
		
		new TitleMenuUtil(AroundList.this, title).show();
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
				Intent i = new Intent(AroundList.this,AroundDetail.class);
//				i.putExtra("Status", myList.get(position-1).getStatus());
//				i.putExtra("refundId",myList.get(position-1).getId()+"" );
//				System.out.println("detail``id"+myList.get(position-1).getId());
				i.putExtra("id", myList.get(position-1).getId());
				System.out.println("id---"+myList.get(position-1).getId());
				startActivityForResult(i, 10);
			}
		});
		event_listview.setAdapter(myAdapter);
		
		if(title.endsWith("Gyms and Fun")){
			type=2;
		}
		if(title.endsWith("Coffee Houses")){
			type=3;
		}
		if(title.endsWith("Others")){
			type=4;
		}
 
		if(title.endsWith("Diners")){
			type=1;
		}
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
		page = 1;
		myList.clear();
		getData();
	}
	private void getData() {
		loadingDialog = LoadingDialog.getLoadingDialg(AroundList.this);
		loadingDialog.show();
		//AsyncHttpClient client = new rows(); //  
		RequestParams params = new RequestParams();
		params.put("rows",rows);
		params.put("page",page); 
		params.put("type", type); 
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		
		 
		params.setUseJsonStreamer(true);
 
	 
		MyApplication.getInstance().getClient().post(Config.getArounds, params, new AsyncHttpResponseHandler() {
		
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
				
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				
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
							new TypeToken<List<AroundEntity>>() {
							}.getType());
		 					 	
		 						if (moreList.size()==0) {
		 							Toast.makeText(getApplicationContext(),
		 									"no more data", Toast.LENGTH_SHORT).show();
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
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});
	
	}

}
