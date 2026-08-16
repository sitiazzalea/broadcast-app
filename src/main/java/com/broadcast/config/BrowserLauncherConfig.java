package com.broadcast.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class BrowserLauncherConfig implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${app.browser.open:false}")
    private boolean browserOpen;

    private static final String URL = "http://localhost:9090/broadcast.xhtml";
    @Override
    public void onApplicationEvent(
            ApplicationReadyEvent event) {
        if (!browserOpen) {
            return;
        }

        /*
         * Delay slightly so the embedded server has fully
         * initialized before opening the browser.
         */
        Thread browserThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);

                            if (Desktop.isDesktopSupported()) {

                                Desktop.getDesktop().browse(new URI(URL));
                            }

                        } catch (Exception e) {

                            /*
                             * Do not make application startup fail
                             * because the browser could not be opened.
                             */
                            System.err.println(
                                    "Unable to open browser: "
                                            + e.getMessage()
                            );
                        }
                    }
                }
        );

        browserThread.setDaemon(true);
        browserThread.start();
    }}
