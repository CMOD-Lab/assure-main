package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void userDTO_defaultConstructor_createsInstance() {
        UserDTO userDTO = new UserDTO();
        assertNotNull(userDTO);
    }

    @Test
    void userDTO_allArgsConstructor_setsAllFields() {
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com", "9876543210", "pass123", "1234");
        assertEquals(1L, userDTO.getUserId());
        assertEquals("John", userDTO.getUserName());
        assertEquals("john@example.com", userDTO.getUserEmail());
        assertEquals("9876543210", userDTO.getUserMobile());
        assertEquals("pass123", userDTO.getUserPass());
        assertEquals("1234", userDTO.getOtp());
    }

    @Test
    void userDTO_settersAndGetters_workCorrectly() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUserName("John");
        userDTO.setUserEmail("john@example.com");
        userDTO.setUserMobile("9876543210");
        userDTO.setUserPass("pass123");
        userDTO.setOtp("1234");

        assertEquals(1L, userDTO.getUserId());
        assertEquals("John", userDTO.getUserName());
        assertEquals("john@example.com", userDTO.getUserEmail());
        assertEquals("9876543210", userDTO.getUserMobile());
        assertEquals("pass123", userDTO.getUserPass());
        assertEquals("1234", userDTO.getOtp());
    }
}
