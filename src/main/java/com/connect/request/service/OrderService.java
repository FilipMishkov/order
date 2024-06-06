package com.connect.request.service;

import com.connect.request.client.UserFeignClient;
import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import com.connect.request.entity.Order;
import com.connect.request.entity.OrderItem;
import com.connect.request.entity.User;
import com.connect.request.exception.ResourceNotFoundException;
import com.connect.request.mapper.OrderItemMapper;
import com.connect.request.mapper.OrderMapper;
import com.connect.request.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private UserFeignClient userClient;
    private OrderItemService orderItemService;

    /* TODO:
        Check if order items exist before saving request

    */

    public List<OrderDto> findAllRequests() {
        return OrderMapper.INSTANCE.toRequestDtos(orderRepository.findAll());
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Request", "id", id.toString())
        );
        return OrderMapper.INSTANCE.toRequestDto(order);
    }

    public List<OrderDto> findOrderByUserId(Long userId, String correlationId) {
        User user = Optional.of(userClient.getUserById(userId, correlationId)).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        return OrderMapper.INSTANCE.toRequestDtos(orderRepository.findByUserId(userId));
    }

    public OrderDto saveOrder(CreateOrderDto createOrderDto, String correlationId) {
        User user = Optional.of(userClient.getUserById(createOrderDto.userId(), correlationId)).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", createOrderDto.userId().toString())
        );

        List<OrderItem> orderItems = OrderItemMapper.INSTANCE.toOrderItemsFromCreate(createOrderDto.orderItems());
        Order order = Order
            .builder()
            .userId(user.getId())
            .orderItems(orderItems)
            .location(createOrderDto.location())
            .vendorId(createOrderDto.vendorId())
            .price(createOrderDto.price())
            .build();

        Order savedOrder = orderRepository.save(order);
        orderItems.forEach(orderItem -> orderItem.setOrderId(savedOrder));
        orderItemService.saveOrderItems(order.getOrderItems());

        return OrderMapper.INSTANCE.toRequestDto(savedOrder);
    }

    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Request", "id", id.toString())
        );
        orderRepository.deleteById(id);
        return true;
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto, String correlationId) {
        User user = Optional.of(userClient.getUserById(orderDto.userId(), correlationId)).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", orderDto.userId().toString())
        );

        //get the existing order
        Order existingOrder = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Request", "id", id.toString())
        );

        //update the order items
        updateOrderItems(id, OrderItemMapper.INSTANCE.toOrderItems(orderDto.orderItems()));

        //update the existing order
        existingOrder.setOrderItems(OrderItemMapper.INSTANCE.toOrderItems(orderDto.orderItems()));
        existingOrder.setLocation(orderDto.location());
        existingOrder.setUserId(user.getId());
        return OrderMapper.INSTANCE.toRequestDto(orderRepository.save(existingOrder));
    }

    public void updateOrderItems(Long orderId, List<OrderItem> orderItems) {
        List<OrderItem> existingOrderItems = orderItemService.findByRequest(orderId);
        List<OrderItem> toDelete = existingOrderItems.stream()
                .filter(existingOrderItem -> orderItems.stream()
                        .noneMatch(orderItem -> orderItem.getId().equals(existingOrderItem.getId())))
                .toList();
        orderItemService.deleteOrderItems(toDelete);
        orderItemService.saveOrderItems(orderItems);
    }

}
