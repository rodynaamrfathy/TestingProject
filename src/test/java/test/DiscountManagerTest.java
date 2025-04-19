package test;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class DiscountManagerTest {
    private Mockery context;
    private IDiscountCalculator mockCalculator;

    // runs before each class
    @Before
    public void setUp() {
        context = new Mockery();
        mockCalculator = context.mock(IDiscountCalculator.class);
    }

    /// DiscountManager constructor
    // DiscountManager(boolean , IDiscountCalculator);

    @Test
    public void constructor_InitializesFieldsCorrectly() {
        // Arrange
        boolean isDiscountsSeason = true;

        // Act
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockCalculator);

        // Assert
        assertNotNull("DiscountManager should be instantiated", discountManager);
    }




    /// calculatePriceAfterDiscount
    // TC-01
    // not discount season input 100 expected output 100
    @Test
    public void calculatePriceAfterDiscount_WhenNotDiscountSeason_ReturnsOriginalPrice() {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        context.checking(new Expectations() {{
            never(mockCalculator).isTheSpecialWeek();
            never(mockCalculator).getDiscountPercentage();
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockCalculator);

        // Act
        double result = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        context.assertIsSatisfied();
    }

    // TC-02
    // discount season = true, and special week discount should be 20%
    @Test
    public void calculatePriceAfterDiscount_WhenDiscountSeasonAndSpecialWeek_Applies20PercentDiscount() {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 80.0;

        context.checking(new Expectations() {{
            oneOf(mockCalculator).isTheSpecialWeek(); will(returnValue(true));
            never(mockCalculator).getDiscountPercentage();
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockCalculator);

        // Act
        double result = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        context.assertIsSatisfied();
    }

    // TC-03
    // discount season + odd week
    @Test
    public void calculatePriceAfterDiscount_WhenDiscountSeasonAndOddWeek_Applies5PercentDiscount() {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 95.0;

        context.checking(new Expectations() {{
            oneOf(mockCalculator).isTheSpecialWeek(); will(returnValue(false));
            oneOf(mockCalculator).getDiscountPercentage(); will(returnValue(5));
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockCalculator);

        // Act
        double result = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        context.assertIsSatisfied();
    }

    // TC-04
    // discount season + even week
    @Test
    public void calculatePriceAfterDiscount_WhenDiscountSeasonAndEvenWeek_Applies7PercentDiscount() {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 93.0;

        context.checking(new Expectations() {{
            oneOf(mockCalculator).isTheSpecialWeek(); will(returnValue(false));
            oneOf(mockCalculator).getDiscountPercentage(); will(returnValue(7));
        }});

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockCalculator);

        // Act
        double result = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        assertEquals(expectedPrice, result, 0.0001);
        context.assertIsSatisfied();
    }
}

