package chart;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import main.MainFormSwing;
import main.ReadSource;

public class Chart {
	public static void ShowChart() {
		// get data for chart
		float[][] thetald = ReadSource.getDataArray();
		int thetald_length = thetald.length;
		double[] xData = new double[thetald_length];
		double[] lData = new double[thetald_length];
		double[] dData = new double[thetald_length];
		for (int i = 0; i <= thetald_length-1; i++) {
			xData[i] = thetald[i][0];
			lData[i] = thetald[i][1];
			dData[i] = thetald[i][2];
		}
		
		// create chart
		XYSeries lserie = new XYSeries("l");
		XYSeries dserie = new XYSeries("d");
		for (int i = 0; i <= thetald_length-1; i++) {
			lserie.add(xData[i], lData[i]);
			dserie.add(xData[i], dData[i]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(lserie);
		dataset.addSeries(dserie);
		JFreeChart chart = ChartFactory.createXYLineChart(null, "theta", "l, d, m", dataset);
		
		// chart customization
		// set the background color for the chart
		chart.setBackgroundPaint(Color.white);
		// get a reference to the plot for further customization
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		
		
		// visualize
		ChartPanel chartPanel = new ChartPanel(chart);		
		MainFormSwing.ChartPanel.add(chartPanel);
		MainFormSwing.ChartPanel.revalidate();
		MainFormSwing.ChartPanel.setVisible(true);
		
	}

}
