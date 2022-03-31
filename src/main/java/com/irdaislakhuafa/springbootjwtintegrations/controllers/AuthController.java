package com.irdaislakhuafa.springbootjwtintegrations.controllers;

import com.irdaislakhuafa.springbootjwtintegrations.models.dtos.UserDto;
import com.irdaislakhuafa.springbootjwtintegrations.models.dtos.UserPayload;
import com.irdaislakhuafa.springbootjwtintegrations.services.AuthService;
import com.irdaislakhuafa.springbootjwtintegrations.services.UserService;
import com.irdaislakhuafa.springbootjwtintegrations.utils.ResponseJwt;
import com.irdaislakhuafa.springbootjwtintegrations.utils.ResponseMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/v2/auth" })
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody(required = true) UserDto userDto) {
        ResponseMessage response;
        try {
            response = ResponseMessage.builder()
                    .status("success")
                    .error(null)
                    .data(userService.save(userService.mapDtoToEntity(userDto)))
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : " + e.getMessage());

            response = ResponseMessage.builder()
                    .status("error")
                    .error(e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = { "/login" })
    public ResponseEntity<?> login(@RequestBody(required = true) UserPayload userPayload) {
        ResponseJwt response;
        try {
            response = authService.generateJwtToken(userPayload);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : " + e.getMessage());
            response = ResponseJwt.builder()
                    .status("error")
                    .token(null)
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
