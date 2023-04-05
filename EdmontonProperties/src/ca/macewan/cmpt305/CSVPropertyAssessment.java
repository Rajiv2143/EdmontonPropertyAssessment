package ca.macewan.cmpt305;

import java.math.BigInteger;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class CSVPropertyAssessment implements PropertyAssessmentInterface {
    private List<PropertyAssessment> properties;

    private String csvFileName;

    private PropertyAssessmentInterface pai;

    private PropertyAssessmentStats result = new PropertyAssessmentStats();



    public CSVPropertyAssessment(String csvFileName) {
        this.csvFileName = csvFileName;
        this.properties = new ArrayList<>();
        readData(csvFileName);
    }

    public CSVPropertyAssessment(List<PropertyAssessment> propertyAssessmentList) {
        this.properties = new ArrayList<>(propertyAssessmentList);
    }

    public CSVPropertyAssessment() throws IOException {
        this("Property_Assessment_Data_2022.csv");
    }


    public void readData(String csvFileName) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileName));

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                properties.add(CreateProperty.CreatePropertyAssessment(line));
            }
        } catch (IOException ioe) {
            System.out.println("File is invalid");
        }
    }

    @Override
    public String toString() {
        return csvFileName;
    }

    public List<PropertyAssessment> getData() {
        return new ArrayList<>(properties);
    }

    public List<PropertyAssessment> getData(int maxRecords) {
        return getData(maxRecords,0);
    }
    public List<PropertyAssessment> getData(int maxRecords, int startIndex) {
        List<PropertyAssessment> maxData = new ArrayList<>();
        maxRecords = Math.min(startIndex + maxRecords, properties.size());

        for (int i = startIndex; i < maxRecords; i++){
            maxData.add(properties.get(i));
        }
        return maxData;
    }


    //Filters data based on neighbourhood string given by user
    public List<PropertyAssessment> getByNeighbourhoodData(String neighbourhood) {
        List<PropertyAssessment> neighbourhoodProperties = new ArrayList<>();
        for (PropertyAssessment p : properties) {
            if (p.getNeighbourhood().getNeighbourhood().equalsIgnoreCase(neighbourhood)) {
                neighbourhoodProperties.add(p);
            }
        }
        return neighbourhoodProperties;
    }

    //Filters data based on assessment class string given by user
    public List<PropertyAssessment> getByAssessmentClassData(String assessmentClass) {
        Set<PropertyAssessment> assessmentProperties = new HashSet<>();

        for (PropertyAssessment p : properties) {
            List<AssessmentClass> assessmentClassList = p.getAssessmentClass();
            for (AssessmentClass a : assessmentClassList) {
                if (a.getAssessmentClass().equalsIgnoreCase(assessmentClass)) {
                    assessmentProperties.add(p);
                }
            }
        }

        return new ArrayList<>(assessmentProperties);
    }

    public PropertyAssessment getByAccountNumber(int accountNumber) {
        for (PropertyAssessment p : properties) {
            if (p.getAccountNumber() == accountNumber) {
                return p;
            }
        }
        return null;
    }

    public int getMinimumAssessedValue() {
        return result.minimumAssessedValue(properties);
    }
    public int getMaximumAssessedValue() {
        return result.maximumAssessedValue(properties);
    }

    public int getRange() {
        return result.rangeValue();
    }

    public BigInteger getMean() {
        return result.mean(properties);
    }

    public int getMedian() {
        return result.median(properties);
    }

    public int size() {return properties.size();}

}
