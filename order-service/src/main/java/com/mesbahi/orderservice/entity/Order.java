package com.mesbahi.orderservice.entity;

import com.mesbahi.orderservice.DP.Observer.OrderObserver;
import com.mesbahi.orderservice.DP.State.*;
import com.mesbahi.orderservice.enums.OrderStatus;
import com.mesbahi.orderservice.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;

    public double getTotal(){
        double somme=0;
        for(ProductItem pi:productItems){
            somme+=pi.getAmount();
        }
        return somme;
    }

    @Transient
    private OrderState state;

    public void cancel() {
        state.cancel(this);
        notifyObservers();
    }

    public void complete() {
        state.complete(this);
        notifyObservers();
    }

    public void pending() {
        state.pending(this);
        notifyObservers();
    }

    public void setStatus(OrderStatus status) {
        switch (status) {
            case CREATED:
                state = new CreatedState();
                break;
            case PENDING:
                state = new PendingState();
                break;
            case COMPLETED:
                state = new CompletedState();
                break;
            case CANCELLED:
                state = new CancelledState();
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }

        this.status = status;
    }
    //Observer
    @Transient
    private List<OrderObserver> observers = new ArrayList<>();

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            new Thread(() -> observer.update(this)).start();
//            observer.update(this);
        }
    }
}
