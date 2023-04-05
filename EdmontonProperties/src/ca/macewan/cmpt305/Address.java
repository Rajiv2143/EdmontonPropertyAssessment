package ca.macewan.cmpt305;

public class Address {
    private String suite;
    private String houseNumber;
    private String streetName;

    public Address(String houseNumber, String streetName) {
        this("", houseNumber, streetName);
    }

    public Address(String suite, String houseNumber, String streetName) {
        this.suite = suite;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        if (suite.length() == 0) {
            return houseNumber + " " + streetName;
        }
        return suite + " " + houseNumber + " " + streetName;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Address)){
            return false;
        }
        Address other = (Address) obj;
        return suite.equals(other.suite)
                && houseNumber.equals(other.houseNumber)
                && streetName.equals(other.streetName);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + suite.hashCode();
        result = 31 * result + houseNumber.hashCode();
        result = 31 * result + streetName.hashCode();
        return result;
    }

    public String getSuite() {return suite;}

    public String getHouseNumber() {return houseNumber;}

    public String getStreetName() {return streetName;}


}