package main;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import messages.Messages;

public class MainFormSwing {

	private JFrame frmReedChart;
	private JTextField textOutputPath;
	private JTextField textInputPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFormSwing window = new MainFormSwing();
					window.frmReedChart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFormSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReedChart = new JFrame();
		frmReedChart.setTitle(Messages.getString("Main.shell.text")); //$NON-NLS-1$
		frmReedChart.setBounds(100, 100, 640, 480);
		frmReedChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel link = new JLabel(Messages.getString("Main.link.text")); //$NON-NLS-1$
		link.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JButton ButtonCreateDXF = new JButton("Create DXF");
		ButtonCreateDXF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadSource.ReadDataSource(textInputPath.getText());
				WriteDXF.WriteToDXF(textOutputPath.getText());
				ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXFPressed.text"));
			}
		});
		ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXF.text")); //$NON-NLS-1$
		
		JLabel LabelFileOutput = new JLabel(Messages.getString("Main.LabelFileOutput.text")); //$NON-NLS-1$
		
		JLabel LabelFileInput = new JLabel(Messages.getString("Main.LabelFileInput.text")); //$NON-NLS-1$
		
		textOutputPath = new JTextField();
		LabelFileOutput.setLabelFor(textOutputPath);
		textOutputPath.setColumns(10);
		
		textInputPath = new JTextField();
		LabelFileInput.setLabelFor(textInputPath);
		textInputPath.setColumns(10);
		
		JButton ButtonBrowseInput = new JButton(Messages.getString("Main.ButtonBrowseInput.text")); //$NON-NLS-1$
		ButtonBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".str", "STR", "str"));
				int ret = fileChooser.showOpenDialog(fileChooser);
				if (ret == JFileChooser.APPROVE_OPTION) {
					textInputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					//ButtonCreateDXF.setText(beforeCreateDXFString);
				}
			}
		});
		
		JButton ButtonBrowseOuput = new JButton(Messages.getString("Main.ButtonBrowseOuput.text")); //$NON-NLS-1$
		ButtonBrowseOuput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Autocad DXF file", "DXF", "dxf"));
				int ret = fileChooser.showSaveDialog(fileChooser);
				if (ret == JFileChooser.APPROVE_OPTION) {
					textOutputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					//ButtonCreateDXF.setText(beforeCreateDXFString);
				}
			}
		});
		
		JInternalFrame internalFrame = new JInternalFrame(Messages.getString("Main.internalFrame.title")); //$NON-NLS-1$
		internalFrame.setVisible(true);
		GroupLayout groupLayout = new GroupLayout(frmReedChart.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(link, GroupLayout.PREFERRED_SIZE, 612, Short.MAX_VALUE)
						.addComponent(internalFrame, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(LabelFileOutput)
								.addComponent(LabelFileInput))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textInputPath, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
									.addGap(18))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(textOutputPath, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
										.addComponent(ButtonCreateDXF, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
									.addGap(18)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(ButtonBrowseOuput, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(ButtonBrowseInput, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(internalFrame, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(LabelFileInput)
						.addComponent(textInputPath, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseInput))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(LabelFileOutput)
						.addComponent(textOutputPath, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseOuput))
					.addGap(18)
					.addComponent(ButtonCreateDXF)
					.addGap(18)
					.addComponent(link, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		frmReedChart.getContentPane().setLayout(groupLayout);
	}
}
