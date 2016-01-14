import java.util.LinkedList;

/* 
 * Factory.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This gives to all producers and consumers their queue and sets the limit on
 * no of final boxes
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * This makes wrapped candies and stores in fixed length queue
 */

public class Factory extends Thread
{
	//Main method
	public static void main(String args[]) throws InterruptedException
	{	
		//Limit for no of final boxes
		int noOfCompleteBoxes = 3;
		
		//Queue for candy
		LinkedList<Integer> candy1 = new LinkedList<Integer>();
		
		//Queue for wrapping paper
		LinkedList<Integer> wrappingPaper1 = new LinkedList<Integer>();
		
		//Queue for wrapped candy
		LinkedList<Integer> wrappedCandy1 = new LinkedList<Integer>();
		
		//Queue for box
		LinkedList<Integer> box1 = new LinkedList<Integer>();
		
		//Queue for final bosex
		LinkedList<Integer> completeBox1 = new LinkedList<Integer>();
		
		//Passing references to all producers and consumers of what they need
		CandyMaker candy = new CandyMaker(candy1);
		WrappingPaperMaker wrappingPaper = new 
				WrappingPaperMaker(wrappingPaper1);
		CandyAndPaperConsumer wrappedCandy = new 
				CandyAndPaperConsumer (wrappedCandy1, candy1, wrappingPaper1);
		BoxMaker box = new BoxMaker(box1);
		CompleteBoxMaker completeBox = new CompleteBoxMaker(wrappedCandy1, box1
				, completeBox1, noOfCompleteBoxes);
		
		//Thread of each producer and consumer is created
		Thread forCandy = new Thread(candy);
		Thread forWrappingPaper = new Thread(wrappingPaper);
		Thread forWrappedCandy = new Thread(wrappedCandy);
		Thread forBox = new Thread(box);
		Thread forCompleteBox = new Thread(completeBox);
		
		/**
		 * A thread can be set as daemon before starting it only
		 * 
		 * So all threads other than final box has been set as daemon so that 
		 * when main thread will terminate then all daemon threads will
		 * automatically be terminated
		 */
		forCandy.setDaemon(true);
		forWrappingPaper.setDaemon(true);
		forWrappedCandy.setDaemon(true);
		forBox.setDaemon(true);
		
		//Starting all threads
		forCandy.start();
		forWrappingPaper.start();
		forWrappedCandy.start();
		forBox.start();
		forCompleteBox.start();
		
		//Join on final thread so that main thread will terminate once final 
		//thread will finish its work
		forCompleteBox.join();
	}
}
