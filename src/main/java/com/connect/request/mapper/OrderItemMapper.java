package com.connect.request.mapper;

import com.connect.request.dto.CreateOrderItemDto;
import com.connect.request.dto.OrderItemDto;
import com.connect.request.entity.OrderItem;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "orderId.id", target = "orderId")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(source = "orderId", target = "orderId.id")
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    List<OrderItemDto> toOrderItemDtos(List<OrderItem> orderItems);
    List<OrderItem> toOrderItems(List<OrderItemDto> orderItemDtos);


    OrderItem toOrderItemFromCreate(CreateOrderItemDto orderItemDto);
    List<OrderItem> toOrderItemsFromCreate(List<CreateOrderItemDto> orderItemDtos);
}
