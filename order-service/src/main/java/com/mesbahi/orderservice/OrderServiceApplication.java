package com.mesbahi.orderservice;

import com.mesbahi.orderservice.DP.State.CancelledState;
import com.mesbahi.orderservice.DP.State.CompletedState;
import com.mesbahi.orderservice.DP.State.CreatedState;
import com.mesbahi.orderservice.DP.State.PendingState;
import com.mesbahi.orderservice.DP.strategy.DiscountPricingStrategy;
import com.mesbahi.orderservice.DP.strategy.RegularPricingStrategy;
import com.mesbahi.orderservice.DP.strategy.VolumeDiscountPricingStrategy;
import com.mesbahi.orderservice.entity.Order;
import com.mesbahi.orderservice.entity.ProductItem;
import com.mesbahi.orderservice.enums.OrderStatus;
import com.mesbahi.orderservice.model.Customer;
import com.mesbahi.orderservice.model.Product;
import com.mesbahi.orderservice.repository.OrderRepository;
import com.mesbahi.orderservice.repository.ProductItemRepository;
import com.mesbahi.orderservice.service.CustomerClient;
import com.mesbahi.orderservice.service.ProductClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            OrderRepository orderRepository,
            ProductItemRepository productItemRepository,
            CustomerClient customerRestClientService,
            ProductClient inventoryRestClientService){
        return args -> {
            List<Customer> customers=customerRestClientService.allCustomers().getContent().stream().toList();
            List<Product> products=inventoryRestClientService.allProducts().getContent().stream().toList();
            Long customerId=1L;
            Random random=new Random();
            Customer customer=customerRestClientService.customerById(customerId);
            for (int i = 0; i <20 ; i++) {
                Order order=Order.builder()
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
                        .createdAt(new Date())
                        .build();

                Order savedOrder = orderRepository.save(order);
                for (int j = 0; j < products.size() ; j++) {
                    if(Math.random()>0.70){
                        ProductItem productItem=ProductItem.builder()
                                .order(savedOrder)
                                .productId(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .quantity(1+random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }

                }
            }
        };
    }
}
