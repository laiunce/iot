package com.iot.iot.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.xml.bind.DatatypeConverter.*;


/**
 * @author Cristian Laiun
 */
@Repository
public class TrackingDaoImpl implements TrackingDao {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TrackingDaoImpl.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DataSource dataSource;

    @Autowired
    public TrackingDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addLogAction(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data) {

        try (Connection conn = dataSource.getConnection()) {

            String jsonString = getJsonString(imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data);

            String sqlInsertIntoActionLog = "INSERT " +
                    "INTO action_log " +
                    "(result) " +
                    "VALUES " +
                    "(?)";

            PreparedStatement preparedStmtInsertIntoactionLog = conn.prepareStatement(sqlInsertIntoActionLog);
            preparedStmtInsertIntoactionLog.setString(1, jsonString);

            preparedStmtInsertIntoactionLog.executeUpdate();
        } catch (SQLException e) {
            log.error("Error connecting to the Data Base, cannot insert into action_log. {}", e.getMessage());
        }
    }

    private String hexDecoder(String messageEncode) {
        String decoded = "";
        try {
            byte[] bytes = Hex.decodeHex(messageEncode.toCharArray());
            decoded = new String(bytes, "UTF-8");
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;

    }

    private String getJsonString(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data) {

        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("imei", imei);
        jsonMap.put("momsn", momsn);
        jsonMap.put("transmit_time", transmit_time);
        jsonMap.put("iridium_latitude", iridium_latitude);
        jsonMap.put("iridium_longitude", iridium_longitude);
        jsonMap.put("iridium_cep", iridium_cep);
        jsonMap.put("dataDecoded", hexDecoder(data));
        try {
            return objectMapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException ignore) {
        }

        return null;
    }


}