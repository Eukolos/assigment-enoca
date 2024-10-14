package com.enoca.ecommerce.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private String timestamp;
    private String error;
    private String path;

}
