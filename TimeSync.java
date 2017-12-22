package intelliBell;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;

import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;

/**
 * This class gets time from the internet server "time-a.nist.gov" and converts it to one of
 * readable formats using SimpleDateFormat class. If the internet is down then it uses the system time.
 * All the methods in this class are kept static so that the time returned is independent of class object used.
 * @author varshaneya
 * @see SimpleDateFormat
 * @see Calendar 
 * @see org.apache.commons.net.ntp.NTPUDPClient 
 * @see org.apache.commons.net.ntp.TimeInfo
 */

public class TimeSync {
	
	//various formatting facilities using SimpleDataFormat class
	private static SimpleDateFormat hrFormat = new SimpleDateFormat("HH");
	private static SimpleDateFormat minFormat = new SimpleDateFormat("mm");
	private static SimpleDateFormat secFormat = new SimpleDateFormat("ss");
	private static SimpleDateFormat dayFormat = new SimpleDateFormat("DD");
	private static SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	private static SimpleDateFormat yrFormat = new SimpleDateFormat("yyyy");
	
	/**
	 * This method gets the current time converts into hour-minute-second format and stores the hour in
	 * array[0], minute in array[1] and second in array[2]. 
	 * @param array[3]
	 */
	
	public static void getTime(int array[]){
		Calendar cal = null;
		try {
			cal = getInternetTime();
		}catch(NoRouteToHostException nhe) {
			cal = Calendar.getInstance();
		}catch(UnknownHostException uhe) {
			cal = Calendar.getInstance();
		}catch(IOException ie) {
			cal = Calendar.getInstance();
		}
		array[0]=Integer.parseInt(hrFormat.format(cal.getTime()));
		array[1]=Integer.parseInt(minFormat.format(cal.getTime()));
		array[2]=Integer.parseInt(secFormat.format(cal.getTime()));
	}
	
	/**
	 * This method gets the current time converts into YYYY-MM-DD format and stores the DD in
	 * array[0], MM in array[1] and YYYY in array[2]. 
	 * @param array[3]
	 */
	
	public static void getDate(int array[]){
		Calendar cal = null;
		try {
			cal = getInternetTime();
		}catch(NoRouteToHostException nhe) {
			cal = Calendar.getInstance();
		}catch(UnknownHostException uhe) {
			cal = Calendar.getInstance();
		}catch(IOException ie) {
			cal = Calendar.getInstance();
		}
		array[0]=Integer.parseInt(dayFormat.format(cal.getTime()));
		array[1]=Integer.parseInt(monthFormat.format(cal.getTime()));
		array[2]=Integer.parseInt(yrFormat.format(cal.getTime()));
	}
	
	public static int getDay() {
		Calendar cal = null;
		try {
			cal = getInternetTime();
		}catch(NoRouteToHostException nhe) {
			cal = Calendar.getInstance();
		}catch(UnknownHostException uhe) {
			cal = Calendar.getInstance();
		}catch(IOException ie) {
			cal = Calendar.getInstance();
		}
		
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Gets internet time from "time-a.nist.gov". Default timeout is set to 5000 ms.
	 * @return instance of Calendar class
	 * @throws UnknownHostException IOException
	 */
	
	public static Calendar getInternetTime() throws IOException {
		String TIME_SERVER = "time-a.nist.gov"; 
	    NTPUDPClient timeClient = new NTPUDPClient();
	    timeClient.setDefaultTimeout(5000);
	    InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
	    TimeInfo timeInfo = timeClient.getTime(inetAddress);
	    Calendar cal = new GregorianCalendar();
	    cal.setTimeInMillis(timeInfo.getReturnTime());
	    timeClient.close();
	    return cal;
	}
}
