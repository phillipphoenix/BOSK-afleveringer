package opg11x1;

/*
Skal bruges i forbindelse med opgave 11.1.
*/
public class JoinOpgave{
	public static void main( String[ ] args ){
		System.out.println( "main started" );
		final Thread t1 = new Thread( new TOne() );
		System.out.println( "t1 made" );
		final Thread t2 = new Thread( new TTwo(t1) );
		System.out.println( "t2 made" );
		t1.start( );
		t2.start( );
		try{ 
			t2.join( ); 
		}
		catch( InterruptedException e ){ 
		}				
		System.out.println( "main finishes" );
	}
}