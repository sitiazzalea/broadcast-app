package com.broadcast.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BroadcastMessageRequest {
    private String message;
    private AgeCategory ageCategory;
}
