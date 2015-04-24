class sysRunable implements Runnable{

	public void run(){
	
		synchronized(this){
			try{
			
			for(int i=0;i<5;i++){
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName()+" loop "+i);
			}
				
			}catch(Exception e)
			{

			}
			
		}

	}
	
	public static void main(String[] args) {
		
	}
	
}