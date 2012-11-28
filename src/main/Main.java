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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

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
		shell.setLayout(new GridLayout(3, false));
		
		Label LabelFileInput = new Label(shell, SWT.NONE);
		LabelFileInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		LabelFileInput.setText(Messages.getString("Main.LabelFileInput.text")); //$NON-NLS-1$
		
		textInputPath = new Text(shell, SWT.BORDER);
		GridData gd_textInputPath = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_textInputPath.minimumWidth = 260;
		textInputPath.setLayoutData(gd_textInputPath);
		
		Button ButtonBrowseInput = new Button(shell, SWT.NONE);
		ButtonBrowseInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ButtonBrowseInput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdOpen = new FileDialog(shell, SWT.OPEN);
				fdOpen.setText(Messages.getString("fdOpen.text"));
				String[] filterExtSave = {"*.str", "*.*"};
				fdOpen.setFilterExtensions(filterExtSave);
				textInputPath.setText(fdOpen.open());
			}
		});
		ButtonBrowseInput.setText(Messages.getString("Main.ButtonBrowseInput.text")); //$NON-NLS-1$
		
		Label labelFileOutput = new Label(shell, SWT.NONE);
		labelFileOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		labelFileOutput.setText(Messages.getString("Main.labelFileOutput.text")); //$NON-NLS-1$
		
		textOutputPath = new Text(shell, SWT.BORDER);
		GridData gd_textOutputPath = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_textOutputPath.minimumWidth = 260;
		textOutputPath.setLayoutData(gd_textOutputPath);
		
		Button ButtonBrowseOuput = new Button(shell, SWT.NONE);
		ButtonBrowseOuput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ButtonBrowseOuput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fdSave = new FileDialog(shell, SWT.SAVE);
				fdSave.setText(Messages.getString("fdSave.text"));
				String[] filterExtSave = {"*.dxf", "*.*"};
				fdSave.setFilterExtensions(filterExtSave);
				textOutputPath.setText(fdSave.open());
			}
		});
		ButtonBrowseOuput.setText(Messages.getString("Main.ButtonBrowseOuput.text")); //$NON-NLS-1$
		new Label(shell, SWT.NONE);
		
		Button ButtonCreateDXF = new Button(shell, SWT.NONE);
		ButtonCreateDXF.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		ButtonCreateDXF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ReadSource.ReadDataSource(textInputPath.getText());
				WriteDXF.WriteToDXF(textOutputPath.getText());
			}
		});
		ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXF.text")); //$NON-NLS-1$
		new Label(shell, SWT.NONE);

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
}
