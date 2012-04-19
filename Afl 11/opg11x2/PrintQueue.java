package opg11x2;

import java.util.LinkedList;
import java.util.List;


public class PrintQueue {

	private List<PrintJob> jobs = new LinkedList<PrintJob>();

	public void addJob(PrintJob pj){
		jobs.add(pj);
	}

	public PrintJob removeJob(){
		return jobs.remove(0);
	}

	public int size(){
		return jobs.size();
	}


}
