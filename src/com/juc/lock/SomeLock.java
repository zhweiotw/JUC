package com.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class SomeLock {
	
	//#	自旋锁 lock就是锁住当前线程和放掉当前线程、、、
	private AtomicReference<Thread> own=new AtomicReference<Thread>();
	public void  lock(){
		Thread currentThread =Thread.currentThread();
		while(own.compareAndSet(null, currentThread)){}
	}
	public void unlock(){
		Thread currentThread=Thread.currentThread();
		while(own.compareAndSet(currentThread, null));
	}

}


class TiketLock{
	//排队锁、、、每次lock的时候，获得一个排队号，然后轮询，到了自己上
	//释放锁，只有当前服务锁和当前线程的票号一致时，才能将服务置为下一个、、、
	private AtomicInteger ticketNum=new AtomicInteger();
	private AtomicInteger serverNum=new AtomicInteger();
	
	public int lock(){
		
		int threadTicket=ticketNum.getAndIncrement();
		//轮询、、、、其实就是空循环。。。
		while(ticketNum.get()!=serverNum.get()){}
		
		return threadTicket;
		
	}
	
	public void unlock(int threadTicket){
		int next=threadTicket+1;
		serverNum.compareAndSet(threadTicket, next);
	}

}
//发明让人的简称、、、
class CLHLock {
    public static class CLHNode {
        private boolean isLocked = true; // 默认是在等待锁
    }

    @SuppressWarnings("unused" )
    private volatile CLHNode tail ;
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater
                  . newUpdater(CLHLock.class, CLHNode .class , "tail" );

    public void lock(CLHNode currentThread) {
        CLHNode preNode = UPDATER.getAndSet( this, currentThread);
        if(preNode != null) {//已有线程占用了锁，进入自旋
            while(preNode.isLocked ) {
            }
        }
    }

    public void unlock(CLHNode currentThread) {
        // 如果队列里只有当前线程，则释放对当前线程的引用（for GC）。
        if (!UPDATER .compareAndSet(this, currentThread, null)) {
            // 还有后续线程
            currentThread. isLocked = false ;// 改变状态，让后续线程结束自旋
        }
    }
}