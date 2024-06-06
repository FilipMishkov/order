package com.connect.request.repository;

import com.connect.request.entity.Order;
import com.connect.request.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Order orderId);

}
