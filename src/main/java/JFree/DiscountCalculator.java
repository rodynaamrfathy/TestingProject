package JFree;

import org.jfree.data.time.Week;

public class DiscountCalculator implements IDiscountCalculator {

    // week the calculator is working with.
    private final Week currentWeek;
    private final int specialWeek = 26;

    // constructor Takes a Week object and stores it in currentWeek.
    public DiscountCalculator(Week week)
    {
        currentWeek = week;
    }

    //Checks if the current week is the special promotional week (week 26).
    public boolean isTheSpecialWeek()
    {
        return currentWeek.getWeek() == specialWeek;
    }

    // Calculates a discount based on whether the week number is even or odd.
    // If even: returns 7% discount.
    // If odd: returns 5% discount.
    public int getDiscountPercentage()
    {
        int weekNumber = currentWeek.getWeek();
        // should return 1-0.07, 1-0.05
        return weekNumber % 2 == 0 ? 7 : 5;
    }
}