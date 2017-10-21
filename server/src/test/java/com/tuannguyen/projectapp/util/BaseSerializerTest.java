package com.tuannguyen.projectapp.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.tuannguyen.projectapp.auth.model.UserForm;
import com.tuannguyen.projectapp.core.json.BaseSerializer;
import com.tuannguyen.projectapp.core.json.SimpleModuleBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BaseSerializerTest {

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Test";
    private static final String CITY = "Wollongong";
    private static final String STATE = "NSW";
    private static final String TITLE = "MR";
    private static final String PASSWORD = "abc";
    private BaseSerializer<UserForm> baseSerializer;
    private UserForm userForm;

    @Before
    public void setup() {
        userForm = new UserForm();
        userForm.setFirstName(FIRST_NAME);
        userForm.setLastName(LAST_NAME);
        userForm.setCity(CITY);
        userForm.setState(STATE);
        userForm.setTitle(TITLE);
        userForm.setPassword(PASSWORD);
        baseSerializer = new BaseSerializer<>();
    }

    @Test
    public void testSerializer() throws JsonProcessingException {
        final SimpleModuleBuilder moduleBuilder = new SimpleModuleBuilder();
        moduleBuilder.configureDefault().withSerializer(UserForm.class, baseSerializer);
        Configuration config = Configuration.defaultConfiguration()
                .addOptions(Option.SUPPRESS_EXCEPTIONS);
        baseSerializer.setAdditionalFields(Arrays.asList("city", "state"));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(moduleBuilder.build());
        String output = objectMapper.writeValueAsString(userForm);
        DocumentContext documentContext = JsonPath.using(config).parse(output);
        assertEquals(documentContext.read("$.city"), CITY);
        assertEquals(documentContext.read("$.firstName"), FIRST_NAME);
        assertEquals(documentContext.read("$.lastName"), LAST_NAME);
        assertEquals(documentContext.read("$.state"), STATE);
        assertNull(documentContext.read("$.password"));
    }
}
