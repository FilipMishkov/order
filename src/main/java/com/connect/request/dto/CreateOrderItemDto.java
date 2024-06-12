package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(
        name = "CreteOrderItemDto",
        description = "Schema to hold CreateOrderItemDto information"
)
public record CreateOrderItemDto(
        @NotEmpty(message = "ProductId can not be a null or empty")
        @Schema(
                description = "Id of product that you're ordering", example = "345441232133243"
        )
        Long productId,
        @NotEmpty(message = "Quantity can not be a null or empty")
        @Schema(
                description = "Quantity of product that you're ordering", example = "2"
        )
        Integer quantity,
        @NotEmpty(message = "Price can not be a null or empty")
        @Schema(
                description = "Price of product that you're ordering", example = "1000"
        )
        Integer price
) {
}
