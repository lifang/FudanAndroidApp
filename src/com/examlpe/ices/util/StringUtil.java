package com.examlpe.ices.util;
 

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;



import android.util.Base64;
import android.util.Log;

 
 

/***
 *  对字符串 加密，解析
 * @author Lijinpeng
 *
 * comdo
 */
public class StringUtil {
	
	
	
	public static String timeUtil(String time){
		String t=time;
		System.out.println(time.length());
		if(time.length()>16){
			t=time.substring(0, 16);
		}
		t=t.replace("-", "/");
		return t;
	}
	
	public static String mobileUtil(String time){
		String t=time;
		System.out.println(time.length());
		if(time.length()==11){
			t=time.substring(0,3)+"*****"+time.substring(7, 11);
		}
		 
		return t;
	}
	
	
	/**
	 *   密码加密  JDK 弃用
	 * @param pass 密码字符串
	 * @param key  密钥
	 * @return  密码密文
	 * @throws Exception
	 */
		public static String psssword(String pass,String key) throws  Exception {
 			String password="";
//			DESedeKeySpec dks=new DESedeKeySpec(key.getBytes("UTF-8"));
//			SecretKeyFactory keyfac=SecretKeyFactory.getInstance("DESede");
//			SecretKey sk=keyfac.generateSecret(dks);
//			
//			Cipher cipher =Cipher.getInstance("DESede/ECB/PKCS5Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, sk);
//			byte[] b =cipher.doFinal(pass.getBytes());
//		 	 BASE64Encoder encoder =new BASE64Encoder();
//			 
//			password=encoder.encode(b).
//					replaceAll("\r", "").replaceAll("\n", "");
//			System.out.println(" password---"+password);
			return password;
		}
	    // 去除所有空格
	    public static String replaceBlank(String str) {
	        String dest = "";
	        if (str != null) {
	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
		/**
		 * 密码加密
		 * @param src
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static String encryptThreeDESECB(String src, String key)
	            throws Exception {
	        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	        SecretKey securekey = keyFactory.generateSecret(dks);

	        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, securekey);
	        byte[] b = cipher.doFinal(src.getBytes());
	        
			Log.e("code",  android.util.Base64.encodeToString(b, Base64.DEFAULT).replaceAll("\r", "").replaceAll("\n", ""));
	        System.out.println("code```"+android.util.Base64.encodeToString(b, Base64.DEFAULT).replaceAll("\r", "").replaceAll("\n", ""));
	        return android.util.Base64.encodeToString(b, Base64.DEFAULT).replaceAll("\r", "").replaceAll("\n", "");
	    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***
		 *  参数加密
		 * @param paramValues
		 * @param secret
		 * @return  加密sign
		 */
		public static String sign(Map<String, String> paramValues, String secret) {
	        StringBuilder sign = new StringBuilder();
	        try {
	            byte[] sha1Digest = null;
	            StringBuilder sb = new StringBuilder();
	            List<String> paramNames = new ArrayList<String>(paramValues.size());
	            paramNames.addAll(paramValues.keySet());
	            Collections.sort(paramNames);
	            sb.append(secret);
	            for (String paramName : paramNames) {
	                sb.append(paramName).append(paramValues.get(paramName));
	            }
	            sb.append(secret);
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	            sha1Digest = md.digest(sb.toString().getBytes("UTF-8"));            
	            for (int i = 0; i < sha1Digest.length; i++) {
	                String hex = Integer.toHexString(sha1Digest[i] & 0xFF);
	                if (hex.length() == 1) {
	                    sign.append("0");
	                }
	                sign.append(hex.toUpperCase());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	       
	        Log.e("sign", sign.toString());
	        return sign.toString();
	    }
}
