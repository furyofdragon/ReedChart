package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.FlowLayout;

public class MainFormSwing {

	private JFrame frame;
	private JTextField textInputPath;
	private JTextField textOutputPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFormSwing window = new MainFormSwing();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(640, 480));
		
		JPanel panel = new JPanel();
		
		JLabel LabelFileInput = new JLabel("File input");
		
		textInputPath = new JTextField();
		textInputPath.setColumns(10);
		
		JButton ButtonBrowseInput = new JButton("Browse");
		
		textOutputPath = new JTextField();
		textOutputPath.setColumns(10);
		
		JLabel labelFileOutput = new JLabel("File output");
		
		JButton ButtonBrowseOuput = new JButton("Browse");
		
		JButton btnAbout = new JButton("About program");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(labelFileOutput)
								.addComponent(LabelFileInput))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textOutputPath, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
								.addComponent(textInputPath, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
								.addComponent(btnAbout, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(ButtonBrowseOuput)
								.addComponent(ButtonBrowseInput))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textInputPath, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(LabelFileInput))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textOutputPath, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButtonBrowseOuput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelFileOutput))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAbout)
					.addGap(28))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setAutoCreateGaps(true);
		gl_panel.setAutoCreateContainerGaps(true);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 612, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 327, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		
		
		XYSeries series1 = new XYSeries("sin(x)");
	    XYSeries series2 = new XYSeries("cos(x)");
	 
	    for(float i = 0; i < Math.PI; i+=0.1){
	      series1.add(i, Math.sin(i));
	      series2.add(i, Math.cos(i));
	    }
	 
	    XYDataset xyDataset = new XYSeriesCollection();
	    ((XYSeriesCollection) xyDataset).addSeries(series1);
	    ((XYSeriesCollection) xyDataset).addSeries(series2);
	    JFreeChart chart = ChartFactory.createXYLineChart(
	                           "", "x", "y",
	                           xyDataset, 
	                           PlotOrientation.VERTICAL,
	                           true, true, true);
	    ChartPanel chartPanel = new ChartPanel(chart);
	    panel.add(chartPanel);
	    GroupLayout gl_chartPanel = new GroupLayout(chartPanel);
	    gl_chartPanel.setHorizontalGroup(
	    	gl_chartPanel.createParallelGroup(Alignment.LEADING)
	    		.addGap(0, 0, Short.MAX_VALUE)
	    );
	    gl_chartPanel.setVerticalGroup(
	    	gl_chartPanel.createParallelGroup(Alignment.LEADING)
	    		.addGap(0, 0, Short.MAX_VALUE)
	    );
	    chartPanel.setLayout(gl_chartPanel);
	    
	    
	}
}
