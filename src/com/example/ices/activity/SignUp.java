package com.example.ices.activity;
 
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.ices.entity.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* 类名称：SignUp   
* 类描述：   注册
* 创建人： ljp 
* 创建时间：2014-12-4 上午10:44:13   
* @version    
*
 */
public class SignUp extends BaseActivity{
	private TextView tv;
	private LinearLayout login_linear_delettime,login_linear_deletemail,login_linear_deletpass1,
	login_linear_deletpass2,login_linear_deletkey,login_linear_signup;
	private EditText login_edit_time,login_edit_eamil,login_edit_pass1,login_edit_pass2,login_edit_key;
	private String code,email,pass1,pass2,mobile="",url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		url=Config.SIGNUP;
		new TitleMenuUtil(SignUp.this, "Sign  Up").show();
		initView();
	}
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//super.onClick(v);
		switch ( v.getId()) {
 
		case R.id.login_linear_signup:  // 获取验证码
			if(check()){
				signUp(code,url,mobile,email,pass1);
			}
			break;
		case R.id.login_linear_deletkey:  
			login_edit_key.setText("");
			break;
		case R.id.login_linear_delettime: //1
			login_edit_time.setText("");
			break;
		case R.id.login_linear_deletemail:
			login_edit_eamil.setText("");
			break;
		case R.id.login_linear_deletpass1:
			login_edit_pass1.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		default : 
			break;
		}
	}
	/**
	 * 注册
	 * @param activation  激活码
	 * @param url  请求地址
	 * @param studentMobilePhone  手机号  可为空
	 * @param studentEmail  邮箱
	 * @param studentPassword  加密的密码
	 */
	private void signUp(String activation,String url,String studentMobilePhone,String studentEmail,String studentPassword) {
		// TODO Auto-generated method stub
		//AsyncHttpClient client = new AsyncHttpClient(); //  
		RequestParams params = new RequestParams();
		params.put("activationCode",activation);
		params.put("studentMobilePhone", studentMobilePhone);
		params.put("studentEmail",studentEmail);
		params.put("studentPassword", studentPassword);
		params.setUseJsonStreamer(true);
		System.out.println(activation+"---"+studentMobilePhone+"---"+studentEmail+"---"+studentPassword);
		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);			
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
						Toast.makeText(getApplicationContext(),jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}	
				} catch (Exception e) {
					// TODO: handle exception
					 Toast.makeText(getApplicationContext(), e.toString(), 1000).show();
				}
 
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub

			}
		});

	}
	/**
	 * 验证输入信息，对密码进行加密
	 * @return
	 */
	private boolean check() {
		// TODO Auto-generated method stub
		code=StringUtil.replaceBlank(login_edit_time.getText().toString());
		if(code.length()==0){
			Toast.makeText(getApplicationContext(), "Activation cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		email=StringUtil.replaceBlank(login_edit_eamil.getText().toString());
		if(email.length()==0){
			Toast.makeText(getApplicationContext(), "Email cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass1=StringUtil.replaceBlank(login_edit_pass1.getText().toString());
		if(StringUtil.replaceBlank(login_edit_pass1.getText().toString()).length()==0){
			Toast.makeText(getApplicationContext(), "Password cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass2=login_edit_pass2.getText().toString();
		if(!pass1.equals(pass2)){
			Toast.makeText(getApplicationContext(), "Confirm Password do not match！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass1=StringUtil.Md5(pass1);
		mobile=StringUtil.replaceBlank(login_edit_key.getText().toString());
		if(  mobile.length()>0&&mobile.length()!=11){
			Toast.makeText(getApplicationContext(), "error number！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void initView() {
		// TODO Auto-generated method stub
		login_linear_signup=(LinearLayout) findViewById(R.id.login_linear_signup);
		login_linear_signup.setOnClickListener(this);
		
		login_linear_delettime=(LinearLayout) findViewById(R.id.login_linear_delettime);
		login_linear_delettime.setOnClickListener(this);
		login_edit_time=(EditText) findViewById(R.id.login_edit_time);
		login_edit_time.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_delettime.setVisibility(View.VISIBLE);
				} else {
					login_linear_delettime.setVisibility(View.GONE);
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
 
		login_linear_deletemail=(LinearLayout) findViewById(R.id.login_linear_deletemail);
		login_linear_deletemail.setOnClickListener(this);
		login_edit_eamil=(EditText) findViewById(R.id.login_edit_eamil);
		login_edit_eamil.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletemail.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemail.setVisibility(View.GONE);
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
		login_linear_deletkey=(LinearLayout) findViewById(R.id.login_linear_deletkey);
		login_linear_deletkey.setOnClickListener(this);
		login_edit_key=(EditText) findViewById(R.id.login_edit_key);
		login_edit_key.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletkey.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletkey.setVisibility(View.GONE);
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
		login_linear_deletpass1=(LinearLayout) findViewById(R.id.login_linear_deletpass1);
		login_linear_deletpass1.setOnClickListener(this);
		login_edit_pass1=(EditText) findViewById(R.id.login_edit_pass1);
		login_edit_pass1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletpass1.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass1.setVisibility(View.GONE);
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
		login_linear_deletpass2=(LinearLayout) findViewById(R.id.login_linear_deletpass2);
		login_linear_deletpass2.setOnClickListener(this);
		login_edit_pass2=(EditText) findViewById(R.id.login_edit_pass2);
		login_edit_pass2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletpass2.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass2.setVisibility(View.GONE);
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
}
