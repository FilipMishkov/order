package com.connect.request.dto;

import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderItemDto> orderItems,
        Integer price,
        String location,
        Long vendorId
) {
}
