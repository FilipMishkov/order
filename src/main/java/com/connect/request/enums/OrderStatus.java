package com.connect.request.enums;

public enum OrderStatus {
    PENDING,
    CREATED,
    IN_PROGRESS,
    COMPLETED;

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return null;
    }
}
