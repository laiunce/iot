package com.iot.iot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;




@Configuration
@EnableScheduling
//@Component
public class IoTOrchestrator {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IoTOrchestrator.class);
    
    public void sendMessage(String message) {
        log.info("Message {}",message);


    }


}