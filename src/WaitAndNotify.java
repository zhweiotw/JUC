
public class WaitAndNotify {
	
	public static void main(String[] args) {
		Thread th1=new ThreadA("test");
		synchronized (th1) {
			th1.start();
			System.out.println(Thread.currentThread().getName()+" is running and wait soon");
			try {
				th1.wait();
				System.out.println(Thread.currentThread().getName()+"after wait running...'");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class ThreadA extends Thread{
	private String name;

	public ThreadA(String name) {
		super(name);
		this.name = name;
	}

	@Override
	public void run() {
		synchronized (this) {
			System.out.println(Thread.currentThread().getName()+" exec notiyAll!");
			notifyAll();
			System.out.println(Thread.currentThread().getName()+" After notify exec ");
			
		}
	}
}