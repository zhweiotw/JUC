import java.util.concurrent.atomic.AtomicLongArray;


public class ProducerConsumer {
	
	

public static void main(String[] args) {
	Stoge stoge=new Stoge(3);
	Producer p=new Producer(stoge);
	Consumer c=new Consumer(stoge);
	Thread th1=new Thread(new producerRunner(p),"p1");
	Thread th2=new Thread(new producerRunner(p),"p2");
	Thread th3=new Thread(new ConsumerRuner(c),"c1");
	th1.start();
	th2.start();
	th3.start();
}	

}

class Stoge{
	private int amount;

	public Stoge(int amount) {
		super();
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
class Producer {
	private Stoge stoge;

	public Producer(Stoge stoge) {
		super();
		this.stoge = stoge;
	}
	
	public  void push(){
		while(true){
		synchronized(stoge){
		
			
				
				while(stoge.getAmount()>10){
					System.out.println(Thread.currentThread().getName()+":too BIG amount! notify a consumer!least  "+stoge.getAmount());
					try {
						
						stoge.wait();

						
							
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
				stoge.setAmount(stoge.getAmount()+1);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+":produce one; least  "+stoge.getAmount());
				stoge.notifyAll();
				
			}
		}

		
		
	}

	public Stoge getStoge() {
		return stoge;
	}

	public void setStoge(Stoge stoge) {
		this.stoge = stoge;
	}
	
}
class Consumer{
	private Stoge stoge;

	public Consumer(Stoge stoge) {
		super();
		this.stoge = stoge;
	}
	public  void pop(){
		while(true){
		synchronized(stoge){
			
			
			while(stoge.getAmount()<5){
				System.out.println(Thread.currentThread().getName()+":too less amount! notify a producer!least  "+stoge.getAmount());
				try {
					
					stoge.wait();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stoge.setAmount(stoge.getAmount()-1);
			System.out.println(Thread.currentThread().getName()+":consumer one; least  "+stoge.getAmount());
			
			stoge.notifyAll();
			
			}
					
				
			
		}
		
	}
	public Stoge getStoge() {
		return stoge;
	}

	public void setStoge(Stoge stoge) {
		this.stoge = stoge;
	}
	
}

class producerRunner implements Runnable{
	private Producer producer;
	public producerRunner(Producer producer) {
		super();
		this.producer = producer;
	}
	@Override
	public void run() {
		producer.push();
		
	}
	
}

class ConsumerRuner implements Runnable{

	private Consumer consumer;
	public ConsumerRuner(Consumer consumer) {
		super();
		this.consumer = consumer;
	}
	@Override
	public void run() {
		consumer.pop();
	}
	
}

