package main;

import java.io.File;

import swtlib.LoadSWT;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File swtJar = new File(LoadSWT.getArchFilename("swtlib/swt"));
		LoadSWT.addJarToClasspath(swtJar);
		
		MainForm.mainform(args);

	}

}
