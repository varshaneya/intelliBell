package intelliBell;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

/**
 * This class stores all the details pertaining to the ringing of the bells
 * These details include start and end of academic year, end semester exams and entrance exams
 * for both the semesters as well the timings to ring for each of them.
 * The dates of holidays for the year are stored and on those days bell(s) will not ring.
 * During the academic year bell(s) will not ring on sundays, but if an exam is scheduled on a sunday then
 * bell(s) ring(s) on that day. 
 * These details are given by the user through the CreateNewProfile class and are stored in object of this 
 * BellProfile type. It implements the interface Serializable for storing the objects of this class in a file.
 * Every time this program is run with a profile name, a file in that name is read which contains the object
 * of this class with the required information to be used for ringing of the bell(s).
 * @author varshaneya
 * @see Serializable 
 * @see CreateNewProfile
 */

public class BellProfile implements Serializable{
	//serial`VersionID for serialisation
	private static final long serialVersionUID = -905136585127639302L;
	
	//name of the profile
	private String name;
	
	//Calendar arrays to store the start and end dates
	private Calendar academicYrStart[];
	private Calendar academicYrEnd[];
	private Calendar endSemStart[];
	private Calendar endSemEnd[];
	private Calendar entranceExamStart;
	private Calendar entranceExamEnd;
	
	//ArrayList to store a list of holidays
	private ArrayList<GregorianCalendar> holidays;
	
	//ArrayList to store a list of IP addresses of Arduino devices attached to the bells 
	private ArrayList<String> ip;
	
	//ArrayList to store list different ringing timings
    private ArrayList<String> academicYrTimings;
    private ArrayList<String> academicYrDuration;
    private ArrayList<String> endSemTimings;
    private ArrayList<String> endSemDuration;
    private ArrayList<String> entraceExamTimings;
    private ArrayList<String> entraceExamDuration;
    
    //converts Calendar entry to dd-MM-yyyy format
    private SimpleDateFormat sdf;
    
    //stores the current ringing profile whether exam day, academic year or no ring
    private int ringStatus;
    
    //contains duration and timings of the current ringing status 
    private ArrayList<String> timings = null;
    private ArrayList<String> duration = null;
    
    //total number of timing slots in the above ringing day
    private int totalTimeSlots;
    
    //array of Timer objects one per IP for scheduling a ring through SendMessage class which extends TimerTask
    private Timer timer[];
    
    //index of the next timing to be rung, in the ArrayList timings
	private int nextSlot;
	
	//static and final variables which stand for a particular part of the year
	public static final int oddSem=0;
	public static final int evenSem=1;
	
    public static final int entranceTime = 1;
    public static final int endSemTime = 2;
    public static final int academicYrTime = 3;
    
	/**
	 * Constructor to initialise and allocate memory for various class members
	 */
    
	public BellProfile() {
		academicYrStart=new Calendar[2];
		academicYrEnd=new Calendar[2];
		endSemStart=new Calendar[2];
		endSemEnd=new Calendar[2];
		ip = new ArrayList<String>();
		endSemTimings = new ArrayList<String>();
		endSemDuration = new ArrayList<String>();
		entraceExamTimings = new ArrayList<String>();
		entraceExamDuration = new ArrayList<String>();
		academicYrTimings = new ArrayList<String>();
		academicYrDuration = new ArrayList<String>();
		holidays = new ArrayList<GregorianCalendar>();
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		name =  new String();
	}
	
	/**
	 * Sets the name of the profile
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the current profile
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Checks holidays class member to see if today is listed as holiday or not.
	 * @param cal1
	 * @return true if today is a holiday, false if today is not a holiday
	 */
	
