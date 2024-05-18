package com.example;
import weka.core.Instances;
public class MainClass {
    public static void main(String[] args) {
        try {
            // Path to the CSV file:
            String filePath = "demo\\src\\main\\resources\\n" + //
                                "ifty_500.csv";
            // Load data using DataLoader class:
            Instances data = DataLoader.loadData(filePath);

            // Output loaded data:
            System.out.println("Loaded data:");
            System.out.println(data);
            
            //  Methods and Class:TO PERFORM VARIOUS ANALYSIS
            DataDescription.describeData(data);
            SectorAnalysis.performSectorAnalysis(data);
            StatisticalAnalysis.performStatisticalAnalysisByIndustry(data);
            EachSectorAnalysis.performEachSectorAnalysis(data);
            IndustryCorrelation.calculateIndustryCorrelation(data);
            HypothesisTesting.performANOVA(data);
            StockAnalysis.performAnalysis(data);
            ShareVolumeAndMarketValueAnalysis. performStatisticalAnalysisByIndustry(data);
            ValueAnalysis.performAnalysis(data);

            Instances data1 = DataLoader.loadData(filePath);

            // Clean data by removing the first four columns
            Instances cleanedData = DataCleaning.removeColumns(data1);

            // Output cleaned data:
            System.out.println("Cleaned data:");
            // System.out.println(cleanedData);
            // KMeansClustering.performKMeansClustering(cleanedData, 3);
            LinearRegressionModel.buildAndEvaluateModel(cleanedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}