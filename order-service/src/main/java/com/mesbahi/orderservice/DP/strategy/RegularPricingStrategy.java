package com.mesbahi.orderservice.DP.strategy;

import com.mesbahi.orderservice.entity.ProductItem;
import org.springframework.stereotype.Component;

@Component
public class RegularPricingStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(ProductItem productItem) {
        return productItem.getPrice() * productItem.getQuantity();
    }
}
