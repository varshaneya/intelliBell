package intelliBell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendSMS {
	public SendSMS(String IP){
		try {
			ProcessBuilder build = new ProcessBuilder("cmd.exe","/c","python .\\bellFailSMS.py "+ IP);

			build.redirectErrorStream(true);
			Process p = build.start();

			BufferedReader read = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;

			while((line = read.readLine()) != null){
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
