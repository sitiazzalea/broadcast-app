package com.broadcast.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PollSessionStatusResponse {
    private List<SessionStatusResponse> sessions;

    public PollSessionStatusResponse() {
    }

    public PollSessionStatusResponse(
            List<SessionStatusResponse> sessions
    ) {
        this.sessions = sessions;
    }
}
