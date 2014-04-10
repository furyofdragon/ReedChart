package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;

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
		frmReedChart.setTitle("Reed chart");
		frmReedChart.setBounds(100, 100, 640, 480);
		frmReedChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel link = new JLabel("This program created by Lysakov I in SeaTech ltd and contains code from http://sourceforge.net/projects/jdxf/\r\ndistributed under GPL license.");
		
		JButton btnNewButton = new JButton("Create DXF");
		
		JLabel labelFileOutput = new JLabel("File output");
		
		JLabel LabelFileInput = new JLabel("File input");
		
		textOutputPath = new JTextField();
		textOutputPath.setColumns(10);
		
		textInputPath = new JTextField();
		textInputPath.setColumns(10);
		
		JButton ButtonBrowseInput = new JButton("Browse");
		
		JButton ButtonBrowseOuput = new JButton("Browse");
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
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
								.addComponent(labelFileOutput)
								.addComponent(LabelFileInput))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
								.addComponent(textInputPath, Alignment.LEADING, 426, 426, 426)
								.addComponent(textOutputPath, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
							.addGap(18)
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
						.addComponent(textInputPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseInput))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelFileOutput)
						.addComponent(textOutputPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseOuput))
					.addGap(18)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(link, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		frmReedChart.getContentPane().setLayout(groupLayout);
	}
}
