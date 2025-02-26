package com.connect.request.service;

import com.connect.order.client.model.Cart;

import com.connect.request.client.CartClient;
import com.connect.request.client.UserClient;
import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import com.connect.request.entity.Order;
import com.connect.request.enums.OrderStatus;
import com.connect.request.exception.ResourceNotFoundException;
import com.connect.request.mapper.OrderMapper;
import com.connect.request.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final CartClient cartClient;

    public List<OrderDto> findAllOrders() {
        return OrderMapper.INSTANCE.toRequestDtos(orderRepository.findAll());
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Request", "id", id.toString())
        );
        return OrderMapper.INSTANCE.toRequestDto(order);
    }

    public List<OrderDto> findOrdersByUserId(Long userId, String correlationId) {
        Optional.of(userClient.getUserById(userId, correlationId)).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        return OrderMapper.INSTANCE.toRequestDtos(orderRepository.findByUserId(userId));
    }

   public OrderDto createOrder(CreateOrderDto createOrderDto, String correlationId) {
       Cart cart = new Cart();
       try {
           cart = cartClient.findByUserId(createOrderDto.userId(), correlationId).getBody();
       } catch (Exception e) {
           System.out.println("Error: " + e);
       }


       Order order = Order.fromCart(cart);
       order.getOrderItems().forEach(orderItem -> orderItem.setOrderId(order));

       Order savedOrder = orderRepository.save(order);
       log.info("Order has been generated successfully with ID: {}", savedOrder.getId());
       cartClient.deleteCart(cart.getUserId(), correlationId);

       return OrderMapper.INSTANCE.toRequestDto(savedOrder);
    }

    public OrderDto updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));
        order.setStatus(OrderStatus.fromString(status));
        Order updatedOrder = orderRepository.save(order);
        log.info("Order status has been updated successfully for ID: {}", updatedOrder.getId());
        return OrderMapper.INSTANCE.toRequestDto(updatedOrder);
    }

    public boolean deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

}
