package com.iot.iot.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public void addLogAction(String message) {

        try (Connection conn = dataSource.getConnection()) {

            String jsonString = getJsonString(message);

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

    private String getJsonString(String message) {

        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("message", message);
        try {
            return objectMapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException ignore) {
        }

        return null;
    }


}