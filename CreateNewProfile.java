package intelliBell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

/**
 * Builds GUI for creating a new profile.
 * @author varshaneya
 */

public class CreateNewProfile {

	protected Shell shell;
	private BellProfile profile;
	private Text txtTypeIpAddress;
	private String ip;
	private String profileName;
	
	/**
	 * Creates a window with name of new profile.
	 * @param newName
	 */
	public CreateNewProfile(String newName) {
		profileName = newName;
		openNewProfile();
	}

	/**
	 * Opens the window.
	 */
	public void openNewProfile() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Checks if the given IP is a valid IP address or not.
	 * @param String ip
	 * @return boolean true if IP is in the right format and false otherwise.
	 */
	
	private boolean checkIp(String ip)
    {
    	String ipParts[] = ip.split("\\.");
    	
    	if(ipParts.length != 4) {
    		MessageBox dialog =
    			    new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
    			dialog.setText("Error");
    			dialog.setMessage("Invalid entry for IP address: "+ip+"\nIP address should have 4 parts separated by dot.");
    			dialog.open();
    		return false;
    	}
    	
    	for(String s:ipParts)
    		if(Integer.parseInt(s)<0 ||Integer.parseInt(s)>255) {
    			MessageBox dialog =
        			    new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
        			dialog.setText("Error");
        			dialog.setMessage("Invalid entry for ip address: "+ip+"\nIP address parts should be non-negative.");
        			dialog.open();
        		return false;
    		}
    	return true;
    }

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		profile = new BellProfile();
		profile.setName(profileName);
		
		shell = new Shell();
		shell.setMinimumSize(new Point(250, 250));
		shell.setSize(450, 600);
		shell.setText("Create new Profile - "+profileName);
		shell.setLayout(null);
		
		/*
		 * Labels section
		 */
		
