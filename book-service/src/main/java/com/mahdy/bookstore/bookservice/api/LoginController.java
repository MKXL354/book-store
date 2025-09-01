package com.mahdy.bookstore.bookservice.api;

import com.mahdy.bookstore.authentication.model.AccessTokenRequest;
import com.mahdy.bookstore.authentication.model.RefreshTokenRequest;
import com.mahdy.bookstore.authentication.model.Token;
import com.mahdy.bookstore.authentication.service.TokenService;
import com.mahdy.bookstore.authentication.util.SecurityUtil;
import com.mahdy.bookstore.authentication.util.model.EncodedHashedPassword;
import com.mahdy.bookstore.bookservice.api.model.UserLoginRequestModel;
import com.mahdy.bookstore.bookservice.api.model.UserLoginResponseModel;
import com.mahdy.bookstore.bookservice.config.LoginControllerConfig;
import com.mahdy.bookstore.bookservice.exception.EntityNotFoundException;
import com.mahdy.bookstore.bookservice.exception.PasswordMismatchException;
import com.mahdy.bookstore.bookservice.persistence.UserRepository;
import com.mahdy.bookstore.bookservice.persistence.model.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    private final LoginControllerConfig config;

    @PostMapping(path = "api/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLoginResponseModel login(@Valid @RequestBody UserLoginRequestModel request) throws EntityNotFoundException, PasswordMismatchException {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity == null) {
            throw new EntityNotFoundException("username: " + request.getUsername() + " not found");
        }
        EncodedHashedPassword hashedPassword = SecurityUtil.generateHashedPassword(request.getPassword(), request.getUsername());
        if (!userEntity.getPassword().equals(hashedPassword.getHashedPassword())) {
            throw new PasswordMismatchException("provided user password is mismatched");
        }
        Map<String, Object> claims = generateLoginClaims(userEntity); new HashMap<>();
        Token accessToken = tokenService.generateAccessToken(new AccessTokenRequest(claims, config.getAccessTimeToLiveMillis()));
        Token refreshToken = tokenService.generateRandomRefreshToken(new RefreshTokenRequest(config.getRefreshTokenMaxLength()));
        return generateUserLoginResponseModel(accessToken.getTokenString(), refreshToken.getTokenString());
//        TODO: way more logic like saving the refresh token and using it later, auth annotation and processing in filter
    }

    private Map<String, Object> generateLoginClaims(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userEntity.getId());
        return claims;
    }

    private UserLoginResponseModel generateUserLoginResponseModel(String accessToken, String refreshToken) {
        UserLoginResponseModel responseModel = new UserLoginResponseModel();
        responseModel.setAccessToken(accessToken);
        responseModel.setRefreshToken(refreshToken);
        return responseModel;
    }
}
//TODO: in a standard impl these private methods should be done in other responsible classes.
