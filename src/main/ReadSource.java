package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Range;

public class ReadSource {
	
	static float thetald[][];
	static int N = 0;
	
	static void ReadDataSourceTxt(String strFileName) {
		
		N = 0;
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
	
	static void ReadDataSourceDoc(String strFileName) {
		try {
			FileInputStream fis=new FileInputStream(strFileName);
			try {
				HWPFDocument document = new HWPFDocument(fis);
				
				int t_start = 0;
				int t_end = 0;
				int l_start = 0;
				int l_end = 0;
				int d_start = 0;
				int d_end = 0;
				
				int nBookmarks = document.getBookmarks().getBookmarksCount();
				for (int i = 0; i < nBookmarks; i++) {
					Bookmark bookmark = document.getBookmarks().getBookmark(i);
					if (bookmark.getName().equals("TS_HEELANGLE")) t_start = bookmark.getStart();
					if (bookmark.getName().equals("TS_TRIMANGLE")) t_end = bookmark.getStart();
					if (bookmark.getName().equals("TS_ARMSOFSTATSTABWALLOW")) l_start = bookmark.getStart();
					if (bookmark.getName().equals("TS_ARMOFDYNSTAB")) l_end = bookmark.getStart();
					if (bookmark.getName().equals("TS_ARMOFDYNSTAB")) d_start = bookmark.getStart();
					if (bookmark.getName().equals("TS_DECKELEV")) d_end = bookmark.getStart();
				}
				
				Range rangeT = new Range(t_start, t_end, document);
				Range rangeL = new Range(l_start, l_end, document);
				Range rangeD = new Range(d_start, d_end, document);
				
				int N1 = Math.min(rangeT.numParagraphs(), rangeL.numParagraphs());
				int N2 = Math.min(rangeT.numParagraphs(), rangeD.numParagraphs());
				N = Math.min(N1, N2) - 1;
				
				thetald = new float[N][3];
				
				StringTokenizer tokenT = new StringTokenizer(rangeT.text(), "\r");
				StringTokenizer tokenL = new StringTokenizer(rangeL.text(), "\r");
				StringTokenizer tokenD = new StringTokenizer(rangeD.text(), "\r");
				
				for (int i = 0; i < N; i++){
					String ss = new String();
					ss = tokenT.nextToken().trim();
					thetald[i][0] = Float.parseFloat(ss);
					ss = tokenL.nextToken().trim();
					thetald[i][1] = Float.parseFloat(ss);
					ss = tokenD.nextToken().trim();
					thetald[i][2] = Float.parseFloat(ss);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static int getArrayDimension() {
		return N;
	}
	
	public static float[][] getDataArray() {
		return thetald;
	}
	

}
