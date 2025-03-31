package com.connect.request.controller;

import com.connect.request.dto.CreateOrderDto;
import com.connect.request.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Operation(
            summary = "Get Orders REST API",
            description = "Get all orders from the database"
    )
    @GetMapping("/")
    public List<OrderDto> getAllOrders(@RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "getAllOrders");
        return orderService.findAllOrders();
    }


    @Operation(
            summary = "Get Orders per User REST API",
            description = "Get all orders a user has made"
    )
    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable("userId") Long userId, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "getRequestByUserId");
        return orderService.findOrdersByUserId(userId, correlationId);
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
   public OrderDto createOrder(@Valid @RequestBody CreateOrderDto createOrderDto, @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
       log(correlationId, "saveRequest");
       try {
           return orderService.createOrder(createOrderDto, correlationId);
       } catch (Exception e) {
           logger.error("Error creating order: {}", e.getMessage());
           return null;
       }
   }

    @Operation(
            summary = "Update Order Status REST API",
            description = "Update the status of an existing order"
    )
    @PutMapping("/{id}/status")
    public OrderDto updateOrderStatus(@PathVariable("id") Long id,
                                      @RequestBody String status,
                                      @RequestHeader(name = "internal-correlation-id", required = false) String correlationId) {
        log(correlationId, "updateOrderStatus");
        return orderService.updateOrderStatus(id, status);
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
