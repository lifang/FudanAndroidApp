package com.example.ices;
/***
 * 
*    
* �����ƣ�Config   
* ��������   �����ļ�
* �����ˣ� ljp 
* ����ʱ�䣺2014-12-4 ����11:13:54   
* @version    
*
 */
public class Config {
	public static final String GETCODE = null;
	public static final String FINDPASSWORD = null;
	
	
	
	/**
	 * ͼƬ·��
	 */
 	public static String IMAGE_PATH = "http://icesapp.fudan.edu.cn/";
 	
	/**
	 * ����·��
	 */
	// public static String PATH = "http://icesapp.fudan.edu.cn/api/"; //  
 	public static String PATH = "http://114.215.149.242:9090/icesapp/api/"; // 
	 
	/**
	 * ͼƬ·��
	 */
//	public static String IMAGE_PATH = "http://192.168.0.250:9090/icesapp/";
	/**
	 * ����·��
	 */
	//public static String PATH = "http://192.168.0.250:9090/icesapp/api/"; //  
	
	//������ַ
	
	/**
	 * ͼƬ·��
	 */
//	public static String IMAGE_PATH = "http://192.168.0.250:8080/icesapp/";
	/**
	 * ����·��
	 */
//	public static String PATH = "http://192.168.0.250:8080/icesapp/api/"; // x���� 
	
	
	/**
	 * ע��
	 */
	public static final String PREDETAIL = PATH+"getContent";
	public static String CODE = "0"; // x���� 
	
	/**
	 * ע��
	 */
	public static String FINDPASS =PATH+ "studentModifyPassword"; // x���� 
	public static String SIGNUP =PATH+ "studentRegister"; // x���� 
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
	public static final String SAVEINFO = PATH+"studentModification";// �޸ĸ�����Ϣ
	public static final String CHANGEPASS = PATH+"studentModifyPassword";// �޸ĸ�����Ϣ
	public static final String EXIT = PATH+"studentLoginOut";
	
	
}
