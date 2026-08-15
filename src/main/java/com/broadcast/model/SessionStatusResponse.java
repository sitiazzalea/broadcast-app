package com.broadcast.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionStatusResponse {
    private String sessionId;
    private int status;

    public SessionStatusResponse() {}

    public SessionStatusResponse(
            String sessionId,
            int status
    ) {
        this.sessionId = sessionId;
        this.status = status;
    }
}
