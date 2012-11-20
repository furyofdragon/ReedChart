package main;

import java.io.File;
import java.io.IOException;

import cib.util.dxf.DXFWriter;
import cib.util.dxf.GroupCode;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// test data
		double xy[][] = {
				{0, 0.000},
				{5, 0.952},
				{10, 1.922},
				{15, 2.938},
				{20, 3.935},
				{25, 4.407},
				{30, 4.903},
				{40, 5.504},
				{50, 5.612},
				{60, 5.188},
				{70, 4.384}
		};
		
		File demofile = new File("demo.dxf");
		try {
			DXFWriter dxffile = new DXFWriter(demofile);
			//dxffile.startSection("HEADER");
			//dxffile.endSection();
			//dxffile.startSection("SECTION");
			dxffile.startSection("ENTITIES");
			
			
			int n = xy.length;
			int i = 0;
			while (i <= n-2) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "POLYGON");
				dxffile.writeGroup(GroupCode.X_1, xy[i][0]);
				dxffile.writeGroup(GroupCode.Y_1, xy[i][1]);
				dxffile.writeGroup(GroupCode.X_2, xy[i+1][0]);
				dxffile.writeGroup(GroupCode.Y_2, xy[i+1][1]);
				
				i++;
			}
			
			
			
			
			
			
			
			
			
			dxffile.endSection();
			dxffile.endFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
