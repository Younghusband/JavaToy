package com.test;
/************************************************
    * Description: 
    *     测试StringBuffer 和 StringBuilder的性能差异
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月2日 下午2:38:33 
**************************************************/
public class TestStringBuffer_StringBuilder {
	 private static final String base = " base string. ";
	 private static final int count = 2000000;

	 public static void stringTest() {
	  long begin, end;
	  begin = System.currentTimeMillis();
	  String test = new String(base);
	  for (int i = 0; i < count/100; i++) {
	   test = test + " add ";
	  }
	  end = System.currentTimeMillis();
	  System.out.println((end - begin)
	    + " millis has elapsed when used String. ");
	 }

	 public static void stringBufferTest() {
	  long begin, end;
	  begin = System.currentTimeMillis();
	  StringBuffer test = new StringBuffer(base);
	  for (int i = 0; i < count; i++) {
	   test = test.append(" add ");
	  }
	  end = System.currentTimeMillis();
	  System.out.println((end - begin)
	    + " millis has elapsed when used StringBuffer. ");
	 }

	 public static void stringBuilderTest() {
	  long begin, end;
	  begin = System.currentTimeMillis();
	  StringBuilder test = new StringBuilder(base);
	  for (int i = 0; i < count; i++) {
	   test = test.append(" add ");
	  }
	  end = System.currentTimeMillis();
	  System.out.println((end - begin)
	    + " millis has elapsed when used StringBuilder. ");
	 }
	
	public static void main(String[] args) {
		stringBuilderTest();
		stringBufferTest();
        stringTest();
	}
}
