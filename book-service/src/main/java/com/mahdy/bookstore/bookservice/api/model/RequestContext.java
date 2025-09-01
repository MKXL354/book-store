package com.mahdy.bookstore.bookservice.api.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
@Component
@RequestScope
@Data
public class RequestContext {

    private long userId;
    private Set<String> userRoles;
    private Map<String, Object> userClaims;
}
