package com;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int temp = scan.nextInt();
		if(temp<11||temp>59)
			throw new RuntimeException("your num is wrong");
		int a = temp%10;   //1-9
		int b = temp/10;   //1-5
		StringBuilder sb = new StringBuilder();
		 switch (a) {
	        case 1:
	     	   sb.append("Faint signals, barely perceptible");
	     	   break;
	        case 2:
	     	   sb.append("Very weak signals");
	     	   break;
	        case 3:
	     	   sb.append("Weak signals");
	     	   break;
	        case 4:
	     	   sb.append("Fair signals");
	     	   break;
	        case 5:
	     	   sb.append("Fairly good signals");
	     	   break;
	        case 6:
	        	sb.append("Good signals");
	        	break;
	        case 7:
	        	sb.append("Moderately strong signals");
	        	break;
	        case 8:
	        	sb.append("Strong signals");
	        	break;
	        case 9:
	        	sb.append("Extremely strong signals");
	        	break;
	        default :
	     	   break;
	     }
		sb.append(", ");
        switch (b) {
           case 1:
        	   sb.append("unreadable");
        	   break;
           case 2:
        	   sb.append("barely readable, occasional words distinguishable");
        	   break;
           case 3:
        	   sb.append("readable with considerable difficulty");
        	   break;
           case 4:
        	   sb.append("readable with practically no difficulty");
        	   break;
           case 5:
        	   sb.append("perfectly readable");
        	   break;
           default :
        	   break;
        }
       System.out.println(sb.append(".").toString());
	}
}
