package com.juc.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JUCCollectionArrayList {
	 // TODO: list是ArrayList对象时，程序会出错。
    private static List<String> list = new ArrayList<String>();
    //private static List<String> list = new CopyOnWriteArrayList<String>();
    public static void main(String[] args) {
    
        // 同时启动两个线程对list进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value = null;
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
                int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+"-"+i;
                list.add(val);
                // 通过“Iterator”遍历List。
                printAll();
            }
        }
    }

}
/*class Aqueue{
	private  CopyOnWriteArrayList<String> cowArrayList=new CopyOnWriteArrayList<String>();
	public void put(String str){
		cowArrayList.add(str);
	}
	public String get(){
		StringBuffer sb=new StringBuffer();
		for(String str:cowArrayList){
			sb.append(str);
		}
		return sb.toString();
	}
} 
class putRun extends Thread{
	private Aqueue aq;
	private String name;
	public putRun(Aqueue aq,String name) {
		super(name);
		this.aq = aq;
		this.name=name;
	}

	@Override
	public void run() {
			
			int i=0;
			while(i++<6){
				aq.put(Thread.currentThread().getName()+" ## "+i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			
	}
	
	
}
class getRun extends Thread{
	private Aqueue aq;
	private String name;
	public getRun(Aqueue aq,String name) {
		super(name);
		this.name=name;
		this.aq = aq;
	}

	@Override
	public void run() {
	System.out.println(Thread.currentThread().getName()+"get info: "+aq.get());
	}
	
	
}*/