	public boolean isHoliday(Calendar cal1) {
		for(Calendar cal2:holidays)
			if((cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR))&&(cal1.get(Calendar.MONTH)== cal2.get(Calendar.MONTH))&&(cal1.get(Calendar.DAY_OF_MONTH)== cal2.get(Calendar.DAY_OF_MONTH)))
				return true;
		return false;
	}
	
	/**
	 * Checks which ring status pertains to today and assigns timings and duration class members
	 * with the respective timings and duration lists.
	 * Ring status corresponds to
        		 * 0 = Don't ring today
        		 * 1 = Entrance exam
        		 * 2 = End semester exam
        		 * 3 = academic year (Should not ring on sundays and holidays during academic year)
	 * @return ringStatus
	 */
	
	public int ringToday() {
		Calendar cal;
		try {
			cal = TimeSync.getInternetTime();
		} catch (IOException e) {
			cal = Calendar.getInstance();
		}
		
		if(entranceExamStart.before(cal) && entranceExamEnd.after(cal)) {
			ringStatus= entranceTime;
			timings= entraceExamTimings;
			duration=entraceExamDuration;
			totalTimeSlots=entraceExamTimings.size();
		}
		else if((endSemStart[oddSem].before(cal)&&endSemEnd[oddSem].after(cal))||(endSemStart[evenSem].before(cal)&&endSemEnd[evenSem].after(cal))) {
			ringStatus= endSemTime;
			timings = endSemTimings;
			duration=endSemDuration;
			totalTimeSlots=endSemTimings.size();
		}
		else if((academicYrStart[oddSem].before(cal)&&academicYrEnd[oddSem].after(cal))||(academicYrStart[evenSem].before(cal)&&academicYrEnd[evenSem].after(cal))) {
			ringStatus= academicYrTime;
			timings = academicYrTimings;
			duration=academicYrDuration;
			totalTimeSlots=academicYrTimings.size();
		}
		else
			ringStatus=0;
		if(isHoliday(cal))
			ringStatus= 0;
		return ringStatus;
	}
	
	/**
	 * @return totalTimeSlots class member which contains the number of rings to be rung.
	 */
	
	public int getTotalTimeSlots() {
		return totalTimeSlots;
	}
	
	/**
	 * With respect to the current hour and minute, it calculates number of ringing slots passed and should
	 * not be scheduled to ring.
	 * @param nowHr
	 * @param nowMin
	 * @return number of ringing slots passed
	 */
	
	public int calTimeSlotsPassed(int nowHr,int nowMin)
    {
        int timeSlotsPassed = 0;
        int hr,min;
        
        for(String time:timings)
        {
            hr = Integer.parseInt(time.substring(0,time.indexOf(":")));
            min = Integer.parseInt(time.substring(time.indexOf(":")+1));
            if((nowHr>hr) || (nowHr == hr && nowMin > min))
                ++timeSlotsPassed;
        }
        return timeSlotsPassed;
    }
	
	/**
	 * Calculates the delay in ringing of the bells for the subsequent timings and stores them in array delay
	 * with respect to the current time. 
	 * @param delay[]
	 * @param nowHr
	 * @param nowMin
	 * @param nowSec
	 * @param nextSlot
	 */
	
	public void calTime2Sleep(int[] delay,int nowHr,int nowMin,int nowSec,int nextSlot)
    {
    	int nextHr,nextMin;
    	int current,upcoming;
    	
        for(int i=nextSlot,j=0;i<totalTimeSlots;i++,j++) {
        nextHr = Integer.parseInt(timings.get(i).substring(0,timings.get(i).indexOf(":")));
        nextMin = Integer.parseInt(timings.get(i).substring(timings.get(i).indexOf(":")+1));
    	
    	current = (nowHr*3600*1000)+(nowMin*60*1000);//+(nowSec*1000);
    	upcoming = (nextHr*3600*1000)+(nextMin*60*1000);
    	
    	if (current < upcoming)
    		delay[j]= (upcoming-current) - (nowSec*1000);
    	else if(current == upcoming)
    		delay[j] = 0;
        }
    }
	
	/**
	 * Invokes the schedule method of each of timer object corresponding to a particular IP, and 
	 * schedules the future rings for each of them.
	 * An array of timer objects is created by createTimer() method. 
	 * @param delay[]
	 * @param tmpNextSlot
	 */
	public void scheduleRing(int[] delay,int tmpNextSlot)
    {
		createTimer();
		nextSlot = tmpNextSlot;
		
        for(int ring:delay) {
        	for(String s:ip)
        		timer[ip.indexOf(s)].schedule(new SendMessage(s,duration.get(tmpNextSlot)),ring);
        	++tmpNextSlot;
        }
    }
	
	/**
	 * Delays the already scheduled ringing by offset minutes. 
	 * @param offset
	 */
	
	public void rescheduleRing(int offset) {
		int status = ringToday();
		int nowTime[]=new int[3];
		
		if(status !=0 && !(status == BellProfile.academicYrTime && TimeSync.getDay() == Calendar.SUNDAY)) {
			TimeSync.getTime(nowTime);
			try {
				Thread.sleep((60-nowTime[2]+2)*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			totalTimeSlots = getTotalTimeSlots();
	        nextSlot = calTimeSlotsPassed(nowTime[0],nowTime[1]);
	        if(nextSlot != totalTimeSlots) {
	        
	        int delay[] = new int[totalTimeSlots-nextSlot];
	        calTime2Sleep(delay,nowTime[0],nowTime[1],nowTime[2],nextSlot);
	        for(int i=0;i<delay.length;i++)
	        	delay[i]+=(offset*60*1000);
		
	        for(int i=0;i<ip.size();i++) {
	        	timer[i].cancel();
	        	timer[i].purge();
	        }
	        
	        scheduleRing(delay,nextSlot);
	    }
	}
    }
	
	/**
	 * 
	 * @return total number of bells to ring.
	 */
	
	public int getNoOfBells() {
		return ip.size();
	}
	
	/**
	 * 
	 * @return ArrayList containing IP addresses of the bells.
	 */
	public ArrayList<String> getIP() {
		return ip;
	}
	
	/**
	 * Adds a new IP address to class member ip.
	 * @param IP
	 */
	
	public void addIP(String IP){
		ip.add(IP);
	}
	
	/**
	 * 
	 * @return start of entrance examination as String object.
	 */
	
	public String getEntranceExamStart() {
		return sdf.format(entranceExamStart.getTime()) ;
	}
	
	/**
	 * 
	 * @return end of entrance examination as String object.
	 */
	
	public String getEntranceExamEnd() {
		return sdf.format(entranceExamEnd.getTime()) ;
	}
	
	/**
	 * 
	 * @param sem
	 * @return start of end semester examination as String object when semester is given as input.
	 */
	
	public String getEndSemStart(int sem) {
		return sdf.format(endSemStart[sem].getTime()) ;
	}
	
	/**
	 * 
	 * @param sem
	 * @return end of end semester examination as String object when semester is given as input.
	 */
	
	public String getEndSemEnd(int sem) {
		return sdf.format(endSemEnd[sem].getTime()) ;
	}
	
	/**
	 * 
	 * @param sem
	 * @return start of academic year as String object when semester is given as input.
	 */
	
	public String getAcademicYrStart(int sem) {
		return sdf.format(academicYrStart[sem].getTime()) ;
	}
	
	/**
	 * 
	 * @param sem
	 * @return end of academic year as String object when semester is given as input.
	 */
	
	public String getAcademicYrEnd(int sem) {
		return sdf.format(academicYrEnd[sem].getTime()) ;
	}
	
	/**
	 * Adds ringing time and ringing duration for end semester exams.
	 * @param hr
	 * @param min
	 * @param duration
	 */
	
	public void addEndSemTimings(Integer hr,Integer min,String duration) {
		endSemTimings.add(hr.toString()+":"+min.toString());
		endSemDuration.add(duration);
	}
	
	/**
	 * Adds ringing time and ringing duration for entrance exams.
	 * @param hr
	 * @param min
	 * @param duration
	 */
	
	public void addEntraceExamTimings(Integer hr,Integer min,String duration) {
		entraceExamTimings.add(hr.toString()+":"+min.toString());
		entraceExamDuration.add(duration);
	}
	
	/**
	 * Adds ringing time and ringing duration for academic year.
	 * @param hr
	 * @param min
	 * @param duration
	 */
	
	public void addAcademicYrTimings(Integer hr,Integer min,String duration) {
		academicYrTimings.add(hr.toString()+":"+min.toString());
		academicYrDuration.add(duration);
	}
	
	/**
	 * Adds holidays in an academic year.
	 * @param day
	 * @param month
	 * @param year
	 */
	
	public void addHolidays(int day,int month,int year) {
		holidays.add(new GregorianCalendar(year,month,day));
	}
	
	/**
	 * Adds start of academic year for a particular semester denoted by integer "sem".
	 * @param day
	 * @param month
	 * @param year
	 * @param sem
	 */
	
	public void addAcademicYrStart(int day,int month,int year,int sem) {
		academicYrStart[sem]=new GregorianCalendar(year, month, day,0,0,0);
	}
	
	/**
	 * Adds end of academic year for a particular semester denoted by integer "sem".
	 * @param day
	 * @param month
	 * @param year
	 * @param sem
	 */
	
	public void addAcademicYrEnd(int day,int month,int year,int sem) {
		academicYrEnd[sem]=new GregorianCalendar(year, month, day,23,59,59);
	}
	
	/**
	 * Adds start of end semester exams for a particular semester denoted by integer "sem".
	 * @param day
	 * @param month
	 * @param year
	 * @param sem
	 */
	
	public void addEndSemStart(int day,int month,int year,int sem) {
		endSemStart[sem]=new GregorianCalendar(year, month, day,0,0,0);
	}
	
	/**
	 * Adds end of end semester exams for a particular semester denoted by integer "sem".
	 * @param day
	 * @param month
	 * @param year
	 * @param sem
	 */
	
	public void addEndSemEnd(int day,int month,int year,int sem) {
		endSemEnd[sem]=new GregorianCalendar(year, month, day,23,59,59);
	}
	
	/**
	 * Adds start of entrance exams for a year.
	 * @param day
	 * @param month
	 * @param year
	 */
	
	public void addEntranceExamStart(int day,int month,int year) {
		entranceExamStart=new GregorianCalendar(year, month, day,0,0,0);
	}
	
	/**
	 * Adds end of entrance exams for a year.
	 * @param day
	 * @param month
	 * @param year
	 */
	
	public void addEntranceExamEnd(int day,int month,int year) {
		entranceExamEnd=new GregorianCalendar(year, month, day,23,59,59);
	}
	
	/**
	 * Creates an array of Timer objects with size of array equal to number of bells.
	 */
	
	public void createTimer() {
		timer = new Timer[getNoOfBells()];
		for(int i=0;i<ip.size();i++)
			timer[i]=new Timer(ip.get(i));
	}	
}
