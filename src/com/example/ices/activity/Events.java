package com.example.ices.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

 

import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.entity.EventEntity;
import com.example.ices.entity.MenuTitle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Events extends BaseActivity{
	private RelativeLayout main_rl_lec,main_rl_trl,main_rl_sport,main_rl_Ceremony,main_rl_sb,main_rl_Forum,
	main_rl_la,main_rl_et,main_rl_other;
	private String url;
    private Dialog loadingDialog;
	private FrameLayout f_l,f2,f3,cef_3,f4,f5,f6,f7,f8;
	private TextView tv_l,f2_tv2,f3_tv3,cetv_3,f4_tv,f5_tv,f6_tv,f7_tv,f8_tv;
	private List<EventEntity>list1 = new ArrayList<EventEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		new TitleMenuUtil(Events.this, "Events").show();
		initView();
		url=Config.getEventsCount;
		 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData();
	}
	private void getData() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
 
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		params.setUseJsonStreamer(true);
		System.out.println("MyApplication.currentUser.getStudentId()---"+MyApplication.currentUser.getStudentId());
		System.out.println("url"+url);
		System.out.println("MyApplication.getToken()---"+MyApplication.getToken());
		System.out.println("params--"+MyApplication.currentUser.getStudentId()+"---"+ MyApplication.getToken());
		
		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String userMsg = new String(responseBody).toString();
	 
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					 
					 
					code = jsonobject.getInt("code");
					
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){
						list1 = gson.fromJson(jsonobject.getString("result"),
			 					new TypeToken<List<EventEntity>>() {
								}.getType());
						
						 if(list1.get(0).getUnReadCount()!=0){
							 System.out.println(list1.get(0).getEventsType()+"");
							 f_l.setVisibility(View.VISIBLE);
							 tv_l.setText(list1.get(0).getUnReadCount()+"");
						 }else{
							 f_l.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(1).getUnReadCount()!=0){
							 System.out.println(list1.get(1).getEventsType()+"");
							 f2.setVisibility(View.VISIBLE);
							 f2_tv2.setText(list1.get(1).getUnReadCount()+"");
						 }else{
							 f2.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(2).getUnReadCount()!=0){
							 System.out.println(list1.get(2).getEventsType()+"");
							 f3.setVisibility(View.VISIBLE);
							 f3_tv3.setText(list1.get(2).getUnReadCount()+"");
						 }else{
							 f3.setVisibility(View.INVISIBLE);
						 }
						 	////cef_3,cetv_3
						 if(list1.get(3).getUnReadCount()!=0){
							 System.out.println(list1.get(3).getEventsType()+"");
							 cef_3.setVisibility(View.VISIBLE);
							 cetv_3.setText(list1.get(3).getUnReadCount()+"");
						 }else{
							 cef_3.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(4).getUnReadCount()!=0){
							 System.out.println(list1.get(4).getEventsType()+"");
							 f4.setVisibility(View.VISIBLE);
							 f4_tv.setText(list1.get(4).getUnReadCount()+"");
						 }else{
							 f4.setVisibility(View.INVISIBLE);
						 }
						 
						 if(list1.get(5).getUnReadCount()!=0){
							 System.out.println(list1.get(5).getEventsType()+"");
							 f5.setVisibility(View.VISIBLE);
							 f5_tv.setText(list1.get(5).getUnReadCount()+"");
						 }else{
							 f5.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(6).getUnReadCount()!=0){
							 System.out.println(list1.get(6).getEventsType()+"");
							 f6.setVisibility(View.VISIBLE);
							 f6_tv.setText(list1.get(6).getUnReadCount()+"");
						 }else{
							 f6.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(7).getUnReadCount()!=0){
							 System.out.println(list1.get(7).getEventsType()+"");
							 f7.setVisibility(View.VISIBLE);
							 f7_tv.setText(list1.get(7).getUnReadCount()+"");
						 }else{
							 f7.setVisibility(View.INVISIBLE);
						 }
						 if(list1.get(8).getUnReadCount()!=0){
							 System.out.println(list1.get(8).getEventsType()+"");
							 f8.setVisibility(View.VISIBLE);
							 f8_tv.setText(list1.get(8).getUnReadCount()+"");
						 }else{
							 f8.setVisibility(View.INVISIBLE);
						 }
						 
						 
						 
						 
						 
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
			}
		});

	
	}
	private void initView() {
		// TODO Auto-generated method stub
		main_rl_lec=(RelativeLayout) findViewById(R.id.main_rl_lec);
		main_rl_lec.setOnClickListener(this);
		main_rl_trl=(RelativeLayout) findViewById(R.id.main_rl_trl);
		main_rl_trl.setOnClickListener(this);
		main_rl_sport=(RelativeLayout) findViewById(R.id.main_rl_sport);
		main_rl_sport.setOnClickListener(this);
		main_rl_Ceremony=(RelativeLayout) findViewById(R.id.main_rl_Ceremony);
		main_rl_Ceremony.setOnClickListener(this);
		main_rl_sb=(RelativeLayout) findViewById(R.id.main_rl_sb);
		main_rl_sb.setOnClickListener(this);
		main_rl_Forum=(RelativeLayout) findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_la=(RelativeLayout) findViewById(R.id.main_rl_la);
		main_rl_la.setOnClickListener(this);
		main_rl_et=(RelativeLayout) findViewById(R.id.main_rl_et);
		main_rl_et.setOnClickListener(this);
		main_rl_other=(RelativeLayout) findViewById(R.id.main_rl_other);
		main_rl_other.setOnClickListener(this);
		f_l=(FrameLayout) findViewById(R.id.f_l);
		tv_l= (TextView) findViewById(R.id.tv_l);
		
		f2=(FrameLayout) findViewById(R.id.f2);
		f2_tv2= (TextView) findViewById(R.id.f2_tv2);
		f4=(FrameLayout) findViewById(R.id.f4);
		f4_tv= (TextView) findViewById(R.id.f4_tv);
		f3=(FrameLayout) findViewById(R.id.f3);
		f3_tv3= (TextView) findViewById(R.id.f3_tv3);
		//cef_3,cetv_3
		cef_3=(FrameLayout) findViewById(R.id.cef_3);
		cetv_3= (TextView) findViewById(R.id.cetv_3);
		
		f5=(FrameLayout) findViewById(R.id.f5);
		f5_tv= (TextView) findViewById(R.id.f5_tv);
		f6=(FrameLayout) findViewById(R.id.f6);
		f6_tv= (TextView) findViewById(R.id.f6_tv);
		f7=(FrameLayout) findViewById(R.id.f7);
		f7_tv= (TextView) findViewById(R.id.f7_tv);
		f8=(FrameLayout) findViewById(R.id.f8);
		f8_tv= (TextView) findViewById(R.id.f8_tv);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_rl_lec:
			Intent main_rl_lec =new Intent(getApplication(),EventListActivity.class);
			main_rl_lec.putExtra("title", "Lectures");
			startActivity(main_rl_lec);
			break;
		case R.id.main_rl_trl: //main_rl_sport
			Intent main_rl_trl =new Intent(getApplication(),EventListActivity.class);
			main_rl_trl.putExtra("title", "Tourism");
			startActivity(main_rl_trl);
			break;
		case R.id.main_rl_sport: //main_rl_Ceremony
			Intent main_rl_sport =new Intent(getApplication(),EventListActivity.class);
			main_rl_sport.putExtra("title", "Sports");
			startActivity(main_rl_sport);
			break;
		case R.id.main_rl_Ceremony: //main_rl_sb
			Intent Ceremony =new Intent(getApplication(),EventListActivity.class);
			Ceremony.putExtra("title", "Ceremonies");
			startActivity(Ceremony);
			break;
			
		case R.id.main_rl_sb: //main_rl_Forum
			Intent main_rl_sb =new Intent(getApplication(),EventListActivity.class);
			main_rl_sb.putExtra("title", "School Clubs");
			startActivity(main_rl_sb);
			break;
			
		case R.id.main_rl_Forum: //main_rl_la
			Intent Forum =new Intent(getApplication(),EventListActivity.class);
			Forum.putExtra("title", "Forums");
			startActivity(Forum);
			break;
		case R.id.main_rl_la: //main_rl_other
			Intent main_rl_la =new Intent(getApplication(),EventListActivity.class);
			main_rl_la.putExtra("title", "Movies and Shows");
			startActivity(main_rl_la);
			break;
		case R.id.main_rl_other: //main_rl_et
			Intent main_rl_other =new Intent(getApplication(),EventListActivity.class);
			main_rl_other.putExtra("title", "Others");
			startActivity(main_rl_other);
			break;
		case R.id.main_rl_et: //main_rl_et
			Intent main_rl_et =new Intent(getApplication(),EventListActivity.class);
			main_rl_et.putExtra("title", "Recreation");
			startActivity(main_rl_et);
			break;
			
			
			
			
			
			
			
			
			
			
		case R.id.ll_events: // 
			Intent ll_events =new Intent(getApplication(),Events.class);
		 
			startActivity(ll_events);
			break;
		case R.id.ll_arrival:
			Intent ll_arrival =new Intent(getApplication(),PreArrivalActivity.class);
			//i.putExtra("directory_contentId", "231");
			startActivity(ll_arrival);
			break;
		default:
			break;
		}
	}
}
