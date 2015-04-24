package com.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class SomeLock {
	
	//#	������ lock������ס��ǰ�̺߳ͷŵ���ǰ�̡߳�����
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
	//�Ŷ���������ÿ��lock��ʱ�򣬻��һ���ŶӺţ�Ȼ����ѯ�������Լ���
	//�ͷ�����ֻ�е�ǰ�������͵�ǰ�̵߳�Ʊ��һ��ʱ�����ܽ�������Ϊ��һ��������
	private AtomicInteger ticketNum=new AtomicInteger();
	private AtomicInteger serverNum=new AtomicInteger();
	
	public int lock(){
		
		int threadTicket=ticketNum.getAndIncrement();
		//��ѯ����������ʵ���ǿ�ѭ��������
		while(ticketNum.get()!=serverNum.get()){}
		
		return threadTicket;
		
	}
	
	public void unlock(int threadTicket){
		int next=threadTicket+1;
		serverNum.compareAndSet(threadTicket, next);
	}

}
//�������˵ļ�ơ�����
class CLHLock {
    public static class CLHNode {
        private boolean isLocked = true; // Ĭ�����ڵȴ���
    }

    @SuppressWarnings("unused" )
    private volatile CLHNode tail ;
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater
                  . newUpdater(CLHLock.class, CLHNode .class , "tail" );

    public void lock(CLHNode currentThread) {
        CLHNode preNode = UPDATER.getAndSet( this, currentThread);
        if(preNode != null) {//�����߳�ռ����������������
            while(preNode.isLocked ) {
            }
        }
    }

    public void unlock(CLHNode currentThread) {
        // ���������ֻ�е�ǰ�̣߳����ͷŶԵ�ǰ�̵߳����ã�for GC����
        if (!UPDATER .compareAndSet(this, currentThread, null)) {
            // ���к����߳�
            currentThread. isLocked = false ;// �ı�״̬���ú����߳̽�������
        }
    }
}