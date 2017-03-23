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
	
	private static String Key = "conf/UMFclientPrivatekey.key";
	private static String myCer = "conf/UMFclientCertificate.cer";
	private static String bankCer = "conf/vpn10030_citic_certificate.cer";
	
    public static void main(String[] args) throws Exception {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<root>");
		sb.append("<head>");
		sb.append("<merchantId>").append("030290060120047").append("</merchantId>");
		sb.append("<requestId>").append("2017031511001003029006012004700000001").append("</requestId>");
		sb.append("<transactionCode>").append("03029138").append("</transactionCode>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<amount>").append("1").append("</amount>");
		sb.append("<orderDate>").append("20170315").append("</orderDate>");
		sb.append("<orderId>").append("086122944010").append("</orderId>");
		sb.append("<period>").append("1").append("</period>");
		sb.append("<periodUnit>").append("02").append("</periodUnit>");
		sb.append("<merchantAbbr>").append("</merchantAbbr>");
		sb.append("<productDesc>").append("</productDesc>");
		sb.append("<productId>").append("</productId>");
		sb.append("<productName>").append("</productName>");
		sb.append("<productNum>").append("</productNum>");
		sb.append("<agrNo>").append("</agrNo>");
		sb.append("<cardName>").append("</cardName>");
		sb.append("<cardNo>").append("</cardNo>");
		sb.append("<bankAbbr>").append("</bankAbbr>");
		sb.append("<idNo>").append("</idNo>");
		sb.append("<mobileNo>").append("</mobileNo>");
		sb.append("<crdUsrProv>").append("</crdUsrProv>");
		sb.append("<crdProvNm>").append("</crdProvNm>");
		sb.append("<crdUsrCity>").append("</crdUsrCity>");
		sb.append("<crdCityNm>").append("</crdCityNm>");
		sb.append("<notifyUrl>").append("</notifyUrl>");
		sb.append("<reserved1>").append("</reserved1>");  
		sb.append("<reserved2>").append("</reserved2>");  //这两个字段到底要不要？
		sb.append("</body>");
		sb.append("</root>");
		String sendStr =sb.toString();
		
		byte [] signedData = OLPdecrypt.sign_crypt(sendStr.getBytes("GBK"), "654321", Key, myCer, bankCer, true, false, true);
		System.out.println(new String(signedData,"GBK"));
		byte[] byteArr =doRequest(3000,6000,"https://11.29.8.164:10030/sft",signedData);
		System.out.println(new String(byteArr,"GBK"));
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
 		out.flush();
 		out.close();
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
