package main;

import java.io.File;
import java.io.IOException;

import cib.util.dxf.DXFWriter;
import cib.util.dxf.GroupCode;

public class WriteDXF {

	static void WriteToDXF(String dxfFileName) {
		// TODO Auto-generated constructor stub
		
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
		int xscale_factor = 1;
		int yscale_factor = 1000;
		int dtheta = 5;
		float dshoulder = 0.5f;
		
		thetald_length = thetald.length;
		
		
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
		xscale_factor = (int)(Math.max(lmax, dmax)*yscale_factor/thetamax);
		
		
		File dxfFile = new File(dxfFileName);
		try {
			DXFWriter dxffile = new DXFWriter(dxfFile);
			dxffile.startSection("HEADER");
			dxffile.endSection();
			dxffile.startSection("ENTITIES");
			
			
			i = 0;
			while (i <= thetald_length-2) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Static shoulders");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][1]*yscale_factor);
				dxffile.writeGroup(GroupCode.X_2, thetald[i+1][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_2, thetald[i+1][1]*yscale_factor);
				
				i++;
			}
			
			i = 0;
			while (i <= thetald_length-2) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Dynamic shoulders");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][2]*yscale_factor);
				dxffile.writeGroup(GroupCode.X_2, thetald[i+1][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_2, thetald[i+1][2]*yscale_factor);
				
				i++;
			}
			
			double textHeight = yscale_factor/10;
			// vertical grid lines and labels
			i = 0;
			while (i <= (int)(thetamax/dtheta)+1) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Grid");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, Math.min(lmin, dmin)*yscale_factor);
				dxffile.writeGroup(GroupCode.X_2, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_2, (Math.max(lmax, dmax)+dshoulder)*yscale_factor);
				
				dxffile.writeGroup(GroupCode.TYPE, "TEXT");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Text");
				dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, Math.min(lmin, dmin)*yscale_factor-2*textHeight);
				dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
				dxffile.writeGroup(GroupCode.TEXT, (i*dtheta));
				
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, "Text");
			dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
			dxffile.writeGroup(GroupCode.Y_1, Math.min(lmin, dmin)*yscale_factor-2*textHeight);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "deg");
			
			// horizontal grid lines and labels
			i = 0;
			while (i <= (int)(Math.max(lmax, dmax)/dshoulder)+1) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Grid");
				dxffile.writeGroup(GroupCode.COLOR, 256);	// color by layer
				dxffile.writeGroup(GroupCode.X_1, thetamin*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
				dxffile.writeGroup(GroupCode.X_2, (thetamax+dtheta)*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_2, i*dshoulder*yscale_factor);
				
				dxffile.writeGroup(GroupCode.TYPE, "TEXT");
				dxffile.writeGroup(GroupCode.LAYER_NAME, "Text");
				dxffile.writeGroup(GroupCode.X_1, thetamin*xscale_factor-3*textHeight);
				dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
				dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
				dxffile.writeGroup(GroupCode.TEXT, (i*dshoulder));
				
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, "Text");
			dxffile.writeGroup(GroupCode.X_1, thetamin*xscale_factor-3*textHeight);
			dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "l, d , m");
			
			
			dxffile.endSection();
			dxffile.endFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
