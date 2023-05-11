package com.mesbahi.orderservice.DP.State;

import com.mesbahi.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class CompletedState implements OrderState{
    @Override
    public void cancel(Order order) {

    }

    @Override
    public void complete(Order order) {

    }

    @Override
    public void pending(Order order) {

    }
}
