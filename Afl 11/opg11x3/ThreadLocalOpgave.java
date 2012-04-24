package opg11x3;
/*
Skal bruges i forbindelse med opgave 11.3.
*/

class ThreadLocalOpgave{
	static Person person;
	static ThreadLocal<Person> threadLocalPerson;

	public static void main( String[ ] args ){
		person = new Person( "Bo", 1987 );

		threadLocalPerson = new ThreadLocal<Person>( ){
			@Override
			protected Person initialValue( ){
				return new Person( "Unknown", 0 );
			}
		};

		Thread t1 = new Thread( new MyRunnable( ) );
		t1.start( );
		Thread t2 = new Thread( new MyRunnable( ) );
		t2.start( );
	}

}

class MyRunnable implements Runnable{
	public void run( ){

		System.out.print( "ThreadLocalOpgave.threadLocalPerson: "  );
		System.out.println( ThreadLocalOpgave.threadLocalPerson.get( )  );

		ThreadLocalOpgave.threadLocalPerson.set( ThreadLocalOpgave.person );

		System.out.print( "ThreadLocalOpgave.threadLocalPerson: "  );
		System.out.println( ThreadLocalOpgave.threadLocalPerson.get( ) );

		Person p = ThreadLocalOpgave.threadLocalPerson.get( );
		p.setName( "Kurt" );

	}
}

class Person{
	String name;
	int bornIn;
	Person( String name, int bornIn ){
		this.setName( name );
		this.bornIn = bornIn;
	}
	public void setName( String name ){
		this.name = name;
	}
	public String toString( ){
		return "Person[ name = " + name + ", bornIn = " + bornIn + "]";
	}
}