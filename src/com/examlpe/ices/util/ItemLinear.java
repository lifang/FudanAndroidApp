package com.examlpe.ices.util;

import com.example.ices.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ItemLinear extends LinearLayout {
	private LinearLayout mylinear;
	public ItemLinear(Context context) {
		super(context);	
		// TODO Auto-generated constructor stub
		 
	}
	 public ItemLinear(Context context, AttributeSet attrs) {    
		 super(context, attrs);    
	       //在构造函数中将Xml中定义的布局解析出来。   
		 mylinear= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.expand_child, null, true);  
	  }    
	    
}
