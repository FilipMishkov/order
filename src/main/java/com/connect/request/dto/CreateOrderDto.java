package com.connect.request.dto;

import java.util.List;

public record CreateOrderDto(
        Long userId,
        List<CreateOrderItemDto> orderItems,
        String location,
        Integer price,
        Long vendorId
) {
}
