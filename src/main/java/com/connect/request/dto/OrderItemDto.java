package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(
        name = "OrderItemDto",
        description = "Schema to hold OrderItemDto information"
)
public record OrderItemDto(
        @NotEmpty(message = "Id can not be a null or empty")
        @Schema(
                description = "Id of product item", example = "345441232133243"
        )
        Long id,
        @NotEmpty(message = "OrderId can not be a null or empty")
        @Schema(
                description = "Id of the orderId the item is part of", example = "345441232133243"
        )
        Long orderId,
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
                description = "Price of product that you're ordering (at the time of ordering) ", example = "89"
        )
        Integer price
) {
}
