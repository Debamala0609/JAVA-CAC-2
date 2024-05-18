package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import weka.core.Instances;

import javax.swing.*;
import java.util.HashMap;

public class StockAnalysis {
    public static void performAnalysis(Instances data) throws Exception {
        // Filter out string attributes
        Instances numericData = filterNumericAttributes(data);

        // Calculate profit (difference between Open and Previous Close)
        HashMap<String, Double> profitMap = new HashMap<>();

        // Identify companies with profit and loss compared to previous close
        int numCompanies = numericData.numInstances();
        int numProfit = 0;
        int numLoss = 0;
        double highestProfit = Double.NEGATIVE_INFINITY;
        double lowestProfit = Double.POSITIVE_INFINITY;
        String highestProfitCompany = "";
        String lowestProfitCompany = "";
        String industryOfHighestProfit = "";
        String industryOfLowestProfit = "";

        for (int i = 0; i < numCompanies; i++) {
            double openPrice = numericData.instance(i).value(1); // Index 1 corresponds to Open
            double previousClose = numericData.instance(i).value(4); // Index 4 corresponds to Previous Close
            double profit = openPrice - previousClose;

            // Update profit and loss counts
            if (profit > 0) {
                numProfit++;
            } else {
                numLoss++;
            }
            // Update highest and lowest profit
            if (profit > highestProfit) {
                highestProfit = profit;
                highestProfitCompany = data.instance(i).stringValue(0); // Company name
                industryOfHighestProfit = data.instance(i).stringValue(2); // Industry
            }
            if (profit < lowestProfit) {
                lowestProfit = profit;
                lowestProfitCompany = data.instance(i).stringValue(0); // Company name
                industryOfLowestProfit = data.instance(i).stringValue(2); // Industry
            }

            profitMap.put(data.instance(i).stringValue(0), profit); // Company name as key
        }

        // Print the number of companies with profit and loss
        System.out.println("Number of companies with profit: " + numProfit);
        System.out.println("Number of companies with loss: " + numLoss);

        // Create donut chart for profit and loss percentages
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Profit Made By No. Of Companies", numProfit);
        dataset.setValue("Loss Made By No. Of Companies", numLoss);
        JFreeChart chart = ChartFactory.createRingChart("Number Of Companies Made Profit/Loss Percentage", dataset, true, true, false);
        

        // Display the donut chart
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Profit/Loss Percentage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);

        // Print the company with the highest profit and its industry
        System.out.println("\nCompany with the highest profit compared to previous close:");
        System.out.println("Company: " + highestProfitCompany);
        System.out.println("Industry: " + industryOfHighestProfit);
        System.out.println("Profit: " + highestProfit);

        // Print the company with the lowest profit and its industry
        System.out.println("\nCompany with the lowest profit compared to previous close:");
        System.out.println("Company: " + lowestProfitCompany);
        System.out.println("Industry: " + industryOfLowestProfit);
        System.out.println("Profit: " + lowestProfit);
        System.out.println("--------------------------------------------------------------------");

    }

    private static Instances filterNumericAttributes(Instances data) {
        // Create a new Instances object to store numeric data
        Instances numericData = new Instances(data);

        // Remove string attributes
        for (int i = data.numAttributes() - 1; i >= 0; i--) {
            if (data.attribute(i).isString()) {
                numericData.deleteAttributeAt(i);
            }
        }

        return numericData;
    }
        } 
    
