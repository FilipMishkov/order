package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CreateOrderDto",
        description = "Schema to hold CreateOrderDto information"
)
public record CreateOrderDto(
        @Schema(
                description = "Id of user that creates an order", example = "1"
        )
        Long userId
) {
}
