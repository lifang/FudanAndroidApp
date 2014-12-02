package com.examlpe.ices.util;

 
import com.example.ices.R;
 

import android.app.Activity;
 
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
 
/***
 * 
*    顶部菜单封装
* 类名称：title 参数表示顶部的显示   
* 类描述：   
* 创建人： ljp 
* 创建时间：2014-12-2 下午2:55:32   
* @version    
*
 */
public class TitleMenuUtil {
	private Activity activity;
	private String title;
	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title,tv_back;
	public TitleMenuUtil(Activity activity,String title) {
		this.activity = activity;
		this.title = title;
	}
	public void show(){
		titleback_linear_back = (LinearLayout) activity.findViewById(R.id.titleback_linear_back);
		tv_back = (TextView) activity.findViewById(R.id.tv_back);
		titleback_text_title = (TextView) activity.findViewById(R.id.titleback_text_title);
		titleback_text_title.setText(title);
		tv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}
	public void show(View v){
		titleback_linear_back = (LinearLayout) v.findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) v.findViewById(R.id.titleback_text_title);
		titleback_text_title.setText(title);
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}
}
