package com.example.ices.activity;
 
import android.content.Intent;
import android.os.Bundle;

import com.example.ices.BaseActivity;
import com.example.ices.R;

public class Welcome extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		Intent i =new Intent(Welcome.this,LoginActivity.class);
		startActivity(i);
	}
}
