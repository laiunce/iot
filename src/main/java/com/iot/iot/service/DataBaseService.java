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

    public void addLogAction(String message) {
        trackingDaoImpl.addLogAction(message);
    }


}