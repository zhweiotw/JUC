package com.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReadWriteLockTest {

	public static void main(String[] args) {
		Text text=new Text("abcd");
		User user=new User("u1",text);
		User u2=new User("u2",text);
		User u3=new User("u3",text);
		User u4=new User("u4",text);
		user.uWrite();
		u2.uread();
		u3.uread();
		u4.uWrite();
		
	}
	
	

}
class User{
	private String name;
	private Text text;
	public User(String name, Text text) {
		super();
		this.name = name;
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	public void uread(){
		getText().readText(name);
	}
	public void uWrite(){
		getText().writeText(name, name+" noon  ");
	}
	
}
class Text{
	private String content;
	ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
	ReadLock readlock=lock.readLock();
	WriteLock writelock=lock.writeLock();
	public Text(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		System.out.println(Thread.currentThread().getName()+" start Reading Text");
		return content;
	}

	public void setContent(String content) {
		System.out.println(Thread.currentThread().getName()+" start write Text");
		this.content = content;
	}
	public void readText(final String userName){
		

		
		
	new Thread( userName){
				@Override
				public void run() {
					readlock.lock();
				
					try {
						String c=getContent();
						Thread.sleep(1000);
						System.out.println(userName+" get Text"+c);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					readlock.unlock();
				}
			}}.start();
		
		
		
	}
	public void writeText(final String userName,final String mod){
		
	
		
	new Thread( userName){
				@Override
				public void run() {
					writelock.lock();
					System.out.println(userName +" start mod text");
					try {
						Thread.sleep(1);
					
					setContent(content+mod);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					writelock.unlock();
				}
			}}.start();
		
	}
		
	
		
	
	
}