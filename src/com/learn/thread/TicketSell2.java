package com.learn.thread;
/************************************************
    * Description:  
    * 
    *  实现Runnable接口来创建线程
    *  
    *  实现Runable除了避免单继承的局限外，还有个好处就是能实现不同进程资源共享，而继承Thread没有这个功能。
    *  并不是这个特点是两者的绝对区别，继承Runable你可以选择不共享。
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午3:12:32 
**************************************************/
public class TicketSell2 {
	 public static void main(String[] args) {
            MyThread2 t1 = new MyThread2("t1");   
            MyThread2 t2 = new MyThread2("t2");   
		 
			Thread thread1 = new Thread(t1);
//			Thread thread2 = new Thread(t1);   // 多个线程的资源共享
			Thread thread2 = new Thread(t2);
			
			thread1.start();
			thread2.start();
			
		}
	}


	class MyThread2 implements Runnable{
		private /*static*/ int ticket = 10;
		private String name;
		public MyThread2(String name){
			this.name = name;
			System.out.println("创建了"+name+" 售票站。");
		}
		
		@Override
		public void run(){
			for(int i=0;i<500;i++){
				System.out.println(this.name+"售出了"+ticket--+"号票");
				if(ticket==0) break;
			}
		}
		
		
}
