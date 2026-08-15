package com.broadcast.controller;

import com.broadcast.model.*;
import com.broadcast.service.BroadcastService;
import com.broadcast.service.SessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BroadcastController {
    private final SessionStatusService sessionStatusService;
    private final BroadcastService broadcastService;

    @PostMapping("/poll_session_status")
    public ResponseEntity<PollSessionStatusResponse> pollSessionStatus(
            @RequestBody PollStatusSessionRequest request
    ) {
        List<SessionStatusResponse> sessions = sessionStatusService.poll(request.getSessionIds());

        return ResponseEntity.ok(new PollSessionStatusResponse(sessions));
    }

    @PostMapping("/broadcast_message")
    public ResponseEntity<BroadcastMessageResponse> broadcastMessage(
            @RequestBody BroadcastMessageRequest request
    ) {

        /*
         * Basic validation.
         */
        if (request == null ||
                request.getMessage() == null ||
                request.getMessage().trim().isEmpty()) {

            return ResponseEntity.badRequest().build();
        }

        if (request.getAgeCategory() == null) {
            return ResponseEntity.badRequest().build();
        }

        BroadcastService.BroadcastMessageResult result =
                broadcastService.createBroadcastSession(
                        request.getAgeCategory(),
                        request.getMessage()
                );

        return ResponseEntity.ok(
                new BroadcastMessageResponse(
                        result.getSessionId(),
                        result.getStatus()
                )
        );
    }
}
