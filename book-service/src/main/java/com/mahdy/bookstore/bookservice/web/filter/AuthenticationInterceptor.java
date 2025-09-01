package com.mahdy.bookstore.bookservice.web.filter;

import com.mahdy.bookstore.authentication.annotation.Authenticate;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.service.TokenService;
import com.mahdy.bookstore.bookservice.api.enumeration.ApplicationConstants;
import com.mahdy.bookstore.bookservice.api.enumeration.UserRoles;
import com.mahdy.bookstore.bookservice.exception.UserAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        Set<String> authorizedRoles;
        Authenticate auth = method.getMethodAnnotation(Authenticate.class);
        if (auth == null) {
            authorizedRoles = Set.of(UserRoles.WEB);
        } else {
            authorizedRoles = Set.of(auth.roles());
        }
        if (authorizedRoles.contains(UserRoles.NONE)) {
            return true;
        }

        String accessToken = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER).replace(ApplicationConstants.TOKEN_PREFIX, "").trim();
//        TODO: maybe do this in a scoped context bean?
        Map<String, Object> claims = tokenService.parseAccessToken(new Token(accessToken)).getClaims();
        List<String> claimedRoles = (List<String>) claims.get(ApplicationConstants.USER_ROLES);
        if (claimedRoles.stream().noneMatch(authorizedRoles::contains)) {
            throw new UserAuthorizationException(String.format("user %s with roles %s is not authorized for roles %s",
                    claims.get(ApplicationConstants.USER_ID), claims.get(ApplicationConstants.USER_ROLES), authorizedRoles));
        }
        return true;
    }
}
