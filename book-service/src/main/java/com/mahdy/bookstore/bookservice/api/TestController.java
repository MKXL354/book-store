package com.mahdy.bookstore.bookservice.api;

import com.mahdy.bookstore.bookservice.externalservice.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mehdi Kamali
 * @since 14/08/2025
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final OrderServiceClient orderServiceClient;

    @GetMapping(path = "/api/test")
    public String getTest() {
        return "this is book service";
    }

    @GetMapping(path = "/api/order-service/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderServiceTest() {
        return orderServiceClient.getTest();
    }
}
