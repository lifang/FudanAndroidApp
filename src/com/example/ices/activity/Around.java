package com.example.ices.activity;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Around extends BaseActivity{ 

	private RelativeLayout main_rl_Food,main_rl_drink,main_rl_sport,main_rl_sm,main_rl_sb,main_post,
	main_rl_other,  main_rl_Hotel;
	private String url;
	  private Dialog loadingDialog;
	private List<EventEntity>list1 = new ArrayList<EventEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.around); 
		new TitleMenuUtil(Around.this, "Around Campus").show();
		initView();
	//	url=Config.getEventsCount;
		 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//getData();
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
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
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
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});

	
	}
	private void initView() {
		// TODO Auto-generated method stub
		main_rl_Food=(RelativeLayout) findViewById(R.id.main_rl_Food);
		main_rl_Food.setOnClickListener(this);
		main_rl_drink=(RelativeLayout) findViewById(R.id.main_rl_drink);
		main_rl_drink.setOnClickListener(this);
		main_rl_sport=(RelativeLayout) findViewById(R.id.main_rl_sport);
		main_rl_sport.setOnClickListener(this);
		main_rl_other=(RelativeLayout) findViewById(R.id.main_rl_other);
		main_rl_other.setOnClickListener(this);
		
		main_rl_sm=(RelativeLayout) findViewById(R.id.main_rl_sm);
		main_rl_sm.setOnClickListener(this);
		main_rl_sb=(RelativeLayout) findViewById(R.id.main_rl_sb);
		main_rl_sb.setOnClickListener(this);
		main_rl_Hotel=(RelativeLayout) findViewById(R.id.main_rl_Hotel);
		main_rl_Hotel.setOnClickListener(this);
		main_post=(RelativeLayout) findViewById(R.id.main_post);
		main_post.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_rl_Food:
			Intent main_rl_lec =new Intent(getApplication(),AroundList.class);
			main_rl_lec.putExtra("title", "Food");
			startActivity(main_rl_lec);
			break;
		case R.id.main_rl_drink: //main_rl_sport
			Intent main_rl_trl =new Intent(getApplication(),AroundList.class);
			main_rl_trl.putExtra("title", "Drink");
			startActivity(main_rl_trl);
			break;
		case R.id.main_rl_sport: //main_rl_Ceremony
			Intent main_rl_sport =new Intent(getApplication(),AroundList.class);
			main_rl_sport.putExtra("title", "Sports");
			startActivity(main_rl_sport);
			break;
		case R.id.main_rl_other: //main_rl_other
			Intent main_rl_other =new Intent(getApplication(),AroundList.class);
			main_rl_other.putExtra("title", "Other");
			startActivity(main_rl_other);
			break;
			
		case R.id.main_rl_Ceremony: //main_rl_sb
			Intent Ceremony =new Intent(getApplication(),EventListActivity.class);
			Ceremony.putExtra("title", "Ceremony");
			startActivity(Ceremony);
			break;
			
 
			
 
		case R.id.main_rl_la: //main_rl_other
			Intent main_rl_la =new Intent(getApplication(),EventListActivity.class);
			main_rl_la.putExtra("title", "Literature and art");
			startActivity(main_rl_la);
			break;
 
		case R.id.main_rl_et: //main_rl_et
			Intent main_rl_et =new Intent(getApplication(),EventListActivity.class);
			main_rl_et.putExtra("title", "Entertainment");
			startActivity(main_rl_et);
			break;
			
		case R.id.main_post: //main_rl_la
			Intent Forum =new Intent(getApplication(),SearchAround.class);
			Forum.putExtra("title", "Post");
			startActivity(Forum);
			break;
			
		case R.id.main_rl_Hotel: //main_rl_Forum
			Intent main_rl_Hotel =new Intent(getApplication(),SearchAround.class);
			main_rl_Hotel.putExtra("title", "Hotel");
			startActivity(main_rl_Hotel);
			break;
		 
			
			
			
		case R.id.main_rl_sb: //main_rl_Forum
			Intent main_rl_sb =new Intent(getApplication(),SearchAround.class);
			main_rl_sb.putExtra("title", "Bank");
			startActivity(main_rl_sb);
			break;
			
			
			
  
		case R.id.main_rl_sm:
			Intent main_rl_sm =new Intent(getApplication(),SearchAround.class);
			main_rl_sm.putExtra("title", "SuperMarket");
			startActivity(main_rl_sm);
			break;
		default:
			break;
		}
	}

}
