package com.tuannguyen.projectapp.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class BaseSerializer<T extends BaseDTO> extends JsonSerializer<T> {
    private List<String> additionalFields;
    private Logger logger = Logger.getLogger(BaseSerializer.class);

    public void setAdditionalFields(List<String> additionalFields) {
        this.additionalFields = additionalFields;
    }

    @Override
    public void serialize(T o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        List<String> requiredFields = o.getRequiredFields();
        requiredFields.addAll(additionalFields);
        jsonGenerator.writeStartObject();
        Class clazz = o.getClass();
        for (String field: requiredFields) {
            try {
                Field f = clazz.getDeclaredField(field);
                f.setAccessible(true);
                Object object = f.get(o);
                jsonGenerator.writeObjectField(field, object);
            } catch (Exception e) {
                logger.error("Error serializing object", e);
            }
        }
        jsonGenerator.writeEndObject();
    }
}
