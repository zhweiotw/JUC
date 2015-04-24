package com.juc.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportjuc {

	public static void main(String[] args) {
		
		
	Thread bThread=new ThreadB("bb");
	Thread mThread=Thread.currentThread();
	
		bThread.start();
		System.out.println(Thread.currentThread().getName()+" currentThread wait before");
		LockSupport.park(mThread);
		System.out.println(Thread.currentThread().getName()+" after wait");
	
	
}
}
class ThreadB extends Thread{
	private String name;

	public ThreadB(String name) {
		super(name);
		this.name = name;
	}

	@Override
	public void run() {
		
			System.out.println (Thread.currentThread().getName()+" notify all");
			//LockSupport.unpark(thread);
			//这种方式略蛋疼、、、
			System.out.println(Thread.currentThread().getName()+" after notify");
		
		
	}
	
	
	
	
}