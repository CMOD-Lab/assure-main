package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    @Test
    void loginDTO_defaultConstructor_createsInstance() {
        LoginDTO loginDTO = new LoginDTO();
        assertNotNull(loginDTO);
    }

    @Test
    void loginDTO_allArgsConstructor_setsAllFields() {
        LoginDTO loginDTO = new LoginDTO("john@example.com", "password123");
        assertEquals("john@example.com", loginDTO.getEmail());
        assertEquals("password123", loginDTO.getPass());
    }

    @Test
    void loginDTO_settersAndGetters_workCorrectly() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john@example.com");
        loginDTO.setPass("password123");

        assertEquals("john@example.com", loginDTO.getEmail());
        assertEquals("password123", loginDTO.getPass());
    }
}
