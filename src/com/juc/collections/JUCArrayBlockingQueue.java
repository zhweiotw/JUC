package com.juc.collections;

import java.util.concurrent.ArrayBlockingQueue;

public class JUCArrayBlockingQueue {

	private static ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(1
			);
	private static volatile int i =0;
	private static volatile int j=0;
	

	public static void main(String[] args) {
		new Pop("t1").start();
		new Pop("t2").start();
		new Push("p1").start();
		new Push("p2").start();
		new Push("p3").start();
	}

	static class Pop extends Thread {
		public Pop(String name) {
			super(name);
		}

		@Override
		public void run() {

			String str;
			try {

				while (j++ < 500) {
					str = abq.take();
					System.out.println(j + ">>"
							+ Thread.currentThread().getName() + " take: "
							+ str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static class Push extends Thread {
		public Push(String name) {
			super(name);
		}

		@Override
		public void run() {

			while (i++ < 500) {
				try {
					abq.put(Thread.currentThread().getName() + " " + i
							+ "  ~");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
			}
		}

	}


}
