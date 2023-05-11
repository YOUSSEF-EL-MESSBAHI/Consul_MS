package com.mesbahi.orderservice.web;

import com.mesbahi.orderservice.DP.Observer.EmailNotificationObserver;
import com.mesbahi.orderservice.DP.State.CancelledState;
import com.mesbahi.orderservice.DP.State.CompletedState;
import com.mesbahi.orderservice.DP.State.CreatedState;
import com.mesbahi.orderservice.DP.State.PendingState;
import com.mesbahi.orderservice.DP.strategy.DiscountPricingStrategy;
import com.mesbahi.orderservice.DP.strategy.RegularPricingStrategy;
import com.mesbahi.orderservice.DP.strategy.VolumeDiscountPricingStrategy;
import com.mesbahi.orderservice.entity.Order;
import com.mesbahi.orderservice.enums.OrderStatus;
import com.mesbahi.orderservice.model.Customer;
import com.mesbahi.orderservice.model.Product;
import com.mesbahi.orderservice.repository.OrderRepository;
import com.mesbahi.orderservice.repository.ProductItemRepository;
import com.mesbahi.orderservice.service.CustomerClient;
import com.mesbahi.orderservice.service.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OrderController {

    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerClient customerRestClientService;
    private ProductClient inventoryRestClientService;

    public OrderController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerClient customerRestClientService, ProductClient inventoryRestClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        Customer customer=customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product=inventoryRestClientService.productById(pi.getProductId());
            pi.setProduct(product);
        });
        order.getProductItems().forEach(productItem -> {
            double randomValue = Math.random();
            if (randomValue < 0.33) {
                productItem.setPricingStrategy(new RegularPricingStrategy());
            } else if (randomValue < 0.67) {
                productItem.setPricingStrategy(new DiscountPricingStrategy());
            } else {
                productItem.setPricingStrategy(new VolumeDiscountPricingStrategy());
            }
            System.out.println(productItem.getPricingStrategy());
        });
        return order;
    }
    @Autowired
    private JavaMailSender javaMailSender;

    @PutMapping("/fullOrder/{id}/{status}")
    public Order updateOrderStatus(@PathVariable Long id, @PathVariable String status) {
        Order order = orderRepository.findById(id).get();
        if (order.getStatus() == OrderStatus.CREATED) {
            order.setState(new CreatedState());
        } else if (order.getStatus() == OrderStatus.PENDING) {
            order.setState(new PendingState());
        } else if (order.getStatus() == OrderStatus.COMPLETED) {
            order.setState(new CompletedState());
        } else if (order.getStatus() == OrderStatus.CANCELLED) {
            order.setState(new CancelledState());
        } else {
            throw new IllegalArgumentException("Invalid OrderStatus value");
        }
        OrderStatus newStatus = OrderStatus.valueOf(status);
        System.out.println("Before: "+order.getState());
        System.out.println("----------------------------------------------------------------------------");
        order.addObserver(new EmailNotificationObserver(javaMailSender));
        switch (newStatus) {
            case CREATED:
                order.pending();
                break;
            case PENDING:
                order.pending();
                break;
            case COMPLETED:
                order.complete();
                break;
            case CANCELLED:
                order.cancel();
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + newStatus);
        }
        System.out.println("Status After: "+order.getStatus());
        System.out.println("State After: "+order.getState());
        order.getProductItems().forEach(productItem -> {
            double randomValue = Math.random();
            if (randomValue < 0.33) {
                productItem.setPricingStrategy(new RegularPricingStrategy());
            } else if (randomValue < 0.67) {
                productItem.setPricingStrategy(new DiscountPricingStrategy());
            } else {
                productItem.setPricingStrategy(new VolumeDiscountPricingStrategy());
            }
            System.out.println(productItem.getPricingStrategy());
        });
        orderRepository.save(order);

        return order;
    }
}






