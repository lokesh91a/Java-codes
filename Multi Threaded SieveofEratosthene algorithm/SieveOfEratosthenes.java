import java.util.ArrayList;

/* 
 * SieveOfEratosthenes.java
 * 
 * Revisions: 
 *     $1$ 
 */


/**
 * This is a Eratosthenes algorithm  game
 *
 * @author      Lokesh Agrawal
 * @author      Sahil Jasrotia
 */

/**
 * Class SieveOfEratosthenes implements threaded version of SieveOfEratosthenes
 * algorithm
 */
public class SieveOfEratosthenes implements Runnable
{
	final static int FIRSTpRIMEuSED = 2;
	
	//Taken as input from command line argument
    static int MAX;
    
    static int maxActiveThreads;
    
    //It stores the status of each number
    final boolean[] numbers;
    
    //Count keeps the status of current active threads
    static  volatile int count = 1;
    
    static int index = 2, limit;
    
    static Object Lock = new Object();
    
    /**
	 * This method implements the "run method" of the interface runnable
	 * 
	 * @param	None
	 * 
	 * @return	None
	 */
    public void run()
    {    	    
    	//This loop doesnt allow maximum number of active threads to be greater
    	//than the given input
    	while(getCount()>maxActiveThreads)
    	{
    		try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	//Increments number of active Threads by 1
    	playWithCount(1);
    	
    	//Using this we know that current thread holds which index value
    	int currentIndex = Integer.parseInt(Thread.currentThread().getName());
    	
    	//Default logic in which all multiples of currentIndex are set as false
    	if(numbers[currentIndex] )
    	{
    		int counter = 2;				
			while ( currentIndex * counter < MAX )
			{	
				numbers[currentIndex * counter] = false;	
				counter++;				
			}						
		}
    	
    	//Decrements number of active Threads by 1
    	playWithCount(0);
    }
    
    /**
	 * Parameterized Constructor
	 * 
	 * @param integer max Limit till which primes numbers are calcuated
	 */
    public SieveOfEratosthenes(int max)
    {
    	//Size of Array of type boolean is defined
    	numbers = new boolean[max];
    	this.MAX = max;
    }
    
    /**
	 * This method is synchronized on Object lock and it increments the number
	 * of active threads on the basis of parameter passed
	 *  
	 * @param	int 1 if 1 then increments count by 1
	 * 				0 if 0 then decrements count by 1
	 * 
	 * @return	None
	 */
    public void playWithCount(int a)
    {
    	synchronized (Lock)
    	{
			if(a==1)
				count++;
			if(a==0)
				count--;
		}
    }
    
    /**
	 * This method is synchronized on Object lock and it returns the current
	 * count of active threads
	 *  
	 * @param	None
	 * 
	 * @return	None
	 */
    public int getCount()
    {
    	synchronized (Lock)
    	{
    		return count;
		}
    }
    
    /**
	 * This method sets the boolean value for all numbers greater than 1 and 
	 * less than MAX as true
	 *  
	 * @param	None
	 * 
	 * @return	None
	 */
    public void determinePrimeNumbers()
    {
    	for(int index = 1; index < MAX; index ++ )
    	{
    		numbers[index] = true;
    	}
	
    	limit = (MAX > 10 ? (int)Math.sqrt(MAX) + 1 : 3);
    	
    }
    
    /**
	 * This method is the test Method. Already given
	 *  
	 * @param	None
	 * 
	 * @return	None
	 */
    public void testForPrimeNumber()
    {
    	int[] test = { 2, 3, 4, 7, 13, 17,523, MAX - 1, MAX};
    	
    	for (int index = 0; index < test.length; index ++ )
    	{
    		if(test[index]<MAX)
    		{
    			System.out.println(test[index] + " = " + numbers[test[index]]);
    		}
    	}
    }
    
    /**
	 * This method is the main Method
	 *  
	 * @param	None
	 * 
	 * @return	None
	 */
    public static void main( String[] args) throws InterruptedException
    {
    	//Maximum number of input threads is taken as input from command
    	//line argument
    	maxActiveThreads = Integer.parseInt(args[0]);
    	SieveOfEratosthenes aSieveOfEratosthenes = new SieveOfEratosthenes(20);
    	
    	aSieveOfEratosthenes.determinePrimeNumbers();
    	
    	//For each index<limit a new Thread is created
    	while(index<limit)
    	{
    		Thread inSequence = new Thread(aSieveOfEratosthenes);
    		
    		//This thread is named as the current index for later use
    		inSequence.setName(Integer.toString(index));
    		inSequence.start();
    		//inSequence.join();
    		//index is incremented
    		index++;
    	}
    	
    	//This loop is used such that except main thread all other threads 
    	//must finish there work to reach onto the next step
    	while(Thread.activeCount()-1!=0)
    	{ Thread.sleep(500);	}
    	aSieveOfEratosthenes.testForPrimeNumber();
    	
    	
    	System.exit(0);
    }
}

