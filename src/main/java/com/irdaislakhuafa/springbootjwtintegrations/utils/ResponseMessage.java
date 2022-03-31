package com.irdaislakhuafa.springbootjwtintegrations.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseMessage {
    private String status;
    private String error;
    private Object data;
}
