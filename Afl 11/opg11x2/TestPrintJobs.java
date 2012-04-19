package opg11x2;

public class TestPrintJobs {

	public static void main(String[] args) throws InterruptedException {
		PrintQueue sharedqueue = new PrintQueue();

		Thread provider1 = new Thread(new JobProvider(sharedqueue, "signe"));
		Thread provider2 = new Thread(new JobProvider(sharedqueue, "jakob"));
		// Thread provider3 = new Thread(new JobProviderBatch(sharedqueue,
		// "kenneth"));

		JobConsumer jb = new JobConsumer(sharedqueue); 
		Thread consumer = new Thread(jb, "Consumer");

		consumer.start();
		provider1.start();
		provider2.start();
		Thread.sleep(10000);
		// provider3.start();

		jb.safeStop();
		
	}

}
