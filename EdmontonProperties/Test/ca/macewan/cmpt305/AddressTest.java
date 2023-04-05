package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address addressOne;
    private Address addressTwo;
    private Address addressThree;
    private Address addressThreeCopy;

    @BeforeEach
    void setUp() {
        addressOne = new Address("220", "1805", "111A STREET NW");
        addressTwo = new Address("305", "10524", "78 AVENUE NW");
        addressThree = new Address("15212", "RAMSAY CRESCENT NW");
        addressThreeCopy = new Address("15212", "RAMSAY CRESCENT NW");
    }
    @Test
    void testToString() {
        assertEquals("220 1805 111A STREET NW", addressOne.toString());
        assertEquals("305 10524 78 AVENUE NW", addressTwo.toString());
        assertEquals("15212 RAMSAY CRESCENT NW", addressThree.toString());

    }

    @Test
    void testEquals() {
        //reflexive
        assertTrue(addressOne.equals(addressOne));
        assertTrue(addressTwo.equals(addressTwo));
        //symmetric
        assertTrue(addressThree.equals(addressThreeCopy));
        assertTrue(addressThreeCopy.equals(addressThree));
        //transitive
        Address addressThreeAgain = new Address("15212", "RAMSAY CRESCENT NW");
        assertTrue(addressThree.equals(addressThreeAgain));
        assertTrue(addressThreeCopy.equals(addressThreeAgain));
    }

    @Test
    void testHashCode() {
        //Equal objects -> equal hashcodes
        assertEquals(addressThree.hashCode(),addressThreeCopy.hashCode());
    }


    @Test
    void getSuite() {
        assertEquals("220", addressOne.getSuite());
    }

    @Test
    void getHouseNumber() {
        assertEquals("1805", addressOne.getHouseNumber());
    }

    @Test
    void getStreetName() {
        assertEquals("111A STREET NW", addressOne.getStreetName());
    }
}