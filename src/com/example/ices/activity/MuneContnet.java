package com.example.ices.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.adapter.MyExpandableListAdapter;
import com.example.ices.entity.MenuTitle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MuneContnet extends BaseActivity{
	private ExpandableListView lv;
	private int type;
	private List<MenuTitle> list= new ArrayList<MenuTitle>();
	private List<MenuTitle> list1= new ArrayList<MenuTitle>();
	private MyExpandableListAdapter myAdapter;
	private ImageView for_campus;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_content);
		lv=(ExpandableListView) findViewById(R.id.lv);
		type=getIntent().getIntExtra("type", 1);
		//if()
		lv.setGroupIndicator(null);
		 lv.setDivider(null);
		lv.setChildDivider(getResources().getDrawable(R.drawable.dre0));
		for_campus=(ImageView) findViewById(R.id.for_campus);
		if(type==3){ 
			title="Campus";
		}
		 if(type==1){
			 for_campus.setVisibility(View.GONE);
			 title="Pre-Arrival";
		 }
		 if(type==2){
			 for_campus.setVisibility(View.GONE);
			 title="Arrival";
		 }
 	 	new TitleMenuUtil(MuneContnet.this, title).show();
		myAdapter = new MyExpandableListAdapter(MuneContnet.this, list);
		lv.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getApplicationContext(), list.get(groupPosition).getContentList().get(childPosition).getContentTitle(), 1000).show();
				Intent i = new Intent(MuneContnet.this,PreDetail.class);
				i.putExtra("id", list.get(groupPosition).getContentList().get(childPosition).getId());
				startActivity(i);
				
				return true;
			}
		});
		lv.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
			//	Toast.makeText(getApplicationContext(), list.get(groupPosition).getDirectoryName(), 1000).show();
				
				if(list.get(groupPosition).getContentList().size()==0){
					Toast.makeText(getApplicationContext(), "no more data", 1000).show();
//					Intent i = new Intent(MuneContnet.this,PreDetail.class);
//					i.putExtra("id", list.get(groupPosition).getDirectorId());
//					startActivity(i);
				}else if(list.get(groupPosition).getContentList().size()==1){
					Intent i = new Intent(MuneContnet.this,PreDetail.class);
					i.putExtra("id", list.get(groupPosition).getContentList().get(0).getId());
					startActivity(i);
					return true;
				}
				
				
				
				else{
					return false;
				}
				
 
				return false;
				 
			}
		});
		getdata();
	 
	}
	private void getdata() {
		// TODO Auto-generated method stub


		// TODO Auto-generated method stub
		//AsyncHttpClient client = new AsyncHttpClient(); //  
		RequestParams params = new RequestParams();
		 
		params.put("studentId",MyApplication.currentUser.getStudentId()); 
		params.put("token", MyApplication.getToken()); //type
		params.put("type",type); 		
		params.setUseJsonStreamer(true);
		 
	 
		MyApplication.getInstance().getClient().post(Config.getContents, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
		 
				Gson gson = new Gson();
				
				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					System.out.println("code--"+code);
					if(code.equals(Config.CODE)){ //判断返回结果是否合法
				 
						list1 = gson.fromJson(jsonobject.getString("result"),
	 					new TypeToken<List<MenuTitle>>() {
						}.getType());
						System.out.println("list.size"+list.size());
					 
						
						list.addAll(list1);
						if(lv==null){
							System.out.println("lv==null" );
						}
						if(myAdapter==null){
							System.out.println("2==null" );
						}
						if(list==null){
							System.out.println("list==null" );
						}
						lv.setAdapter(myAdapter);
 
					}else  {
						code = jsonobject.getString("message");
						Toast.makeText(getApplicationContext(), code, 1000).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					System.out.println("MSG" + "e````"+e.toString());	
					e.printStackTrace();
					
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "网络连接错误", 1000).show();
			}
		});
	
	}
 
}
