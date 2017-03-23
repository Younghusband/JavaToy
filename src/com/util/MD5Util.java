package com.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/************************************************
    * Description: 
    * 
    *   尝试在这里用MD5加密敏感信息
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月22日 下午4:00:32 
**************************************************/
public class MD5Util {
	/**
	 * 拼装后的字符串调用MD5加密并返回加密后的字符串 
	 * 
	 * */
	public static String returnMD5(String input){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes("UTF-8"));
			byte [] b = md.digest();
			int i;
			StringBuffer buf = new StringBuffer();
			for(int j=0;j<b.length;j++){
				i=b[j];
				if(i<0){
					i+=256;
				}
				if(i<16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}catch(UnsupportedEncodingException e){
			return null;
		}
	}
}
