package com;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 上午10:00:47 
**************************************************/
public class TestProxy {
	
	static{
		// 设置系统证书库参数
		System.setProperty("javax.net.ssl.keyStore", "conf/client.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		// 设置系统信任证书库
		System.setProperty("javax.net.ssl.trustStore", "conf/client.truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	}
	
	
    public static void main(String[] args) throws Exception {
    	String sendStr = "";
		sendStr = sendStr.concat("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sendStr = sendStr.concat("<ROOT>");
		sendStr = sendStr.concat("<stdprocode>0281</stdprocode>");
		sendStr = sendStr.concat("<custcode>90060120012</custcode>");
		sendStr = sendStr.concat("<merchantId>011690060120012</merchantId>");
		sendStr = sendStr.concat("<posid>123456789</posid>");
		sendStr = sendStr.concat("<sdid>10000070</sdid>");
		sendStr = sendStr.concat("<custid>011690047840039</custid>");
		sendStr = sendStr.concat("<custnm>中信52118295</custnm>");
		sendStr = sendStr.concat("<custacctnum>6226981600504118</custacctnum>");
		sendStr = sendStr.concat("<certtype>0</certtype>");
		sendStr = sendStr.concat("<certnum>43073019800928201X</certnum>");
		sendStr = sendStr.concat("<moblnum>22222222222</moblnum>");
		sendStr = sendStr.concat("<custpswd>88532f8dd101904a</custpswd>");
		sendStr = sendStr.concat("<stdtrack2></stdtrack2>");
		sendStr = sendStr.concat("<stdtrack3></stdtrack3>");
		sendStr = sendStr.concat("<opendt>20160314</opendt>");
		sendStr = sendStr.concat("<txdate>20160314</txdate>");
		sendStr = sendStr.concat("<txtime>112020</txtime>");
		sendStr = sendStr.concat("<remark1></remark1>");
		sendStr = sendStr.concat("<remark2></remark2>");
		sendStr = sendStr.concat("<remark3></remark3>");
		sendStr = sendStr.concat("</ROOT>");
		
		doRequest(3000,6000,"https://11.29.8.164:10030",sendStr.getBytes("GBK"));
	}
    
    
    public static byte[] doRequest(int connectTimeout,int readTimeout, String queryUrl ,byte [] sendData) throws Exception {
		
 	   URL url = new URL(queryUrl);
 		HostnameVerifier hv = new HostnameVerifier()
 		{
 			public boolean verify(String urlHostName, SSLSession session)
 			{
 				return true;
 			}
 		};
 		System.setProperty("java.protocol.handler.pkgs", "sun.net.www.protocol");
 		HttpsURLConnection.setDefaultHostnameVerifier(hv);
 		Date current = new Date(System.currentTimeMillis());
 		System.out.println("begint to open connection at " + current);
 		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
 		Date end = new Date(System.currentTimeMillis());
 		System.out.println("open connection ok at " + end + ",cost:" + (end.getTime() - current.getTime()));
 		connection.setRequestProperty("Content-Type", "text/xml");
 		connection.setDoOutput(true);
 		connection.setDoInput(true);
 		connection.setRequestMethod("POST");
 		connection.setUseCaches(false);
 		connection.setReadTimeout(readTimeout);
 		connection.setConnectTimeout(connectTimeout);
 		
 		
 		current = new Date(System.currentTimeMillis());
 		System.out.println("[SSLIX]notifyEai,begint to write data at " + current);
 		OutputStream out = connection.getOutputStream();
 		out.write(sendData);
 		end = new Date(System.currentTimeMillis());
 		System.out.println("write data ok at " + end + ",cost:" + (end.getTime() - current.getTime()));
 		current = new Date(System.currentTimeMillis());
 		System.out.println("begint to read data at " + current);

 		byte[] recvMsg = new byte[1024];
 		byte[] valiMsg = null;
 		int readed = 0;

 		ByteArrayOutputStream baos = new ByteArrayOutputStream();

 		while ((readed = connection.getInputStream().read(recvMsg)) != -1)
 		{
 			baos.write(recvMsg, 0, readed);
 		}

 		baos.flush();
 		valiMsg = baos.toByteArray();
 		System.out.println(valiMsg.length);
 		end = new Date(System.currentTimeMillis());
 		System.out.println("read data ok at " + end + ",cost:" + (end.getTime() - current.getTime()));

 		System.out.println("开始返回状态码");
 		Integer statusCode = connection.getResponseCode();
 		System.out.println("返回状态码:" + statusCode);
 		connection.disconnect();
 		return valiMsg;
 	}
    
    
} 
