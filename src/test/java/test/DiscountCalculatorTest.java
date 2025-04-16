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

    @Test
    public void testWhenOdd() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 16);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertEquals(5,discountCalculator.getDiscountPercentage());
    }

    @Test
    public void testWhenEven() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertEquals(7,discountCalculator.getDiscountPercentage());
    }

}