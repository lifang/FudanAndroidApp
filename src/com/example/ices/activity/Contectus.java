package com.example.ices.activity;

import android.os.Bundle;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.R;
/**
 * 
*    
* 类名称：Contectus   
* 类描述：   联系我们
* 创建人： ljp 
* 创建时间：2014-12-8 上午11:07:55   
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
