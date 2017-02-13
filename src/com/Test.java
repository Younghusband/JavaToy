package com;

import java.util.HashMap;
import java.util.Map;

/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年2月3日 下午5:27:55 
**************************************************/
public class Test {
	
	public static void main(String[] args) {
        String str = "  20161111";
        String str2 = " 111112";
        String str3 = str.trim()+str2.trim();
        System.out.println(str3);        
        
	}
	
	private Map<String,String> str2Map (String str){
        Map<String,String> tree = new HashMap<String,String>();
        String strArr[] = str.split("&");
		for(int i=0;i<strArr.length;i++){
			String temp[] = strArr[i].split("=");// 测试,2的用途
			if(temp.length==2){
				tree.put(temp[0], temp[1]);
			}else
				tree.put(temp[0], ""); 
		}
		return tree;
	}

}
