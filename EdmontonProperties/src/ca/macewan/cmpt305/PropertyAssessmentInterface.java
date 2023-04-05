package ca.macewan.cmpt305;

import java.util.List;

public interface PropertyAssessmentInterface {
    /**
     *
     * @param neighbourhood
     * @return A List containing Property Assessments filtered by neighbourhood
     *         or null if an exception is caught
     */
    List<PropertyAssessment> getByNeighbourhoodData(String neighbourhood);

    /**
     *
     * @return A List containing Property Assessments
     */
    List<PropertyAssessment> getData();

    /**
     * Information on limits and offsets retried from here
     * https://dev.socrata.com/docs/paging.html#2.1
     * @param maxRecords
     * @return A List containing Property Assessments of maxRecords size
     *         or null if an exception is caught
     */

    List<PropertyAssessment> getData(int maxRecords);

    /**
     * Information on limits and offsets retried from here
     * https://dev.socrata.com/docs/paging.html#2.1
     * @param maxRecords
     * @param startIndex
     * @return A List containing Property Assessments of maxRecords size
     * and/or a starting Index within the data or null if an exception is caught
     */

    List<PropertyAssessment> getData(int maxRecords, int startIndex);

    /**
     *
     * @param assessmentClass
     * @return A List of Property Assessments filtered by Assessment Class
     *         or null if an exception is caught
     */

    List<PropertyAssessment> getByAssessmentClassData(String assessmentClass);

    /**
     *
     * @param accountNumber
     * @return A Property Assessment or returns null.
     */
    PropertyAssessment getByAccountNumber(int accountNumber);

}
