package com.tuannguyen.projectapp.util.service;

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.OperationNotSupportedException;
import java.util.Map;

@Controller
public class CheckExistsController {

    private final CheckExistsService checkExistsService;
    private final Logger logger = Logger.getLogger(CheckExistsController.class);

    @Autowired
    public CheckExistsController(CheckExistsService checkExistsService) {
        this.checkExistsService = checkExistsService;
    }

    @RequestMapping("/exists/{table}")
    public @ResponseBody
    Map<String, Object> checkExists(@PathVariable("table") String table, @RequestParam("field") String field, @RequestParam("value") String value) {
        logger.debug(String.format("Checking if field %s with value %s exists in table%s", field, value, table));
        boolean exists = checkExistsService.checkExists(table, field, value);
        return ImmutableMap.<String, Object>builder()
                .put("table", table)
                .put("exists", exists)
                .put("field", field)
                .put("value", value)
                .build();
    }
}
