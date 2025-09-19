package com.flipkart.ecomsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.flipkart.ecomsystem.dto.OrderDetailsDto;
import com.flipkart.ecomsystem.dto.OrderStatusDto;
import com.flipkart.ecomsystem.dto.PlaceOrderDto;
import com.flipkart.ecomsystem.entity.Order;
import com.flipkart.ecomsystem.entity.OrderItems;
import com.flipkart.ecomsystem.entity.Product;
import com.flipkart.ecomsystem.entity.User;
import com.flipkart.ecomsystem.enums.OrderStatus;
import com.flipkart.ecomsystem.repository.OrderRepository;
import com.flipkart.ecomsystem.repository.ProductRepository;
import com.flipkart.ecomsystem.repository.UserRepository;

@Service
public class OrderService {
    
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
    private ProductRepository productRepository;
	@Autowired
    private UserRepository userRepository;
	
	
	public Order createOrder(PlaceOrderDto orderDto) {
		Order order = new Order();
        order.setUserId(orderDto.getUserId());
        List<OrderItems> orderItems = new ArrayList<>();
        double total = 0.0;
        
        for (OrderItems cartItem:orderDto.getItems()) {
        	Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + cartItem.getProductId()));
        	
        	OrderItems orderItem = new OrderItems();
        	orderItem.setProductId(product.getId());
            orderItem.setQty(cartItem.getQty());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            total += product.getPrice() * cartItem.getQty();
        }
        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.pending);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepo.save(order);
	}
	
	
	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}
	
	public OrderDetailsDto getOrderDetails(String id) {
		Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
		
		User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + order.getUserId()));
		
		OrderDetailsDto dto = new OrderDetailsDto();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus().toString());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setItems(order.getItems());

        dto.setCustomerName(user.getName());
        dto.setCustomerEmail(user.getEmail());
        dto.setCustomerStreet(user.getStreet());
        dto.setCustomerCity(user.getCity());
        dto.setCustomerZip(user.getZip());

        return dto;
	}
	
	
	
	public Order updateOrderStatus(String id,OrderStatusDto orderStatusDto) {
		Order existingOrder =orderRepo.findById(id).orElseThrow(()->new RuntimeException("Order not found with id: " + id));
		existingOrder.setStatus(orderStatusDto.getStatus());
		return orderRepo.save(existingOrder);

	}
	
	
	
}
