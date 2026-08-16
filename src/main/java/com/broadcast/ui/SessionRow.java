package com.broadcast.ui;

import java.io.Serializable;

public class SessionRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sessionId;
    private int status;
    private String time;

    public SessionRow() {
    }

    public SessionRow(
            String sessionId,
            int status,
            String time
    ) {
        this.sessionId = sessionId;
        this.status = status;
        this.time = time;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
