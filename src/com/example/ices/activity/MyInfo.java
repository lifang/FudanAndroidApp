package com.example.ices.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.LoadingDialog;
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
* 类名称：MyInfo   
* 类描述：  改变个人信息
* 创建人： ljp 
* 创建时间：2014-12-8 上午10:09:25   
* @version    
*
 */
public class MyInfo extends BaseActivity{
	private LinearLayout login_linear_delettel,login_linear_deletemail,login_linear_deletcode;
	private EditText login_edit_tel,login_edit_email,login_edit_code;
	private TextView next_sure;
    private Dialog loadingDialog;
	private String url,studentMobilePhone,studentEmail,activation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		new TitleMenuUtil(MyInfo.this, "Contact Information").show();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		url=Config.SAVEINFO;
		next_sure=(TextView) findViewById(R.id.next_sure);
		next_sure.setOnClickListener(this);
		next_sure.setText("Save");
		next_sure.setVisibility(View.VISIBLE);
		login_linear_delettel=(LinearLayout) findViewById(R.id.login_linear_delettel);
		login_linear_delettel.setOnClickListener(this);
		login_edit_tel=(EditText) findViewById(R.id.login_edit_tel);
		login_edit_tel.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_delettel.setVisibility(View.VISIBLE);
				} else {
					login_linear_delettel.setVisibility(View.GONE);
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
		
		// login_edit_email login_linear_deletemail
		login_linear_deletemail=(LinearLayout) findViewById(R.id.login_linear_deletemail);
		login_linear_deletemail.setOnClickListener(this);
		login_edit_email=(EditText) findViewById(R.id.login_edit_email);
		login_edit_email.addTextChangedListener(new TextWatcher() {
			
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
		
		
		// login_edit_code  login_linear_deletcode
		login_linear_deletcode=(LinearLayout) findViewById(R.id.login_linear_deletcode);
		login_linear_deletcode.setOnClickListener(this);
		login_edit_code=(EditText) findViewById(R.id.login_edit_code);
		login_edit_code.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletcode.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletcode.setVisibility(View.GONE);
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
		case R.id.login_linear_delettel:
			login_edit_tel.setText("");
			break;
		case R.id.login_linear_deletemail:  //login_linear_deletemail
			login_edit_email.setText("");
			break;
		case R.id.login_linear_deletcode:  //login_linear_deletemail
			login_edit_code.setText("");
			break;
		case R.id.next_sure:  
			if(check()){
				save();
			}		 
		break;
		default : 
			break;
		}
	}
	private void save() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
	//	AsyncHttpClient client = new AsyncHttpClient(); //  
		RequestParams params = new RequestParams();
		
		params.put("activationCode",activation);
		params.put("studentEmail",studentEmail);
		params.put("studentMobilePhone",studentMobilePhone);
		params.put("id", MyApplication.currentUser.getStudentId());
		params.put("token",MyApplication.getToken());
		params.setUseJsonStreamer(true);
		System.out.println("---"+studentMobilePhone+MyApplication.currentUser.getStudentId()+MyApplication.getToken());
		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);			
				Gson gson = new Gson();
				
				JSONObject jsonobject = null;
				int  code = 0;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getInt("code");
					if(code==0){ //判断返回结果是否合法
//						User current = gson.fromJson(jsonobject.getString("studentInfo"), new TypeToken<User>() {
//	 					}.getType());
//	 					 MyApplication.currentUser = current;
//	 					 MyApplication.setToken(jsonobject.getString("token"));
//	 					 Intent i =new Intent(LoginActivity.this,MainActivity.class);
//	 					 startActivity(i);
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"), 1000).show();
	 					 finish();
					}else{
					 
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"), 1000).show();
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
	private boolean check() {
		// TODO Auto-generated method stub
		
	//	params.put("activation",activation);
	//	params.put("studentEmail",studentEmail);
	//	params.put("studentMobilePhone",studentMobilePhone);
		studentMobilePhone=StringUtil.replaceBlank(login_edit_tel.getText().toString());
		if(studentMobilePhone.length()==0){
			Toast.makeText(getApplicationContext(), "studentMobilePhone cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		studentEmail=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(studentEmail.length()==0){
			Toast.makeText(getApplicationContext(), "studentEmail cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		activation=StringUtil.replaceBlank(login_edit_code.getText().toString());
		if(activation.length()==0){
			Toast.makeText(getApplicationContext(), "activation cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
 
	 
		return true;
	}
}
