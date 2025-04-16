package test;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.junit.Test;

import static org.junit.Assert.*;

public class YearTest {
    Year year;

    private void arrange() {
        year = new Year();
    }
    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }
    @Test
    public void GetYearTest() {
        year = new Year(2024);
        assertEquals(2024, year.getYear());

    }
    @Test
    public void PreviousYearTestValid() {
        year = new Year(2025);
        assertEquals(2024, ((Year) year.previous()).getYear());
    }
    @Test
    public void PreviousYearTest1900() { //Bug Take care !!!!!!!!!!!
        year = new Year(1900);
        assertNull(((Year) year.previous()).getYear());
    }
    @Test
    public void NextYearValid() {
        year = new Year(2025);
        assertEquals(2026, ((Year) year.next()).getYear());
    }
    @Test
    public void NextYear9999() {
        year = new Year(9999);
        assertNull(year.next());
    }
    @Test
    public void GetSerialIndexTest() {
        year = new Year(2022);
        assertEquals(2022L, year.getSerialIndex());


    }
    @Test
    public void testToStringReturnsYearAsString() {
        Year year = new Year(2024);
        assertEquals("2024", year.toString());
    }
    @Test
    //Bug it should return 2537 based on the calc hash code= 31*17+year
    public void HashTest() {
        Year year = new Year(2010);
        assertEquals(2537, year.hashCode());
    }



}