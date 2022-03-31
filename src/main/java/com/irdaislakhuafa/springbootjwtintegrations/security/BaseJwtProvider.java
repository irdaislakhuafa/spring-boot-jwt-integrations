package com.irdaislakhuafa.springbootjwtintegrations.security;

import org.springframework.security.core.Authentication;

public interface BaseJwtProvider {
    public String generateToken(Authentication authentication);

    public boolean validateToken(String token);

    public String getId(String token);
}
