package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentClassTest {
    private AssessmentClass assessmentOne;
    private AssessmentClass assessmentTwo;
    private AssessmentClass assessmentThree;
    private AssessmentClass assessmentThreeCopy;

    @BeforeEach
    void setUp() {
        assessmentOne = new AssessmentClass("RESIDENTIAL", 100);
        assessmentTwo = new AssessmentClass("COMMERCIAL", 29);
        assessmentThree = new AssessmentClass("FARMLAND", 2);
        assessmentThreeCopy = new AssessmentClass("FARMLAND", 2);
    }
    @Test
    void testToString() {
        assertEquals("RESIDENTIAL 100%", assessmentOne.toString());
        assertEquals("COMMERCIAL 29%", assessmentTwo.toString());
        assertEquals("FARMLAND 2%", assessmentThree.toString());
    }

    @Test
    void testEquals() {
        // reflexive
        assertTrue(assessmentOne.equals(assessmentOne));
        // symmetric
        assertTrue(assessmentThree.equals(assessmentThreeCopy));
        assertTrue(assessmentThreeCopy.equals(assessmentThree));
        // transitive
        AssessmentClass assessmentThreePartTwo = new AssessmentClass("FARMLAND", 2);
        assertTrue(assessmentThree.equals(assessmentThreeCopy));
        assertTrue(assessmentThreeCopy.equals(assessmentThreePartTwo));
        assertTrue(assessmentThreePartTwo.equals(assessmentThree));
        //false results
        assertFalse(assessmentOne.equals(assessmentTwo));
        assertFalse(assessmentOne.equals(assessmentThree));
        assertFalse(assessmentThree.equals(assessmentTwo));
    }

    @Test
    void testHashCode() {
        assertTrue(assessmentThree.hashCode() == assessmentThreeCopy.hashCode());
    }

    @Test
    void getAssessmentClass() {
        assertEquals("RESIDENTIAL", assessmentOne.getAssessmentClass());
        assertEquals("FARMLAND", assessmentThree.getAssessmentClass());
    }

    @Test
    void getPercentage() {
        assertEquals(2, assessmentThree.getPercentage());
        assertEquals(29, assessmentTwo.getPercentage());
    }
}