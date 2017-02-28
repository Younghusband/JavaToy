package com;

import java.util.Scanner;

/************************************************
    * Description: 
    * @author    Vermouth.yf  
    * @version  1.0
    * @date ：2017年2月22日 下午5:28:29 
**************************************************/
public class TestMaoPao {
     public static void main(String[] args) {
    	 Input input = new Input(6);
    	 int [] arr = input.getNumArr();
    	 System.out.println("排序前的数组");
    	 input.print(arr);
    	 //---------开始排序
    	 input.maopao(arr);
    	 input.print(arr);
    	 
	}
}

class Input {
     private Scanner scan;
     private int [] arr;
     public Input(int count){
    	 System.out.println("请输入"+count+"个数字。每个数字以回车隔开");
    	 this.arr = new int [count];
    	 scan = new Scanner(System.in);
    	 for(int i =0;i<arr.length;i++){
    		 arr[i] = scan.nextInt();
    	 }
    	 System.out.println("输入完毕");
     }
     
	 public int[] getNumArr(){
		 	return this.arr;
	 }
	 
	 public void print(int[] arr){
		 for(int i:arr) System.out.print(i+" ");
    	 System.out.println();
	 }
	 
	 //升序 每次最外轮循环将最小值放在首位
	 public int[] maopao(int [] arr){
		 for(int i=0;i<arr.length-1;i++){
			 for(int j=0;j<arr.length-1-i;j++){
				 if(arr[j]>arr[j+1]){
					 int temp = arr[j];
					 arr[j] = arr[j+1];
					 arr[j+1] = temp;
				 }
				 
			 }
		 }
		 System.out.println("排序后的数组如下↓");
		 return arr;
	 }
	
}