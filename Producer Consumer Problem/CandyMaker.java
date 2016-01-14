import java.beans.IntrospectionException;
import java.util.LinkedList;
import java.util.Queue;

/* 
 * CandyMaker.java
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
 * This makes candies and stores in fixed length queue
 */
public class CandyMaker implements Runnable
{	
	//Queue for storing candies
	Queue<Integer> candy;
	
	//Parameterized Constructor
	public CandyMaker(LinkedList<Integer> candy)
	{
		this.candy = candy;
	}
	
	//Run method for candy class
	public void run()
	{
		//Will run always(if not daemon)
		while(true)
		{
			//Synchronized on its own queue
			synchronized (candy)
			{
				//If no of candies stored in queue is greater than 3 then 
				//this thread will go on wait
				while(candy.size()>=3)
				{
						try {
							candy.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
				}
				System.out.println("I added one candy to candy maker");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Adds a candy
				candy.add(1);
				//Notifies to all Threads synchronized on this lock
				candy.notifyAll();
			}
		}
	}
}