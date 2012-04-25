package opg11x4;

public class SecondExample {
	
	public static void main(String[] args) {
		
		Thread t = new Thread() {
			@Override
			public void run() {
				System.out.println( "startet" );
				this.interrupt();
				try{
					Thread.sleep( 1000 );
				}
				catch( InterruptedException e ){
					System.out.println( "forstyrret" );
				}
				System.out.println( "færdig" );
			}
		};
		
		// Run the thread
		t.start();
	}
	
}
