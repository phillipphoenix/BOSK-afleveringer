package opg11x2;

public class JobProvider implements Runnable{
    PrintQueue queue;
    String user;

    public JobProvider (PrintQueue queue, String user){
	this.queue = queue;
	this.user = user;
    }

    public void run(){
	    synchronized ( queue ) {
	    	queue.addJob(new PrintJob( "Document printed for: " + user ));
	    	queue.notifyAll();
	    	System.out.println("All are notified that " + user + " has put a document in the queue");
	    	System.out.println(queue.size());
	    }
    }


}
