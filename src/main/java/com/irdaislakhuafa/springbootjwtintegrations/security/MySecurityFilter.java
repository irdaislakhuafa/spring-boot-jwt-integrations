package com.irdaislakhuafa.springbootjwtintegrations.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irdaislakhuafa.springbootjwtintegrations.models.entities.User;
import com.irdaislakhuafa.springbootjwtintegrations.services.UserService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MySecurityFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private final String JWT_HEADER = "Authorization";
    private final String BEARER_TOKEN = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // get token
            final String token = getJWTTokenFromRequest(request);

            if ((token != null) && (!token.isEmpty()) && (!token.isBlank()) && (jwtProvider.validateToken(token))) {
                // get id from token
                String id = jwtProvider.getId(token);

                // get user
                User user = userService.findById(id).get();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : " + e.getMessage());
        }
        // Proceed without invoking this filter.
        filterChain.doFilter(request, response);
    }

    private final String getJWTTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TOKEN)) {
            return bearerToken.substring(BEARER_TOKEN.length());
        } else {
            log.error("Error : while get JWT from request");
            return null;
        }
    }

}
