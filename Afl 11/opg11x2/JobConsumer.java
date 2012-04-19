package opg11x2;

public class JobConsumer implements Runnable {

	boolean run = true;
	PrintQueue queue;

	public JobConsumer(PrintQueue queue) {
		this.queue = queue;
	}

	public void run() {
		while (run) {
			synchronized ( queue ) {
				if ( queue.size() == 0 )
					try {

						System.out.println("Printer waiting for jobs");
						queue.wait();

					} catch (InterruptedException e) {
						System.out.println("New jobs to print");
					}
			}

			synchronized ( queue ) {
				while ( queue.size() > 0 ) {
					System.out.println("Printing job:");
					System.out.println( "   " + queue.removeJob() );
				}
			}
		}

	}

	public void safeStop() {
		run = false;
		synchronized ( queue ) {
			queue.notifyAll();
		}
	}
}
