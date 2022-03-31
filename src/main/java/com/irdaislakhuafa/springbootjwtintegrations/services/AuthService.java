package com.irdaislakhuafa.springbootjwtintegrations.services;

import javax.transaction.Transactional;

import com.irdaislakhuafa.springbootjwtintegrations.models.dtos.UserPayload;
import com.irdaislakhuafa.springbootjwtintegrations.security.JwtProvider;
import com.irdaislakhuafa.springbootjwtintegrations.utils.ResponseJwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public ResponseJwt generateJwtToken(UserPayload userPayload) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userPayload.getUsername(), userPayload.getPassword()));
            String token = jwtProvider.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseJwt.builder()
                    .status("success")
                    .token(token)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
