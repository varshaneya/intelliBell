package intelliBell;

/**
 * This class sends ring signal when triggered manually from the console
 * @author varshaneya
 */

public class ManualControl implements Runnable 
{
	private String IP;
	private String duration;
	
	/**
	 * Constructor to initialise the IP of the Arduino and duration of ringing
	 * @param IP
	 * @param duration
	 */
	
	public ManualControl(String IP,String duration) 
	{
		this.IP=IP;
		this.duration=duration;
		Thread t = new Thread(this,"Manual Control");	//creating a new thread 
		t.start();	//start the execution of the thread
	}
	
	/**
	 * Sends the ring signal to specified device for the specified duration.
	 */
	
	@Override
	public void run()
	{
		new SendMessage(IP,duration).run() ;
	}

}
