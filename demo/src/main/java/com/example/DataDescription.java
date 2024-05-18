package com.example;

import weka.core.Instances;
import weka.core.Attribute;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DataDescription {
    public static void describeData(Instances data) {
        // Print dataset size
        System.out.println("Dataset Size: " + data.numInstances() + " rows");

        // Print column names and their data types
        System.out.println("\nDataset Columns:");
        System.out.println("Index\tName\t\t\tType");
        System.out.println("-----\t----\t\t\t----");

        for (int i = 0; i < data.numAttributes(); i++) {
            Attribute attribute = data.attribute(i);
            String type = attribute.isNumeric() ? "Numeric" : attribute.isNominal() ? "Nominal" : "Other";
            System.out.printf("%-5d\t%-20s\t%-10s%n", i, attribute.name(), type);
        }

        // Print basic statistics for numeric attributes
        System.out.println("\nNumeric Attribute Statistics:");
        System.out.println("Attribute\tMean\t\tStandard Deviation\tMin\t\tMax");
        System.out.println("---------\t----\t\t------------------\t---\t\t---");

        for (int i = 0; i < data.numAttributes(); i++) {
            Attribute attribute = data.attribute(i);

            // Only describe numeric attributes
            if (attribute.isNumeric()) {
                DescriptiveStatistics stats = new DescriptiveStatistics();
                
                // Gather statistics for the current attribute
                for (int j = 0; j < data.numInstances(); j++) {
                    stats.addValue(data.instance(j).value(attribute));
                }

                // Print attribute statistics
                System.out.printf("%-10s\t%-10.2f\t%-20.2f\t%-10.2f\t%-10.2f%n", 
                                  attribute.name(), 
                                  stats.getMean(), 
                                  stats.getStandardDeviation(), 
                                  stats.getMin(), 
                                  stats.getMax());
            }
        }
    }
}
