package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(
        name = "OrderDto",
        description = "Schema to hold Order information"
)
public record OrderDto(
        @NotEmpty(message = "Id can not be a null or empty")
        @Schema(
                description = "Id of the order", example = "345441232133243"
        )
        Long id,
        @NotEmpty(message = "UserId can not be a null or empty")
        @Schema(
                description = "Id of the user that made the order", example = "345441232133243"
        )
        Long userId,
        @NotEmpty(message = "OrderItems list can not be a null or empty")
        @Schema(
                description = "List of items in the order"
        )
        List<OrderItemDto> orderItems,
        @NotEmpty(message = "Price can not be a null or empty")
        @Schema(
                description = "Total price of the order", example = "2400"
        )
        Integer price,
        @NotEmpty(message = "Location can not be a null or empty")
        @Schema(
                description = "Location of where the items should be delivered", example = "Prashka 76A"
        )
        String location,
        @NotEmpty(message = "VendorId can not be a null or empty")
        @Schema(
                description = "Id of vendor from which items are bought", example = "2134213213123"
        )
        Long vendorId
) {
}
