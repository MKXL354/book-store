package com.mahdy.bookstore.orderservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mehdi Kamali
 * @since 14/08/2025
 */
@RestController
public class TestController {

    @GetMapping(path = "/api/test")
    public String getTest() {
        return "this is order service";
    }
}
