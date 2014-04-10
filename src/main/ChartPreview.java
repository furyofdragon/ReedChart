package main;

import java.awt.Color;

//import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartPreview {

	//  @SuppressWarnings("deprecation")
	public static void chart() {
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
	    chart.setBackgroundPaint(Color.WHITE);
	    
	    MainForm.composite.setChart(chart);
	    
	    /**
	    Frame frame = SWT_AWT.new_Frame(MainForm.composite);
	    //JFreeChart chart = createChart();
	    ChartPanel chartPanel = new ChartPanel(chart);
	    frame.add(chartPanel);
	    */
	    
	    /**
	    JFrame frame = 
	        new JFrame("MinimalStaticChart");
	    // Помещаем график на фрейм
	    frame.getContentPane()
	        .add(new ChartPanel(chart));
	    frame.setSize(600,302);
	    frame.show();
	    */
	  }
	
}
