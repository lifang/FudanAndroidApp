package com.example.ices.activity;

import android.os.Bundle;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.R;
/**
 * 
*    
* �����ƣ�Contectus   
* ��������   ��ϵ����
* �����ˣ� ljp 
* ����ʱ�䣺2014-12-8 ����11:07:55   
* @version    
*
 */
public class Contectus extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		new TitleMenuUtil(Contectus.this, "Contect Us").show();
	}
}
