package com.test;

import java.util.Scanner;

public class Main {
      public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int f = scan.nextInt();
		double c = ((f-32)*5)/9;
		System.out.println((int)c);
	}
}
