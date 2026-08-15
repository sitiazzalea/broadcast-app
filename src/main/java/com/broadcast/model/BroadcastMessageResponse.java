package com.broadcast.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BroadcastMessageResponse {
    private String sessionId;
    private int status;

    public BroadcastMessageResponse(
            String sessionId,
            int status
    ) {
        this.sessionId = sessionId;
        this.status = status;
    }
}
