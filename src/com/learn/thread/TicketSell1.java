package com.learn.thread;
/************************************************
    * Description: 
    * 
    * 继承Thread来创建线程
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午2:57:52 
**************************************************/
public class TicketSell1 {
	
	
    public static void main(String[] args) {
		MyThread1 t1 = new MyThread1("T1号");
		MyThread1 t2 = new MyThread1("T2号");
		
		t2.start();
		t1.start();
	}
}


class MyThread1 extends Thread{
	private /*static*/ int ticket = 10;
	public MyThread1(String name){
		super(name);  
		System.out.println("创建了"+name+" 售票站。");
	}
	
	@Override
	public void run(){
		for(int i=0;i<500;i++){
			System.out.println(getName()+"售出了"+ticket--+"号票");
			if(ticket==0) break;
		}
	}
	
	
}