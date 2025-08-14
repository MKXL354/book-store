package com.mahdy.bookstore.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Mehdi Kamali
 * @since 14/08/2025
 */
@SpringBootApplication
@EnableFeignClients
public class BookServiceApp {

    public static void main( String[] args ) {
        SpringApplication.run(BookServiceApp.class, args);
    }
}
