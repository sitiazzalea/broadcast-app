package com.broadcast.service;

import com.broadcast.model.SessionStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionStatusService {
    private final SessionStatusStore sessionStatusStore;

    public List<SessionStatusResponse> poll(List<String> sessionIds){
        List<SessionStatusResponse> result = new ArrayList<>();

        if (sessionIds == null) {
            return result;
        }

        for (String sessionId : sessionIds) {

            if (sessionId == null || sessionId.trim().isEmpty()) {
                continue;
            }

            Integer status = sessionStatusStore.poll(sessionId);

            if (status == null) {
                continue;
            }

            result.add(new SessionStatusResponse(sessionId, status));
        }

        return result;
    }
}
