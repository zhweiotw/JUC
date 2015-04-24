package com.juc.collections;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class JUCArrayBlockingQueueNoBlocking {
	
	private static Queue<String> abq=new ArrayBlockingQueue<String>(20);
	
	public static void main(String[] args) {
		new MyThread("p1").start();
		new MyThread("p2").start();
	}
	private static void printAll(){
		Iterator it=abq.iterator();
		while(it.hasNext()){
			String re=(String) it.next();
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(Thread.currentThread().getName()+"  "+re);
		}
		System.out.println();
	} 
	
	static class MyThread extends Thread{
		public MyThread(String name){
			super(name);
		}
		@Override
		public void run() {
			int i=0;
			while(i++<6){
				abq.add(Thread.currentThread().getName()+" add "+i);
				
				printAll();
			}
		}
		
	}
	
	

}
