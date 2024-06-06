package com.connect.request.controller;

import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.connect.request.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public List<OrderDto> getAllOrders(@RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "getAllRequests");
        return orderService.findAllRequests();
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrderByUserId(@PathVariable("userId") Long userId, @RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "getRequestByUserId");
        return orderService.findOrderByUserId(userId, correlationId);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") Long id, @RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "getRequestById");
        return orderService.findOrderById(id);
    }

    @PostMapping("/")
    public OrderDto saveOrder(@RequestBody CreateOrderDto createOrderDto, @RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "saveRequest");
        return orderService.saveOrder(createOrderDto, correlationId);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(
            @PathVariable("id") Long id,
            @RequestBody OrderDto orderDto,
            @RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "updateRequest");
        return orderService.updateOrder(id, orderDto, correlationId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id, @RequestHeader("internal-correlation-id") String correlationId) {
        log(correlationId, "deleteRequest");
        orderService.deleteOrder(id);
    }

    private void log(String correlationId, String methodName) {
        logger.debug("ORDER_CONTROLLER: method name = {}", methodName);
        logger.debug("Correlation ID: {}", correlationId);
    }

}
