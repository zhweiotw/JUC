package com.juc.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorPoolSize {

	static class MyCallable implements Callable<String>{

		@Override
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName()+" running!");
			return Thread.currentThread().getName()+" result!";
			
		}
		
	} 
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec=Executors.newFixedThreadPool(5);
		int i=0;
		Long curtime=System.currentTimeMillis();
		List list=new ArrayList<Callable>();
		while(i++<6){
			list.clear();
			System.out.println("put thread"+i+"to threadpool");
			list.add(new MyCallable());
			Thread.sleep(2000);
			List<Future<String>> fl=exec.invokeAll(list);
			for(Future f:fl){
				System.out.println(f.get());
			}
		}
		Long aftTime=System.currentTimeMillis();

		System.out.println(aftTime-curtime);
		exec.shutdown();
	}
}
