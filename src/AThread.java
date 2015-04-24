
public class AThread extends Thread {

	
		public AThread(String tname){
			super(tname);
		}
		@Override
		public void run(){
		
				
				
				System.out.println(Thread.currentThread().getName()+" nitify()");
				int salt=0;
				for(int i=0;i<200;i++){
					salt+=i;
				}
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				System.out.println(salt);
				
			
		}
}
