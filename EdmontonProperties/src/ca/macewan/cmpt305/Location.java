package ca.macewan.cmpt305;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = checkRange(latitude,-90, 90);
        this.longitude = checkRange(longitude,-180,180);


    }
    @Override
    public String toString() {return "(" + latitude + ", " + longitude + ")";}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Location)){
            return false;
        }
        Location other = (Location) obj;
        return longitude == other.longitude
                && latitude == other.latitude;

    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(latitude);
        result = 31 * result + Double.hashCode(longitude);
        return result;
    }

    public static double checkRange(double value, double min, double max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Latitude/Longitude: Invalid Value");
        }
        return value;
    }


    public double getLongitude() {return longitude;}

    public double getLatitude() {return latitude;}
}
