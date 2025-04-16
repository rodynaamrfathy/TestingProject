package JFree;

public class DiscountManager {
    //If true, discounts are applied; if false, no discount will be applied.
    boolean isDiscountsSeason;
    IDiscountCalculator discountCalculator;

    public DiscountManager(boolean isDiscountsSeason, IDiscountCalculator discountCalculator) {
        this.isDiscountsSeason = isDiscountsSeason;
        // this depends on another class so in testing will be a mock object
        this.discountCalculator = discountCalculator;
    }

    // case 1: isDiscountsSeason is false, it returns the original price because no discount applies.
    // case 2: isDiscountsSeason is true apply 20%
    // case 3: If it's not the special week but it is the discount season, it calculates
    // the discount using the discountCalculator.getDiscountPercentage()
    public double calculatePriceAfterDiscount(double price) {
        if (! isDiscountsSeason) {
            return price;
        }

        if (discountCalculator.isTheSpecialWeek())
            return price * .8;

        return price * discountCalculator.getDiscountPercentage();
    }
}