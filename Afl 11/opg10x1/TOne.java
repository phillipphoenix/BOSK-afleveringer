package opg10x1;

public class TOne implements Runnable {

	@Override
	public void run() {
		System.out.println( "t1 started" );
		try{ 
			Thread.sleep( 2000 );
		}
		catch( InterruptedException e ){ 
		}				
		System.out.println( "t1 finishes" );
	}

}
