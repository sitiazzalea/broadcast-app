package com.broadcast.model;

public final class SessionStatus {
    public static final int ACCEPTED = 1;
    public static final int SENDING = 2;
    public static final int SUCCESS = 3;
    public static final int FAILED = 4;

    private SessionStatus() {}
}
