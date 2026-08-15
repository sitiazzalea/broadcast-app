package com.broadcast.service;

import com.broadcast.model.AgeCategory;
import com.broadcast.model.Customer;
import com.broadcast.model.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BroadcastAsyncService {
    private final CustomerService customerService;
    private final SessionStatusStore sessionStatusStore;
    private final WhatsAppClient whatsAppClient;

    private static final Logger log = LogManager.getLogger(BroadcastAsyncService.class);

    @Async("broadcastTaskExecutor")
    public void process(
            String sessionId,
            AgeCategory category,
            String message
    ) {
        try {
            List<Customer> customers = customerService.findCustomers(category);

            sessionStatusStore.put(sessionId, SessionStatus.SENDING);
            log.debug("ADHOC: BroadcastAsyncService: session_id: {} status: {}", sessionId, SessionStatus.SENDING);


            // TODO:
            // whatsappService.send(message, customers);
            int result = whatsAppClient.sendBroadcast(
                    message,
                    customers
            );
            sessionStatusStore.put(sessionId, result);
            log.debug("ADHOC: AFTER CALL WHATSAPP API: session_id: {} status: {}", sessionId, result);

        } catch (Exception e) {
            sessionStatusStore.put(sessionId, SessionStatus.FAILED);
            log.error(e.getMessage());
        }
    }

}
