package com.mesbahi.orderservice.DP.Observer;

import com.mesbahi.orderservice.entity.Order;

public interface OrderObserver {
    void update(Order order);
}
