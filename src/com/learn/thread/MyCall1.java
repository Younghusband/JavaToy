package com.learn.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/************************************************
    * Description: 
    * 
    *   使用ExecutorService、Callable、Future实现有返回结果的线程
    *   
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年3月14日 下午4:25:38 
**************************************************/
public class MyCall1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("程序开始运行----");
		Date date1 = new Date(); 
		int poolsize = 5;
		
		//创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(poolsize);
		//创建多个有返回值的任务
		List<Future> list = new ArrayList<Future>();
		for(int i=0;i<poolsize;i++){
			Callable c = new Caller1(i+"号线程");
			//执行任务并获取Future对象
			Future f = pool.submit(c);
			// 
			list.add(f);
		}
    	
		//关闭线程池
		pool.shutdown();
    	for(Future f:list){
    		System.out.println(">>>"+f.get().toString());  //f.get()获得的是call的返回值
    	}
    	
    	Date date2 = new Date();
    	
    	System.out.println("-------程序结束运行---------"+"\n运行时间"+(date2.getTime()-date1.getTime())+"毫秒");
		
	}
    
    
}

class Caller1 implements Callable{
	private String taskname;
	public Caller1(String name){
		this.taskname = name;
	}

	@Override
	public Object call() throws Exception {
		System.out.println(">>>"+taskname+" 程序启动。");
		Date dateTmp1 = new Date();
	    Thread.sleep(1000);	
		Date dateTmp2 = new Date();
		long time = dateTmp2.getTime()-dateTmp1.getTime();
		return taskname+"任务返回运行结果，当前任务时间【"+time+"毫秒】";
	}
	
}