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
		double thetald[][] = {
				{0, 0.000, 0.000},
				{5, 0.952, 0.042},
				{10, 1.922, 0.167},
				{15, 2.938, 0.379},
				{20, 3.935, 0.679},
				{25, 4.407, 1.043},
				{30, 4.903, 1.449},
				{40, 5.504, 2.365},
				{50, 5.612, 3.340},
				{60, 5.188, 4.287},
				{70, 4.384, 5.126}
		};
		
		double thetamin;
		double thetamax;
		double lmin;
		double lmax;
		double dmin;
		double dmax;
		int thetald_length;
		int i; 					// counter
		
		thetald_length = thetald.length;
		thetamin = thetald[0][0];
		thetamax = thetald[0][thetald_length];
		
		i = 0;
		while (i < thetald_length-1) {
			thetamin = Math.min(thetald[i][0], thetald[i][0]);
			thetamax = Math.max(thetald[i][0], thetald[i][0]);
			lmin = Math.min(thetald[i][1], thetald[i][1]);
			lmax = Math.max(thetald[i][1], thetald[i][1]);
			dmin = Math.min(thetald[i][2], thetald[i][2]);
			dmax = Math.max(thetald[i][2], thetald[i][2]);
			i++;
		}
		
		
		File demofile = new File("demo.dxf");
		try {
			DXFWriter dxffile = new DXFWriter(demofile);
			dxffile.startSection("HEADER");
			dxffile.endSection();
			dxffile.startSection("ENTITIES");
			
			
			i = 0;
			while (i <= thetald_length-2) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Static shoulders");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][1]);
				dxffile.writeGroup(GroupCode.X_2, thetald[i+1][0]);
				dxffile.writeGroup(GroupCode.Y_2, thetald[i+1][1]);
				
				i++;
			}
			
			i = 0;
			while (i <= thetald_length-2) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Dynamic shoulders");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][2]);
				dxffile.writeGroup(GroupCode.X_2, thetald[i+1][0]);
				dxffile.writeGroup(GroupCode.Y_2, thetald[i+1][2]);
				
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
