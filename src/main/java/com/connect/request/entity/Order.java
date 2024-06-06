package com.connect.request.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @OneToMany(mappedBy = "orderId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItems;
    @Column(name = "location")
    private String location;
    @Column(name = "price")
    private Integer price;
    @Column(name = "vendor_id")
    private Long vendorId;
}
