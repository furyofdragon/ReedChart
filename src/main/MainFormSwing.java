package main;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import messages.Messages;

import javax.swing.JPanel;

import chart.Chart;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MainFormSwing {

	private JFrame frmReedChart;
	private JTextField textOutputPath;
	private JTextField textInputPath;
	private String settingsFile = "settings.ini";
	private String lastInputDir = "";
	private String lastOutputDir = "";
	public static JPanel ChartPanel;

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
		readsettings();
		initialize();
		initializeLF();
	}

	private void readsettings() {
		// read settings.ini file for lastdir location
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingsFile));
			lastInputDir = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			lastInputDir = "";
		}
		catch (IOException e) {
			e.printStackTrace();
			lastInputDir = "";
		}
	}
	
	private void writeSettings() {
		// write settings.ini file for lastdir location
		try {
			PrintWriter pw = new PrintWriter(settingsFile, "UTF-8");
			pw.write(lastInputDir);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void initializeLF() {
		// setup System L&F as default
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReedChart = new JFrame();
		frmReedChart.setTitle(Messages.getString("Main.shell.text")); //$NON-NLS-1$
		frmReedChart.setBounds(100, 100, 640, 480);
		frmReedChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel link = new JLabel(Messages.getString("MainFormSwing.link.text")); //$NON-NLS-1$
		link.setToolTipText(Messages.getString("Main.link.text")); //$NON-NLS-1$
		link.setEnabled(false);
		link.setHorizontalAlignment(SwingConstants.CENTER);
		
		final JButton ButtonCreateDXF = new JButton(Messages.getString("Main.ButtonCreateDXF.text"));
		ButtonCreateDXF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".doc", "DOC", "doc"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".str", "STR", "str"));
				fileChooser.setCurrentDirectory(new File(lastInputDir));
				int ret = fileChooser.showOpenDialog(fileChooser);
				if (ret == JFileChooser.APPROVE_OPTION) {
					lastInputDir = fileChooser.getCurrentDirectory().toString();
					writeSettings();
					
					String PathString = fileChooser.getSelectedFile().getAbsolutePath();
					textInputPath.setText(PathString);
					int i = PathString.lastIndexOf('.');
					textOutputPath.setText(PathString.substring(0, i) + ".dxf");
					String fileExt = PathString.substring(i+1);
					
					if (fileExt.equalsIgnoreCase("doc")) {
						ReadSource.ReadDataSourceDoc(textInputPath.getText());
					}
					else {
					if (fileExt.equalsIgnoreCase("str")) {
						ReadSource.ReadDataSourceTxt(textInputPath.getText());
					}
					}
					
					Chart.ShowChart();
					ButtonCreateDXF.setText(Messages.getString("Main.ButtonCreateDXF.text"));
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
					String PathString = fileChooser.getSelectedFile().getAbsolutePath();
					int i = PathString.lastIndexOf('.');
					int l = PathString.length();
					if (i == l-3) {
						textOutputPath.setText(PathString);
					}
					else {
						textOutputPath.setText(PathString + ".dxf");
					}
				}
			}
		});
		
		ChartPanel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(frmReedChart.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(ChartPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(LabelFileOutput)
								.addComponent(LabelFileInput))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(ButtonCreateDXF, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
								.addComponent(textOutputPath, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
								.addComponent(textInputPath, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(ButtonBrowseInput, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(ButtonBrowseOuput, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(link, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(ChartPanel, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textInputPath, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseInput)
						.addComponent(LabelFileInput))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textOutputPath, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(LabelFileOutput)
						.addComponent(ButtonBrowseOuput))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(ButtonCreateDXF)
						.addComponent(link, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		ChartPanel.setLayout(new GridLayout(1, 0, 0, 0));
		frmReedChart.getContentPane().setLayout(groupLayout);
	}
}
