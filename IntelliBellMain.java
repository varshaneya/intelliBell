package intelliBell;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This is the main class that drives the execution.
 * If there is a profile which already exists, then its name should be given as an input argument for
 * the IntelliBell console to automatically schedule the rings as per the stored timings.
 * The facility to manually ring the bell and rescheduling of previously scheduled rings are also possible. 
 * Running without any input arguments lets one to create a new profile but ringing bells is not possible. 
 * @author varshaneya
 *
 */

public class IntelliBellMain {
	/**
	 * Main method from where the execution starts.
	 * @param args[]
	 */
	
	public static void main(String[] args) {
		try {
			if(args.length != 0) {
				//profile already created so the bell can run automatically
				//read the details of the stored profile and schedule the ringing. 
				System.out.println("Reading profile \""+args[0]+"\".");
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(args[0]));
				BellProfile bellProfile = new BellProfile();
				bellProfile = (BellProfile) ois.readObject();
				ois.close();
				
				System.out.println("Profile \""+args[0]+"\" read successfully.");
				System.out.println("Starting the automatic scheduler and GUI.");
				//Automatic scheduler of ringing
				AutomaticScheduler as = new AutomaticScheduler(bellProfile);
				//GUI for manual ringing and rescheduling of bells
				new IntelliBellGUI(false,bellProfile,as);
				System.out.println("IntelliBell services are running successfully");
			}else {
				//GUI to create a new profile
				new IntelliBellGUI(true);
			}
			
		}catch(FileNotFoundException f) {
			System.out.println("The profile file \""+args[0]+"\" is not found. Please check the name.");
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load the profile file \""+args[0]+"\".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

