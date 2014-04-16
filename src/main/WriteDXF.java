package main;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import cib.util.dxf.DXFWriter;
import cib.util.dxf.GroupCode;

public class WriteDXF {

	static void WriteToDXF(String dxfFileName) {
		// TODO Auto-generated constructor stub
		
		float[][] thetald = ReadSource.getDataArray();
		
		float thetamin = 0;
		float thetamax = 0;
		float lmin = 0;
		float lmax = 0;
		float dmin = 0;
		float dmax = 0;
		int thetald_length;
		int i; 						// counter
		int xscale_factor = 1;
		int yscale_factor = 1000;	// scale factor for shoulders
		int dtheta = 5;				// in degrees
		float dshoulder;
		
		String staticShouldersName = "Static shoulders";
		String dynamicShouldersName = "Dynamic shoulders";
		String gridName = "Grid";
		String textName = "Text";
		
		
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
		
		//dshoulder = ((int)(10*Math.max(Math.max(lmax, dmax), -Math.min(lmin, dmin))))/100f;
		dshoulder = 0.2f*((float) Math.pow(10, (int)Math.log10(1000*Math.max(lmax, dmax)))/1000);
		
		// scale factor for theta values
		xscale_factor = (int)(Math.max(lmax, dmax)*yscale_factor/thetamax);
		
		
		File dxfFile = new File(dxfFileName);
		try {
			DXFWriter dxffile = new DXFWriter(dxfFile);
			
			/*
			dxffile.startSection("HEADER");
			dxffile.writeGroup(GroupCode.GC9, "$ACADVER");
			dxffile.writeGroup(GroupCode.GC1, "AC1009");
			dxffile.writeGroup(GroupCode.GC9, "$DWGCODEPAGE");
			dxffile.writeGroup(GroupCode.GC3, "ANSI_1252");
			dxffile.endSection();
			
			dxffile.startSection("TABLES");
			dxffile.writeGroup(GroupCode.TYPE, "TABLE");
			dxffile.writeGroup(GroupCode.NAME, "LAYER");
			dxffile.writeGroup(GroupCode.HANDLE, "HDL");
			dxffile.writeGroup(GroupCode.SUBCLASS_DATA_MARKER, "SCDM");
			dxffile.writeGroup(GroupCode.INT_1, 10);	// maximum rows in table
			dxffile.writeGroup(GroupCode.TYPE, "LAYER");
			dxffile.writeGroup(GroupCode.NAME, staticShouldersName);
			dxffile.writeGroup(GroupCode.COLOR, 2);		// yellow
			dxffile.writeGroup(GroupCode.TYPE, "LAYER");
			dxffile.writeGroup(GroupCode.NAME, dynamicShouldersName);
			dxffile.writeGroup(GroupCode.COLOR, 3);		// green
			dxffile.writeGroup(GroupCode.TYPE, "LAYER");
			dxffile.writeGroup(GroupCode.NAME, gridName);
			dxffile.writeGroup(GroupCode.COLOR, 9);		// grey
			dxffile.writeGroup(GroupCode.TYPE, "LAYER");
			dxffile.writeGroup(GroupCode.NAME, textName);
			dxffile.writeGroup(GroupCode.COLOR, 4);		// blue
			dxffile.writeGroup(GroupCode.TYPE, "ENDTAB");
			dxffile.endSection();
			*/
			
			dxffile.startSection("ENTITIES");
			
			
			dxffile.writeGroup(GroupCode.TYPE, "POLYLINE");
			dxffile.writeGroup(GroupCode.LAYER_NAME, staticShouldersName);
			dxffile.writeGroup(GroupCode.COLOR, 2);	// color by layer 256 // yellow 2
			dxffile.writeGroup(GroupCode.ENTITIES_FOLLOW_FLAG, 1);
			dxffile.writeGroup(GroupCode.X_1, 0);
			dxffile.writeGroup(GroupCode.Y_1, 0);
			dxffile.writeGroup(GroupCode.Z_1, 0);
			dxffile.writeGroup(GroupCode.INT_1, 4);
			dxffile.writeGroup(GroupCode.INT_6, 6);
			i = 0;
			while (i <= thetald_length-1) {
				dxffile.writeGroup(GroupCode.TYPE, "VERTEX");
				dxffile.writeGroup(GroupCode.LAYER_NAME, staticShouldersName);
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][1]*yscale_factor);
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "SEQEND");
			
			
			dxffile.writeGroup(GroupCode.TYPE, "POLYLINE");
			dxffile.writeGroup(GroupCode.LAYER_NAME, dynamicShouldersName);
			dxffile.writeGroup(GroupCode.COLOR, 3);	// color by layer 256 // green 3
			dxffile.writeGroup(GroupCode.ENTITIES_FOLLOW_FLAG, 1);
			dxffile.writeGroup(GroupCode.X_1, 0);
			dxffile.writeGroup(GroupCode.Y_1, 0);
			dxffile.writeGroup(GroupCode.Z_1, 0);
			dxffile.writeGroup(GroupCode.INT_1, 4);
			dxffile.writeGroup(GroupCode.INT_6, 6);
			i = 0;
			while (i <= thetald_length-1) {
				dxffile.writeGroup(GroupCode.TYPE, "VERTEX");
				dxffile.writeGroup(GroupCode.LAYER_NAME, dynamicShouldersName);
				dxffile.writeGroup(GroupCode.X_1, thetald[i][0]*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, thetald[i][2]*yscale_factor);
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "SEQEND");
			
			
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);		// digits after comma
			
			double textHeight = dshoulder*yscale_factor/4;
			
			float xmin = Math.min(thetamin, 0)*xscale_factor;
			float xmax = (thetamax+dtheta)*xscale_factor;
			float ymin = (Math.min(Math.min(lmin, dmin),0)-dshoulder)*yscale_factor;
			float ymax = (Math.max(lmax, dmax)+dshoulder)*yscale_factor;
			
			// vertical grid lines and labels
			i = Math.min((int)(thetamin/dtheta), 0);
			while (i <= (int)(thetamax/dtheta)+1) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, gridName);
				dxffile.writeGroup(GroupCode.COLOR, 9);	// color by layer 256 // grey 6
				dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, ymin);
				dxffile.writeGroup(GroupCode.X_2, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_2, ymax);
				
				dxffile.writeGroup(GroupCode.TYPE, "TEXT");
				dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
				dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
				dxffile.writeGroup(GroupCode.Y_1, -2*textHeight);
				dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
				dxffile.writeGroup(GroupCode.TEXT, (i*dtheta));
				
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
			dxffile.writeGroup(GroupCode.X_1, i*dtheta*xscale_factor);
			dxffile.writeGroup(GroupCode.Y_1, -2*textHeight);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "deg");
			
			// one radian
			float radian = 57.3f;
			dxffile.writeGroup(GroupCode.TYPE, "LINE");
			dxffile.writeGroup(GroupCode.LAYER_NAME, gridName);
			dxffile.writeGroup(GroupCode.COLOR, 1);	// red
			dxffile.writeGroup(GroupCode.X_1, radian*xscale_factor);
			dxffile.writeGroup(GroupCode.Y_1, ymin);
			dxffile.writeGroup(GroupCode.X_2, radian*xscale_factor);
			dxffile.writeGroup(GroupCode.Y_2, ymax);
			
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
			dxffile.writeGroup(GroupCode.COLOR, 1);	// red
			dxffile.writeGroup(GroupCode.X_1, radian*xscale_factor);
			dxffile.writeGroup(GroupCode.Y_1, ymax+dshoulder*yscale_factor);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "1 radian");
			dxffile.writeGroup(GroupCode.ANGLE_1, 90);
			
			
			// horizontal grid lines and labels
			i = Math.min((int)(Math.min(lmin, dmin)/dshoulder)-1, 0);
			while (i <= (int)(Math.max(lmax, dmax)/dshoulder)+1) {
				dxffile.writeGroup(GroupCode.TYPE, "LINE");
				dxffile.writeGroup(GroupCode.LAYER_NAME, gridName);
				dxffile.writeGroup(GroupCode.COLOR, 9);	// color by layer 256 // grey
				dxffile.writeGroup(GroupCode.X_1, xmin);
				dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
				dxffile.writeGroup(GroupCode.X_2, xmax);
				dxffile.writeGroup(GroupCode.Y_2, i*dshoulder*yscale_factor);
				
				dxffile.writeGroup(GroupCode.TYPE, "TEXT");
				dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
				dxffile.writeGroup(GroupCode.X_1, Math.min(thetamin, 0)*xscale_factor-3*textHeight);
				dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
				dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
				dxffile.writeGroup(GroupCode.TEXT, nf.format((i*dshoulder)));
				
				i++;
			}
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
			dxffile.writeGroup(GroupCode.X_1, -3*textHeight);
			dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "l, d , m");
			
			// xscale_factor
			dxffile.writeGroup(GroupCode.TYPE, "TEXT");
			dxffile.writeGroup(GroupCode.LAYER_NAME, textName);
			dxffile.writeGroup(GroupCode.X_1, 0);
			i = Math.min((int)(Math.min(lmin, dmin)/dshoulder)-3, 0);
			dxffile.writeGroup(GroupCode.Y_1, i*dshoulder*yscale_factor);
			dxffile.writeGroup(GroupCode.DOUBLE_1, textHeight);
			dxffile.writeGroup(GroupCode.TEXT, "1 degree = "+xscale_factor+" mm");
			
			
			dxffile.endSection();
			dxffile.endFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
