package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    private static Calendar calendar;

    @BeforeClass
    public static void init()
    {
        calendar = Calendar.getInstance();
    }

    // isTheSpecialWeek
    // TC-01
    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 16);  // June 10, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertFalse(discountCalculator.isTheSpecialWeek());
    }

    // TC-02
    // Test missing cases ( JUNE, 23 is a date in week 26 )
    @Test
    public void testIsTheSpecialWeekWhenTrue() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertTrue(discountCalculator.isTheSpecialWeek());
    }

    // getDiscountPercentage
    // TC-01
    @Test
    public void getDiscountPercentage_Week15() throws Exception {
        // Arrange
        Week week10 = new Week(15, 2023);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week10);
        // Assert
        assertEquals(5,discountCalculator.getDiscountPercentage());
    }

    // TC-02
    @Test
    public void getDiscountPercentage_Week10() throws Exception {
        // Arrange
        Week week10 = new Week(10, 2023);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week10);
        // Assert
        assertEquals(7,discountCalculator.getDiscountPercentage());
    }

    // TC-03
    // BOUNDARY
    @Test
    public void getDiscountPercentage_FirstWeek() {
        // Arrange
        Week week1 = new Week(1, 2023);
        // Act
        DiscountCalculator calculator = new DiscountCalculator(week1);
        // Assert
        assertEquals(5, calculator.getDiscountPercentage());
    }

    // TC-04
    // BOUNDARY
    @Test
    public void getDiscountPercentage_LastWeek52() {
        // Arrange
        Week week52 = new Week(52, 2023);
        // Act
        DiscountCalculator calculator = new DiscountCalculator(week52);
        // Assert
        assertEquals(7, calculator.getDiscountPercentage());
    }

    // TC-05
    // Additional test for 53-week year if needed BOUNDARY
    @Test
    public void getDiscountPercentage_Week53() {
        // Arrange
        Week week53 = new Week(53, 2020); // 2020 had 53 weeks
        // Act
        DiscountCalculator calculator = new DiscountCalculator(week53);
        // Assert
        assertEquals(5, calculator.getDiscountPercentage());
    }

    // TC-06
    // Additional test for negative week
    @Test (expected = IllegalArgumentException.class)
    public void getDiscountPercentage_WeekNeg50() {
        // Arrange
        Week week53 = new Week(-50, 2020); // 2020 had 53 weeks
        // Act
        DiscountCalculator calculator = new DiscountCalculator(week53);
        // Assert
        assertEquals(5, calculator.getDiscountPercentage());
    }

    // TC-06
    // Additional test for negative week
    @Test (expected = IllegalArgumentException.class)
    public void getDiscountPercentage_WeekNull() {
        // Act
        DiscountCalculator calculator = new DiscountCalculator((Week) null);
        // Assert
        assertEquals(5, calculator.getDiscountPercentage());
    }

}