/* 
 * bridgeManager.java 
 * 
 * Version: 
 *     $1$ 
 * 
 * Revisions: 
 *     $1$ 
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This program simulates the crossing of a bridge by trucks
 * 
 * @author Sahil Jasrotia
 * @author Lokesh Agrawal
 */
public class bridgeManager extends Thread {

	private Queue<Truck> theTruckQueue; // Holds the Trucks in a Queue
	private final int MAX_SIZE = 4; // Max no of trucks on bridge
	private Queue<Truck> theBridgeQueue;// Holds the trucks on bridge
	private final int MAX_WEIGHT = 200000;// Max Weight bridge can hold

	/**
	 * The main program.
	 * 
	 * @param args
	 *            command line arguments are ignored
	 */
	public static void main(String args[]) {
		// Create the Truck queue. This queue will hold information about
		// the number of trucks waiting to cross the bridge
		Queue<Truck> theTruckQueue = new LinkedList<Truck>();

		// Create the bridge queue. This is to simulate the number of
		// trucks on running on the bridge.
		Queue<Truck> theBridgeQueue = new LinkedList<Truck>();

		// Create the bridgeManager. Bridge Manager is a thread that actually
		// manager the number of trucks running on the truck.
		bridgeManager theManager = new bridgeManager(theTruckQueue,
				theBridgeQueue);

		// Create the leftTruck Thread and rightTruck Thread.
		// These two threads are responsible for simulating the trucks coming
		// from left and right side of the bridge.
		leftTruck lTruck = new leftTruck(theTruckQueue);
		rightTruck rTruck = new rightTruck(theTruckQueue);

		// Start all the threads
		theManager.start();
		lTruck.start();
		rTruck.start();
	}

	/**
	 * Constructor to initialize the Bridge Manager
	 * 
	 * @param theTruckQueue
	 *            , theBridgeQueue
	 * 
	 * @return None
	 */
	public bridgeManager(Queue<Truck> theTruckQueue, Queue<Truck> theBridgeQueue) {
		this.theTruckQueue = theTruckQueue;
		this.theBridgeQueue = theBridgeQueue;
	}

	/**
	 * Run Method which simulates the number of threads running on the bridge
	 * 
	 * @param  None
	 * 
	 * @return None
	 */
	public void run() {
		while (true) {
			Truck theTruck; // Stores the Truck Object

			// synchronized on truck queue before removing the truck objects
			synchronized (theTruckQueue) {
				// If there are no Trucks then wait for trucks to arrive
				while (theTruckQueue.isEmpty()) {
					try {
						theTruckQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				theTruck = theTruckQueue.remove();
			}
			// Check for the maximum trucks allowed on the bridge and max weight
			// allowed on the bridge
			// Do not allow any more trucks if we exceed any of the limit
			while (theBridgeQueue.size() == MAX_SIZE
					|| (theTruck.getWeight() + getTotalWeight()) >= MAX_WEIGHT) {
				System.out.println("Total number of trucks on Bridge: "
						+ theBridgeQueue.size());
				System.out.println("Total Weight on Bridge is: "
						+ getTotalWeight());
				Truck truckOnBridge = theBridgeQueue.remove();
				System.out.println("The truck Entered "
						+ truckOnBridge.getSide() + " of the Bridge left");
			}

			// Allow the trucks on the bridge on first come first serve basis
			System.out.println("Truck " + theTruck.getSide()
					+ " is Entering the Bridge");
			theBridgeQueue.add(theTruck);
			System.out.println("Truck " + theTruck.getSide()
					+ " is running on the Bridge");
		}
	}

	/**
	 * Calculates the total weight of trucks currently on bridge
	 * 
	 * @param None
	 * 
	 * @return totalWeight
	 */
	public int getTotalWeight() {
		int totalWeight = 0;
		Iterator<Truck> it = theBridgeQueue.iterator();
		while (it.hasNext()) {
			totalWeight += it.next().getWeight();
		}
		return totalWeight;
	}
}
