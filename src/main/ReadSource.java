package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadSource {
	
	static float thetald[][];
	static int N = 0;
	
	static void ReadDataSource(String strFileName) {
		
		String line = null;
		String tableSeparator = "--------------------------------------------------------------------------------------------------------";

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
						nn = nn + 1;
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
		
		thetald = new float[N][3];
		for (int i = 0; i < N; i++) {
			StringTokenizer token = new StringTokenizer(al.get(i), "|");
			//token.nextToken().trim();
			thetald[i][0] = Float.parseFloat(token.nextToken().trim());
			token.nextToken().trim();
			token.nextToken().trim();
			token.nextToken().trim();
			token.nextToken().trim();
			thetald[i][1] = Float.parseFloat(token.nextToken().trim());
			thetald[i][2] = Float.parseFloat(token.nextToken().trim());
			
		}
		
		/**
		try {
			PrintWriter pw = new PrintWriter("res.txt");
			int i = 0;
			pw.println("Lines in table " + N);
			pw.println("Lines in file " + nn);
			while (i < N) {
				pw.println(al.get(i));
				i++;
			}
			pw.println("");
			pw.println("");
			for (int j = 0; j < N; j++) {
				pw.println(thetald[j][0] + "\t" + thetald[j][1] + "\t" + thetald[j][2]);
			}
			
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	
	static int getArrayDimension() {
		return N;
	}
	
	public static float[][] getDataArray() {
		return thetald;
	}
	

}
