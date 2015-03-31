package com.example.ices.activity;
 
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import com.example.ices.BaseActivity;
import com.example.ices.R;
 
 
public class Welcome extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
	 
 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isNetworkConnected(Welcome.this)){
			Intent i =new Intent(Welcome.this,LoginActivity.class);
			startActivity(i);
			finish();
		}else{
			AlertDialog.Builder builder = new Builder(Welcome.this);
			builder.setMessage("网络连接断开，请先启用您的网络");
			builder.setTitle("提示");
			builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_SETTINGS);
					startActivityForResult(intent, 0);
					finish();
				}
			});
			builder.setNegativeButton("退出软件", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			});
			builder.setCancelable(false);
			builder.create().show();
		}
	}
	
	public   boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
