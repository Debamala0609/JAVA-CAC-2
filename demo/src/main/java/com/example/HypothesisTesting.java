package com.example;

import org.apache.commons.math3.stat.inference.OneWayAnova;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HypothesisTesting {
    public static void performANOVA(Instances data) {
        // Check if the 'Industry' and 'Last Traded Price' attributes exist in your dataset
        Attribute industryAttribute = data.attribute("Industry");
        Attribute priceAttribute = data.attribute("Share Volume");

        if (industryAttribute == null || priceAttribute == null) {
            System.err.println("Required attributes not found in the dataset.");
            return;
        }

        // Prepare data for ANOVA
        Map<String, List<Double>> industryPriceMap = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String industry = data.instance(i).stringValue(industryAttribute);
            double price = data.instance(i).value(priceAttribute);

            if (!industryPriceMap.containsKey(industry)) {
                industryPriceMap.put(industry, new ArrayList<>());
            }
            industryPriceMap.get(industry).add(price);
        }

        // Perform ANOVA for each pair of industries
        List<String> industries = new ArrayList<>(industryPriceMap.keySet());
        for (int i = 0; i < industries.size() - 1; i++) {
            for (int j = i + 1; j < industries.size(); j++) {
                String industry1 = industries.get(i);
                String industry2 = industries.get(j);

                // Extract samples for the two industries
                List<double[]> samples = new ArrayList<>();
                samples.add(listToArray(industryPriceMap.get(industry1)));
                samples.add(listToArray(industryPriceMap.get(industry2)));

                // Perform ANOVA if there are at least two categories with two or more values
                if (samples.get(0).length >= 2 && samples.get(1).length >= 2) {
                    OneWayAnova anova = new OneWayAnova();
                    double pValue = anova.anovaPValue(samples);

                    // Output result
                    System.out.println("Hypothesis Testing between " + industry1 + " and " + industry2);
                    System.out.println("ANOVA p-value: " + pValue);
                    if (pValue < 0.05) {
                        System.out.println("There is significant evidence to reject the null hypothesis.");
                    } else {
                        System.out.println("There is no significant evidence to reject the null hypothesis.");
                    }
                    System.out.println("-----------------------------");
                } else {
                    System.out.println("Insufficient data for ANOVA between " + industry1 + " and " + industry2 + ".");
                }
            }
        }
    }

    // Utility method to convert List<Double> to double[]
    private static double[] listToArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
