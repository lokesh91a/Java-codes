import java.util.LinkedList;
import java.util.Queue;

/* 
 * WrappingPaper.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This is a program which prints numbers from 0-98 in sequence again and again
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * This makes wrapping papers and stores in fixed length queue
 */

public class WrappingPaperMaker implements Runnable
{
	//Queue for storing wrapping papers
	Queue<Integer> wrappingPaper;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param wrappingPaper	Queue for storing wrapping papers
	 */
	public WrappingPaperMaker(LinkedList<Integer> wrappingPaper)
	{
		this.wrappingPaper = wrappingPaper;
	}
	
	//Run method for thread of this class
	public void run()
	{
		//Will always run(If not a daemon thread
		while(true)
		{
			//Synchronized on its own queue
			synchronized (wrappingPaper)
			{
				//If no of wrapping papers produced is greater than 6 then this 
				//thread will go in wait
				while(wrappingPaper.size()>=6||(10-wrappingPaper.size()<3))
				{
					try {
						wrappingPaper.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("I added 3 wraps to wrapping paper maker");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Adds 3 wrapping papers
				wrappingPaper.add(1);
				wrappingPaper.add(1);
				wrappingPaper.add(1);
				
				//Notifies to all Threads synchronized on this lock
				wrappingPaper.notifyAll();
			}
		}
	}
}
