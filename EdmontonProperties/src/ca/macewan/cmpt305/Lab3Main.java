package ca.macewan.cmpt305;

import java.text.NumberFormat;
import java.util.*;

public class Lab3Main {

    public static void main(String[] args) {
        Scanner myCSV = new Scanner(System.in);
        NumberFormat nF  = NumberFormat.getCurrencyInstance(Locale.US);
        nF.setMaximumFractionDigits(0);
        System.out.print("CSV filename: ");
        String csvFileName = myCSV.nextLine();
        CSVPropertyAssessment properties = new CSVPropertyAssessment(csvFileName);


        System.out.print("Assessment class: ");
        String assessmentClassType = myCSV.nextLine();
        CSVPropertyAssessment assessmentClassData = (CSVPropertyAssessment) properties.getByAssessmentClassData(assessmentClassType);
        System.out.println("n = " + assessmentClassData.size());
        System.out.println("min = " + nF.format(assessmentClassData.getMinimumAssessedValue()));
        System.out.println("max = " + nF.format(assessmentClassData.getMaximumAssessedValue()));
        System.out.println("range = " + nF.format(assessmentClassData.getRange()));
        System.out.println("mean = " + nF.format(assessmentClassData.getMean()));
        System.out.println("median = " + nF.format(assessmentClassData.getMedian()));


    }
}