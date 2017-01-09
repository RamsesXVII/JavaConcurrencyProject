package speedupViz;

import ChartDirector.Chart;
import ChartDirector.ChartViewer;
import ChartDirector.LineLayer;
import ChartDirector.XYChart;

public class MultiLine {
	private  double[] data0;
	private  double[] data1;
	private double[] data2; 
	private double[] data3;
	private double[] data4;

	private  String[] labels;

	//Name of demo program
	public String toString() { return "Multi-Line Chart (1)"; }

	//Number of charts produced in this demo
	public int getNoOfCharts() { return 1; }

	//Main code for creating charts
	public void createChart(ChartViewer viewer, int chartIndex)
	{

		// Create an XYChart object of size 600 x 300 pixels, with a light blue (EEEEFF) background,
		// black border, 1 pxiel 3D border effect and rounded corners
		XYChart c = new XYChart(900, 350, 0xeeeeff, 0x000000, 1);
		c.setRoundedFrame();

		// Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white background. Turn on
		// both horizontal and vertical grid lines with light grey color (0xcccccc)
		c.setPlotArea(95, 58, 520, 195, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

		// Add a legend box at (50, 30) (top of the chart) with horizontal layout. Use 9pt Arial
		// Bold font. Set the background and border color to Transparent.
		c.addLegend(50, 30, false, "Arial Bold", 8).setBackground(Chart.Transparent);

		// Add a title box to the chart using 15pt Times Bold Italic font, on a light blue (CCCCFF)
		// background with glass effect. white (0xffffff) on a dark red (0x800000) background, with
		// a 1 pixel 3D border.
		c.addTitle("SpeedUp variation", "Times New Roman Bold Italic", 15).setBackground(0xccccff, 0x000000, Chart.glassEffect());

		// Add a title to the y axis
		c.yAxis().setTitle("SpeedUp");

		// Set the labels on the x axis.
		c.xAxis().setLabels(labels);

		// Display 1 out of 3 labels on the x-axis.
		c.xAxis().setLabelStep(3);

		// Add a title to the x axis
		c.xAxis().setTitle("Height of Full Binary Tree");

		// Add a line layer to the chart
		LineLayer layer = c.addLineLayer2();

		// Set the default line width to 2 pixels
		layer.setLineWidth(2);

		// Add the three data sets to the line layer. For demo purpose, we use a dash line color for
		// the last line
		layer.addDataSet(data0, 0xff0000, "ex1 NotBounded Parallel Adder");
		layer.addDataSet(data1, 0x008800, "ex2 Bounded Parallel Adder");
		layer.addDataSet(data4, 0x330000, "ex3 Fork Join Parallel Adder");

		layer.addDataSet(data2, c.dashLineColor(0x3333ff, Chart.DashLine), "SpeedUp=1");
		layer.addDataSet(data3, c.dashLineColor(0x9999ff, Chart.DashLine), "Theorical Limit");


		// Output the chart
		viewer.setChart(c);

		//include tool tip for the chart
		viewer.setImageMap(c.getHTMLImageMap("clickable", "",
				"title='[{dataSetName}] SpeedUp {xLabel}: {value} '"));
	}
	public double[] getData0() {
		return data0;
	}

	public void setData0(double[] data0) {
		this.data0 = data0;
	}

	public double[] getData1() {
		return data1;
	}

	public void setData1(double[] data1) {
		this.data1 = data1;
	}

	public double[] getData2() {
		return data2;
	}

	public void setData2(double[] data2) {
		this.data2 = data2;
	}
	public double[] getData3() {
		return data3;
	}

	public void setData3(double[] data3) {
		this.data3 = data3;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public void setData4(double[] data4) {
		this.data4=data4;

	}


}
