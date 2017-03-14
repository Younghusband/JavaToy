package com.learn.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/************************************************
    * Description: 
    * 
    * 测试Callable接口
    * 
    * 可返回值的任务必须实现Callable接口。类似的，无返回值的任务必须实现Runnable接口。
    * 
    * 这种创建线程的方式好麻烦啊，都不知道为什么要这样
    * 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午3:48:43 
**************************************************/
public class MyCall {
     public static void main(String[] args) throws Exception {
		Callable<Integer> oneCall = new MyCaller<Integer>("call1");
		
		FutureTask<Integer> oneTask = new FutureTask<Integer>(oneCall);
		
		Thread t1 = new Thread(oneTask);
		t1.start();
	}
}

class MyCaller<Integer> implements Callable<Integer>{
    private Integer i ;
    private String name;
    public MyCaller(String name){
    	this.name = name;
    }
	@Override
	public Integer call() throws Exception {
		System.out.println(this.name+"被执行了");	
		return i;
	}
	
}
