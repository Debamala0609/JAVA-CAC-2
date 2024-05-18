package com.example;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class DataCleaning {
    public static Instances removeColumns(Instances data) throws Exception {
        // Remove the first four columns (1-4)
        String[] options = new String[]{"-R", "1-4"}; // Removing first 4 columns
        Remove remove = new Remove();
        remove.setOptions(options);
        remove.setInputFormat(data);
        Instances cleanedData = Filter.useFilter(data, remove);

        // Remove all string attributes
        options = new String[]{"-R", getStringAttributesIndices(cleanedData)};
        remove = new Remove();
        remove.setOptions(options);
        remove.setInputFormat(cleanedData);
        cleanedData = Filter.useFilter(cleanedData, remove);

        return cleanedData;
    }

    private static String getStringAttributesIndices(Instances data) {
        StringBuilder indices = new StringBuilder();
        for (int i = 0; i < data.numAttributes(); i++) {
            if (data.attribute(i).isString()) {
                if (indices.length() > 0) {
                    indices.append(",");
                }
                indices.append(i + 1);
            }
        }
        return indices.toString();
    }
}
