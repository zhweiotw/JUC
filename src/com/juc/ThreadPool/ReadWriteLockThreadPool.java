package com.juc.ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReadWriteLockThreadPool {
	private final static int MAX_POOLSIZE=2;
	static ExecutorService exec=Executors.newFixedThreadPool(MAX_POOLSIZE);
	
	static class Text{
		private String context;
		private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
		private ReadLock readlock;
		private WriteLock writelock;
		public Text(String context) {
			super();
			this.context = context;
			readlock=lock.readLock();
			writelock=lock.writeLock();
		}

		public String getContext() {
			return context;
		}

		public void setContext(String context) {
			this.context = context;
		}
		public String read() throws InterruptedException, ExecutionException{
				Runnable r=new ReadProceed(this,readlock);
				exec.execute(r);
				return null;
				
		
		}
		public void write(String newc){
			
				Runnable r=	new WriteProceed(this, newc,writelock);
				exec.execute(r);
			
			
		}
		
	}
	static class ReadProceed implements Runnable{
		private Text t;
		private ReadLock readlock;
		public ReadProceed(Text t,ReadLock readlock){
			this.t=t;
			this.readlock=readlock;
		}
		@Override
		public void run(){
			readlock.lock();
			try {
				
				Thread.sleep(10);
				String result=t.getContext();
				System.out.println(Thread.currentThread().getName()+"get "+result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{readlock.unlock();}
		
		}
		
	}
	static class WriteProceed implements Runnable{
		private Text t;
		private String newc;
		private WriteLock writelock;
		public WriteProceed(Text t,String newc,WriteLock writelock){
			this.t=t;
			this.newc=newc;
			this.writelock=writelock;
		}
		@Override
		public void  run() {
			writelock.lock();
			
			try {
				Thread.sleep(1);
				System.out.println(Thread.currentThread().getName()+"write ##"+newc);
				t.setContext(t.getContext()+newc);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{writelock.unlock();}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Text t=new Text("tt");
		
		t.write(" ## 1");
		t.write(" ## 2");
	
		t.read();t.read();t.read();t.read();t.read();t.read();

	
		t.write(" ## 3");
		t.write(" ## 4");
		t.write(" ## 5");	t.write(" ## 6");t.read();t.read();t.read();t.write(" ## 7");	t.write(" ## 8");t.read();t.read();t.read();t.read();
		
	
		exec.shutdown();
		System.out.println("over");
	}
	
	
	

}
