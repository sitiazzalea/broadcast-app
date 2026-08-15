package com.broadcast.service;

import com.broadcast.model.AgeCategory;
import com.broadcast.model.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BroadcastService {
    private final BroadcastAsyncService broadcastAsyncService;
    private final SessionStatusStore sessionStatusStore;

    private static final Logger log = LogManager.getLogger(BroadcastService.class);

    public BroadcastMessageResult createBroadcastSession(
            AgeCategory category,
            String message
    ) {

        String sessionId = UUID.randomUUID().toString();

        // 1 = ACCEPTED
        sessionStatusStore.put(sessionId, SessionStatus.ACCEPTED);
        log.debug("ADHOC: Broadcast Service: session_id: {} status: {}", sessionId, SessionStatus.ACCEPTED);

        broadcastAsyncService.process(
                sessionId,
                category,
                message
        );

        return new BroadcastMessageResult(
                sessionId,
                SessionStatus.ACCEPTED
        );
    }

    public static class BroadcastMessageResult {

        private final String sessionId;
        private final int status;

        public BroadcastMessageResult(
                String sessionId,
                int status
        ) {
            this.sessionId = sessionId;
            this.status = status;
        }

        public String getSessionId() {
            return sessionId;
        }

        public int getStatus() {
            return status;
        }
    }

}
