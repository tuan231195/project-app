package com.tuannguyen.projectapp.util.service;

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CheckExistsService {
    private Logger logger = Logger.getLogger(CheckExistsService.class);
    private final JdbcTemplate jdbcTemplate;
    private Map<String, List<String>> allowedColumns = ImmutableMap.<String, List<String>>builder()
            .put("user", Arrays.asList("username", "email"))
            .build();

    @Autowired
    public CheckExistsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkExists(String table, String field, String value){
        if (!(allowedColumns.containsKey(table) && allowedColumns.get(table).contains(field))) {
            throw new IllegalArgumentException();
        }
        long count;
        try {
            count = jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM %s m WHERE %s = ?", table, field), Long.class, value);
        } catch (Exception e) {
            logger.debug("Failed to execute query: " + e.getMessage());
            count = 0;
        }
        return count > 0;
    }
}
