package com.connect.request.controller;

import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.connect.request.service.OrderService;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Orders",
        description = "CRUD REST APIs for Orders to CREATE, UPDATE, FETCH AND DELETE orders details"
)
@RestController
@RequestMapping("/api/v1/")
@Validated
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Get Orders REST API",
            description = "Get all orders from the database"
    )
    @GetMapping("/")
    public List<OrderDto> getAllOrders(@RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "getAllRequests");
        return orderService.findAllRequests();
    }


    @Operation(
            summary = "Get Orders per User REST API",
            description = "Get all orders a user has made"
    )
    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrderByUserId(@PathVariable("userId") Long userId, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "getRequestByUserId");
        return orderService.findOrderByUserId(userId, correlationId);
    }

    @Operation(
            summary = "Get Order by Id REST API",
            description = "Get an order by its id"
    )
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") Long id, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "getRequestById");
        return orderService.findOrderById(id);
    }

    @Operation(
            summary = "Create Orders REST API",
            description = "Create new order"
    )
    @PostMapping("/")
    public OrderDto saveOrder(@Valid @RequestBody CreateOrderDto createOrderDto, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "saveRequest");
        return orderService.saveOrder(createOrderDto, correlationId);
    }

    @Operation(
            summary = "Update Orders REST API",
            description = "Update an existing order"
    )
    @PutMapping("/{id}")
    public OrderDto updateOrder(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrderDto orderDto,
            @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "updateRequest");
        return orderService.updateOrder(id, orderDto, correlationId);
    }

    @Operation(
            summary = "Delete Order REST API",
            description = "Delete an existing order by its id"
    )
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "deleteRequest");
        orderService.deleteOrder(id);
    }

    private void log(String correlationId, String methodName) {
        logger.debug("ORDER_CONTROLLER: method name = {}", methodName);
        logger.debug("Correlation ID: {}", correlationId);
    }

}
