package com.iot.iot.repository;

import java.util.List;
import java.util.Set;

/**
 * @author Cristian Laiun
 */
public interface TrackingDao {

    void addLogAction(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data);
}
