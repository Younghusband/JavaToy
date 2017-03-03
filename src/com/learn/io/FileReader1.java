package com.learn.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.util.PropertiesUtil;

/************************************************
    * Description: 
    * 
    *  030290060120017,20170206,100.22,2.00
       030290060120017,030290060120017001,20170206,20170206,50.22
       030290060120017,030290060120017002,20170206,20170206,50.00
       
                    需求吧是这样子的：  从文件的第二行开始读取  将包括第二行在内的内容按行输出到另一个文件里。
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月3日 下午2:35:50 
**************************************************/
public class FileReader1 {
	
	private static String input = PropertiesUtil.getValue("FileReader1.INPUTPATH");
	private static String output = PropertiesUtil.getValue("FileReader1.OUTPUTPATH");
	private static String merid = PropertiesUtil.getValue("FileReader1.MERID");
	private static String fileHead = PropertiesUtil.getValue("FileReader1.HEAD");
	private static String bankId = PropertiesUtil.getValue("FileReader1.bankId");
	
	
	public static void main(String[] args) throws IOException {
		String s = PropertiesUtil.getValue("FileReader1.INPUTPATH");
		System.out.println(s);
		copyAndHandleFile("20170303");
	}
	
	public static void copyAndHandleFile(String collateDate){
		InputStream in = null;
		OutputStream out = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String readfilename = fileHead+"_"+merid+"_"+collateDate;
		String writefilename = bankId+"-"+collateDate;
		File inputfile = new File(input+File.separator+readfilename+".dat");
		try {
			in = new FileInputStream(inputfile);
			File outputfile = new File(output+File.separator+writefilename+".txt");
			out = new FileOutputStream(outputfile);
			br =  new BufferedReader(new InputStreamReader(in));
			bw =  new BufferedWriter(new OutputStreamWriter(out));
			String s = null;
			int count =0;
				while((s=br.readLine())!=null){
					StringBuffer sb = new StringBuffer();
					System.out.println("该行内容"+s);
					if(count>0){
					   String [] strArr = s.split(",");
					   for(int i=0;i<strArr.length;i++){
						   if(i!=strArr.length-1){
						       sb.append(strArr[i]+",");
						   }else{
							   double temp = 0.0;
							   temp = Double.valueOf(strArr[i]);
							   int fen = (int)(temp*100);
							   sb.append(String.valueOf(fen));
						   }
					   }
					   bw.write(sb.toString()+"\n");
					}
					count++;
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.flush();
				br.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("文件操作完毕");
		}
	}

}
