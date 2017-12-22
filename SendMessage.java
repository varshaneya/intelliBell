package intelliBell;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * This class extends the TimerTask class and sends ring signal to the Arduino via Ethernet. 
 * Each bell has an Arduino device attached to it with a pre-configured IP.
 * Character 'l' and 's' stand for long and short bell rings. These are sent according to the duration of ring
 * stored in an instance of BellProfile corresponding to that timing.
 * @author varshaneya
 */

public class SendMessage  extends TimerTask
{
    private String ip;
    private String duration;
    
    /**
     * IP of the Arduino and the duration to ring are initialised during the creation of object of this class.
     * @param String ip
     * @param String duration
     */

    protected SendMessage(String ip,String duration)
    {
        this.ip = ip;
        this.duration = duration;
    }
    
    /**
     * Method prints any error message on the screen.
     * @param s
     */
    
    public void printErrMsg(String s)
    {
        synchronized(this)
        {
            System.out.println(s);
            System.out.flush();
        }
    }
    
    /**
     * Method to print log to a log file
     * @param String s
     */
    
    private synchronized void printLog(String s)
    {
    	FileWriter writer;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY:HH:mm:ss");
			writer = new FileWriter("log.txt",true);
			writer.write(new String(sdf.format(cal.getTime())) + ":: ");
			writer.write(s);
			writer.write("\n");
			writer.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Cannot write to log file");
		}
    }
    
    /**
     * Overridden method of the TimerTask class which attempts to connect to the Arduino device.
     * After a series of 5 attempts the function reports an error and stores the error in log file. 
     */
    
    @Override
    public void run()
    {
        int numAttempts=1;	//holds the number of attempts to connect to the  Arduino device
		
		int flag=0;
		Socket socket = new Socket();
		while(numAttempts<=5 && flag == 0) {
			 try{
				 	socket.connect(new InetSocketAddress(ip,5000));
	                PrintWriter sendMsg = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	                sendMsg.println(duration);
	                sendMsg.flush();
	                printErrMsg("ring signal is sent to bell at ip "+ip);
	                flag = 1;
	            }catch(ConnectException e){
	                String printStr = e.toString();
	                printErrMsg("Attempt Number "+numAttempts+ ". " +printStr.substring(27)+" by " +ip);
	            }catch(NoRouteToHostException nhe){
	            	String printStr = nhe.toString();
	            	printErrMsg("Attempt Number "+numAttempts+ ". " +printStr.substring(33)+ " on ip " + ip);
	            }catch(UnknownHostException u){
	            	printErrMsg(u.toString()+ " on ip " + ip);
	            	printLog(u.toString()+ " on ip " + ip+"\n");
	            	flag=1;
	            }catch(IOException i){
	            	printLog(i.toString()+"\n");
	            	flag=1;
	            }
			 ++numAttempts;
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        /*for(numAttempts = 1;numAttempts<=5;numAttempts++)
        {
            try(socket = new Socket())
            {
                PrintWriter sendMsg = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                sendMsg.println(duration);
                sendMsg.flush();
                printErrMsg("ring signal is sent to bell at ip "+ip);
                flag = 1;
            }catch(ConnectException e){
                String printStr = e.toString();
                printErrMsg("Attempt Number "+numAttempts+ ". " +printStr.substring(27)+" by " +ip);
            }catch(NoRouteToHostException nhe){
            	String printStr = nhe.toString();
            	printErrMsg("Attempt Number "+numAttempts+ ". " +printStr.substring(33)+ " on ip " + ip);
            }catch(UnknownHostException u){
            	printErrMsg(u.toString()+ " on ip " + ip);
            	printLog(u.toString()+ " on ip " + ip);
            	flag=1;
            }catch(IOException i){
            	printLog(i.toString());
            	flag=1;
            }finally {
            	if(flag == 1)
            		socket.close();
            }
        }*/
        
        if(numAttempts == 6)
        {
            new SendSMS(ip);
        	printErrMsg("Max number of attempts tried on "+ip+". Check connection to "+ip);
            printLog("Max number of attempts tried on "+ip+". Check connection to "+ip+"\n");
        }
    }
}
