package com.example;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import weka.core.Instances;

public class ShareVolumeAndMarketValueAnalysis {
    public static void performStatisticalAnalysisByIndustry(Instances data) {
        // Assuming the data has attributes named "ShareVolume" and "MarketValue"
        // Get the attribute indices
        int shareVolumeIndex = data.attribute("Share Volume").index();
        int marketValueIndex = data.attribute("Value (Indian Rupee)").index();
        
        // Extract share volume and market value data
        double[] shareVolume = data.attributeToDoubleArray(shareVolumeIndex);
        double[] marketValue = data.attributeToDoubleArray(marketValueIndex);

        // Call the analysis function
        performShareVolumeAndMarketValueAnalysis(shareVolume, marketValue);
    }

    private static void performShareVolumeAndMarketValueAnalysis(double[] shareVolume, double[] marketValue) {
        // Calculate basic statistics for share volume
        DescriptiveStatistics volumeStats = new DescriptiveStatistics(shareVolume);

        // Calculate basic statistics for market value (market cap)
        DescriptiveStatistics valueStats = new DescriptiveStatistics(marketValue);

        // Output the statistics for share volume
        System.out.println("Share Volume Statistics:");
        System.out.println("Mean: " + volumeStats.getMean());
        System.out.println("Standard Deviation: " + volumeStats.getStandardDeviation());
        System.out.println("Min: " + volumeStats.getMin());
        System.out.println("Max: " + volumeStats.getMax());

        // Output the statistics for market value (market cap)
        System.out.println("\nMarket Value Statistics:");
        System.out.println("Mean: " + valueStats.getMean());
        System.out.println("Standard Deviation: " + valueStats.getStandardDeviation());
        System.out.println("Min: " + valueStats.getMin());
        System.out.println("Max: " + valueStats.getMax());
    }
}
