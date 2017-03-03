/**
 * 
 * Description: 读取错误提示信息的类，此类可以实现将错误提示中不变部分和变化部分区分开
 * Copyright: Copyright (c) 2008
 * Company:联动优势
 * @author 任水
 * @version 1.0
 * @date Apr 6, 2009
 */
package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;


public class PropertiesUtil {
	private static final String root = "conf/";
	private static final String[] files = {"test1"};
	private static Properties properties = new Properties();
	static{
		load();
	}
	/**
	 * 
	 * description: 加载配置文件
	 */
	private static void load(){
		InputStream in = null;
		for(String file : files){
			String name = root+file + ".properties";		
			try {
				in = new FileInputStream(name);
				properties.load(in);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("加载配置文件" + file + "错误");
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					in = null;
				}
			}
		}	
	}
	/*
	 * 从消息定义文件中取出code所对应的消息
	 */
	public static String getValue(String key){
		return properties.getProperty(key).trim();
	}
	/**
	 * 
	 * description: 从消息定义文件中取出code所对应的消息, args为占位符的实际值
	 * @param code
	 * @param args
	 * @return 消息
	 */
	public static String getValue(String key, Object[] args){
		return MessageFormat.format(getValue(key),args).trim();
	}
	public static void main(String[] args){
	}
}

