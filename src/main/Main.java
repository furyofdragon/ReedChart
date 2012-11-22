package main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Main {
	private static Text textInputPath;
	private static Text textOutputPath;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(435, 125);
		shell.setMinimumSize(435, 125);
		shell.setText("Reed chart");
		
		Label LabelFileInput = new Label(shell, SWT.NONE);
		LabelFileInput.setBounds(10, 10, 64, 21);
		LabelFileInput.setText("File input");
		
		Label labelFileOutput = new Label(shell, SWT.NONE);
		labelFileOutput.setBounds(10, 37, 64, 23);
		labelFileOutput.setText("File output");
		
		textInputPath = new Text(shell, SWT.BORDER);
		textInputPath.setBounds(80, 10, 260, 21);
		
		textOutputPath = new Text(shell, SWT.BORDER);
		textOutputPath.setBounds(80, 37, 260, 23);
		
		Button ButtonBrowseInput = new Button(shell, SWT.NONE);
		ButtonBrowseInput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdOpen = new FileDialog(shell, SWT.OPEN);
				fdOpen.setText("Open");
				String[] filterExtSave = {"*.str", "*.*"};
				fdOpen.setFilterExtensions(filterExtSave);
				textInputPath.setText(fdOpen.open());
			}
		});
		ButtonBrowseInput.setBounds(346, 10, 68, 23);
		ButtonBrowseInput.setText("Browse");
		
		Button ButtonBrowseOuput = new Button(shell, SWT.NONE);
		ButtonBrowseOuput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdSave = new FileDialog(shell, SWT.SAVE);
				fdSave.setText("Save as");
				String[] filterExtSave = {"*.dxf", "*.*"};
				fdSave.setFilterExtensions(filterExtSave);
				textOutputPath.setText(fdSave.open());
			}
		});
		ButtonBrowseOuput.setBounds(346, 37, 68, 23);
		ButtonBrowseOuput.setText("Browse");
		
		Button ButtonCreateDXF = new Button(shell, SWT.NONE);
		ButtonCreateDXF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ReadSource.ReadDataSource();
				WriteDXF.WriteToDXF(textOutputPath.getText());
			}
		});
		ButtonCreateDXF.setBounds(155, 66, 110, 23);
		ButtonCreateDXF.setText("Create DXF");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
}
