package com.example.ices.activity;

import android.os.Bundle;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.R;

/**
 * 
*    
* �����ƣ�About   
* ��������   ��������
* �����ˣ� ljp 
* ����ʱ�䣺2014-12-8 ����11:02:56   
* @version    
*
 */
public class About extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		new TitleMenuUtil(About.this, "About US").show();
	}
}
