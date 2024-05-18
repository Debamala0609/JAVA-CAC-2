package com.example;

import weka.core.AttributeStats;
import weka.core.Instances;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class SectorAnalysis {
    public static void performSectorAnalysis(Instances data){
        // Ensure the 'Industry' and 'Last Traded Price' attributes exist in your dataset
        int industryIndex = data.attribute("Industry").index();
        int priceIndex = data.attribute("Last Traded Price").index();

        // Create maps to hold the total last traded price and count for each sector
        Map<String, Double> sectorTotalPrice = new HashMap<>();
        Map<String, Integer> sectorCount = new HashMap<>();

        // Loop through each instance in the dataset
        for (int i = 0; i < data.numInstances(); i++) {
            // Get the industry and last traded price for the current instance
            String industry = data.instance(i).stringValue(industryIndex);
            double price = data.instance(i).value(priceIndex);

            // Update the sector total price and count
            sectorTotalPrice.put(industry, sectorTotalPrice.getOrDefault(industry, 0.0) + price);
            sectorCount.put(industry, sectorCount.getOrDefault(industry, 0) + 1);
        }

        // Calculate the average last traded price for each sector
        Map<String, Double> sectorAveragePrice = new HashMap<>();
        for (String sector : sectorTotalPrice.keySet()) {
            double total = sectorTotalPrice.get(sector);
            int count = sectorCount.get(sector);
            double average = total / count;
            sectorAveragePrice.put(sector, average);
        }

        // Find the sector with the highest average last traded price
        String highestSector = "";
        double highestAveragePrice = Double.MIN_VALUE;
        for (String sector : sectorAveragePrice.keySet()) {
            double averagePrice = sectorAveragePrice.get(sector);
            if (averagePrice > highestAveragePrice) {
                highestAveragePrice = averagePrice;
                highestSector = sector;
            }
        }

        // Find the sector with the lowest average last traded price
        String lowestSector = "";
        double lowestAveragePrice = Double.MAX_VALUE;
        for (String sector : sectorAveragePrice.keySet()) {
            double averagePrice = sectorAveragePrice.get(sector);
            if (averagePrice < lowestAveragePrice) {
                lowestAveragePrice = averagePrice;
                lowestSector = sector;
            }
        }

        // Output the result
        
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println( "Stock information to determine the sectors with the highest and lowest average last traded prices.");
                        
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Sector with the highest average last traded price: " + highestSector);
        System.out.println("Highest average last traded price: " + highestAveragePrice);
        System.out.println("Sector with the lowest average last traded price: " + lowestSector);
        System.out.println("Lowest average last traded price: " + lowestAveragePrice);

        
        System.out.println("---------------------------------------");
        System.out.println("---------------------------------------");
    }
}
