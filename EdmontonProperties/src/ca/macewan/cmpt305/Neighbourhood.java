package ca.macewan.cmpt305;

public class Neighbourhood {
    private String neighbourhoodID;
    private String neighbourhood;
    private String neighbourhoodWard;

    public Neighbourhood(String neighbourhoodID, String neighbourhood, String neighbourhoodWard) {
        this.neighbourhoodID = neighbourhoodID;
        this.neighbourhood = neighbourhood;
        this.neighbourhoodWard = neighbourhoodWard;

    }

    @Override
    public String toString() {return neighbourhood + " (" + neighbourhoodWard + ")";}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Neighbourhood)){
            return false;
        }
        Neighbourhood other = (Neighbourhood) obj;
        return neighbourhoodID.equals(other.neighbourhoodID)
                && neighbourhood.equals(other.neighbourhood)
                && neighbourhoodWard.equals(other.neighbourhoodWard);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + neighbourhoodID.hashCode();
        result = 31 * result + neighbourhood.hashCode();
        result = 31 * result + neighbourhoodWard.hashCode();
        return result;
    }

    public String getNeighbourhood() {return neighbourhood;}

    public String getNeighbourhoodID() {return neighbourhoodID;}

    public String getNeighbourhoodWard() {return neighbourhoodWard;}
}
