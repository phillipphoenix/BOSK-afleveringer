package opg11x1;

public class TTwo implements Runnable {

	Thread t;
	
	public TTwo(Thread t) {
		this.t = t;
	}
	
	@Override
	public void run() {
		System.out.println( "t2 started" );
		try{ 
			t.join( ); 
		}
		catch( InterruptedException e ){ 
		}				
		System.out.println( "t2 finishes" );
	}

}
