
public class J4Test {
public static void main(String[] args) {
	
	AThread th1=new AThread("th1");
	
		
		System.out.println(Thread.currentThread().getName()+" start th1");
		th1.start();
		try {
			th1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" wait()");
		
		System.out.println(Thread.currentThread().getName()+" continue()");
		
	

}
}
