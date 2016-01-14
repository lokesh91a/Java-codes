import java.util.LinkedList;
import java.util.Queue;

/* 
 * BoxMaker.java
 * 
 * Revisions: 
 *     $1$ 
 */

/**
 * This produces empty boxes
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * This makes empty boxes and stores in fixed length queue
 */
public class BoxMaker implements Runnable
{
	//Queue for storing empty boxes
	Queue<Integer> box;
	
	/**
	 * Parameterized constructor
	 * 
	 * @param box  Queue for empty boxes  
	 */
	public BoxMaker(LinkedList<Integer> box)
	{
		this.box = box;
	}
	
	//Run method
	public void run()
	{
		//Will run forever(if not daemon)
		while(true)
		{
			//Synchronized on its own queue
			synchronized (box)
			{
				//If no. of boxes is greater than 4 then it will go in wait
				while(box.size()>=4)
				{
					try {
						box.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("I added one box in box maker");
				
				//Adds a single box
				box.add(1);
				
				//Notifies to all blocks synchronized on this lock
				box.notifyAll();
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
