package com.mesbahi.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mesbahi.orderservice.DP.strategy.PricingStrategy;
import com.mesbahi.orderservice.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @Transient
    private Product product;
    private double price;
    private int quantity;
    private double discount;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Order order;
    @Transient
    private PricingStrategy pricingStrategy;

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public synchronized double getAmount(){
        Thread t = new Thread(() -> {
            // Calculate price using the pricing strategy
            this.price = pricingStrategy.calculatePrice(this);
        });
        t.start();
        try {
            // Wait for the thread to finish before returning the price
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.price;
        //return pricingStrategy.calculatePrice(this);
    }
}
