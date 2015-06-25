package com.example.ices;
/***
 * 
*    
* 类名称：Config   
* 类描述：   配置文件
* 创建人： ljp 
* 创建时间：2014-12-4 上午11:13:54   
* @version    
*
 */
public class Config {
	public static final String GETCODE = null;
	public static final String FINDPASSWORD = null;
	
	
	
	/**
	 * 图片路径
	 */
 	public static String IMAGE_PATH = "http://icesapp.fudan.edu.cn/";
 	
	/**
	 * 配置路径
	 */
	// public static String PATH = "http://icesapp.fudan.edu.cn/api/"; //  
 	public static String PATH = "http://114.215.149.242:9090/icesapp/api/"; // 
	 
	/**
	 * 图片路径
	 */
//	public static String IMAGE_PATH = "http://192.168.0.250:9090/icesapp/";
	/**
	 * 配置路径
	 */
	//public static String PATH = "http://192.168.0.250:9090/icesapp/api/"; //  
	
	//外网地址
	
	/**
	 * 图片路径
	 */
//	public static String IMAGE_PATH = "http://192.168.0.250:8080/icesapp/";
	/**
	 * 配置路径
	 */
//	public static String PATH = "http://192.168.0.250:8080/icesapp/api/"; // x线上 
	
	
	/**
	 * 注册
	 */
	public static final String PREDETAIL = PATH+"getContent";
	public static String CODE = "0"; // x线上 
	
	/**
	 * 注册
	 */
	public static String FINDPASS =PATH+ "studentModifyPassword"; // x线上 
	public static String SIGNUP =PATH+ "studentRegister"; // x线上 
	public static String LOGIN=PATH+ "studentLogin";
	public static String checkVersion=PATH+"getAPPLatestVersion";
	public static String getEventsCount=PATH+"getEventsCount";
	public static String getActivationCode=PATH+"getActivationCode/";
	public static String studentFindPassword=PATH+"studentFindPassword";
	public static String getContents=PATH+"getContents";
	public static int ROWS=5;
	public static String getEvents=PATH+"getEvents";
	public static String getEvent=PATH+"getEvent";
	public static String joinEvent=PATH+"joinEvent";
	public static String getArounds=PATH+"getArounds";
	public static String getAround=PATH+"getAround";
	//http://192.168.0.250:8080/icesapp/api/readNotification
	public static String readAllNotification=PATH+"readAllNotification";
	public static String getNotifications=PATH+"getNotifications";
	public static String getNotification=PATH+"getNotification";
	public static String getNotificationUnReadCount=PATH+"getNotificationUnReadCount";
	public static final String SAVEINFO = PATH+"studentModification";// 修改个人信息
	public static final String CHANGEPASS = PATH+"studentModifyPassword";// 修改个人信息
	public static final String EXIT = PATH+"studentLoginOut";
	
	
}
