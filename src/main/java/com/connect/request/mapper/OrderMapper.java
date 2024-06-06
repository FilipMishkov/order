package com.connect.request.mapper;

import com.connect.request.dto.OrderDto;
import com.connect.request.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toRequestDto(Order order);
    Order toRequest(OrderDto orderDto);
    List<OrderDto> toRequestDtos(List<Order> orders);
    List<Order> toRequests(List<OrderDto> orderDtos);

}
