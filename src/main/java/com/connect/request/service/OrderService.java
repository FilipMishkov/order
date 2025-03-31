package com.connect.request.service;

import com.connect.order.client.model.Cart;

import com.connect.request.client.CartClient;
import com.connect.request.client.UserClient;
import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import com.connect.request.entity.Order;
import com.connect.request.entity.RandomEntity;
import com.connect.request.enums.OrderStatus;
import com.connect.request.exception.ResourceNotFoundException;
import com.connect.request.mapper.OrderMapper;
import com.connect.request.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final CartClient cartClient;
    private final KafkaTemplate<String, RandomEntity> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

   public OrderDto createOrder(CreateOrderDto createOrderDto, String correlationId) throws Exception {
       Cart cart = new Cart();
       try {
           cart = cartClient.findByUserId(createOrderDto.userId(), correlationId).getBody();
       } catch (Exception e) {
           System.out.println("Error: " + e);
       }


       Order order = Order.fromCart(cart);
       order.getOrderItems().forEach(orderItem -> orderItem.setOrderId(order));

       Order savedOrder = orderRepository.save(order);
       LOGGER.info("Order has been generated successfully with ID: {}", savedOrder.getId());
       cartClient.deleteCart(cart.getUserId(), correlationId);

       RandomEntity random = RandomEntity.builder()
                       .orderId(savedOrder.getId())
                      .userId(savedOrder.getUserId())
               .location(savedOrder.getLocation())
               .price(savedOrder.getPrice())
                        .build();

       ProducerRecord<String, RandomEntity> record = new ProducerRecord<>(
               "order-created-topic",
               savedOrder.getId().toString(),
               random
       );
       record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

       SendResult<String, RandomEntity> result =
               kafkaTemplate.send(record).get();

       LOGGER.info("Partition: " + result.getRecordMetadata().partition());
       LOGGER.info("Topic: " + result.getRecordMetadata().topic());
       LOGGER.info("Offset: " + result.getRecordMetadata().offset());

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
