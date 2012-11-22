package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Main {
	private static Text text;
	private static Text text_1;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 120);
		shell.setText("Reed chart");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 49, 13);
		lblNewLabel.setText("File input");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(10, 37, 49, 13);
		lblNewLabel_1.setText("File output");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(80, 10, 260, 19);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(80, 37, 260, 19);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(346, 10, 68, 23);
		btnNewButton.setText("Browse");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(346, 37, 68, 23);
		btnNewButton_1.setText("Browse");
		
		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.setBounds(183, 62, 68, 23);
		btnNewButton_2.setText("Create DXF");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		WriteDXF.WriteToDXF();
		
	}
}
