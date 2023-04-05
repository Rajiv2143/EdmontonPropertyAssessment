package ca.macewan.cmpt305;

import java.util.Objects;

public class AssessmentClass {
    private String assessmentClass;
    private int pct;

    public AssessmentClass(String assessmentClass, int pct){
        this.assessmentClass = assessmentClass;
        this.pct = pct;
    }
    @Override
    public String toString() {
        return assessmentClass + " " + pct + "%";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof AssessmentClass)){
            return false;
        }
        AssessmentClass other = (AssessmentClass) obj;
        return pct == other.pct
                && assessmentClass.equals(other.assessmentClass);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + pct;
        result = 31 * result + assessmentClass.hashCode();
        return result;
    }

    public String getAssessmentClass() {return assessmentClass;}

    public int getPercentage() {return pct;}
}
