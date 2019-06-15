/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iot.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Cristian Laiun
 */


@SpringBootApplication
@EnableScheduling
public class IoTHandler {

    private static final Logger log = LoggerFactory.getLogger(IoTHandler.class);

    private static String[] args;
    private static ConfigurableApplicationContext  context;

    public static void main(String[] args) {
        IoTHandler.args = args;
        IoTHandler.context = SpringApplication.run(IoTHandler.class, args);
    }

    private static void reloadContext() {
        context.close();
        context = SpringApplication.run(IoTHandler.class, args);
    }

    public static void restart() {
        log.info("Restarting Application...");
        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                reloadContext();
                log.info("Application Restarted!");
            } catch (InterruptedException ignored) {
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}

