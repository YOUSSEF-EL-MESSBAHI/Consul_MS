package com.mesbahi.orderservice.web;

import com.mesbahi.orderservice.entity.Order;
import com.mesbahi.orderservice.model.Customer;
import com.mesbahi.orderservice.model.Product;
import com.mesbahi.orderservice.repository.OrderRepository;
import com.mesbahi.orderservice.repository.ProductItemRepository;
import com.mesbahi.orderservice.service.CustomerClient;
import com.mesbahi.orderservice.service.ProductClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        return order;
    }
}
