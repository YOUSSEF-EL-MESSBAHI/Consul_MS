package com.mesbahi.inventoryservice;

import com.mesbahi.inventoryservice.entity.Product;
import com.mesbahi.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
//	@Autowired
//	private Product.Builder product;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository){
		return args -> {
			Random random=new Random();
			for (int i = 1; i <10 ; i++) {
				Product.Builder productBuilder = new Product.Builder()
//						.setId((long) i)
						.setName("Computer " + i)
						.setPrice(1200 + Math.random() * 10000)
						.setQuantity(1 + random.nextInt(200));
				Product product = productBuilder.build();
				productRepository.save(product);
//				---------------------------------------------------------------
//				productRepository.saveAll(List.of(
//						Product.builder()
//								.name("Compuer "+i)
//								.price(1200+Math.random()*10000)
//								.quantity(1+random.nextInt(200)).build()
//
//
//				));
			}

		};
	}
}
