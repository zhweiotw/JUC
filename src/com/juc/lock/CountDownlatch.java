package com.juc.lock;

import java.util.concurrent.CountDownLatch;

public class CountDownlatch {
	private final static int MAX_COUNTDOWN=5;
	static CountDownLatch latch;
	public static void main(String[] args) {
		try{
			latch=new CountDownLatch(MAX_COUNTDOWN);
			for(int i=0;i<5;i++){
				System.out.println(i+"     starting ");
				new testLatch(i+"  ").start();
			}
			latch.await();
			System.out.println(Thread.currentThread().getName()+"runing over...");
		}
		catch(Exception e){}
	}	
	
	
	static class testLatch extends Thread{
		private String name;
		
		public testLatch(String name) {
			super(name);
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+" runing ...");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" over ..");
			latch.countDown();
		
		}
		
		
	}
	
	
}



