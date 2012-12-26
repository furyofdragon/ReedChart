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
import org.eclipse.swt.widgets.Link;

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
		shell.setModified(true);
		shell.setSize(435, 175);
		//shell.setMinimumSize(435, 175);
		shell.setText(Messages.getString("Main.shell.text")); //$NON-NLS-1$
		shell.setLayout(new GridLayout(3, false));
		
		Label LabelFileInput = new Label(shell, SWT.NONE);
		GridData gd_LabelFileInput = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_LabelFileInput.minimumHeight = -1;
		gd_LabelFileInput.minimumWidth = -1;
		LabelFileInput.setLayoutData(gd_LabelFileInput);
		LabelFileInput.setText(Messages.getString("Main.LabelFileInput.text")); //$NON-NLS-1$
		
		textInputPath = new Text(shell, SWT.BORDER);
		GridData gd_textInputPath = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textInputPath.minimumWidth = 260;
		textInputPath.setLayoutData(gd_textInputPath);
		
		Button ButtonBrowseInput = new Button(shell, SWT.NONE);
		GridData gd_ButtonBrowseInput = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_ButtonBrowseInput.minimumWidth = -1;
		gd_ButtonBrowseInput.minimumHeight = -1;
		ButtonBrowseInput.setLayoutData(gd_ButtonBrowseInput);
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
		GridData gd_labelFileOutput = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_labelFileOutput.minimumWidth = -1;
		gd_labelFileOutput.minimumHeight = -1;
		labelFileOutput.setLayoutData(gd_labelFileOutput);
		labelFileOutput.setText(Messages.getString("Main.labelFileOutput.text")); //$NON-NLS-1$
		
		textOutputPath = new Text(shell, SWT.BORDER);
		GridData gd_textOutputPath = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textOutputPath.minimumWidth = 260;
		textOutputPath.setLayoutData(gd_textOutputPath);
		
		Button ButtonBrowseOuput = new Button(shell, SWT.NONE);
		GridData gd_ButtonBrowseOuput = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_ButtonBrowseOuput.minimumWidth = -1;
		gd_ButtonBrowseOuput.minimumHeight = -1;
		ButtonBrowseOuput.setLayoutData(gd_ButtonBrowseOuput);
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
		
		final Button ButtonCreateDXF = new Button(shell, SWT.NONE);
		GridData gd_ButtonCreateDXF = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_ButtonCreateDXF.minimumWidth = -1;
		gd_ButtonCreateDXF.minimumHeight = -1;
		ButtonCreateDXF.setLayoutData(gd_ButtonCreateDXF);
		ButtonCreateDXF.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ReadSource.ReadDataSource(textInputPath.getText());
				WriteDXF.WriteToDXF(textOutputPath.getText());
				ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXFPressed.text"));
			}
		});
		ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXF.text")); //$NON-NLS-1$
		new Label(shell, SWT.NONE);
		
		Link link = new Link(shell, SWT.NONE);
		GridData gd_link = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_link.minimumWidth = -1;
		gd_link.minimumHeight = -1;
		gd_link.widthHint = 359;
		gd_link.verticalIndent = 5;
		gd_link.horizontalIndent = 5;
		link.setLayoutData(gd_link);
		link.setText(Messages.getString("Main.link.text")); //$NON-NLS-1$
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
