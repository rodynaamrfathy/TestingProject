package test;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

public class DiscountManagerTest {

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsFalse() throws Exception {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        mockingContext.checking(new Expectations(){
            {
                // make sure that none of the functions are called
                never(mockedDependency).isTheSpecialWeek();
                never(mockedDependency).getDiscountPercentage();
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double result = discountManager.calculatePriceAfterDiscount(100);
        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        // make sure that mocking Expectations Is Satisfied
        // make sure that the actual value exactly equals the expected value
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() throws Exception {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 80.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        mockingContext.checking(new Expectations(){
            {
                // make sure that none of the functions are called
                oneOf(mockedDependency).isTheSpecialWeek();will(returnValue(true));
                never(mockedDependency).getDiscountPercentage();
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double result = discountManager.calculatePriceAfterDiscount(100);
        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        // make sure that mocking Expectations Is Satisfied
        // make sure that the actual value exactly equals the expected value

    }
    @Test
    //BUG TAKE CAREEEEEE!!!!!!!!
    public void testCalculatePriceRevealsBug_WhenPercentageUsedAsWholeNumber() {
        // Arrange
        boolean isDiscountsSeason = true;  // force discount to be applied
        double originalPrice = 100.0;
        double expectedCorrectPrice = 93.0; // what we should get if 7% is correctly used as 0.07
        Mockery context = new Mockery();
        IDiscountCalculator mockedCalculator = context.mock(IDiscountCalculator.class);
        context.checking(new Expectations() {{
            oneOf(mockedCalculator).isTheSpecialWeek(); will(returnValue(false));
            oneOf(mockedCalculator).getDiscountPercentage(); will(returnValue(7)); // 7%
        }});
        DiscountManager manager = new DiscountManager(isDiscountsSeason, mockedCalculator);
        // Act
        double result = manager.calculatePriceAfterDiscount(originalPrice);
        // Assert
        assertEquals(expectedCorrectPrice, result, 0.0001);
    }
}

// test missing cases
