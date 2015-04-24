package com.juc.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallableAndFuture {
	
	static class Mycallable implements Callable<String>{

		@Override
		public String call() throws Exception {
			
			System.out.println(Thread.currentThread().getName()+" running!");
			
			return Thread.currentThread().getName();
		}}
	
	static class MyRunable implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+" running!");
		}
		
	}
public static void main(String[] args) throws InterruptedException, ExecutionException {
	ExecutorService exec=Executors.newSingleThreadExecutor();
	Future<String>	f1=exec.submit(new Mycallable());
	String fr=f1.get();
	System.out.println(fr);
	String fr2=f1.get();
	System.out.println(fr.equals(fr2));
	exec.shutdown();
	FutureTask<String> ft=new FutureTask<String>(new MyRunable(),"123");
	ft.run();
	System.out.println(ft.get());
}	


}

