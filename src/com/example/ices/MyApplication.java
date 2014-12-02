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
	 * �洢��ǰ�û�������Ϣ,����welcome��ʼ���û���Ϣ
	 */
	public static User currentUser = new User();
	//����list��������ÿһ��activity�ǹؼ�   
    private List<Activity> mList = new LinkedList<Activity>();   
 // add Activity     
    public void addActivity(Activity activity) {    
        mList.add(activity);    
    }    
    //�ر�ÿһ��list�ڵ�activity   
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
