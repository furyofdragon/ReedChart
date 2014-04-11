package xchart;

import javax.swing.JPanel;

import main.MainFormSwing;
import main.ReadSource;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;
import com.xeiam.xchart.XChartPanel;

public class Xchart extends XChartPanel{
	
	public Xchart(Chart chart) {
		super(chart);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a simple Chart using QuickChart
	 */
	public static void ShowChart() {
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
	
	// Create Chart
		Chart chart = new ChartBuilder().chartType(ChartType.Line).title("Chart").xAxisTitle("X").yAxisTitle("Y").build();
		chart.addSeries("l", xData, lData);
		chart.addSeries("d", xData, dData);
	
		// Customize Chart
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyleManager().setAxisTitlesVisible(true);
		
		// Show it
		new Xchart(chart).displayChart(MainFormSwing.getPanel());
	
	}

	private void displayChart(JPanel Panel) {
		// TODO Auto-generated method stub
		
	}

}
