package com.example;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndustryCorrelation {
    public static void calculateIndustryCorrelation(Instances data) {
        // Extract high and low values for each industry
        Map<String, List<Double>> industryHighValues = new HashMap<>();
        Map<String, List<Double>> industryLowValues = new HashMap<>();
        extractHighLowValues(data, industryHighValues, industryLowValues);

        // Calculate correlations between high and low values for each industry
        Map<String, Double> correlationCoefficients = new HashMap<>();
        calculateCorrelations(industryHighValues, industryLowValues, correlationCoefficients);

        // Output correlation coefficients
        displayCorrelationCoefficients(correlationCoefficients);
    }

    private static void extractHighLowValues(Instances data, Map<String, List<Double>> industryHighValues, Map<String, List<Double>> industryLowValues) {
        Attribute industryAttribute = data.attribute("Industry");
        Attribute highAttribute = data.attribute("High");
        Attribute lowAttribute = data.attribute("Low");

        for (int i = 0; i < data.numInstances(); i++) {
            String industry = data.instance(i).stringValue(industryAttribute);
            double highValue = data.instance(i).value(highAttribute);
            double lowValue = data.instance(i).value(lowAttribute);

            industryHighValues.computeIfAbsent(industry, k -> new ArrayList<>()).add(highValue);
            industryLowValues.computeIfAbsent(industry, k -> new ArrayList<>()).add(lowValue);
        }
    }

    private static void calculateCorrelations(Map<String, List<Double>> industryHighValues, Map<String, List<Double>> industryLowValues, Map<String, Double> correlationCoefficients) {
        for (String industry : industryHighValues.keySet()) {
            List<Double> highValues = industryHighValues.get(industry);
            List<Double> lowValues = industryLowValues.get(industry);

            if (highValues.size() >= 2 && lowValues.size() >= 2) {
                double correlation = calculateCorrelationCoefficient(highValues, lowValues);
                correlationCoefficients.put(industry, correlation);
            }
        }
    }

    private static double calculateCorrelationCoefficient(List<Double> values1, List<Double> values2) {
        double[] arr1 = values1.stream().mapToDouble(Double::doubleValue).toArray();
        double[] arr2 = values2.stream().mapToDouble(Double::doubleValue).toArray();
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        return correlation.correlation(arr1, arr2);
    }

    private static void displayCorrelationCoefficients(Map<String, Double> correlationCoefficients) {
        System.out.println("Correlation Coefficients between High and Low Values for Each Industry:");
        for (Map.Entry<String, Double> entry : correlationCoefficients.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
