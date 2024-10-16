package com.example.quickcart.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class CustomErrorResponse {
    private Instant timeStamp;
    private String error;

    public CustomErrorResponse(String error) {
        timeStamp = Instant.now();
        this.error = error;
    }
}
