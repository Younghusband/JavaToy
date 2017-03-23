package com.learn.thread;
/************************************************
    * Description: 
    *    测试join方法
    *    
    *    在当前线程 调用A线程的join方法， 当前线程会等待A线程执行完毕
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午5:50:46 
**************************************************/
public class TestJoin {
     public static void main(String[] args) {
		System.out.println("主线程"+Thread.currentThread().getName()+"启动。");
		Thread1 t1 = new Thread1("A");
		Thread1 t2 = new Thread1("B");
		Thread1 t3 = new Thread1("C");
		t1.start();
		t2.start();
		t3.start();   //进入就绪状态  不执行
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//试着注释掉3个join() 看区别
		
		System.out.println("主线程"+Thread.currentThread().getName()+"终止。");
	}
}


class Thread1 extends Thread{
	private String name;
	public Thread1(String name){
		super(name);
	}
	
	@Override
	public void run(){
		String name = Thread.currentThread().getName();
		System.out.println("线程"+name+"启动！");
		for(int i=0;i<5;i++){
			System.out.println(name+">>"+i);
			try {
				sleep((int)Math.random()*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("线程"+name+"终止！");
	}
	
	
	
}