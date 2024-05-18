package com.example;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import java.io.File;

public class DataLoader {
    public static Instances loadData(String filePath) throws Exception {
        // Load data from CSV file
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filePath));
        return loader.getDataSet();
    }
}
