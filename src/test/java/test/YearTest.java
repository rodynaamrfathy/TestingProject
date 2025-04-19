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

    // Class Year public RegularTimePeriod previous()

    // Class Year public RegularTimePeriod previous() TC-01
    // Basic functionality Test
    @Test
    public void PreviousYearTestValid() {
        year = new Year(2024);
        assertEquals(2023, ((Year) year.previous()).getYear());
    }

    // Class Year public RegularTimePeriod previous() TC-02
    // A Lower that Bound case (1899)
    // BUG it only works within range 1900 to 9999
    @Test (expected = Exception.class)
    public void PreviousYearTestBounds() {
        year = new Year(1899);
        assertEquals(1898, ((Year) year.previous()).getYear());
    }

    // Class Year public RegularTimePeriod previous() TC-03
    // A Lower than Bound case (-2000)
    // BUG: Year accepts invalid lower-bound input (outside 1900–9999) and returns previous() as -2001
    @Test (expected = Exception.class)
    public void PreviousYearTestNegative() {
        year = new Year(-2000);
        assertEquals(-1999, ((Year) year.previous()).getYear());
    }

    // Class Year public RegularTimePeriod previous() TC-04
    // An edge case 1900
    // BUG it should return null not the previous year range 1900-9999
    @Test
    public void PreviousYearTestEdge() {
        year = new Year(1900);
        assertNull(((Year) year.previous()).getYear());
    }
    /////////////////////////////////////////////////////////
    // public RegularTimePeriod next()

    // public RegularTimePeriod next() TC-01
    // Basic functionality
    @Test
    public void NextYearValid() {
        year = new Year(2025);
        assertEquals(2026, ((Year) year.next()).getYear());
    }
    // public RegularTimePeriod next() TC-02
    // Working totally fine with the bound 9999
    @Test
    public void NextYearBounds() {
        year = new Year(9999);
        assertNull(year.next());
    }
    // public RegularTimePeriod next() TC-03
    // Bug shouldn't work with year below the min bounds
    @Test(expected = Exception.class)
    public void NextYearMinBounds() {
        year = new Year(1899);
        assertEquals(1900, ((Year) year.next()).getYear());
    }
    // public RegularTimePeriod next() TC-04
    // Bug shouldn't work with negative values 1900-9999 is the range
    @Test(expected = Exception.class)
    public void NextYearNegative() {
        year = new Year(-2025);
        assertEquals(-2024, ((Year) year.next()).getYear());
    }

    ///////////////////////////////////////////////////

    // Class Year public long getSerialIndex() TC-01
    // Basic functionality
    @Test
    public void GetSerialIndexTest() {
        year = new Year(2022);
        assertEquals(2022L, year.getSerialIndex());
    }
    // Class Year public long getSerialIndex() TC-02
    // Bug shouldn't work with year below the min boundary
    @Test(expected = Exception.class)
    public void GetSerialIndexTestMinBounds() {
        year = new Year(1899);
        assertEquals(1899L, year.getSerialIndex());
    }
    // Class Year public long getSerialIndex() TC-03
    // works well throws an exception
    @Test
    public void GetSerialIndexTestMaxBounds() {
        year = new Year(9999);
        assertEquals(9999L, year.getSerialIndex());
    }
    // Class Year public long getSerialIndex() TC-04
    // works well throws an exception because it shouldnt work with neg values
    @Test(expected = Exception.class)
    public void GetSerialIndexTestNegative() {
        year = new Year(-2000);
        assertEquals(-2000L, year.getSerialIndex());
    }
    // Class Year public long getSerialIndex() TC-05
    // Just above the lower bound (should work fine)
    @Test
    public void GetSerialIndexTestLowerEdge() {
        year = new Year(1900);
        assertEquals(1900L, year.getSerialIndex());
    }
    // Class Year public long getSerialIndex() TC-06
    // Just below the upper bound (should work fine)
    @Test
    public void GetSerialIndexTestUpperEdge() {
        year = new Year(9998);
        assertEquals(9998L, year.getSerialIndex());
    }

    ///////////////////////////////////////////////////
    //toString()

    // toString() TC-01
    // basic
    @Test
    public void testToStringReturnsYearAsString() {
        Year year = new Year(2024);
        assertEquals("2024", year.toString());
    }

    // toString() TC-02
    // Negative Year String Representation
    @Test
    public void testToString_NegativeYear() {
        Year year = new Year(-1000);
        assertEquals("-1000", year.toString());
    }

    // toString() TC-03
    // Minimum Year String Representation
    @Test
    public void testToString_MinimumYear() {
        Year year = new Year(1900);
        assertEquals(String.valueOf(1900), year.toString());
    }

    // toString() TC-04
    // maximum Year String Representation
    @Test
    public void testToString_MaximumYear() {
        Year year = new Year(9999);
        assertEquals(String.valueOf(9999), year.toString());
    }

    // toString() TC-05
    // Zero Year String Representation
    @Test
    public void testToString_ZeroYear() {
        Year year = new Year(0);
        assertEquals("0", year.toString());
    }

    // toString() TC-06
    //Single Digit Year String Representation
    @Test
    public void testToString_SingleDigitYear() {
        Year year = new Year(5);
        assertEquals("5", year.toString());
    }

    ///////////////////////////////////////////////////
    // Class Year public int hashCode()

    // Class Year public int hashCode() TC-01
    // Bug it produce the wrong hash value of a year
    // the hash value is calculated by 31*17+year
    @Test
    public void HashTest() {
        Year year = new Year(2024);
        assertEquals(2551, year.hashCode());
    }

    ///////////////////////////////////////////////////////
    /// getFirstMillisecond

    // Class Year public long getFirstMillisecond(java.util.Calendar calendar) TC-01
    // Basic Functionality
    @Test
    public void GetFirstMilliSecondTest() {
        Year year = new Year(2021);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long milliSecondTime = calendar.getTimeInMillis();
        assertEquals(milliSecondTime, year.getFirstMillisecond(calendar));
    }

    // Class Year public long getFirstMillisecond(Calendar calendar) TC-02
    // Tests min boundary of supported years (1900)
    @Test
    public void GetFirstMillisecond_LowerBoundary() {
        Year year = new Year(1900);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getFirstMillisecond(calendar));
    }

    // Class Year public long getFirstMillisecond(Calendar calendar) TC-03
    // Tests Max boundary of supported years (9999)
    @Test
    public void GetFirstMillisecond_UpperBoundary() {
        Year year = new Year(9999);
        Calendar calendar = Calendar.getInstance();
        calendar.set(9999, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getFirstMillisecond(calendar));
    }

    // Class Year public long getFirstMillisecond(Calendar calendar) TC-04
    // BUG: Invalid year (-2025) should not be accepted
    @Test (expected = Exception.class)
    public void GetFirstMillisecond_InvalidYear() {
        Year year = new Year(-2025);
        Calendar calendar = Calendar.getInstance();
        calendar.set(-2025, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        year.getFirstMillisecond(calendar);
    }

    // Class Year public long getFirstMillisecond(Calendar calendar) TC-05
    // BUG: Invalid year 10000 should not be accepted
    @Test (expected = Exception.class)
    public void GetFirstMillisecond_OutofBoundary() {
        Year year = new Year(10000);
        Calendar calendar = Calendar.getInstance();
        calendar.set(10000, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        year.getFirstMillisecond(calendar);
    }

    // Class Year public long getFirstMillisecond(Calendar calendar) TC-06
    // Tests with calendar in UTC timezone
    @Test
    public void GetFirstMillisecond_UTCTimezone() {
        Year year = new Year(2021);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2021, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getFirstMillisecond(calendar));
    }
    ////////////////////////////////////////////////////////////////////////
    /// getLastMillisecond

    // Class Year public long getLastMillisecond(Calendar calendar) TC-01
    // Basic Functionality (2021)
    @Test
    public void GetLastMillisecondTest_Basic() {
        Year year = new Year(2021);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    // Class Year public long getLastMillisecond(Calendar calendar) TC-02
    // Min bound of valid range
    @Test
    public void GetLastMillisecondTest_LowerBound() {
        Year year = new Year(1900);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    // Class Year public long getLastMillisecond(Calendar calendar) TC-03
    // Max bound of valid range
    @Test
    public void GetLastMillisecondTest_UpperBound() {
        Year year = new Year(9999);
        Calendar calendar = Calendar.getInstance();
        calendar.set(9999, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    // Class Year public long getLastMillisecond(Calendar calendar) TC-04
    // BUG: Year below 1900 should not be accepted
    @Test (expected = Exception.class)
    public void GetLastMillisecondTest_InvalidYear() {
        Year year = new Year(-2025);
        Calendar calendar = Calendar.getInstance();
        calendar.set(-2025, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long result = year.getLastMillisecond(calendar);
        System.out.println("BUG: Returned value for invalid year = " + result);
        assertTrue("BUG: Invalid year should not be allowed", result > 0); // force fail
    }

    // Class Year public long getLastMillisecond(Calendar calendar) TC-05
    // BUG: Year above 10000 should not be accepted
    @Test (expected = Exception.class)
    public void GetLastMillisecondTest_exceddBounds() {
        Year year = new Year(10000);
        Calendar calendar = Calendar.getInstance();
        calendar.set(10000, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long result = year.getLastMillisecond(calendar);
        System.out.println("BUG: Returned value for invalid year = " + result);
        assertTrue("BUG: Invalid year should not be allowed", result > 0); // force fail
    }

    // Class Year public long getLastMillisecond(Calendar calendar) TC-06
    // Checks result using UTC timezone calendar
    @Test
    public void GetLastMillisecondTest_UTC() {
        Year year = new Year(2022);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2022, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    //////////////////////////////////////////////////////////////////////////
    /// equals


    // Class Year public boolean equals(java.lang.Object object) TC-01
    // Basic functionality
    @Test
    public void EqualsTestTwoValues() {
        Year y1 = new Year(2024);
        Year y2 = new Year(2024);
        assertTrue(y1.equals(y2));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-02
    // Bug the value 1899 shouldn't be accepted
    @Test (expected = Exception.class)
    public void EqualsTestTwoValuesInvalidLowerBoundary() {
        Year y1 = new Year(1899);
        Year y2 = new Year(1899);
        assertTrue(y1.equals(y2));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-03
    // Works well with the upper boundary
    @Test (expected = Exception.class)
    public void EqualsTestTwoValuesInvalidUpperBoundary() {
        Year y1 = new Year(10000);
        Year y2 = new Year(10000);
        assertTrue(y1.equals(y2));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-04
    // basic Functionality
    @Test
    public void EqualsTestoneValue() {
        Year y1 = new Year(2023);
        assertTrue(y1.equals(y1));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-05
    // Bug the value 1899 shouldn't be accepted
    @Test (expected = Exception.class)
    public void EqualsTestoneValueInvalidLowerBoundary() {
        Year y1 = new Year(1899);
        assertTrue(y1.equals(y1));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-06
    // works well with the upper boundary
    @Test (expected = Exception.class)
    public void EqualsTestoneValueInvalidUpperBoundary() {
        Year y1 = new Year(10000);
        assertTrue(y1.equals(y1));
    }
    // Class Year public boolean equals(java.lang.Object object) TC-07
    @Test
    public void testEquals_DifferentClass() {
        Year y1 = new Year(2024);
        Object Compare = "2024";
        assertFalse(y1.equals(Compare));
    }

    ///////////////////////////////////////////////////
    /// compareTo

    // compareTo(java.lang.Object o1) TC-01
    // zero == same,
    @Test
    public void CompareToEqualYearsTest() {
        Year y1 = new Year(2024);
        Year y2 = new Year(2024);
        assertEquals(0, y1.compareTo(y2));
    }

    // compareTo(java.lang.Object o1) TC-02
    // negative == before
    @Test
    public void CompareToBeforeTest() {
        Year y1 = new Year(2023);
        Year y2 = new Year(2024);
        assertTrue(y1.compareTo(y2) < 0);
    }

    // compareTo(java.lang.Object o1) TC-03
    // positive == after
    @Test
    public void CompareToAfterTest() {
        Year y1 = new Year(2025);
        Year y2 = new Year(2024);
        assertTrue(y1.compareTo(y2) > 0);
    }

    // compareTo(java.lang.Object o1) TC-04
    // Invalid Type Comparison Test
    @Test (expected = Exception.class)
    //Bug should throw an exception but nth was thrown
    public void CompareToDifferentClassTest() {
        Year y1 = new Year(2024);
        Object notAYear = "2024";
        y1.compareTo(notAYear);
    }

    // compareTo(java.lang.Object o1) TC-05
    // Edge Case Comparisons
    @Test
    public void testCompareTo_MinimumYear() {
        Year minYear = new Year(1900);
        Year year2023 = new Year(2023);
        assertTrue(minYear.compareTo(year2023) < 0);
    }

    // compareTo(java.lang.Object o1) TC-06
    // Edge Case Comparisons
    @Test
    public void testCompareTo_MaximumYear() {
        Year maxYear = new Year(9999);
        Year year2023 = new Year(2023);
        assertTrue(maxYear.compareTo(year2023) > 0);
    }

    // compareTo(java.lang.Object o1) TC-07
    // Null Comparison Test
    @Test (expected = NullPointerException.class)
    public void testCompareTo_Null() {
        Year year2023 = new Year(2023);
        year2023.compareTo(null);
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

    // Year parseYear(java.lang.String s) TC-02
    // Test with alphanumeric string letters + numbers Input:  202a
    @Test
    public void parseTest2NotParsable() {
        String test = "202a";
        assertNull(Year.parseYear(test));
    }

    // Year parseYear(java.lang.String s) TC-03
    // Test with valid 4-digit year string with spaces Input: " 2023 "
    @Test
    public void parseTest3Parsable() {
        String test = " 2023";
        assertEquals(2023, Year.parseYear(test).getYear());
    }
}