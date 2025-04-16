package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    private static Calendar calendar;
    private static Mockery mockingContext;

    @BeforeClass
    public static void init()
    {
        calendar = Calendar.getInstance();
        mockingContext = new Mockery();
    }

    /*@Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 16);  // June 10, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertFalse(discountCalculator.isTheSpecialWeek());
        assertEquals(5,discountCalculator.getDiscountPercentage());
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
        assertEquals(7,discountCalculator.getDiscountPercentage());
    }*/

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
        assertEquals(5,discountCalculator.getDiscountPercentage());
    }
    // Test missing cases ( JUNE, 23 is a date in week 26 )
    @Test
    public void testIsTheSpecialWeekWhenTrue() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025
        Date date = calendar.getTime();
        Week mockedWeek = new mockingContext.mock(Week.class);
        mockingContext.Checking(new Expectations() {
            {
                allowing(mockedWeek)
            }
        });
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(mockedWeek);
        // Assert
        assertTrue(discountCalculator.isTheSpecialWeek());
        assertEquals(7,discountCalculator.getDiscountPercentage());
    }

    @Test
    //Bug Not sure awy ashan da mfrod yrg3 0.05 bas fn btrg3 5
    public void testWhenOdd() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 16);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertFalse(discountCalculator.isTheSpecialWeek());
        assertEquals(0.05,discountCalculator.getDiscountPercentage(),0.0001);


    }
    @Test
    //Bug Not sure awy ashan da mfrod yrg3 0.07 bas fn btrg3 5
    public void testWhenEven() throws Exception {
        // Arrange
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        // Act
        DiscountCalculator discountCalculator = new DiscountCalculator(week);
        // Assert
        assertTrue(discountCalculator.isTheSpecialWeek());
        assertEquals(0.07,discountCalculator.getDiscountPercentage(),0.0001);


    }

}