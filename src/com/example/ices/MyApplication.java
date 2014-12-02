package com.example.ices;

 
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.example.ices.entity.User;
import android.app.Activity;
import android.app.Application;
 

public class MyApplication extends Application{
	private static MyApplication  mInstance=null;
	//private ArrayList<Order> orderList = new ArrayList<Order>();
	
	
	/**
	 * 存储当前用户对象信息,需在welcome初始化用户信息
	 */
	public static User currentUser = new User();
	//运用list来保存们每一个activity是关键   
    private List<Activity> mList = new LinkedList<Activity>();   
 // add Activity     
    public void addActivity(Activity activity) {    
        mList.add(activity);    
    }    
    //关闭每一个list内的activity   
    public void exit() {    
        try {    
            for (Activity activity:mList) {    
                if (activity != null)    
                    activity.finish();    
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        } 
        
    }  
 

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;		 
	}
	

	public static MyApplication getInstance() {
		return mInstance;
	}
	
 
}
