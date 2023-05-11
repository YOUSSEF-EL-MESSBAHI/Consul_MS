package com.mesbahi.orderservice.DP.strategy;

import com.mesbahi.orderservice.entity.ProductItem;
import org.springframework.stereotype.Component;

@Component
public class VolumeDiscountPricingStrategy implements PricingStrategy{
    private static final int DISCOUNT_THRESHOLD = 7;
    private static final double DISCOUNT_PERCENTAGE = 0.1;
    @Override
    public double calculatePrice(ProductItem productItem) {
        double price = productItem.getPrice();
        int quantity = productItem.getQuantity();
        double discount = productItem.getDiscount();

        if (quantity >= DISCOUNT_THRESHOLD) {
            discount += DISCOUNT_PERCENTAGE;
        }

        return price * quantity * (1 - discount);
    }
}
