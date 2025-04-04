package com.connect.request.client;

import com.connect.request.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users", path = "/api/v1/")
public interface UserClient {
    @GetMapping("/{id}")
    User getUserById(
            @PathVariable("id") Long id,
            @RequestHeader(name = "internal-correlation-id", required = false) String correlationId
    );

}
