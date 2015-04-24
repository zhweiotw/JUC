package com.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutilConditions {
	
	public static void main(String[] args) {
		Buffer bb=new Buffer(2);
		Thread th1=bb.new ReadRunner("r1");
		Thread th2=bb.new ReadRunner("r2");
		Thread th3=bb.new ReadRunner("r3");
		Thread th4=bb.new WriteRunner("w1");
		Thread th5=bb.new ReadRunner("r5");
		Thread th6=bb.new ReadRunner("r6");
		Thread th7=bb.new WriteRunner("w2");
		Thread th8=bb.new WriteRunner("w3");
		Thread th9=bb.new WriteRunner("w4");
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
		th6.start();
		th7.start();
		th8.start();
		th9.start();
		
	}

}
class Buffer{
private int len;
final  Lock lock=new ReentrantLock();
final Condition fullCondition=lock.newCondition();
final Condition emptyCondition=lock.newCondition();
public Buffer(int len) {
	super();
	this.len = len;
}
public int getLen() {
	return len;
}
public void setLen(int len) {
	this.len = len;
}
public void write(){
	lock.lock();
	try{
	while(true){
		while(getLen()>10)
			try {
				System.out.println(Thread.currentThread().getName()+" found that buffer is full");
				fullCondition.signal();
				emptyCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		setLen(getLen()+1);
		System.out.println(Thread.currentThread().getName()+" write a byte!   buffer leat:"+ getLen());
	
		
	}}finally{
		lock.unlock();
	}
	
	
	
}
public void   read(){
	lock.lock();
	try{
		
		while(true){
			while(getLen()<1)
				try {
					System.out.println(Thread.currentThread().getName()+" found that buffer is empty");
					emptyCondition.signal();
					fullCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			setLen(getLen()-1);
			System.out.println(Thread.currentThread().getName()+" read 1 byte!   buffer leat:"+ getLen());
		
		
	}}
	
	finally{lock.unlock();}
}

	 class ReadRunner extends Thread{
		private String name;
		
		public ReadRunner(String name) {
			super(name);
			this.name = name;
		}

		@Override
		public void run() {
			read();
		}
		
	}
	 
	 class WriteRunner extends Thread{
		 private String name;
			
			public WriteRunner(String name) {
				super(name);
				this.name = name;
			}

			@Override
			public void run() {
				write();
			}
	 }
	 
}



