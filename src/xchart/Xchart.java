package xchart;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.SwingWrapper;

public class Xchart {
	/**
	 * Creates a simple Chart using QuickChart
	 */
	public static void ShowChart() {
		double[] xData = new double[] { 0.0, 1.0, 2.0 };
	    double[] yData = new double[] { 2.0, 1.0, 0.0 };
	 
	    // Create Chart
	    Chart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
	 
	    // Show it
	    new SwingWrapper(chart).displayChart();
	}
}
