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
import messages.Messages;

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
		shell.setText(Messages.getString("Main.shell.text")); //$NON-NLS-1$
		
		Label LabelFileInput = new Label(shell, SWT.NONE);
		LabelFileInput.setBounds(10, 10, 64, 21);
		LabelFileInput.setText(Messages.getString("Main.LabelFileInput.text")); //$NON-NLS-1$
		
		Label labelFileOutput = new Label(shell, SWT.NONE);
		labelFileOutput.setBounds(10, 37, 64, 23);
		labelFileOutput.setText(Messages.getString("Main.labelFileOutput.text")); //$NON-NLS-1$
		
		textInputPath = new Text(shell, SWT.BORDER);
		textInputPath.setBounds(80, 10, 260, 21);
		
		textOutputPath = new Text(shell, SWT.BORDER);
		textOutputPath.setBounds(80, 37, 260, 23);
		
		Button ButtonBrowseInput = new Button(shell, SWT.NONE);
		ButtonBrowseInput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdOpen = new FileDialog(shell, SWT.OPEN);
				fdOpen.setText(Messages.getString("fdOpen.text"));
				String[] filterExtSave = {"*.str", "*.*"};
				fdOpen.setFilterExtensions(filterExtSave);
				textInputPath.setText(fdOpen.open());
			}
		});
		ButtonBrowseInput.setBounds(346, 10, 68, 23);
		ButtonBrowseInput.setText(Messages.getString("Main.ButtonBrowseInput.text")); //$NON-NLS-1$
		
		Button ButtonBrowseOuput = new Button(shell, SWT.NONE);
		ButtonBrowseOuput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdSave = new FileDialog(shell, SWT.SAVE);
				fdSave.setText(Messages.getString("fdSave.text"));
				String[] filterExtSave = {"*.dxf", "*.*"};
				fdSave.setFilterExtensions(filterExtSave);
				textOutputPath.setText(fdSave.open());
			}
		});
		ButtonBrowseOuput.setBounds(346, 37, 68, 23);
		ButtonBrowseOuput.setText(Messages.getString("Main.ButtonBrowseOuput.text")); //$NON-NLS-1$
		
		Button ButtonCreateDXF = new Button(shell, SWT.NONE);
		ButtonCreateDXF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ReadSource.ReadDataSource(textInputPath.getText());
				WriteDXF.WriteToDXF(textOutputPath.getText());
			}
		});
		ButtonCreateDXF.setBounds(155, 66, 110, 23);
		ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXF.text")); //$NON-NLS-1$

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
}
