package com.example;

import weka.core.AttributeStats;
import weka.core.Instances;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class StatisticalAnalysis {
    public static void performStatisticalAnalysisByIndustry(Instances data) {
        // Group data by industry
        Map<String, Double> industryHighestTradedPrice = getIndustryHighestTradedPrice(data);

        // Generate a pie chart for the distribution of highest traded prices among industries
        generatePieChart(industryHighestTradedPrice);
    }

    private static Map<String, Double> getIndustryHighestTradedPrice(Instances data) {
        Map<String, Double> industryHighestPrice = new HashMap<>();

        // Loop through each instance and find the highest traded price for each industry
        for (int i = 0; i < data.numInstances(); i++) {
            String industry = data.instance(i).stringValue(data.attribute("Industry"));
            double price = data.instance(i).value(data.attribute("Last Traded Price"));

            // Update the highest traded price for the industry if needed
            if (!industryHighestPrice.containsKey(industry) || price > industryHighestPrice.get(industry)) {
                industryHighestPrice.put(industry, price);
            }
        }

        return industryHighestPrice;
    }

    private static void generatePieChart(Map<String, Double> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String industry : data.keySet()) {
            dataset.setValue(industry, data.get(industry));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribution of Highest Traded Prices among Industries", // chart title
                dataset, // data
                true, // include legend
                true, // tooltips
                false // urls
        );

        // Display the pie chart in a Swing frame
        JFrame frame = new JFrame("Pie Chart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
