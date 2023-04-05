package ca.macewan.cmpt305;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyAssessmentStats {
    private int minValue = 0;

    private int maxValue = 0;

    private String csvFileName;

    private List<PropertyAssessment> properties;

    private PropertyAssessmentInterface pai;




    public PropertyAssessmentStats(String csvFilename) {
        this.csvFileName = csvFilename;
        this.pai = new CSVPropertyAssessment(csvFileName);
        this.properties = pai.getData();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public PropertyAssessmentStats() {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    public int minimumAssessedValue(List<PropertyAssessment> propertyAssessmentsList) {
        for (PropertyAssessment p: propertyAssessmentsList) {
            if (p.getAssessedValue() < minValue) {
                minValue = p.getAssessedValue();
            }
        }
        return minValue;
    }

    public int maximumAssessedValue(List<PropertyAssessment> propertyAssessmentsList) {
        for (PropertyAssessment p: propertyAssessmentsList) {
            if (p.getAssessedValue() > maxValue) {
                maxValue = p.getAssessedValue();
            }
        }
        return maxValue;
    }

    public int rangeValue() {
        return maxValue - minValue;
    }
    public BigInteger total(List<PropertyAssessment> propertyAssessmentsList) {
        BigInteger Total = BigInteger.valueOf(0);
        for (PropertyAssessment p: propertyAssessmentsList) {
            Total = Total.add(BigInteger.valueOf((p.getAssessedValue())));
        }
        return Total;
    }

    public BigInteger mean(List<PropertyAssessment> propertyAssessmentsList) {
        BigInteger totalRecords = BigInteger.valueOf(propertyAssessmentsList.size());
        return total(propertyAssessmentsList).divide(totalRecords);
    }

    public List<Integer> sortData(List<PropertyAssessment> propertyAssessmentsList) {
        List<Integer> temp = new ArrayList<Integer>();
        for (PropertyAssessment p: propertyAssessmentsList) {
            temp.add(p.getAssessedValue());
        }
        Collections.sort(temp);
        return temp;
    }

    public Integer median(List<PropertyAssessment> propertyAssessmentsList) {
        List<Integer> temp = sortData(propertyAssessmentsList);

        if (propertyAssessmentsList.size() %2 == 0){
            return (temp.get(propertyAssessmentsList.size()/2) + temp.get((propertyAssessmentsList.size()/2) + 1))/2;
        }
        else{
            return temp.get(propertyAssessmentsList.size()/2);
        }

    }
    public CSVPropertyAssessment getAssessmentData(String assessmentClass) {
        List<PropertyAssessment> assessmentProperties = pai.getByAssessmentClassData(assessmentClass);
        if (assessmentProperties.size() == 0){return null;}
        return new CSVPropertyAssessment(assessmentProperties);
    }
    public CSVPropertyAssessment getNeighbourhoodData(String neighbourhood) {
        List<PropertyAssessment> neighbourhoodProperties = pai.getByNeighbourhoodData(neighbourhood);
        if (neighbourhoodProperties.size() == 0){return null;}
        return new CSVPropertyAssessment(neighbourhoodProperties);

    }



}

