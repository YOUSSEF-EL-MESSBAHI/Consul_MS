package com.mesbahi.orderservice.DP.State;

import com.mesbahi.orderservice.entity.Order;
import com.mesbahi.orderservice.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class CancelledState implements OrderState{
    @Override
    public void cancel(Order order) {

    }

    @Override
    public void complete(Order order) {

    }

    @Override
    public void pending(Order order) {
        order.setStatus(OrderStatus.CREATED);
    }
}
