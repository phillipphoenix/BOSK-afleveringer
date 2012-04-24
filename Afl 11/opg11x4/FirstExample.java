package opg11x4;

public class FirstExample {

	private static Thread t1;
	
	public static void main(String[] args) {

		t1 = new Thread() {
			@Override
			public void run() {
				System.out.println( "startet" );
				try{
					Thread.sleep( 1000 );
				}
				catch( InterruptedException e ){
					System.out.println( "forstyrret" );
				}
				System.out.println( "færdig" );
			}
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				t1.interrupt();
			}
		};
		
		// De to tråde startes lige efter hinanden og t2 interrupter så t1
		t1.start();
		t2.start();

	}

}
