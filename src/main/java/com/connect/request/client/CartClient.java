package com.connect.request.client;

import com.connect.order.client.api.CartApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cart")
public interface CartClient extends CartApi {

}
