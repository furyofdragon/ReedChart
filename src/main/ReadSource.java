package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReadSource {
	
	static void ReadDataSource(String strFileName) {
		
		String line = null;
		String tableSeparator = "--------------------------------------------------------------------------------------------------------";
		int N = 0;
		int nn = 0;
		int flag = 1;
		ArrayList<String> al = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(strFileName));
			while ((line = br.readLine()) != null) {
				nn = nn + 1;
				if (line.equals(tableSeparator)) {
					flag = flag*(-1);
					if (flag < 0) {		// table start
						//count lines
						while (!(line = br.readLine()).equals(tableSeparator)) {
							nn = nn + 1;
							N = N+1;
							al.add(line);
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PrintWriter pw = new PrintWriter("res.txt");
			int i = 0;
			pw.println(N);
			pw.println(nn);
			while (i < N) {
				pw.println(al.get(i));
				i++;
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
