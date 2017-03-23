package com.learn.thread;
/************************************************
    * Description: 
    *   测试yield() 方法
    *   
    *   Thread.yield()方法作用是：暂停当前正在执行的线程对象，并执行其他线程。
    *   
    *    yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。
    *    因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。
    *    但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。
    *    
    *    所以，说了这么多。。。 为什么还要用yield...
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午6:05:59 
**************************************************/
public class TestYield {
       public static void main(String[] args) {
		Thread2 t1 = new Thread2("张三");
		Thread2 t2 = new Thread2("李四");
		
		t1.start();
		t2.start();
	}
}

class Thread2 extends Thread{
	public Thread2(String name){
		super(name);
	}
	
	@Override
	public void run(){
		System.out.println(">>>"+getName()+"启动。");
		for(int i=1;i<=40;i++){
			System.out.println(">>>"+getName()+"  >>>"+i);
			if(i==30)// 当i为30时，该线程就会把CPU时间让掉，让其他或者 自己的 线程执行（也就是谁先抢到谁执行） 
				yield();
		}
		
	}
	
}