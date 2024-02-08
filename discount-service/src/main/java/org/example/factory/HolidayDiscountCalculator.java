package org.example.factory;

public class HolidayDiscountCalculator implements DiscountCalculator{
    @Override
    public double calculateDiscount() {
        return 0.2;
    }
}
