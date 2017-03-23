package com;
/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月17日 下午3:13:02 
**************************************************/
public class TestString {
     public static void main(String[] args) {
		String str = "03029006012004731715171252541704";
		String merid = "030290060120047";
		String reqTime = "151712";
		String reqDate = "20170317";
		String temp = str.substring(str.indexOf(reqTime)+reqTime.length());
		System.out.println(temp);
		str = merid.concat(reqDate).concat(reqTime).concat(temp);
		System.out.println(str);
		System.out.println("0302900601200472017031711001010001003".equals("030290060120047201703177031711001010001003"));
		System.out.println(str.indexOf("a"));
	}
}
