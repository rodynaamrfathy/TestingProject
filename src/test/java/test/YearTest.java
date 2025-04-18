package test;

import org.jfree.data.time.Year;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class YearTest {
    Year year;
    Date time;
    Calendar calendar;
    TimeZone zone;


    // Class Year public Year () TC-01
    @Test
    public void testNoArgConstructor() {
        year = new Year();
        assertEquals(2025, year.getYear());
    }
    ///////////////////////////////////////////////////
    // Class Year public Year (int year)

    // Class Year public Year (int year) TC-01
    // Maximum valid year (9999)
    @Test
    public void testYearParameterizedDateConstructorMax() {
        year = new Year(9999);
        assertEquals(9999, year.getYear());
    }

    // Class Year public Year (int year) TC-02
    // Minimum valid year (1900)
    @Test
    public void testYearParameterizedDateConstructorMin() {
        year = new Year(1900);
        assertEquals(1900, year.getYear());
    }

    // Class Year public Year (int year) TC-03
    // A typical year in the middle (e.g., 2024)
    @Test
    public void testYearParameterizedDateConstructor() {
        year = new Year(2024);
        assertEquals(2024, year.getYear());
    }

    // Class Year public Year (int year) TC-04
    // A Lower that Bound case (1899)
    // BUG it only works within range 1900 to 9999
    @Test (expected = Exception.class)
    public void testParameterizedDateConstructorBug() {
        year = new Year(1899);
        assertEquals(1899, year.getYear());
    }

    // Class Year public Year (int year) TC-05
    // Null Date Test
    @Test(expected = IllegalArgumentException.class)
    public void testParameterizedDateConstructorNull() {
        new Year((Date) null);
    }
    ///////////////////////////////////////////////////

    // Class Year public Year (Date time)

    // Class Year public Year (Date time) TC-01
    // Basic Date Conversion (e.g., 1/1/2021)
    @Test
    public void testYearWithTimeDateBasic() throws Exception {
        calendar = Calendar.getInstance();
        calendar.set(2005, Calendar.JANUARY, 7);
        time = calendar.getTime();
        year = new Year(time);
        assertEquals(2005, year.getYear());
    }

    // Class Year public Year (Date time) TC-02
    // Edge Cases
    // January 1 boundary
    @Test
    public void testYearWithTimeBoundariesBeginning() throws Exception {
        calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        year = new Year(calendar.getTime());
        assertEquals(2025, year.getYear());
    }

    // Class Year public Year (Date time) TC-03
    // Edge Cases
    // December 31 boundary
    @Test
    public void testYearWithTimeBoundariesEnd() throws Exception {
        calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.DECEMBER, 31, 23, 59, 59);
        year = new Year(calendar.getTime());
        assertEquals(2025, year.getYear());
    }
    ///////////////////////////////////////////////////

    // Class Year public Year (Date time, TimeZone zone)

    // Class Year public Year (Date time, TimeZone zone) TC-01
    // Basic Functionality Test
    @Test
    public void InstantYearTimeZone() throws Exception {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2021, Calendar.DECEMBER, 31,21,0,0 );
        time = calendar.getTime();
        zone= TimeZone.getTimeZone("ASIA/TOKYO");
        year = new Year(time, zone, Locale.getDefault());
        assertEquals(2021, year.getYear());
    }

    // Class Year public Year (Date time, TimeZone zone) TC-02
    // Time Zone Boundary Tests New York (UTC-5)
    @Test
    public void InstantYearTimeZoneBoundaryNeg5() throws Exception {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        time = calendar.getTime();
        zone= TimeZone.getTimeZone("America/New_York");
        year = new Year(time, zone, Locale.getDefault());
        // New York (UTC-5) should still be 2024
        assertEquals(2024, year.getYear());
    }

    // Class Year public Year (Date time, TimeZone zone) TC-03
    // Time Zone Boundary Tests
    @Test
    public void InstantYearTimeZoneBoundaryPlus9() throws Exception {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        time = calendar.getTime();
        zone= TimeZone.getTimeZone("Asia/Tokyo");
        year = new Year(time, zone, Locale.getDefault());
        // Tokyo (UTC+9) should be 2025
        assertEquals(2025, year.getYear());
    }

    // Class Year public Year (Date time, TimeZone zone) TC-04
    // Null test case
    @Test(expected = IllegalArgumentException.class)
    public void testNullDate() {
        new Year(null, TimeZone.getDefault(), Locale.getDefault());
    }

    ///////////////////////////////////////////////////

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
    @Test
    public void GetFirstMilliSecondTest() {
        Year year = new Year(2021);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long milliSecondTime = calendar.getTimeInMillis();
        assertEquals(milliSecondTime, year.getFirstMillisecond(calendar));
    }
    @Test
    public void GetLastMilliSecondTest() {
        Year year = new Year(2021);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long milliSecondTime = calendar.getTimeInMillis();
        assertEquals(milliSecondTime, year.getLastMillisecond(calendar));
    }
    @Test
    public void EqualsTestTwoValues() {
        Year y1 = new Year(2024);
        Year y2 = new Year(2024);
        assertTrue(y1.equals(y2));


    }
    @Test
    public void EqualsTestoneValue() {
        Year y1 = new Year(2024);
        assertTrue(y1.equals(y1));


    }
    @Test
    public void testEquals_DifferentClass() {
        Year y1 = new Year(2024);
        Object Compare = "2024";
        assertFalse(y1.equals(Compare));
    }
    @Test
    public void CompareToEqualYearsTest() {
        Year y1 = new Year(2024);
        Year y2 = new Year(2024);
        assertEquals(0, y1.compareTo(y2));
    }

    @Test
    public void CompareToBeforeTest() {
        Year y1 = new Year(2023);
        Year y2 = new Year(2024);
        assertTrue(y1.compareTo(y2) < 0);
    }

    @Test
    public void CompareToAfterTest() {
        Year y1 = new Year(2025);
        Year y2 = new Year(2024);
        assertTrue(y1.compareTo(y2) > 0);
    }
    @Test
    //Bug should throw an exception but nth was thrown
    public void CompareToDifferentClassTest() {
        Year y1 = new Year(2024);
        Object notAYear = "2024";

        assertThrows(ClassCastException.class, () -> {
            y1.compareTo(notAYear);
        });
    }

    ///////////////////////////////////////////////////
    // Year parseYear(java.lang.String s)

    // Year parseYear(java.lang.String s) TC-01
    //Test with valid 4-digit year string Input
    @Test
    public void parseTest() {
        String test = "2024";
        assertEquals(2024, Year.parseYear(test).getYear());

    }

    // Year parseYear(java.lang.String s) TC-01
    //Test with valid 4-digit year string Input
    @Test
    //Bug Not Sure
    public void parseTest2NotParsable() {
        String test = "202a";
        assertNull(Year.parseYear(test));
    }




    //year parse

    // Valid test cases
    //Test with valid 4-digit year string Input: "2023"

    // Test with valid 4-digit year string with spaces Input: " 2023 "

    // Test with minimum valid year Input: "1900" -> Boundary

    // Test with maximum valid year Input: "9999" -> Boundary


    //Invalid input

    //Test with non-numeric string Input: "abcd"

    //Test with alphanumeric string letters + numbers Input: "2023a"

    // Test with empty string Input: " "

    // Test with year below minimum valid Input: "1899"

    // Test with year above maximum valid Input: "10000"

    // Test with zero year Input: "0"

    // Test with negative year Input: "-2023"




}