package com.connect.request.dto;

public record CreateOrderItemDto(
        Long productId,
        Integer quantity,
        Integer price
) {
}
