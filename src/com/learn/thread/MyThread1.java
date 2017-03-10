package com.learn.thread;
/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月9日 下午6:11:55 
**************************************************/
public class MyThread1 extends Thread{
	private String name;
	private static int i= 0;
	public MyThread1(String name){ this.name = name;}
	public String getName1(){
		return this.name;
	}

	public static void main(String[] args) {
		MyThread1 t1 = new MyThread1("Thread1");
		MyThread1 t2 = new MyThread1("Thread2");
		MyThread1 t3 = new MyThread1("Thread3");
		
		
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	@Override
	public void run(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		System.out.println(getName1()+" int="+i);
	}
	
}
