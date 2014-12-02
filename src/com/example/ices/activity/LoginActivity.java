package com.example.ices.activity;
 
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.R;
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
	private TextView tv_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		new TitleMenuUtil(LoginActivity.this, "Sign  In").show();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
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
			Toast.makeText(getApplicationContext(), "login_linear_signUP",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_password:  
			Toast.makeText(getApplicationContext(), "tv_password",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_linear_deletpass:   
			login_edit_pass.setText("");
		break;
		case R.id.login_linear_deletemali:   
			login_edit_email.setText("");
		break;
		case R.id.login_linear_signin:
			Toast.makeText(getApplicationContext(), "login_linear_signin",
					Toast.LENGTH_SHORT).show();
			break;
		default : 
			break;
		}
	}
}
