package com.broadcast.service;

import com.broadcast.model.SessionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStatusStore {
    private final Map<String, Integer> statuses = new ConcurrentHashMap<>();
    private static final Logger log = LogManager.getLogger(SessionStatusStore.class);

    public void put(String sessionId, int status) {
        statuses.put(sessionId, status);
        log.debug("ADHOC: session_id: {} status: {}, session count is {}", sessionId, status, statuses.size());
    }

    public Integer get(String sessionId) {
        return statuses.get(sessionId);
    }

    public Integer remove(String sessionId) {
        return statuses.remove(sessionId);
    }

    public Integer poll(String sessionId) {

        Integer status = statuses.get(sessionId);

        if (status == null) {
            return null;
        }

        if (status == SessionStatus.SUCCESS || status == SessionStatus.FAILED) {
            statuses.remove(sessionId, status);
            log.debug("ADHOC: session_id: {} is removed, session count is {}", sessionId, statuses.size());
        }

        return status;
    }
}
