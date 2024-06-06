package com.connect.request.dto;

public record OrderItemDto(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity,
        Integer price
) {
}
