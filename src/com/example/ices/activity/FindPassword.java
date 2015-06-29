package com.example.ices.activity;

 
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
 
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ices.entity.Result;
import com.example.ices.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* 类名称：FindPassword   
* 类描述：   找回密码
* 创建人： ljp 
* 创建时间：2014-12-3 上午9:27:57   
* @version    
*
 */
public class FindPassword extends BaseActivity   {
	private TextView tv_code,tv_msg,tv_count,tv_check;
	private EditText login_edit_email,login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletemali,login_linear_deletcode,login_linear_deletpass 
	,login_linear_deletpass2, ll_msg,login_linear_signin;
	private int Countmun=60;
	private Thread myThread;
	private Boolean isRun=true;
	private ImageView img_check,img_check_n;
	public  String vcode="";
	private String url,email,pass;
	private Runnable runnable;
    private Dialog loadingDialog;
    final Handler handler = new Handler(){          // handle  
        public void handleMessage(Message msg){  
            switch (msg.what) {  
            case 1:  
            	if(Countmun==0){
            	  
            		isRun=false;
            		tv_code.setClickable(true);
            		ll_msg.setVisibility(View.INVISIBLE);
            		tv_count.setText("" +60);  
            		tv_code.setText("Sent Code");
            		System.out.println("destroy`"+Countmun);
            	}else{
                 	Countmun--;  
                    tv_count.setText("" + Countmun);  
            		System.out.println("Countmun`D2`"+Countmun);
            	}
    
            }  
            super.handleMessage(msg);  
        }  
    };  
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpass);
		new TitleMenuUtil(FindPassword.this, "Find  Password").show();
		initView();
		url=Config.FINDPASS;
	   runnable = new Runnable() {  
	        @Override  
	        public void run() {  
	        	if(Countmun==0){
	        		ll_msg.setVisibility(View.INVISIBLE);
	        		Countmun=60;
	        		tv_code.setClickable(true);
	    			tv_code.setText("Sent Code");
	        	}else{
	        		
	        		Countmun--;  
		        	tv_count.setText("" + Countmun);  
		            handler.postDelayed(this, 1000);  
	        	}
	         
	        }  
	    };
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// myThread.stop();
		//myThread.destroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch ( v.getId()) {
		case R.id.login_linear_signup:
			Toast.makeText(getApplicationContext(), "login_linear_signUP",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_code:  // 获取验证码tv_check
			tv_check.setVisibility(View.INVISIBLE);
			email=StringUtil.replaceBlank(login_edit_email.getText().toString());
			if(email.length()==0){
				Toast.makeText(getApplicationContext(), "Email cannot be empty！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			
			
		 	tv_code.setClickable(false);
			tv_code.setText("Resent Code");
			getCode();
			break;
		case R.id.tv_check:  // 获取验证码 
			System.out.println("vcode"+vcode);
			 
			if(login_edit_code.getText().toString().equals(vcode)){
				img_check.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
			}else{
				img_check.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
			}
			
			break;
		case R.id.login_linear_signin:  // 获取验证码 
			pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
			System.out.println(pass+"---");
			pass= StringUtil.Md5(pass);
			 System.out.println(pass+"---");
 		if(check()){
 			  sure();
 				 
 			}
			 
			break;
		case R.id.login_linear_deletemali:
			login_edit_email.setText("");
			break;
		case R.id.login_linear_deletcode:
			login_edit_code.setText("");
			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		default : 
			break;
		}
	}
	private boolean check() {
		// TODO Auto-generated method stub
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(email.length()==0){
			Toast.makeText(getApplicationContext(), "Email cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
			Toast.makeText(getApplicationContext(), "vcode cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_code.getText().toString().endsWith(vcode)){
			Toast.makeText(getApplicationContext(), "vcode error！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		
		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(pass.length()==0){
			Toast.makeText(getApplicationContext(), "Password cannot be empty！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_pass2.getText().toString().equals(pass)){
			Toast.makeText(getApplicationContext(), "Password donot match！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass=StringUtil.Md5(pass);
		return true;
	}

	private void sure() {
		// TODO Auto-generated method stub
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
		RequestParams params = new RequestParams();
		params.put("studentPassword",pass);
		params.put("activationCode",vcode); 
		params.put("studentEmail", email); 
		System.out.println(pass+"-md5--");
		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		System.out.println(pass+"--md5-");
		
	 
		
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient().post(Config.studentFindPassword, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
				System.out.println("headers" + headers.toString());	
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
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
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
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				System.out.println("eee" + responseBody.toString());	
			}
		});
	
	}

	/**
	 * 获取验证码
	 */
	private void getCode() {
		loadingDialog = LoadingDialog.getLoadingDialg(this);
		loadingDialog.show();
 		ll_msg.setVisibility(View.VISIBLE);
		tv_count.setText("" +60);  
		tv_code.setText("Resent Code");
	//	myThread= new Thread(new MyThread());
 	//	myThread.start();
		 handler.postDelayed(runnable, 1000);  
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		RequestParams params = new RequestParams();
		params.put("email",email);
		System.out.println("email----"+email);
		params.setUseJsonStreamer(true);
		AsyncHttpClient client =MyApplication.getInstance().getClient();
		client.setTimeout(30000);
		client.post(Config.getActivationCode, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);			
				Gson gson = new Gson();
				JSONObject jsonobject;
			 
			 
				try {
					jsonobject = new JSONObject(responseMsg);
					int code=jsonobject.getInt("code");
					System.out.println("code`1`"+code);
					if(code==-2){
						System.out.println("code`-2`"+code);
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==0){
						tv_check.setVisibility(View.VISIBLE);
						System.out.println("code`0`"+code);
						Result rs = gson.fromJson(jsonobject.getString("result"), new TypeToken<Result>() {
	 					}.getType());
						vcode=rs.getActivationCode();
						System.out.println("vcode"+vcode);
					}else{
						System.out.println("MSG" + "else" );	
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("MSG" + "e````"+e.toString());	
				//	 Toast.makeText(getApplicationContext(), e.toString(), 1000).show();
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
		tv_check=(TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		img_check=(ImageView) findViewById(R.id.img_check);
		img_check_n=(ImageView) findViewById(R.id.img_check_n);
		tv_code=(TextView) findViewById(R.id.tv_code);
		tv_code.setOnClickListener(this);
		tv_count=(TextView) findViewById(R.id.tv_count);
		tv_msg=(TextView) findViewById(R.id.tv_msg);
		ll_msg=(LinearLayout) findViewById(R.id.ll_msg);
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		login_linear_deletemali=(LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
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
		
	//	login_linear_deletpass
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
		//	login_linear_deletpass
		login_linear_deletpass=(LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
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
	   public class MyThread implements Runnable{      // thread  
	        @Override  
	        public void run(){  
	         
	            while(isRun){  
	            	System.out.println("run``"+Countmun);
	   	        
	                try{  
	                	    Thread.sleep(1000);     // sleep 1000ms  
	  	                    Message message = new Message();  
	  	                    message.what = 1;  
	  	                    handler.sendMessage(message);  
	                }catch (Exception e) {  
	               
	            	}
	        	}
	        }  
	    } 
	 
}
