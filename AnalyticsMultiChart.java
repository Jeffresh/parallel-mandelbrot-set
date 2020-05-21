import java.awt.*;
import java.util.LinkedList;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import javax.swing.*;

import org.knowm.xchart.*;

/**
 * Creates a real-time chart using SwingWorker
 */
public class AnalyticsMultiChart {

    public  JPanel speedUpPanel;
    public  JPanel computationTimePanel;
    public  XYChart speedUpChart;
    public  XYChart computationTimeChart;
    public  JFrame chartFrame;
    public  GuiMandelbrot Guiref;
    private  String chartTitle;
    private  LinkedList<Double>[] speedUpData;
    private  LinkedList<Double>[] computationTimeData;


    AnalyticsMultiChart(String chartTitle, String x_axis_name, String y_axis_name){
        this.chartTitle = chartTitle;
        speedUpChart = new XYChartBuilder()
                .title(y_axis_name).xAxisTitle(x_axis_name)
                .yAxisTitle(y_axis_name).width(600).height(300).build();
        speedUpChart.getStyler().setLegendVisible(true);
        speedUpChart.getStyler().setXAxisTicksVisible(true);
        speedUpChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        computationTimeChart = new XYChartBuilder()
                .title("Speed Up").xAxisTitle(x_axis_name)
                .yAxisTitle("Speed Up").width(600).height(300).build();
        computationTimeChart.getStyler().setLegendVisible(true);
        computationTimeChart.getStyler().setXAxisTicksVisible(true);
        computationTimeChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

    }

    public void setRef(GuiMandelbrot ref){
        Guiref = ref;
    }


    public void createSeries(){
        computationTimeChart.addSeries("Computation Time",new double[] { 0 }, new double[] { 0 })
                    .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        speedUpChart.addSeries("Speed Up",new double[] { 0 }, new double[] { 0 })
                .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

    }



    public void getComputationTime(){
        computationTimeData = new LinkedList[1];
        computationTimeData[0] = Guiref.getComputationTimeData();
        double[]array = new double[computationTimeData[0].size()];
        for (int i = 0; i < computationTimeData[0].size(); i++){
            array[i] = computationTimeData[0].get(i)+0.0;
            computationTimeChart.updateXYSeries("Computation Time",null, array, null);
        }
    }

    public void getSpeedUp(){
        speedUpData = new LinkedList[1];
        speedUpData[0] = Guiref.getSpeedUpData();
        double[]array = new double[speedUpData[0].size()];
        for (int i = 0; i < speedUpData[0].size(); i++){
            array[i] = speedUpData[0].get(i)+0.0;
            speedUpChart.updateXYSeries("Speed Up",null, array, null);
        }
    }



    public void plot(){
        getComputationTime();
        getSpeedUp();

        speedUpPanel.revalidate();
        speedUpPanel.repaint();
        computationTimePanel.revalidate();
        computationTimePanel.repaint();

    }

    public void show(){
        speedUpPanel = new XChartPanel(speedUpChart);
        computationTimePanel = new XChartPanel(computationTimeChart);
        chartFrame = new JFrame("Charts");
        GridLayout layout = new GridLayout(2,1);
        chartFrame.setLayout(layout);
        chartFrame.add(speedUpPanel);
        chartFrame.add(computationTimePanel);
        chartFrame.setSize(600,600);
        chartFrame.setMaximumSize(new Dimension(100,600));
        chartFrame.setMaximumSize(new Dimension(100,600));


        chartFrame.setAlwaysOnTop(true);
        chartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chartFrame.setTitle(chartTitle);
        chartFrame.setOpacity(1);
        chartFrame.setBackground(Color.WHITE);
        chartFrame.setVisible(true);
        chartFrame.pack();
    }
}