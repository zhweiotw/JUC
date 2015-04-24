package com.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPC {
	
	public static void main(String[] args) {
		Stoge stoge=new Stoge(10);
		Thread th1=new Thread(new Producer(stoge),"p1");
		Thread th2=new Thread(new Producer(stoge),"p2");
		Thread th3=new Thread(new Consumer(stoge),"c1");
		Thread th4=new Thread(new Consumer(stoge),"c2");
		Thread th5=new Thread(new Consumer(stoge),"c3");
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
	}
	

}

class Stoge{
	
	
	private int captitial;
	private Lock lock;
	private Condition condition;
	public Stoge(int captitial){
		this.captitial=captitial;
		this.lock=new ReentrantLock();
		this.condition=lock.newCondition();
	}
	public int getCaptitial() {
		return captitial;
	}
	public void setCaptitial(int captitial) {
		this.captitial = captitial;
	}
	
	public void pop(){
		lock.lock();
		try{
			while(getCaptitial()<1){
				System.out.println(Thread.currentThread().getName()+" con't consumer !");
				condition.await();
			}
			
			setCaptitial(getCaptitial()-1);
			System.out.println(Thread.currentThread().getName()+"consume a product! least: "+getCaptitial());
			
			Thread.sleep(100);
			condition.signalAll();
		}catch(Exception e){}
		finally{lock.unlock();}
	
	}
	public void push(){
		lock.lock();
		try{
			
			while(getCaptitial()>100){
				System.out.println(Thread.currentThread().getName()+" con't product more!");
				condition.await();
			}
			setCaptitial(getCaptitial()+1);
			System.out.println(Thread.currentThread().getName()+"produce a product! least: "+getCaptitial());
			Thread.sleep(100);
			condition.signalAll();
		}catch(Exception e){
			
		}
		finally{lock.unlock();}
	
	}
}


class Producer implements Runnable {
	private Stoge stoge;
	
	public Stoge getStoge() {
		return stoge;
	}

	public void setStoge(Stoge stoge) {
		this.stoge = stoge;
	}

	public Producer(Stoge stoge) {
		super();
		this.stoge = stoge;
	}

	@Override
	public void run() {
		while(true){
			stoge.push();
		}
	}
	
}

class Consumer implements Runnable {
	private Stoge stoge;
	
	public Stoge getStoge() {
		return stoge;
	}

	public void setStoge(Stoge stoge) {
		this.stoge = stoge;
	}

	public Consumer(Stoge stoge) {
		super();
		this.stoge = stoge;
	}

	@Override
	public void run() {
		while(true){
			stoge.pop();
		}
	}
	
}
