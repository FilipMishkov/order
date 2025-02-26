package com.connect.request.entity;

import com.connect.order.client.model.Cart;
import com.connect.request.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "requests")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @OneToMany(mappedBy = "orderId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems;
    @Column(name = "location")
    private String location;
    @Column(name = "price")
    private Integer price;
    @Column(name = "vendor_id")
    private Long vendorId;
    @Column(name = "status")
    private OrderStatus status;

    public static Order fromCart(Cart cart) {
        return Order.builder()
                .userId(cart.getUserId())
                .location(cart.getAddress())
                .price(cart.getTotalPrice())
//                .vendorId(cart.getVendorId())
                .orderItems(
                    cart.getCartItems().stream()
                            .map(OrderItem::fromCartItem)
                            .toList()
                )
                .status(OrderStatus.PENDING)
                .build();
    }
}
