package ca.macewan.cmpt305;

import java.util.*;
import java.io.*;

public class PropertyAssessment implements Comparable<PropertyAssessment> {
    private int accountNumber;
    private Address address;
    private Neighbourhood neighbourhood;
    private int assessedValue;

    private Location location;
    private List<AssessmentClass> assessmentClass;
    private boolean garage;

    public PropertyAssessment(int accountNumber, Address address, boolean garage, Neighbourhood neighbourhood,
                              int assessedValue, Location location, List<AssessmentClass> assessmentClass) {

        this.accountNumber = accountNumber;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.assessedValue = assessedValue;
        this.location = location;
        this.assessmentClass = new ArrayList<>(assessmentClass);
        this.garage = garage;
    }
    @Override
    public String toString() {
        return accountNumber + ", " + address + ", " + neighbourhood + ", "
                + assessedValue + "," + location + "," + assessmentClass;
    }
    public int compareTo(PropertyAssessment assessment) {
        return Integer.compare(assessedValue, assessment.getAssessedValue());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof PropertyAssessment)){
            return false;
        }
        PropertyAssessment other = (PropertyAssessment) obj;
        return accountNumber == other.accountNumber
                && address.equals(other.address)
                && neighbourhood.equals(other.neighbourhood)
                && assessedValue == other.assessedValue
                && location.equals(other.location)
                && assessmentClass.equals(other.assessmentClass);

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + accountNumber;
        result = 31 * result + address.hashCode();
        result = 31 * result + neighbourhood.hashCode();
        result = 31 * result + assessedValue;
        result = 31 * result + location.hashCode();
        result = 31 * result + assessmentClass.hashCode();
        return result;
    }

    public int getAccountNumber() {return accountNumber;}

    public Address getAddress() {return address;}

    public Neighbourhood getNeighbourhood() {return neighbourhood;}

    public int getAssessedValue() {return assessedValue;}

    public Location getLocation() {return location;}

    public List<AssessmentClass> getAssessmentClass() {return assessmentClass;}

    public boolean hasGarage() {return garage;}


}
