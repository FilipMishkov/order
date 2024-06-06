package com.connect.request.service;

import com.connect.request.entity.OrderItem;
import com.connect.request.entity.Order;
import com.connect.request.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    public void deleteOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }

    public List<OrderItem> findByRequest(Long requestId) {
        return orderItemRepository.findByOrderId(Order.builder().id(requestId).build());
    }

}
