package com.example;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import weka.core.Instances;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ValueAnalysis {

    // Perform trend analysis, correlation analysis, and sector comparison
    public static void performAnalysis(Instances data) {
        // Extract share volume and market value data
        double[] shareVolume = new double[data.numInstances()];
        for (int i = 0; i < data.numInstances(); i++) {
            shareVolume[i] = data.instance(i).value(0); // Assuming share volume is at index 0
        }

        // Sector Comparison: Calculate average share volume for each sector
        Map<String, DescriptiveStatistics> sectorStatistics = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String sector = data.instance(i).stringValue(2); // Assuming sector is at index 2
            if (!sectorStatistics.containsKey(sector)) {
                sectorStatistics.put(sector, new DescriptiveStatistics());
            }
            sectorStatistics.get(sector).addValue(shareVolume[i]);
        }

        // Visualize sector comparison
        visualizeSectorComparison(sectorStatistics);
    }

    // Visualize sector comparison using bar chart
    private static void visualizeSectorComparison(Map<String, DescriptiveStatistics> sectorStatistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String sector : sectorStatistics.keySet()) {
            dataset.addValue(sectorStatistics.get(sector).getMean(), "Mean Share Volume", sector);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Sector Comparison",
                "Sector",
                "Mean Share Volume",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Sector Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
