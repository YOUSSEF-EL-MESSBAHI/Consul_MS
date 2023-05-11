package com.mesbahi.orderservice.DP.State;

import com.mesbahi.orderservice.entity.Order;
import com.mesbahi.orderservice.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class PendingState implements OrderState{
    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("it is Cancelled");
    }

    @Override
    public void complete(Order order) {
        System.out.println("t1");
        order.setStatus(OrderStatus.COMPLETED);
    }

    @Override
    public void pending(Order order) {
        System.out.println("test");
    }
}
