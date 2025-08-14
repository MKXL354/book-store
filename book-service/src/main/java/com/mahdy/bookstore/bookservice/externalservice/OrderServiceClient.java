package com.mahdy.bookstore.bookservice.externalservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mehdi Kamali
 * @since 14/08/2025
 */
@FeignClient(name = "order-service", url = "http://order-service")
public interface OrderServiceClient {

    @GetMapping(path = "/api/test")
    String getTest();
}
