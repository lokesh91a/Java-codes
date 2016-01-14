import java.util.LinkedList;
import java.util.Queue;

/* 
 * CandyAndPaperConsumer.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This consumes 1 candy and 1 wrapping paper and makes 1 wrapped candy
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * This makes wrapped candies and stores in fixed length queue
 */

public class CandyAndPaperConsumer implements Runnable
{
	//Queue for wrapped candy
	Queue<Integer> wrappedCandy;
	
	//For storing reference of queue of CandyMaker
	Queue<Integer> candy;
	
	//For storing reference of queue of WrappingPaperMaker
	Queue<Integer> wrappingPaper;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param wrappedCandy  Queue for wrapped candy
	 * 		  candy		    For storing reference of queue of CandyMaker
	 * 		  wrappingPaper For storing reference of queue of 
	 * 						WrappingPaperMaker
	 * 		  
	 */
	public CandyAndPaperConsumer(LinkedList<Integer> wrappedCandy, 
			LinkedList<Integer> candy, LinkedList<Integer> wrappingPaper)
	{
		this.wrappedCandy = wrappedCandy;
		this.candy = candy;
		this.wrappingPaper = wrappingPaper;
	}
	
	//Run method 
	public void run()
	{
		//Will run forever(If not daemon)
		while(true)
		{
			//Synchronized on its own queue
			synchronized (wrappedCandy)
			{
				//If wrapped candies are more than 4 then this thread will
				//go in wait
				while(wrappedCandy.size()>=4)
				{
					try {
						wrappedCandy.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//synchronized on queue of candy maker
				synchronized (candy)
				{
					//if there are no candies then this will wait and whenever
					//candy maker will make one candy then it will notify this
					//and it will run
					while(candy.isEmpty())
					{
						try {
							candy.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				//Removes one  candy
				candy.remove();
				
				//Notifies that this has removed one candy 
				candy.notifyAll();
				}
				
				//synchronized on wrapping paper queue
				synchronized (wrappingPaper)
				{
					//If there are no wrapping papers then this will go in wait
					//and will release the key
					while(wrappingPaper.isEmpty())
					{
						try {
							wrappingPaper.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Removes 1 wrapping paper
					wrappingPaper.remove();
					
					//Notifies that it has removed a wrapping paper
					wrappingPaper.notifyAll();
				}
				
				//Now when it has both a  candy and a wrapping paper then it 
				//wraps it and save in its queue
				wrappedCandy.add(1);
				
				//Notifies that it has added one wrapped candy
				wrappedCandy.notifyAll();
				System.out.println("consumed 1 candy and 1 wrapping " +
						"paper and added 1 wrapped candy");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
