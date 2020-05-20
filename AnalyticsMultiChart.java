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
//    public  CellularAutomata1D CA1Dref;
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

//    public void setRef(CellularAutomata1D ref){
//        CA1Dref = ref;
//    }

    public void getData () {

//        fifo_population = CA1Dref.getPopulation();
//        double[][] array = new double[CA1Dref.states_number][fifo_population[0].size()];
//
//        for (int j = 0; j < CA1Dref.states_number; j++) {
//            for (int i = 0; i < fifo_population[j].size(); i++)
//                array[j][i] = fifo_population[j].get(i)+0.0;
//            population_chart.updateXYSeries("state "+(j),null, array[j], null);
//        }
    }

    public void create_series(){
        speedUpChart.addSeries("Computation time",new double[] { 0 }, new double[] { 0 })
                    .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        computationTimeChart.addSeries("Speed up",new double[] { 0 }, new double[] { 0 })
                .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

    }



    public void gerComputationTime(){
        computationTimeData = new LinkedList[1];
//        fifo_hamming[0] = CA1Dref.getHammingDistance();
        double[]array = new double[computationTimeData[0].size()];
        for (int i = 0; i < computationTimeData[0].size(); i++){
            array[i] = computationTimeData[0].get(i)+0.0;
            computationTimeChart.updateXYSeries("Computation Time",null, array, null);
        }
    }

    public void getSpeedUp(){
        speedUpData = new LinkedList[1];
//        fifo_entropy[0] = CA1Dref.getEntropy();
        double[]array = new double[speedUpData[0].size()];
        for (int i = 0; i < speedUpData[0].size(); i++){
            array[i] = speedUpData[0].get(i)+0.0;
            speedUpChart.updateXYSeries("Speed up",null, array, null);
        }
    }



    public void plot(){
//        getData();
//        getDataHamming();
//        getDataSpatialEntropy();

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