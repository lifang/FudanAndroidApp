package com.example.ices.activity;

import android.os.Bundle;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.ices.BaseActivity;
import com.example.ices.R;

/**
 * 
*    
* 类名称：About   
* 类描述：   关于我们
* 创建人： ljp 
* 创建时间：2014-12-8 上午11:02:56   
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
