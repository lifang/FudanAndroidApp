package com.example.ices.activity;
 
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
import com.examlpe.ices.util.StringUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.Config;
import com.example.ices.MyApplication;
import com.example.ices.R;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* 类名称：LoginActivity   
* 类描述：   登录 
* 创建人： ljp 
* 创建时间：2014-12-2 上午11:14:43   
* @version    
*
 */
public class LoginActivity extends BaseActivity{
	private LinearLayout login_linear_signin,login_linear_deletemali,login_linear_deletpass,login_linear_signup;
	private EditText login_edit_email,login_edit_pass;
	private TextView tv_password,titleback_text_title;
	private String name,pass,url,deviceToken;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	 
		titleback_text_title=(TextView) findViewById(R.id.titleback_text_title);
		titleback_text_title.setText("Sign  In");
//		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);     
//		deviceToken= tm.getDeviceId();
		mySharedPreferences = getSharedPreferences("Usermsg", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		 
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceToken = tm.getDeviceId();
		System.out.println("deviceToken"+deviceToken+tm.toString());
		initView();
		name= mySharedPreferences.getString("name", "");
		pass= mySharedPreferences.getString("pass", "");
		login_edit_pass.setText(pass);
	 
		login_edit_email.setText(name);
	}

	private void initView() {
		// TODO Auto-generated method stub
		url=Config.LOGIN;
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		tv_password=(TextView) findViewById(R.id.tv_password);
		tv_password.setOnClickListener(this);
		login_linear_deletemali=(LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
		login_linear_deletpass=(LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
		login_linear_signup=(LinearLayout) findViewById(R.id.login_linear_signup);
		login_linear_signup.setOnClickListener(this);

		login_edit_email=(EditText) findViewById(R.id.login_edit_email);
		login_edit_email.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletemali.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemali.setVisibility(View.GONE);
				}
			 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		login_edit_pass=(EditText) findViewById(R.id.login_edit_pass);
		login_edit_pass.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletpass.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch ( v.getId()) {
		case R.id.login_linear_signup:
	 
			Intent iSIGNUP =new Intent(LoginActivity.this,SignUp.class);
			startActivity(iSIGNUP);
			break;
		case R.id.tv_password:  
			Intent i =new Intent(LoginActivity.this,FindPassword.class);
			startActivity(i);
			break;
		case R.id.login_linear_deletpass:   
			login_edit_pass.setText("");
		break;
		case R.id.login_linear_deletemali:   
			login_edit_email.setText("");
		break;
		case R.id.login_linear_signin:
			  
		 if(check()){
		 
		 	  login(name,pass);//调试用法	  
 
		 }
	 
			break;
		default : 
			break;
		}
	}
	private void test() {
		// TODO Auto-generated method stub
		url="http://114.215.149.242:18080/ZFMerchant/api/customers/getOne/8";
		Log.e("LJP", url);
		MyApplication.getInstance().getClient().get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) { 
				Log.e("LJP", "1111");
				String responseMsg = new String(responseBody).toString();
				Log.e("LJP", responseMsg);
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				Log.e("LJP", "EEEEEEEEEEE");
			}
		});
	}

	/**
	 * 
	 * @param name2 用户名
	 * @param pass2 密码
	 */
	private void login(String name2, String pass2) {
		// TODO Auto-generated method stub
		 AsyncHttpClient client = new AsyncHttpClient(); //  
	 
		RequestParams params = new RequestParams();
		 
		params.put("studentEmail",name); 
		params.put("studentPassword", pass); 
		params.put("pushDeviceType", 3); 	 
		params.setUseJsonStreamer(true);
 	//MyApplication.getInstance().getClient().set
 	///MyApplication.getInstance().getClient().post(context, name2, headers, entity, contentType, responseHandler)
    //	MyApplication.getInstance().getClient().addHeader("dadad", "jdjdj");  
 	// MyApplication.getInstance().getClient().post(context, name2, headers, params, null, responseHandler); 
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				
				 
				///String re = new String(responseBody).toString();
			  // 	headers[o].get
				
				for(int i =0;i<headers.length;i++){
					System.out.println(headers[i].toString());
				}
//				
//				
				
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
 
				Gson gson = new Gson();
				
				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					if(code.equals(Config.CODE)){ //判断返回结果是否合法
					//	"code":0,"message":"用户登录成功","token":"14188706016196","result":{"studentEmail":"475813996@qq.com","studentId":6,"studentStatus":2,"studentMobilePhone":"18862243513"}}
						User current = gson.fromJson(jsonobject.getString("result"), new TypeToken<User>() {
	 					}.getType());
	 					 MyApplication.currentUser = current;
	 					 
	 					editor.putString("name", current.getStudentEmail());
	 					editor.putString("pass", login_edit_pass.getText().toString());
	 					editor.putString("tag",mySharedPreferences.getString("tag", "tag")+","+current.getStudentId());
	 					editor.commit();
	 					 
	 					 
	 					 MyApplication.setToken(jsonobject.getString("token"));
	 					 Intent i =new Intent(LoginActivity.this,MainActivity.class);
	 					 startActivity(i);
	 				 
	 					 finish();
					}else{
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
				login_linear_signin.setClickable(true);
				Toast.makeText(getApplicationContext(), "请检查网络问题",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private boolean check() {
		// TODO Auto-generated method stub
		name=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(name.length()==0){
			Toast.makeText(getApplicationContext(), "Email cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(pass.length()==0){
			Toast.makeText(getApplicationContext(), "Password cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass=StringUtil.Md5(pass);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	     
	            finish();
	            System.exit(0);
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
