package com.iot.iot.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;


/**
 * @author Cristian Laiun
 */
@Repository
public class TrackingFormatDaoImpl implements TrackingFormatDao {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TrackingFormatDaoImpl.class);

    private DataSource dataSource;

    @Autowired
    public TrackingFormatDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addLogAction(String imei, String momsn, String transmit_time, String iridium_latitude, String iridium_longitude, String iridium_cep, String data) {


        try (Connection conn = dataSource.getConnection()) {

            //split string by spaces
            String db_data = hexDecoder(data);
            String format = "";
            String db_transmit_time = transmit_time;
            float db_iridium_latitude = Float.valueOf(iridium_latitude.trim()).floatValue();
            float db_iridium_longitude = Float.valueOf(iridium_longitude.trim()).floatValue();
            float db_iridium_cep = Float.valueOf(iridium_cep.trim()).floatValue();
            float db_temperature1 = 0;
            float db_temperature2 = 0;
            float db_temperature3 = 0;




            try {
                String[] splitArray = db_data.split(";");
                db_temperature1 = Float.valueOf(splitArray[1].split(":")[1].trim()).floatValue();
                db_temperature2 = Float.valueOf(splitArray[2].split(":")[1].trim()).floatValue();
                db_temperature3 = Float.valueOf(splitArray[3].split(":")[1].trim()).floatValue();
                format = "ok";
            } catch (Exception ex) {
                format = "fail";
            }


            String sqlInsertIntoActionLog = "INSERT " +
                    "INTO action_format_temperature_log " +
                    "(imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data,temperature1,temperature2,temperature3,format) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStmtInsertIntoactionLog = conn.prepareStatement(sqlInsertIntoActionLog);
            preparedStmtInsertIntoactionLog.setString(1, imei);
            preparedStmtInsertIntoactionLog.setString(2, momsn);
            preparedStmtInsertIntoactionLog.setString(3, db_transmit_time);
            preparedStmtInsertIntoactionLog.setFloat(4, db_iridium_latitude);
            preparedStmtInsertIntoactionLog.setFloat(5, db_iridium_longitude);
            preparedStmtInsertIntoactionLog.setFloat(6, db_iridium_cep);
            preparedStmtInsertIntoactionLog.setString(7, db_data);
            preparedStmtInsertIntoactionLog.setFloat(8, db_temperature1);
            preparedStmtInsertIntoactionLog.setFloat(9, db_temperature2);
            preparedStmtInsertIntoactionLog.setFloat(10, db_temperature3);
            preparedStmtInsertIntoactionLog.setString(11, format);

            preparedStmtInsertIntoactionLog.executeUpdate();
        } catch (SQLException e) {
            log.error("Error connecting to the Data Base, cannot insert into action_log. {}", e.getMessage());
        }
    }

    private String hexDecoder(String messageEncode) {
        String decoded = "Empty message";
        try {
            byte[] bytes = Hex.decodeHex(messageEncode.toCharArray());
            decoded = new String(bytes, "UTF-8");
        } catch (DecoderException e) {
            decoded = "Error Decoding";
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            decoded = "Error Decoding";
            e.printStackTrace();
        }
        return decoded;

    }



}