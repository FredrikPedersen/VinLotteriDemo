package com.fredrikpedersen.experis_vin_lotteri.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected <T> String mapToJson(final T pojo) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(pojo);
    }

    protected <T> T mapFromJson(final String json, final Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(json, clazz);
    }

    /**
     * Utility method for parsing an MvcResult's response body to a Java Object
     */
    protected <T> T parseResponseData(final MvcResult mvcResult, final Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        final String jsonResponse = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, clazz);
    }

    protected <T> List<T> parseResponseList(final MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        final String jsonResponse = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, new TypeReference<>(){});
    }
}
