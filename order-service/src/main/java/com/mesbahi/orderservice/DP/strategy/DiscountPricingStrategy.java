package com.mesbahi.orderservice.DP.strategy;

import com.mesbahi.orderservice.entity.ProductItem;
import org.springframework.stereotype.Component;

@Component
public class DiscountPricingStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(ProductItem productItem) {
        return productItem.getPrice() * productItem.getQuantity() * (1 - productItem.getDiscount());
    }
}
