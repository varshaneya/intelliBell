package intelliBell;
import java.util.*;
/** 
 * This class schedules the ringing of bells using TimerTask, with respect to the current time synchronised
 * from the Internet or the system time if Internet is down.
 * The class is automatically called when the executable is called with a profile and this runs on a separate
 * thread by name "Automatic scheduler".
 * It firsts checks if the bell should ring today or not by invoking the ringToday() method from BellProfile class. 
 * If the bell is supposed to ring today then 
 * scheduling is done using TimerTask class.
 * @author varshaneya
 * @see TimerTask 
 * @see Runnable 
 */

public class AutomaticScheduler implements Runnable
{
    private int nextSlot;	//calculates the number of slots passed in the ringing timetable wrt current time
    private int totalTimeSlots;	//total number of slots in the ringing timetable
    private int nowTime[];	//integer array of size 3 to store hr, min and sec.
    private BellProfile bellParam;	//stores the profile of the bell to be rung
    private Thread t;	//holds the current thread in execution
    
    /**
     * Constructor that takes the profile of bell to be rung.
     * The class BellProfile contains the details of academic year, entrance exams, end semester exams and 
     * holidays for a particular group of bells that should go on and off accordingly.
     * @see BellProfile 
     * @param bdetail
     */
    
    public AutomaticScheduler(BellProfile bdetail) {
    	
    	bellParam = bdetail;
    	nowTime=new int[3]; 
        t = new Thread(this,"Automatic scheduler");	//creating a thread
        t.start();		//starting the thread
	}
    
    /**
     * Called by a method that wishes to stop the automatic scheduler.
     * Generally called when the program is closed manually by the user. 
     */
    
    public void stopAutoScheduler(){
    	if(t != null)
    		t.interrupt();	//stopping the scheduling by interrupting the thread "Automatic scheduler".
    }
    
    /**
     * Method that is overridden from the Runnable interface.
     * The code that is to be executed by the thread is given here. 
     */
    
    @Override
    public void run()
    {
        
    	int time2Sleep;	//the scheduler sleeps till next the day after scheduling all the bells for the day.
    	int delay[];	//delay in time so that the bells can be scheduled for ringing using TimerTask
    	int status;	//stores the ringing status, whether today is a holiday, entrance or end sem exam day or a normal academic day. 

        while(true)
        {
        	try 
        	{   
        		//get the current day and check with the profile whether to ring today or not
        		status = bellParam.ringToday();
        		
        		/*
        		 * 0 = Dont ring today
        		 * 1 = Entrance exam
        		 * 2 = End semester exam
        		 * 3 = academic year (Should not ring on sundays during academic year)
        		 */
        		if(status !=0 && !(status == BellProfile.academicYrTime && TimeSync.getDay() == Calendar.SUNDAY)) {
        			//if the bells are to ring today then
        			//get the internet time from TimeSync class
        			TimeSync.getTime(nowTime);
        			
        			//calculate the total number of slots in the time table depending upon the status
        			totalTimeSlots = bellParam.getTotalTimeSlots();
        			
        			//calculate the number of ringing slots passed with respect to current time
        			//this is to ensure that the bell does not ring for previous time slot
        	        nextSlot = bellParam.calTimeSlotsPassed(nowTime[0],nowTime[1]);
        	        
        	        //check if all the time slots are before the current time
        	        if(nextSlot != totalTimeSlots) {
        	        // if no, then calculate the delay wrt current time and schedule the rings
        	        delay = new int[totalTimeSlots-nextSlot];
        	        bellParam.calTime2Sleep(delay,nowTime[0],nowTime[1],nowTime[2],nextSlot);
        			bellParam.scheduleRing(delay,nextSlot);
        	        }
        		}
        		
        		//Once the scheduling is done sleep for the rest of the day.
    			TimeSync.getTime(nowTime);
            	
            	time2Sleep = ((24-nowTime[0])*(3600*1000))+((60-nowTime[1]+1)*(60*1000)) + ((60-nowTime[2])*1000);
				Thread.sleep(time2Sleep);
    			
        	}catch (InterruptedException e){
        		//if there is an interrupt raised then quit out of the loop and close the scheduler
        		//this is done to shut down the automatic scheduler.
        		break;
			}

        }
    }
}
