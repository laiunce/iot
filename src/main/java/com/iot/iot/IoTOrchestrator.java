package com.iot.iot;

import java.util.ArrayList;
import java.util.List;

import com.iot.iot.service.DataBaseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;




@Configuration
@EnableScheduling
//@Component
public class IoTOrchestrator {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IoTOrchestrator.class);


    @Autowired
    private DataBaseService dataBaseService;

    public void sendMessage(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data) {
        log.info("Message {},{},{},{},{},{},{}",imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data);
        dataBaseService.addLogAction(imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data);
    }


}

