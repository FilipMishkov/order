package com.connect.request.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RandomEntity {

    private Long orderId;
    private Long userId;
    private String location;
    private Integer price;
}