package com.connect.request.entity;

import com.connect.order.client.model.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    public static OrderItem fromCartItem(final CartItem cartItem) {
        return OrderItem.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice())
                .build();
    }
}
