package com.connect.request.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Schema(
        name = "CreateOrderDto",
        description = "Schema to hold CreateOrderDto information"
)
public record CreateOrderDto(
        @NotEmpty(message = "UserId can not be a null or empty")
        @Schema(
                description = "Id of user that creates an order", example = "345441232133243"
        )
        Long userId,
        @NotEmpty(message = "OrderItems list can not be a null or empty")
        @Schema(
                description = "List of items in the order"
        )
        List<CreateOrderItemDto> orderItems,
        @NotEmpty(message = "Location can not be a null or empty")
        @Schema(
                description = "Location of where the items should be deliveredr"
        )
        String location,

        @NotEmpty(message = "Price can not be a null or empty")
        @Schema(
                description = "Total price of the order", example = "1000"
        )
        Integer price,
        @NotEmpty(message = "VendorId can not be a null or empty")
        @Schema(
                description = "Id of vendor from which items are bought", example = "2134213213123"
        )
        Long vendorId
) {
}
