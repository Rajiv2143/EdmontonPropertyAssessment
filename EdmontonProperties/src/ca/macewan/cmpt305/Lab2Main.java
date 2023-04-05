package ca.macewan.cmpt305;

import java.text.NumberFormat;
import java.util.*;

public class Lab2Main {

    public static void main(String[] args) {
        Scanner myCSV = new Scanner(System.in);
        NumberFormat nF  = NumberFormat.getCurrencyInstance(Locale.US);
        nF.setMaximumFractionDigits(0);
        System.out.print("CSV filename: ");
        String csvFileName = myCSV.nextLine();
        CSVPropertyAssessment properties = new CSVPropertyAssessment(csvFileName);
        //System.out.println(properties.getData());
        System.out.println("n = " + properties.size());
        System.out.println("min = " + nF.format(properties.getMinimumAssessedValue()));
        System.out.println("max = " + nF.format(properties.getMaximumAssessedValue()));
        System.out.println("range = " + nF.format(properties.getRange()));
        System.out.println("mean = " + nF.format(properties.getMean()));
        System.out.println("median = " + nF.format(properties.getMedian()));


        System.out.println();
        System.out.print("Account Number: ");
        int accountNumber = Integer.parseInt(myCSV.nextLine());

        try {
            PropertyAssessment result = properties.getByAccountNumber(accountNumber);

            System.out.println("Address = " + result.getAddress());
            System.out.println("Assessed value = " + nF.format(result.getAssessedValue()));
            System.out.println("Assessment class = " + result.getAssessmentClass());
            System.out.println("Neighbourhood = " + result.getNeighbourhood());
            System.out.println("Location = " + result.getLocation());

        }
        catch (IllegalArgumentException ioe) {
            System.out.println("Failed to find " + csvFileName);
            return;
        }
        System.out.println();
        System.out.print("Neighbourhood: ");
        String neighbourhood = myCSV.nextLine();
        try {
            CSVPropertyAssessment neighbourhoodProperties =  new CSVPropertyAssessment(properties.getByNeighbourhoodData(neighbourhood)) ;
            System.out.println("Statistics (neighbourhood = " + neighbourhood + ")");
            System.out.println("n = " + neighbourhoodProperties.size());
            System.out.println("min = " + nF.format(neighbourhoodProperties.getMinimumAssessedValue()));
            System.out.println("max = " + nF.format(neighbourhoodProperties.getMaximumAssessedValue()));
            System.out.println("range = " + nF.format(neighbourhoodProperties.getRange()));
            System.out.println("mean = " + nF.format(neighbourhoodProperties.getMean()));
            System.out.println("median = " + nF.format(neighbourhoodProperties.getMedian()));

        }
        catch (IllegalArgumentException ioe) {
            System.out.println("Failed to find " + csvFileName);
            return;
        }






    }
}