import java.util.LinkedList;
import java.util.Queue;

/* 
* CompleteBoxMaker.java
* 
* Revisions: 
*     $1$ 
*/

/**
* This consumes 4 wrapped candy and 1 box and makes 1 final box
*
* @author      Lokesh Agrawal
* @author      Sahil Jasrotia
*/

/**
* This makes final boxes and stores in fixed length queue
*/
public class CompleteBoxMaker implements Runnable
{
	//For storing reference of queue of wrapped candies
	Queue<Integer> wrappedCandy;
	
	//Queue for storing final boxes
	Queue<Integer> completeBox;
	
	//For storing reference of queue of box maker
	Queue<Integer> box;
	
	//For setting the limit on final boxes
	int noOfCompleteBoxes, count = 1;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param wrappedCandy  For storing reference of queue of wrapped candy
	 * 		  box		    For storing reference of queue of box
	 * 		  completeBox 	Queue for storing final boxes
	 * 		  noOfCompleteBoxes For setting the limit on final boxes
	 * 		  
	 */
	public CompleteBoxMaker(LinkedList<Integer> wrappedCandy, 
			LinkedList<Integer> box, LinkedList<Integer> completeBox, 
			int noOfCompleteBoxes)
	{
		this.wrappedCandy = wrappedCandy;
		this.box = box;
		this.completeBox = completeBox;
		this.noOfCompleteBoxes = noOfCompleteBoxes;
	}
	
	//Run method
	public void run()
	{
		//This fill run till the time it makes given no of final boxes
		while(count<=noOfCompleteBoxes)
		{
			//Synchronized on its own queue
			synchronized (completeBox)
			{
				//If no of complete boxes is greater than 2 then it will go 
				//in wait
				while(completeBox.size()>2)
				{
					try {
						completeBox.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//Synchronized on queue of wrapped candy
				synchronized (wrappedCandy)
				{
					//If  no of wrapped candies is less than 4 then it will 
					// go to wait and will wake up whenever it will be notified
					while(wrappedCandy.size()<4)
					{
						try {
							wrappedCandy.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//Removes 4 wrapped candies and notifies that I have 
					//removed
					wrappedCandy.remove();
					wrappedCandy.remove();
					wrappedCandy.remove();
					wrappedCandy.remove();
					wrappedCandy.notifyAll();
				}
				
				//Synchronized on queue of box
				synchronized (box)
				{
					//This will go in wait if no. of boxes is empty
					while(box.isEmpty())
					{
						try {
							box.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Removes 1 box and notifies to all threads waiting on this
					//queue that i have removes one box
					box.remove();
					box.notifyAll();
				}
				
				//Adds 1 final box and notifies to all threads waiting on this
				//queue that i have added 1 final box(if any)
				completeBox.add(1);
				completeBox.notifyAll();
				System.out.println("Final bos has been made by consuming 4 " +
						"wrapped candies and 1 empty box");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//increases count for checking how many boxes have already been
				//made
				count++;
			}
		}
	}
}
