package com.example;

import weka.core.Instances;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EachSectorAnalysis {
    @SuppressWarnings("unused")
    public static void performEachSectorAnalysis(Instances data) {
        // Check if the 'Industry', 'Last Traded Price', and 'Company Name' attributes exist in your dataset
        int industryIndex = -1;
        int priceIndex = -1;
        int nameIndex = -1;

        if (data.attribute("Industry") != null) {
            industryIndex = data.attribute("Industry").index();
        } else {
            System.err.println("Attribute 'Industry' not found in the dataset.");
            return;
        }

        if (data.attribute("Last Traded Price") != null) {
            priceIndex = data.attribute("Last Traded Price").index();
        } else {
            System.err.println("Attribute 'Last Traded Price' not found in the dataset.");
            return;
        }

        if (data.attribute("ï»¿Company Name") != null) {
            nameIndex = data.attribute("ï»¿Company Name").index();
        } else {
            System.err.println("Attribute 'Company Name' not found in the dataset.");
            return;
        }

        // Create maps to hold the highest and lowest last traded prices for each sector
        Map<String, Double> sectorHighestPrice = new HashMap<>();
        Map<String, Double> sectorLowestPrice = new HashMap<>();

        // Loop through each instance in the dataset
        for (int i = 0; i < data.numInstances(); i++) {
            // Get the industry, last traded price, and company name for the current instance
            String industry = data.instance(i).stringValue(industryIndex);
            double price = data.instance(i).value(priceIndex);
            String company = data.instance(i).stringValue(nameIndex);

            // Update the highest and lowest prices for the current sector
            if (!sectorHighestPrice.containsKey(industry) || price > sectorHighestPrice.get(industry)) {
                sectorHighestPrice.put(industry, price);
            }
            if (!sectorLowestPrice.containsKey(industry) || price < sectorLowestPrice.get(industry)) {
                sectorLowestPrice.put(industry, price);
            }
        }

        // Output the result for each sector
        for (String sector : sectorHighestPrice.keySet()) {
            double highestPrice = sectorHighestPrice.get(sector);
            double lowestPrice = sectorLowestPrice.get(sector);

            System.out.println("Sector: " + sector);
            System.out.println("Highest Last Traded Price: " + highestPrice);
            System.out.println("Lowest Last Traded Price: " + lowestPrice);
            System.out.println("-----------------------------");
        }

        // After calculating the statistics, call the method to display the bar graph
        displayBarGraph(sectorHighestPrice, sectorLowestPrice);
    }

    private static void displayBarGraph(Map<String, Double> sectorHighestPrice, Map<String, Double> sectorLowestPrice) {
        // Create a bar graph dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String sector : sectorHighestPrice.keySet()) {
            dataset.addValue(sectorHighestPrice.get(sector), "Highest Price", sector);
            dataset.addValue(sectorLowestPrice.get(sector), "Lowest Price", sector);
        }

        // Generate the bar graph
        JFreeChart barChart = ChartFactory.createBarChart(
                "Highest and Lowest Last Traded Prices by Sector",
                "Sector", "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Display the bar graph in a Swing frame
        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        ChartPanel chartPanel = new ChartPanel(barChart);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
