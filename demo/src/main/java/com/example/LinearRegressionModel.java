package com.example;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;

public class LinearRegressionModel {
    public static void buildAndEvaluateModel(Instances data) throws Exception {
        // Set the target attribute (52 Week Low in this case)
        int targetIndex = data.attribute("52 Week Low").index();
        data.setClassIndex(targetIndex);

        // Initialize the linear regression model
        LinearRegression model = new LinearRegression();

        // Build the model
        model.buildClassifier(data);

        // Evaluate the model
        Evaluation eval = new Evaluation(data);
        eval.evaluateModel(model, data);

        // Output model details
        System.out.println("Linear Regression Model:\n" + model);
        
        // Output evaluation results
        System.out.println("\nEvaluation Results:");
        System.out.println("Mean Absolute Error (MAE): " + eval.meanAbsoluteError());
        System.out.println("Root Mean Squared Error (RMSE): " + eval.rootMeanSquaredError());
        System.out.println("Coefficient of Determination (R-squared): " + eval.correlationCoefficient());
        
        // Iterate through each instance to predict and print the actual vs predicted values
        System.out.println("\nPredictions:");
        System.out.println("Instance\tActual Value\tPredicted Value");
        System.out.println("--------\t------------\t---------------");
        
        for (int i = 0; i < data.numInstances(); i++) {
            double actualValue = data.instance(i).classValue();
            double predictedValue = eval.predictions().get(i).predicted();
            System.out.printf("%-8d\t%-12.2f\t%-15.2f%n", i + 1, actualValue, predictedValue);
        }
    }
}
