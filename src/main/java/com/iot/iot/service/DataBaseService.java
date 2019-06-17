package com.iot.iot.service;


import com.iot.iot.repository.TrackingDaoImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Cristian Laiun
 */


@Service
public class DataBaseService {

    @Autowired
    TrackingDaoImpl trackingDaoImpl;

    public void addLogAction(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data) {
        trackingDaoImpl.addLogAction(imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data);
    }


}