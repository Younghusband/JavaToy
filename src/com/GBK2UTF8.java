package com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/************************************************
    * Description: 
    *    批量处理文件  将gbk编码的java文件批量转换为UTF-8文件      #使用commons-io-1.4.jar
    *    
    *    对文件进行判断  如果已经是utf-8的话 不进行转换 直接保留
    *    
    *    经测试  转换的方法是有效的  可以将gbk编码的文件确实转换为UTF-8的文件 ,但是校验的方法始终只能得到gbk(也就是默认)
    *    
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年1月22日 上午10:47:33 
**************************************************/
public class GBK2UTF8 {
	
	public static void main(String[] args) throws Exception {
//		 System.out.println(s1);
		 
		 String src = "E:/workspace/client/src/com/https";
		 String target = "E:/temp/httpsUtil";
		 
		 transfer(src,target);
	}
    
    
    public static void transfer(String srcDirPath ,String utf8DirPath) throws IOException{
    	
    	//获取所有java文件 
    	Collection<File> javaGbkFileCol =  FileUtils.listFiles(new File(srcDirPath), new String[]{"java"}, true);
    	for (File javaGbkFile : javaGbkFileCol) { 
    	      //UTF8格式文件路径 
    	      String utf8FilePath = utf8DirPath+javaGbkFile.getAbsolutePath().substring(srcDirPath.length()); 
    	       //使用GBK读取数据，然后用UTF-8写入数据 
    	      FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));        
    	}
    	
    }
    
    public static String judgeIfUTF8(String path) throws Exception{
    	 BufferedInputStream bin = new BufferedInputStream(new FileInputStream(path));
         int p = (bin.read() << 8) + bin.read();       
         String code = null;     
         switch (p) {    
             case 0xefbb:    
                 code = "UTF-8";    
                 break;    
             case 0xfffe:    
                 code = "Unicode";    
                 break;    
             case 0xfeff:    
                 code = "UTF-16BE";    
                 break;    
             default:    
                 code = "GBK";    
         }    
         return code;  
    }
    
    
}
