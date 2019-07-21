package com.iot.iot.repository;

/**
 * @author Cristian Laiun
 */
public interface TrackingFormatDao {

    void addLogAction(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data);
}
