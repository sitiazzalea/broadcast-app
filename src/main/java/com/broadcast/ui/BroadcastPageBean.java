package com.broadcast.ui;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.broadcast.model.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Named
@ViewScoped
public class BroadcastPageBean implements Serializable {

    private static final Logger log = LogManager.getLogger(BroadcastPageBean.class);

    private static final long serialVersionUID = 1L;

    private static final String BROADCAST_URL =
            "http://localhost:9090/api/broadcast_message";

    private static final String POLL_URL =
            "http://localhost:9090/api/poll_session_status";

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private transient RestTemplate restTemplate;

    @Getter
    @Setter
    private AgeCategory ageCategory;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private List<SessionRow> sessions;

    public AgeCategory[] getAgeCategories() {
        return AgeCategory.values();
    }

    @PostConstruct
    public void init() {
        sessions = new ArrayList<SessionRow>();
        restTemplate = new RestTemplate();
    }

    private void ensureClient() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

    public void submitBroadcast() {
        ensureClient();

        if (ageCategory == null || ageCategory.name().isEmpty()) {
            addMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Validation",
                    "Please select an age category."
            );
            return;
        }

        if (message == null || message.trim().isEmpty()) {
            addMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Validation",
                    "Please enter a message."
            );
            return;
        }

        BroadcastMessageRequest request = new BroadcastMessageRequest();

        request.setMessage(message);
        request.setAgeCategory(ageCategory);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BroadcastMessageRequest> entity =
                    new HttpEntity<>(
                            request,
                            headers
                    );

            ResponseEntity<BroadcastMessageResponse> response =
                    restTemplate.postForEntity(
                            BROADCAST_URL,
                            entity,
                            BroadcastMessageResponse.class
                    );

            BroadcastMessageResponse body = response.getBody();

            if (body == null ||
                    body.getSessionId() == null ||
                    body.getSessionId().trim().isEmpty()) {

                addMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Broadcast Failed",
                        "Backend returned an invalid response."
                );
                return;
            }

            sessions.add(
                    0,
                    new SessionRow(
                            body.getSessionId(),
                            body.getStatus(),
                            LocalTime.now().format(TIME_FORMATTER)
                    )
            );

            message = "";
            ageCategory = null;

            addMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Broadcast Accepted",
                    "Session " + body.getSessionId() + " has been accepted."
            );

        } catch (RestClientException e) {
            addMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Connection Error",
                    "Unable to connect to the broadcasting backend."
            );
        }
    }

    public void pollSessionStatus() {
        ensureClient();

        List<String> activeSessionIds =new ArrayList<>();

        for (SessionRow row : sessions) {
            if (row.getStatus() == 1 || row.getStatus() == 2) {
                activeSessionIds.add(row.getSessionId());
            }
        }

        if (activeSessionIds.isEmpty()) {
            return;
        }

        PollStatusSessionRequest request = new PollStatusSessionRequest();

        request.setSessionIds(activeSessionIds);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PollStatusSessionRequest> entity =
                    new HttpEntity<>(
                            request,
                            headers
                    );

            ResponseEntity<PollSessionStatusResponse> response =
                    restTemplate.postForEntity(
                            POLL_URL,
                            entity,
                            PollSessionStatusResponse.class
                    );

            PollSessionStatusResponse body = response.getBody();

            if (body == null || body.getSessions() == null) {
                return;
            }

            for (SessionStatusResponse item : body.getSessions()) {
                updateSessionStatus(
                        item.getSessionId(),
                        item.getStatus()
                );
            }

        } catch (RestClientException e) {
            /*
             * Do not let a temporary polling failure break the JSF view.
             * The next p:poll request will retry.
             */
        }
    }

    private void updateSessionStatus(
            String sessionId,
            int status
    ) {
        for (SessionRow row : sessions) {
            if (row.getSessionId().equals(sessionId)) {
                row.setStatus(status);
                return;
            }
        }
    }

    public int getAcceptedCount() {
        return countStatus(1);
    }

    public int getSendingCount() {
        return countStatus(2);
    }

    public int getSuccessCount() {
        return countStatus(3);
    }

    public int getFailedCount() {
        return countStatus(4);
    }

    private int countStatus(int target) {
        int count = 0;

        for (SessionRow row : sessions) {
            if (row.getStatus() == target) {
                count++;
            }
        }

        return count;
    }

    public String getStatusLabel(int status) {
        switch (status) {
            case 1:
                return "Accepted";
            case 2:
                return "Sending";
            case 3:
                return "Success";
            case 4:
                return "Failed";
            default:
                return "Unknown";
        }
    }

    public String getStatusStyleClass(int status) {
        switch (status) {
            case 1:
                return "status-accepted";
            case 2:
                return "status-sending";
            case 3:
                return "status-success";
            case 4:
                return "status-failed";
            default:
                return "";
        }
    }

    private void addMessage(
            FacesMessage.Severity severity,
            String summary,
            String detail
    ) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(severity, summary, detail)
        );
    }



}
