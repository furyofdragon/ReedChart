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
		float thetald[][] = {
				{0F, 0.000F, 0.000F},
				{5F, 0.952F, 0.042F},
				{10F, 1.922F, 0.167F},
				{15F, 2.938F, 0.379F},
				{20F, 3.935F, 0.679F},
				{25F, 4.407F, 1.043F},
				{30F, 4.903F, 1.449F},
				{40F, 5.504F, 2.365F},
				{50F, 5.612F, 3.340F},
				{60F, 5.188F, 4.287F},
				{72F, 4.384F, 5.126F}
		};
		
		float thetamin = 0;
		float thetamax = 0;
		float lmin = 0;
		float lmax = 0;
		float dmin = 0;
		float dmax = 0;
		int thetald_length;
		int i; 					// counter
		int scale_factor = 1;
		int dtheta = 5;
		float ds;
		
		thetald_length = thetald.length;
		
		// convert shoulders from m to mm
		i = 0;
		while (i <= thetald_length-1) {
			thetald[i][1] = thetald[i][1]*1000;
			thetald[i][2] = thetald[i][2]*1000;
			i++;
		}
		
		// find min and max values
		thetamin = thetald[0][0];
		thetamax = thetald[0][0];
		lmin = thetald[0][1];
		lmax = thetald[0][1];
		dmin = thetald[0][2];
		dmax = thetald[0][2];
		i = 1;
		while (i <= thetald_length-1) {
			thetamin = Math.min(thetald[i][0], thetamin);
			thetamax = Math.max(thetald[i][0], thetamax);
			lmin = Math.min(thetald[i][1], lmin);
			lmax = Math.max(thetald[i][1], lmax);
			dmin = Math.min(thetald[i][2], dmin);
			dmax = Math.max(thetald[i][2], dmax);
			i++;
		}
		
		// scale factor for theta values
		scale_factor = (int)(Math.max(lmax, dmax)/thetamax);
		i = 0;
		while (i <= thetald_length-1) {
			thetald[i][0] = thetald[i][0]*scale_factor;
			i++;
		}
		
		ds = (int)(Math.max(lmax, dmax)/10);
		
		
		
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
			
			// vertical grid lines
			i = 0;
			while (i <= (int)(thetamax/dtheta)) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Grid");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, i*dtheta*scale_factor);
				dxffile.writeGroup(GroupCode.Y_1, Math.min(lmin, dmin));
				dxffile.writeGroup(GroupCode.X_2, i*dtheta*scale_factor);
				dxffile.writeGroup(GroupCode.Y_2, Math.max(lmax, dmax));
				i++;
			}
			// horizontal grid lines
			i = 0;
			while (i <= (int)(Math.max(lmax, dmax)/ds)) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Grid");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetamin*scale_factor);
				dxffile.writeGroup(GroupCode.Y_1, i*ds);
				dxffile.writeGroup(GroupCode.X_2, thetamax*scale_factor);
				dxffile.writeGroup(GroupCode.Y_2, i*ds);
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
