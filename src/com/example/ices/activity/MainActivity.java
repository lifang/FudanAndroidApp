package com.example.ices.activity;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
 
import com.examlpe.ices.util.ClientUpdate;
import com.examlpe.ices.util.LoadingDialog;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.baidu.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
 
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
 /***
  * 
 *    
 * 类名称：MainActivity   
 * 类描述：    首页，检查是否有新消息
 * 创建人： ljp 
 * 创建时间：2014-12-4 下午4:42:16   
 * @version    
 *
  */

public class MainActivity extends BaseActivity {
	private LinearLayout ll_more,ll_pre,ll_arrival,ll_events,campus;
	private String url;
	private View is_show;
	int code = 0;
	private RelativeLayout ll_arround,rela_message;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private  Boolean isQuit=false;
    private Dialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		new ClientUpdate(MainActivity.this).checkSetting();
		 url=Config.getNotificationUnReadCount;
			mySharedPreferences = getSharedPreferences("Usermsg", MODE_PRIVATE);
			editor = mySharedPreferences.edit();
		 
	        PushManager.startWork(getApplicationContext(),
	                PushConstants.LOGIN_TYPE_API_KEY,
	                Utils.getMetaValue(MainActivity.this, "api_key"));
	        Resources resource = this.getResources();
	        String pkgName = this.getPackageName();
	        // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
	        PushManager.enableLbs(getApplicationContext());
	        // Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
	        // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
	        // 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
	        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
	                getApplicationContext(), resource.getIdentifier(
	                        "notification_custom_builder", "layout", pkgName),
	                resource.getIdentifier("notification_icon", "id", pkgName),
	                resource.getIdentifier("notification_title", "id", pkgName),
	                resource.getIdentifier("notification_text", "id", pkgName));
	        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
	        cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
	                | Notification.DEFAULT_VIBRATE);
	        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
	        cBuilder.setLayoutDrawable(resource.getIdentifier(
	                "simple_notification_icon", "drawable", pkgName));
	     PushManager.setNotificationBuilder(this, 1, cBuilder);
	     String  oldTag= mySharedPreferences.getString("tag", "tag");
	     System.out.println("oldtag--"+oldTag);   
	     List<String> tagsd5 = Utils.getTagsList(oldTag);	        
	     PushManager.delTags(getApplicationContext(), tagsd5);
         List<String> tagNew = Utils.getTagsList(MyApplication.currentUser.getStudentId());
         PushManager.setTags(getApplicationContext(),tagNew);
 	        
     	editor.putString("tag",MyApplication.currentUser.getStudentId());
    	editor.commit();
     	System.out.println("newTag--"+mySharedPreferences.getString("tag", ""));
			 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	 
		 
			getData();
		 
		 
	}
	private void getData() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		//loadingDialog.show();
		RequestParams params = new RequestParams();
		params.put("studentId", MyApplication.currentUser.getStudentId());
		params.put("token", MyApplication.getToken()); 
		params.setUseJsonStreamer(true);
		System.out.println("params--"+MyApplication.currentUser.getStudentId()+"---"+ MyApplication.getToken());
		
		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					//loadingDialog.dismiss();
				}
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				System.out.println("userMsg`` `" + userMsg);
				JSONObject jsonobject = null;
				 
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
					//	Intent i =new Intent(getApplication(),LoginActivity.class);
						//startActivity(i);
					}else if(code==0){
						code = jsonobject.getInt("result");
						if(code>0){
							is_show.setVisibility(View.VISIBLE);
						}else{
							is_show.setVisibility(View.INVISIBLE);
						}
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
					//loadingDialog.dismiss();
				}
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_internet),
						Toast.LENGTH_SHORT).show();
			}
		});

	
	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("进入 回调···");
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null){
			 isQuit = data.getExtras().getBoolean("isQuit");
		}
	 
		if(isQuit){
			finish();
		}
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		rela_message=(RelativeLayout) findViewById(R.id.rela_message);
		rela_message.setOnClickListener(this);
		ll_arround=(RelativeLayout) findViewById(R.id.ll_arround);
		ll_arround.setOnClickListener(this);
		ll_more=(LinearLayout) findViewById(R.id.ll_more);
		ll_more.setOnClickListener(this);
		ll_pre=(LinearLayout) findViewById(R.id.ll_pre);
		ll_pre.setOnClickListener(this);
		ll_arrival=(LinearLayout) findViewById(R.id.ll_arrival);
		ll_arrival.setOnClickListener(this);
		ll_events=(LinearLayout) findViewById(R.id.ll_events);
		ll_events.setOnClickListener(this);
		is_show=findViewById(R.id.is_show);
		campus=(LinearLayout) findViewById(R.id.campus);
		campus.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_more:
			Intent ll_more1 =new Intent(getApplication(),More.class);
			 startActivityForResult(ll_more1, 78);
			 
			break;
			
		case R.id.ll_pre: //ll_events
			Intent ll_pre =new Intent(getApplication(),MuneContnet.class);
			ll_pre.putExtra("type", 1);
			startActivity(ll_pre);
			break;
		case R.id.campus: //rela_message
			Intent campus =new Intent(getApplication(),MuneContnet.class);
			campus.putExtra("type", 3);
			startActivity(campus);
			break;
		case R.id.rela_message: //rela_message
			Intent rela_message =new Intent(getApplication(),MessageActivity.class);
			rela_message.putExtra("code", code);
			System.out.println("code====="+code);
			startActivity(rela_message);
			break;
		case R.id.ll_events: // 
			Intent ll_events =new Intent(getApplication(),Events.class);
			startActivity(ll_events);
			break;
		case R.id.ll_arrival:
			Intent ll_arrival =new Intent(getApplication(),MuneContnet.class);
			ll_arrival.putExtra("type",2);
			startActivity(ll_arrival);
			break;
		case R.id.ll_arround: 
			Intent ll_arround =new Intent(getApplication(),PreArrivalActivity.class);
			//i.putExtra("directory_contentId", "231");ll_arround
			startActivity(ll_arround);
			break;
		default:
			break;
		}
	}
 
}
