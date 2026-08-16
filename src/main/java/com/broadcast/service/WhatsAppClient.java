package com.broadcast.service;

import com.broadcast.model.Customer;
import com.broadcast.model.SessionStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WhatsAppClient {
    public int sendBroadcast(String message, List<Customer> customers) {
        /*Berhubung tidak punya akses ke WhatsApp API di sini saya hanya simulasi
         saja memanggil WhatsApp API dengan memberi delay
         secara random antara sepersepuluh sampai tiga detik,
         untuk mensimulasikan pemanggilan API WhatsApp yang kecepatannya berbeda-beda.
         Lalu return-nya sebagian besar(75%) sukses, sisanya gagal*/

        int delayMillis = ThreadLocalRandom.current()
                .nextInt(100, 3001);

        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return SessionStatus.FAILED;
        }

        int randomValue = ThreadLocalRandom.current().nextInt(100);

        if (randomValue < 90) {
            return SessionStatus.SUCCESS;
        }

        return SessionStatus.FAILED;

        /*
         * 1. Untuk call real WhatsApp API
         *
         * WhatsAppRequest request = new WhatsAppRequest();
         * request.setMessage(message);
         *
         * for (Customer customer : customers) {
         *     request.addRecipient(customer.getPhoneNumber());
         * }
         *
         *
         * Response response = httpClient.post(
         *     WHATSAPP_API_URL,
         *     request,
         *     authenticationToken
         * );
         *
         *
         * if (response.isSuccessful()) {
         *     return 3;
         * }
         *
         * return 4;
         *
         *
         */


    }
}
