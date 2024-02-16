package org.example.service;


import org.example.factory.DiscountCalculator;
import org.example.factory.FixedDiscountCalculator;
import org.example.factory.HolidayDiscountCalculator;


import java.time.LocalDate;

public class DiscountService {
    private DiscountCalculator discountCalculator;
    private LocalDate localDate = LocalDate.now();
    public double getDiscount() {
        if(localDate.getDayOfMonth() % 2 == 0){
            discountCalculator = new HolidayDiscountCalculator();
        } else {
            discountCalculator = new FixedDiscountCalculator();
        }
        return discountCalculator.calculateDiscount();
    }
}
