package com.mesbahi.orderservice.DP.State;

import com.mesbahi.orderservice.entity.Order;

public interface OrderState {
    void cancel(Order order);
    void complete(Order order);
    void pending(Order order);
}