		Label lblAcademicYear = new Label(shell, SWT.NONE);
		lblAcademicYear.setBounds(10, 10, 91, 15);
		lblAcademicYear.setText("Academic Year:");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 163, 434, 8);
		
		Label lblSemesterExams = new Label(shell, SWT.NONE);
		lblSemesterExams.setText("Semester Exams:");
		lblSemesterExams.setBounds(10, 168, 91, 15);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setText("Start:");
		label_2.setBounds(10, 261, 34, 15);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setText("End:");
		label_4.setBounds(304, 261, 34, 15);
		
		Label label_5 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setBounds(0, 322, 434, 8);
		
		Label lblEntranceExam = new Label(shell, SWT.NONE);
		lblEntranceExam.setText("Entrance Exams:");
		lblEntranceExam.setBounds(10, 336, 91, 15);
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setText("Start:");
		label_6.setBounds(10, 357, 34, 15);
		
		Label label_8 = new Label(shell, SWT.NONE);
		label_8.setText("End:");
		label_8.setBounds(304, 357, 34, 15);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText("Timings:");
		label_1.setBounds(96, 133, 54, 15);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("Timings:");
		label_3.setBounds(96, 291, 54, 15);
		
		Label label_9 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_9.setBounds(0, 413, 434, 8);
		
		Label label_10 = new Label(shell, SWT.NONE);
		label_10.setText("Start:");
		label_10.setBounds(10, 210, 34, 15);
		
		Label label_11 = new Label(shell, SWT.NONE);
		label_11.setText("End:");
		label_11.setBounds(304, 210, 34, 15);
		
		Label lblEvenSemester = new Label(shell, SWT.NONE);
		lblEvenSemester.setBounds(10, 240, 80, 15);
		lblEvenSemester.setText("Even Semester:");
		
		Label lblOddSemester = new Label(shell, SWT.NONE);
		lblOddSemester.setText("Odd Semester:");
		lblOddSemester.setBounds(10, 189, 80, 15);
		
		Label label_12 = new Label(shell, SWT.NONE);
		label_12.setText("Odd Semester:");
		label_12.setBounds(10, 31, 80, 15);
		
		Label label_13 = new Label(shell, SWT.NONE);
		label_13.setText("Start:");
		label_13.setBounds(10, 52, 34, 15);
		
		Label label_14 = new Label(shell, SWT.NONE);
		label_14.setText("End:");
		label_14.setBounds(304, 52, 34, 15);
		
		Label label_16 = new Label(shell, SWT.NONE);
		label_16.setText("Even Semester:");
		label_16.setBounds(10, 82, 80, 15);
		
		Label label_17 = new Label(shell, SWT.NONE);
		label_17.setText("Start:");
		label_17.setBounds(10, 103, 34, 15);
		
		Label lblIpAddressesOf = new Label(shell, SWT.NONE);
		lblIpAddressesOf.setBounds(10, 488, 111, 15);
		lblIpAddressesOf.setText("IP addresses of bells: ");
		
		Label label_15 = new Label(shell, SWT.NONE);
		label_15.setText("End:");
		label_15.setBounds(304, 103, 34, 15);
		
		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setText("Timings:");
		label_7.setBounds(96, 387, 54, 15);

		Label label_18 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_18.setBounds(0, 474, 434, 8);
		
		Label lblHolidays = new Label(shell, SWT.NONE);
		lblHolidays.setBounds(10, 424, 55, 15);
		lblHolidays.setText("Holidays");
		
		/*
		 * DateTime and buttons section
		 */
		
		/*
		 * acedemic yr design start
		 */
		
		//odd sem start
		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setDay(1);
		dateTime.setMonth(0);
		dateTime.setYear(2000);
		profile.addAcademicYrStart(1, 0, 2000, BellProfile.oddSem);
		dateTime.setBounds(50, 52, 80, 24);

		
		//odd sem end
		DateTime dateTime_1 = new DateTime(shell, SWT.BORDER);
		dateTime_1.setDay(1);
		dateTime_1.setMonth(0);
		dateTime_1.setYear(2000);
		profile.addAcademicYrEnd(1, 0, 2000, BellProfile.oddSem);
		dateTime_1.setBounds(344, 52, 80, 24);

		// add button for odd sem
		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addAcademicYrEnd(dateTime_1.getDay(),dateTime_1.getMonth(),dateTime_1.getYear(),BellProfile.oddSem);
				profile.addAcademicYrStart(dateTime.getDay(),dateTime.getMonth(),dateTime.getYear(),BellProfile.oddSem);
				MessageBox dialog = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Odd semester from " + dateTime.getDay()+"-"+(dateTime.getMonth()+1)+"-"+dateTime.getYear()+" to "+
						dateTime_1.getDay()+"-"+(dateTime_1.getMonth()+1)+"-"+dateTime_1.getYear()
						);
				dialog.open();
			}
		});
		btnAdd.setText("Add");
		btnAdd.setBounds(197, 51, 54, 25);
		
		
		//even sem end
		DateTime dateTime_11 = new DateTime(shell, SWT.BORDER);
		dateTime_11.setDay(1);
		dateTime_11.setMonth(0);
		dateTime_11.setYear(2000);
		profile.addAcademicYrEnd(1, 0, 2000, BellProfile.evenSem);
		dateTime_11.setBounds(344, 103, 80, 24);
		
		//even sem start
		DateTime dateTime_12 = new DateTime(shell, SWT.BORDER);
		dateTime_12.setDay(1);
		dateTime_12.setMonth(0);
		dateTime_12.setYear(2000);
		profile.addAcademicYrStart(1, 0, 2000, BellProfile.evenSem);
		dateTime_12.setBounds(50, 103, 80, 24);
		
		//button for even sem add
		Button button_6 = new Button(shell, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addAcademicYrStart(dateTime_12.getDay(),dateTime_12.getMonth(),dateTime_12.getYear(),BellProfile.evenSem);
				profile.addAcademicYrEnd(dateTime_11.getDay(),dateTime_11.getMonth(),dateTime_11.getYear(),BellProfile.evenSem);
				MessageBox dialog = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Even semester from " + dateTime_12.getDay()+"-"+(dateTime_12.getMonth()+1)+"-"+dateTime_12.getYear()+" to "+
						dateTime_11.getDay()+"-"+(dateTime_11.getMonth()+1)+"-"+dateTime_11.getYear()
						);
				dialog.open();
			}
		});
		button_6.setText("Add");
		button_6.setBounds(197, 102, 54, 25);
		
		//timings for academic yr
		DateTime dateTime_6 = new DateTime(shell, SWT.BORDER | SWT.TIME);
		dateTime_6.setHours(0);
		dateTime_6.setMinutes(0);
		dateTime_6.setSeconds(0);
		dateTime_6.setBounds(156, 133, 70, 24);
		
		//academic yr long ring
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			profile.addAcademicYrTimings(dateTime_6.getHours(), dateTime_6.getMinutes(), "l");
			MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		  	dialog.setText("Info");
		  	dialog.setMessage("Long bell timing "+dateTime_6.getHours()+":"+ dateTime_6.getMinutes()+" is recorded for"+" academic year.");
		  	dialog.open();
		}
		});
		button_1.setText("Long");
		button_1.setBounds(232, 133, 54, 25);

		//academic yr short	ring
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addAcademicYrTimings(dateTime_6.getHours(), dateTime_6.getMinutes(), "s");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		   		dialog.setText("Info");
		   		dialog.setMessage("Short bell timing "+dateTime_6.getHours()+":"+ dateTime_6.getMinutes()+" is recorded for"+" academic year.");
		   		dialog.open();
			}
		});
		button.setText("Short");
		button.setBounds(293, 133, 54, 24);
		
		/*
		 * acedemic yr design end
		 */
		
		/*
		 * end semester exams design start 
		 */
		
		//odd sem start
		DateTime dateTime_9 = new DateTime(shell, SWT.BORDER);
		dateTime_9.setDay(1);
		dateTime_9.setMonth(0);
		dateTime_9.setYear(2000);
		dateTime_9.setBounds(50, 210, 80, 24);
		profile.addEndSemStart(1,0,2000,BellProfile.oddSem);
						
		//odd sem end
		DateTime dateTime_10 = new DateTime(shell, SWT.BORDER);
		dateTime_10.setDay(1);
		dateTime_10.setMonth(0);
		dateTime_10.setYear(2000);
		dateTime_10.setBounds(344, 210, 80, 24);
		profile.addEndSemEnd(1,0,2000,BellProfile.oddSem);
		
		//odd sem add button
		Button button_7 = new Button(shell, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEndSemEnd(dateTime_10.getDay(),dateTime_10.getMonth(),dateTime_10.getYear(),BellProfile.oddSem);
				profile.addEndSemStart(dateTime_9.getDay(),dateTime_9.getMonth(),dateTime_9.getYear(),BellProfile.oddSem);
				MessageBox dialog = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Odd semester End semester from " + dateTime_9.getDay()+"-"+(dateTime_9.getMonth()+1)+"-"+dateTime_9.getYear()+" to "+
						dateTime_10.getDay()+"-"+(dateTime_10.getMonth()+1)+"-"+dateTime_10.getYear()
						);
				dialog.open();
			}
		});
		button_7.setText("Add");
		button_7.setBounds(197, 209, 54, 25);

		//even sem start
		DateTime dateTime_2 = new DateTime(shell, SWT.BORDER);
		dateTime_2.setDay(1);
		dateTime_2.setMonth(0);
		dateTime_2.setYear(2000);
		dateTime_2.setBounds(50, 261, 80, 24);
		profile.addEndSemStart(1,0,2000,BellProfile.evenSem);
		
		//even sem end
		DateTime dateTime_3 = new DateTime(shell, SWT.BORDER);
		dateTime_3.setDay(1);
		dateTime_3.setMonth(0);
		dateTime_3.setYear(2000);
		dateTime_3.setBounds(344, 261, 80, 24);
		profile.addEndSemEnd(1,0,2000,BellProfile.evenSem);
		
		//even sem add button
		Button button_8 = new Button(shell, SWT.NONE);
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEndSemEnd(dateTime_3.getDay(),dateTime_3.getMonth(),dateTime_3.getYear(),BellProfile.evenSem);
				profile.addEndSemStart(dateTime_2.getDay(),dateTime_2.getMonth(),dateTime_2.getYear(),BellProfile.evenSem);
				MessageBox dialog = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Even semester End semester from " + dateTime_2.getDay()+"-"+(dateTime_2.getMonth()+1)+"-"+dateTime_2.getYear()+" to "+
						dateTime_3.getDay()+"-"+(dateTime_3.getMonth()+1)+"-"+dateTime_3.getYear()
						);
				dialog.open();
			}
		});
		button_8.setText("Add");
		button_8.setBounds(197, 261, 54, 25);
		
		//timings for end sem
		DateTime dateTime_7 = new DateTime(shell, SWT.BORDER | SWT.TIME);
		dateTime_7.setHours(0);
		dateTime_7.setMinutes(0);
		dateTime_7.setSeconds(0);
		dateTime_7.setBounds(156, 291, 70, 24);
		
		//end sem long
		Button button_2 = new Button(shell, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEndSemTimings(dateTime_7.getHours(), dateTime_7.getMinutes(), "l");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Long bell timing "+dateTime_7.getHours()+":"+ dateTime_7.getMinutes()+" is recorded for "+"end semester exam.");
				dialog.open();
			}
		});
		button_2.setText("Long");
		button_2.setBounds(232, 291, 54, 25);
				
		//end sem short
		Button button_3 = new Button(shell, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEndSemTimings(dateTime_7.getHours(), dateTime_7.getMinutes(), "s");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		        dialog.setText("Info");
		        dialog.setMessage("Short bell timing "+dateTime_7.getHours()+":"+ dateTime_7.getMinutes()+" is recorded for "+"end semester exam.");
		        dialog.open();
			}
		});
		button_3.setText("Short");
		button_3.setBounds(293, 291, 54, 24);
				
		/*
		 * end semester exams design end 
		 */
		
		/*
		 * entrance exam design start
		 */

		//entrance exam start
		DateTime dateTime_4 = new DateTime(shell, SWT.BORDER);
		dateTime_4.setDay(1);
		dateTime_4.setMonth(0);
		dateTime_4.setYear(2000);
		profile.addEntranceExamStart(dateTime_4.getDay(),dateTime_4.getMonth(),dateTime_4.getYear());
		dateTime_4.setBounds(50, 357, 80, 24);
		
		//entrance exam end
		DateTime dateTime_5 = new DateTime(shell, SWT.BORDER);
		dateTime_5.setDay(1);
		dateTime_5.setMonth(0);
		dateTime_5.setYear(2000);
		profile.addEntranceExamEnd(dateTime_5.getDay(),dateTime_5.getMonth(),dateTime_5.getYear());
		dateTime_5.setBounds(344, 357, 80, 24);
		
		//entrance exam add button
		Button button_9 = new Button(shell, SWT.NONE);
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEntranceExamEnd(dateTime_5.getDay(),dateTime_5.getMonth(),dateTime_5.getYear());
				profile.addEntranceExamStart(dateTime_4.getDay(),dateTime_4.getMonth(),dateTime_4.getYear());
				MessageBox dialog = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Entrance exams are from " + dateTime_4.getDay()+"-"+(dateTime_4.getMonth()+1)+"-"+dateTime_4.getYear()+" to "+
						dateTime_5.getDay()+"-"+(dateTime_5.getMonth()+1)+"-"+dateTime_5.getYear()
						);
				dialog.open();
			}
		});
		button_9.setText("Add");
		button_9.setBounds(197, 356, 54, 25);
				
		//entrance exam timings
		DateTime dateTime_8 = new DateTime(shell, SWT.BORDER | SWT.TIME);
		dateTime_8.setHours(0);
		dateTime_8.setMinutes(0);
		dateTime_8.setSeconds(0);
		dateTime_8.setBounds(156, 387, 70, 24);
		
		//entrance timings long
		Button button_4 = new Button(shell, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEntraceExamTimings(dateTime_8.getHours(), dateTime_8.getMinutes(), "l");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		      	dialog.setText("Info");
		        dialog.setMessage("Long bell timing "+dateTime_8.getHours()+":"+ dateTime_8.getMinutes()+" is recorded for "+"entrance exam.");
		        dialog.open();
			}
		});
		button_4.setText("Long");
		button_4.setBounds(232, 387, 54, 25);
				
		//entrance timings short
		Button button_5 = new Button(shell, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addEntraceExamTimings(dateTime_8.getHours(), dateTime_8.getMinutes(), "s");
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Short bell timing "+dateTime_8.getHours()+":"+ dateTime_8.getMinutes()+" is recorded for "+"entrance exam.");
				dialog.open();
			}
		});
		button_5.setText("Short");
		button_5.setBounds(293, 387, 54, 24);
		
		/*
		
		 * entrance exam design end
		 */
		
		/*
		 * Save profile button
		 */
		
		Button btnSaveProfile = new Button(shell, SWT.NONE);
		btnSaveProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(profile.getNoOfBells() != 0) {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(profileName));
						oos.writeObject(profile);
						oos.flush();
						MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
	        			dialog.setText("Summary");
	        			String message = null;
	        			message=("New profile "+profileName+" has been created.\n\n"+
	        			"Entrance Exam \nStart: " + profile.getEntranceExamStart()+" - End: "+profile.getEntranceExamEnd()+
	        			"\n\nOdd semester:\nAcademic year:\nStart: "+profile.getAcademicYrStart(BellProfile.oddSem)+
	        			" - End: "+profile.getAcademicYrEnd(BellProfile.oddSem)+"\nEnd semester: \nStart: "+
	        			profile.getEndSemStart(BellProfile.oddSem)+" - End: "+profile.getEndSemEnd(BellProfile.oddSem)+
	        			"\n\nEven semester:\nAcademic year:\nStart: "+profile.getAcademicYrStart(BellProfile.evenSem)+
	        			" - End: "+profile.getAcademicYrEnd(BellProfile.evenSem)+"\nEnd semester: \nStart: "+
	        			profile.getEndSemStart(BellProfile.evenSem)+" - End: "+profile.getEndSemEnd(BellProfile.evenSem)
	        					);
	        			dialog.setMessage(message);
	        			dialog.open();
	        			oos.close();
	        			shell.close();
					}else {
						MessageBox dialog =
		        			    new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		        			dialog.setText("Error");
		        			dialog.setMessage("IP address of bells not added. Please add atleast one IP to proceed.");
		        			dialog.open();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveProfile.setBounds(185, 527, 75, 25);
		btnSaveProfile.setText("Save Profile");
		
		/**
		 * IP address design start
		 */
		
		txtTypeIpAddress = new Text(shell, SWT.BORDER);
		txtTypeIpAddress.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text text = (Text) e.widget;
				ip = text.getText();
			}
		});
		txtTypeIpAddress.setText("type IP address");
		txtTypeIpAddress.setBounds(10, 509, 111, 21);
		
		Button btnAddIp = new Button(shell, SWT.NONE);
		btnAddIp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkIp(ip)) {
					profile.addIP(ip);
					MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
        			dialog.setText("Info");
        			dialog.setMessage("IP address "+ip+" added to "+profileName+".");
        			dialog.open();
				}
				
			}
		});
		btnAddIp.setBounds(344, 507, 75, 25);
		btnAddIp.setText("Add IP");
		
		/**
		 * IP address design end
		 */
		
		/**
		 * Add holiday design start
		 */
		DateTime dateTime_13 = new DateTime(shell, SWT.BORDER);
		dateTime_13.setYear(2000);
		dateTime_13.setMonth(0);
		dateTime_13.setDay(1);
		dateTime_13.setBounds(119, 444, 80, 24);
		
		Button btnAddHoliday = new Button(shell, SWT.NONE);
		btnAddHoliday.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				profile.addHolidays(dateTime_13.getDay(), dateTime_13.getMonth(), dateTime_13.getYear());
				MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
	    		dialog.setText("Info");
	    		dialog.setMessage("Holiday "+dateTime_13.getDay()+"-"+ (dateTime_13.getMonth()+1)+"-"+dateTime_13.getYear()+" is recorded for"+" academic year.");
	    		dialog.open();
			}
		});
		btnAddHoliday.setText("Add holiday");
		btnAddHoliday.setBounds(232, 444, 75, 25);
		
		/**
		 * Add holiday design end
		 */

	}
}
