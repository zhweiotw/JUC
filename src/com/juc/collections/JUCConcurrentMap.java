package com.juc.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class JUCConcurrentMap {

	static Map<String,String> map=new HashMap<String, String>();
	public static void main(String[] args) {
		new MyThread("t1").start();
		new MyThread("t2").start();
	
		
		
	}
	private static void printAll(){
		String key=null;
		String value=null;
		Iterator mit=map.entrySet().iterator();
		while(mit.hasNext()){
			Entry<String, String> entry=(Entry<String, String>) mit.next();
			key=entry.getKey();
			value=entry.getValue();
			System.out.print(Thread.currentThread().getName()+"  "+key+" : "+value+",");
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
			String ke=i+Thread.currentThread().getName();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(ke,i+"v" );
			printAll();
		}
		}
		
		
	}
		
}
