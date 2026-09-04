package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {

    @Test
    void exceptionResponse_defaultConstructor_createsInstance() {
        ExceptionResponse response = new ExceptionResponse();
        assertNotNull(response);
    }

    @Test
    void exceptionResponse_allArgsConstructor_setsAllFields() {
        Date date = new Date();
        ExceptionResponse response = new ExceptionResponse(date, "Error message", "Error details");
        assertEquals(date, response.getDate());
        assertEquals("Error message", response.getMessage());
        assertEquals("Error details", response.getDetails());
    }

    @Test
    void exceptionResponse_settersAndGetters_workCorrectly() {
        ExceptionResponse response = new ExceptionResponse();
        Date date = new Date();
        response.setDate(date);
        response.setMessage("Test error");
        response.setDetails("Test details");

        assertEquals(date, response.getDate());
        assertEquals("Test error", response.getMessage());
        assertEquals("Test details", response.getDetails());
    }
}
