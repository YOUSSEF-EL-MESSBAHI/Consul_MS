package com.mesbahi.orderservice.DP.strategy;

import com.mesbahi.orderservice.entity.ProductItem;

public interface PricingStrategy {
    double calculatePrice(ProductItem productItem);
}
