package intelliBell;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Spinner;

/**
 * GUI for main program.
 * @author varshaneya
 */

public class IntelliBellGUI implements Runnable{

	protected Shell shlIntellibell;
	protected Shell newProfileShell;
	private Text txtCreate;
	private String newProfile = "";
	private boolean createProfile;
	private int delay;
	private BellProfile bellProfile;
	private String selectedIP;
	private Thread t;
	private AutomaticScheduler as;

	/**
	 * Constructor launches the application.
	 * @param createProfile
	 * @param bprofile
	 * @param as
	 */
	public IntelliBellGUI(boolean createProfile,BellProfile bprofile,AutomaticScheduler as) {
		this.createProfile = createProfile;
		this.bellProfile = bprofile;
		this.as=as;
		String tname = new String("IntelliBellGUI");
		
		if(!createProfile)
			tname= tname+" - "+bprofile.getName();
		
		t = new  Thread(this,tname);
		t.start();
	}
	
	public IntelliBellGUI(boolean createProfile) {
		this.createProfile = createProfile;
		this.bellProfile = null;
		t = new  Thread(this, "IntelliBellGUI");
		t.start();
	}
	/**
	 * @wbp.parser.entryPoint
	 */

	/**
	 * Open the window.
	 *
	 */
	public void run()  {
		Display display = Display.getDefault();
		createContents();
		shlIntellibell.open();
		shlIntellibell.layout();
		//minimize the window when the daemon is running on pre-defined profile
		shlIntellibell.setMinimized(!createProfile);
		
		while (!shlIntellibell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Creates the GUI.
	 */
	protected void createContents() {
		shlIntellibell = new Shell();
		shlIntellibell.setSize(450, 300);
		if(createProfile)
			shlIntellibell.setText("IntelliBell - Create Profile");
		else
			shlIntellibell.setText("IntelliBell - "+ bellProfile.getName());
		shlIntellibell.addShellListener(new ShellListener() {
			@Override
			public void shellClosed(ShellEvent event) {
				shlIntellibell.dispose();
				if(as != null)
					as.stopAutoScheduler();
				t.interrupt();
				System.out.println("IntelliBell services are quitting. Bye.");
			}

			@Override
			public void shellActivated(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void shellDeactivated(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void shellDeiconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void shellIconified(ShellEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		 * Labels section start
		 */
		
		Label lblCreateProfile = new Label(shlIntellibell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblCreateProfile.setText("create profile");
		lblCreateProfile.setBounds(0, 31, 434, 7);
		
		Label label = new Label(shlIntellibell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 69, 434, 14);
		
		Label label_1 = new Label(shlIntellibell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setText("create profile");
		label_1.setBounds(0, 112, 434, 7);
				
		Label lblStatus = new Label(shlIntellibell, SWT.NONE);
		lblStatus.setBounds(10, 120, 55, 15);
		lblStatus.setText("Status:");
		
		/*
		 * Labels section end
		 */
		
		/*
		 * Create new profile start
		 */
		
		Button btnCreate = new Button(shlIntellibell, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new CreateNewProfile(newProfile);
				shlIntellibell.close();
			}
		});
		btnCreate.setBounds(341, 8, 83, 21);
		btnCreate.setText("Create Profile");
		btnCreate.setEnabled(createProfile);

		txtCreate = new Text(shlIntellibell, SWT.BORDER);
		txtCreate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text text = (Text) e.widget;
				newProfile=text.getText();
			}
		});
		txtCreate.setText("Profile Name");
		txtCreate.setBounds(10, 8, 83, 21);
		txtCreate.setEnabled(createProfile);
		
		/*
		 * Create new profile end
		 */
		
		/*
		 * Manual ring design start
		 */
		
		Button btnRingManually = new Button(shlIntellibell, SWT.NONE);
		btnRingManually.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ManualControl(selectedIP,"s");
				
				MessageBox dialog = new MessageBox(shlIntellibell, SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Short ring signal manually sent to bell at IP "+selectedIP);
				dialog.open();
			}
		});
		btnRingManually.setText("Manual short ring");
		btnRingManually.setBounds(318, 42, 106, 21);
		btnRingManually.setEnabled(!createProfile);
		
		Combo combo = new Combo(shlIntellibell, SWT.NONE);
		if(!createProfile) {
		ArrayList<String> IP = bellProfile.getIP();
		for(String s:IP)
			combo.add(s);
		}
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index;
				if((index = combo.getSelectionIndex()) != -1)
					selectedIP = combo.getItem(index);
			}
		});
		combo.setBounds(10, 44, 91, 14);
		combo.setText("Choose IP");
		combo.setEnabled(!createProfile);
		
		Button btnManualLongRing = new Button(shlIntellibell, SWT.NONE);
		btnManualLongRing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ManualControl(selectedIP,"l");
				MessageBox dialog = new MessageBox(shlIntellibell, SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("Long ring signal manually sent to bell at IP "+selectedIP);
				dialog.open();
			}
		});
		btnManualLongRing.setText("Manual long ring");
		btnManualLongRing.setEnabled(true);
		btnManualLongRing.setBounds(182, 42, 106, 21);
		btnManualLongRing.setEnabled(!createProfile);

		
		/*
		 * Manual ring design end
		 */
		
		/*
		 * Delayed ring design start
		 */
		
		Button btnDelayRing = new Button(shlIntellibell, SWT.NONE);
		btnDelayRing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bellProfile.rescheduleRing(delay);
				MessageBox dialog = new MessageBox(shlIntellibell, SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Info");
				dialog.setMessage("All the bells are rescheduled to ring after " + delay +" min.");
				dialog.open();
			}
		});
		btnDelayRing.setText("Delay Ring");
		btnDelayRing.setBounds(341, 89, 83, 21);
		btnDelayRing.setEnabled(!createProfile);
		
		
		
		Spinner spinner = new Spinner(shlIntellibell, SWT.BORDER);
		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delay=spinner.getSelection();
			}
		});
		spinner.setBounds(10, 89, 47, 22);
		spinner.setEnabled(!createProfile);
		
		/*
		 * Delayed ring design end
		 */
	}
}
