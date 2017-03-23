package com.test;
/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月22日 下午5:52:38 
**************************************************/
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
public class TestCMD {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("cmd.exe /c ipconfig /all");
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
 
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
