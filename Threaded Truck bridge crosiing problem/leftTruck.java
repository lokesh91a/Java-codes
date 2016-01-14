/* 
 * leftTruck.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.Queue;

/**
 * This class simulates the trucks coming from the left side of the bridge
 * 
 * 
 * @author Sahil Jasrotia
 * @author Lokesh Agrawal
 */
public class leftTruck extends Thread {

	private static String side = "from left side";
	private Queue<Truck> theTruckQueue;

	/**
	 * Constructor to initialize the Truck queue
	 * 
	 * @param theTruckQueue
	 *            Holds the trucks in this queue
	 * 
	 * @return None
	 */
	public leftTruck(Queue<Truck> theTruckQueue) {
		this.theTruckQueue = theTruckQueue;
	}

	/**
	 * Run Method which simulates the trucks coming from the left side of the
	 * bridge
	 * 
	 * @param None
	 * 
	 * @return None
	 */
	public void run() {

		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			synchronized (theTruckQueue) {
				Truck theTruck = new Truck(side);
				theTruckQueue.add(theTruck);
				theTruckQueue.notify();
			}
		}
	}

}