package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(
        name = "CreateOrderDto",
        description = "Schema to hold CreateOrderDto information"
)
public record CreateOrderDto(
        @NotEmpty(message = "UserId can not be a null or empty")
        @Schema(
                description = "Id of user that creates an order", example = "345441232133243"
        )
        Long userId
) {
}
