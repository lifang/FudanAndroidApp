package com.example.ices.activity;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 
import com.baidu.android.pushservice.PushManager;
import com.examlpe.ices.util.ClientUpdate;
import com.examlpe.ices.util.LoadingDialog;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.baidu.Utils;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class More extends BaseActivity {
	private RelativeLayout rl_info, rl_cp,rl_aboutus,rl_contectus,rl_Update;
	private LinearLayout login_linear_exit;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
    private Dialog loadingDialog;
	//    //	 Intent i =new Intent(More.this,MainActivity.class);
		// startActivity(i);
     //  finish();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
		 
				System.out.println("进入 --fihish");
				Intent aa = new Intent(More.this, LoginActivity.class);
				 
				aa.putExtra("isQuit", true);
	                //设置返回数据
				More.this.setResult(RESULT_OK, aa);
				More.this.finish();
				break;
 
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		initView();
		//new TitleMenuUtil(More.this, "More").show();
		TextView tv=(TextView) findViewById(R.id.titleback_text_title);
		tv.setText("More");
		findViewById(R.id.tv_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent aa = new Intent();				 
				aa.putExtra("isQuit", false);
	                //设置返回数据
				More.this.setResult(RESULT_OK, aa);
				More.this.finish();
			}
		});
		mySharedPreferences = getSharedPreferences("Usermsg", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
	}

	private void initView() {
		// TODO Auto-generated method stub
		rl_info = (RelativeLayout) findViewById(R.id.rl_info);
		rl_info.setOnClickListener(this);
		rl_cp = (RelativeLayout) findViewById(R.id.rl_cp);
		rl_cp.setOnClickListener(this);
		login_linear_exit = (LinearLayout) findViewById(R.id.login_linear_exit);
		login_linear_exit.setOnClickListener(this);
		rl_aboutus = (RelativeLayout) findViewById(R.id.rl_aboutus);
		rl_aboutus.setOnClickListener(this);
		rl_contectus= (RelativeLayout) findViewById(R.id.rl_contectus);
		rl_contectus.setOnClickListener(this);
		rl_Update= (RelativeLayout) findViewById(R.id.rl_Update);
		rl_Update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rl_info:

			Intent rl_info = new Intent(More.this, MyInfo.class);
			startActivity(rl_info);
			break;
		case R.id.rl_Update: // rl_Update
			new ClientUpdate(More.this).checkSetting();
			break;
		case R.id.rl_contectus: // rl_Update
			Intent rl_contectus = new Intent(More.this, Contectus.class);
			startActivity(rl_contectus);
			break;
		case R.id.rl_cp: //  
			Intent rl_cp = new Intent(More.this, ChangePass.class);
			startActivity(rl_cp);
			break;
		case R.id.login_linear_exit: 
		 
			 exit();
			break;
		case R.id.rl_aboutus:  
			Intent rl_aboutus = new Intent(More.this, About.class);
			startActivity(rl_aboutus);
			break;
		default:
			break;
		}
	}

	private void exit() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
	 	AsyncHttpClient client = new AsyncHttpClient(); //  
		RequestParams params = new RequestParams();
		//activation
		//activation
		params.put("studentId",MyApplication.currentUser.getStudentId());
		params.put("token",MyApplication.getToken()); 
		 
		params.setUseJsonStreamer(true);
 
		String url_exit=Config.EXIT;
		MyApplication.getInstance().getClient().post(url_exit, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
			 	String responseMsg = new String(responseBody).toString();
			 	System.out.println("MSG" + responseMsg);			
				Gson gson = new Gson();
				
				JSONObject jsonobject = null;
				int code = 0;
				try {
				 	jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getInt("code");
					if(code==0){ //判断返回结果是否合法
 
	 					 MyApplication.currentUser = new User();
	 					 System.out.println("studentinfo``"+MyApplication.currentUser.getStudentId());
	 					 MyApplication.setToken("");
	 				 	Toast.makeText(getApplicationContext(), "Exit ICES Success!", 1000).show();
	 		 
	 				       
	 			        String  oldTag= mySharedPreferences.getString("tag", "");
	 			        System.out.println("oldtag--"+oldTag);
	 			        List<String> tagsd = Utils.getTagsList(oldTag);	        
	 		            PushManager.delTags(getApplicationContext(), tagsd);
	 		            PushManager.unbind(More.this);
	 		        
	 		           handler.sendEmptyMessage(0);
	 		           
	 	           
					}else{
					 
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"), 1000).show();
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
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
