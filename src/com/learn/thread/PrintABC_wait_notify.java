package com.learn.thread;
/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月15日 上午10:16:25 
**************************************************/
public class PrintABC_wait_notify {
      
	
	
	   public static void main(String[] args) throws InterruptedException {
		   Object a = new Object();     
	        Object b = new Object();     
	        Object c = new Object();     
	        MyThreadPrinter1 pa = new MyThreadPrinter1("A", c, a);     
	        MyThreadPrinter1 pb = new MyThreadPrinter1("B", a, b);     
	        MyThreadPrinter1 pc = new MyThreadPrinter1("C", b, c);     
	             
	            Thread aT = new Thread(pa); 
	            Thread bT = new Thread(pb); 
	            Thread cT = new Thread(pc); 
	            
	            aT.start();  
	        Thread.sleep(100);  //确保按顺序A、B、C执行  
	            bT.start();  
	        Thread.sleep(100);    
	            cT.start();     
	        Thread.sleep(100);  
	        
	        System.out.println("aT:"+aT.isAlive()+" bT:"+bT.isAlive()+" cT:"+cT.isAlive());
	        //-----------如果不终止的话  程序不会结束
	        if(aT.isAlive())
	        	aT.stop();
	        if(bT.isAlive())
	        	bT.stop();
	        if(cT.isAlive())
	        	cT.stop();
	        
	        //并没有关闭
	}
}

class MyThreadPrinter1 implements Runnable{
	private String name;
	private Object prev;
	private Object self;
	
	public MyThreadPrinter1(String name,Object prev,Object self){
		this.name = name;
		this.prev = prev;
		this.self = self;
	}

	@Override
	public void run() {
		int count = 10;
		while(count>0){
			synchronized(prev){
				synchronized(self){
					System.out.println(Thread.currentThread().getName()+"线程打印了："+name);
					count--;
					
					self.notify();   
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
