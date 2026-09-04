package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.dto.LoginDTO;
import com.constructWeek3.assure.dto.UserDTO;
import com.constructWeek3.assure.exception.EmptyInputException;
import com.constructWeek3.assure.exception.IncorrectPasswordAndEmail;
import com.constructWeek3.assure.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUserName("John Doe");
        userDTO.setUserEmail("john.doe@example.com");
        userDTO.setUserMobile("9876543210");
        userDTO.setUserPass("password123");
        userDTO.setOtp("");

        loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@example.com");
        loginDTO.setPass("password123");
    }

    // ---- getUserName tests ----

    @Test
    void getUserName_validUserId_returnsUserNameWithFoundStatus() {
        when(userService.getUserName(1L)).thenReturn("John Doe");

        ResponseEntity<String> response = userController.getUserName(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("John Doe", response.getBody());
    }

    // ---- authenticateUser tests ----

    @Test
    void authenticateUser_emptyOtp_callsAuthenticateUserAndReturnsOkWithOTPSent() {
        userDTO.setOtp("");
        doNothing().when(userService).authenticateUser(any(MappingJacksonValue.class));

        ResponseEntity response = userController.authenticateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP sent", response.getBody());
        verify(userService, times(1)).authenticateUser(any(MappingJacksonValue.class));
    }

    @Test
    void authenticateUser_emptyOtp_serviceThrowsException_propagatesException() {
        userDTO.setOtp("");
        doThrow(new EmptyInputException("Enter name")).when(userService).authenticateUser(any(MappingJacksonValue.class));

        assertThrows(EmptyInputException.class, () -> userController.authenticateUser(userDTO));
    }

    // ---- getUserDetails tests ----

    @Test
    void getUserDetails_validCredentials_returnsOkWithUserId() {
        when(userService.getUserDetails(loginDTO)).thenReturn(1L);

        ResponseEntity response = userController.getUserDetails(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void getUserDetails_invalidCredentials_propagatesException() {
        when(userService.getUserDetails(loginDTO)).thenThrow(new IncorrectPasswordAndEmail("Invalid credentials"));

        assertThrows(IncorrectPasswordAndEmail.class, () -> userController.getUserDetails(loginDTO));
    }

    // ---- removePhoneOTP tests ----

    @Test
    void removePhoneOTP_validRequest_returnsOkStatus() {
        doNothing().when(userService).removePhoneOTP(userDTO);

        ResponseEntity response = userController.removePhoneOTP(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).removePhoneOTP(userDTO);
    }

    // ---- configurer tests ----

    @Test
    void configurer_returnsWebMvcConfigurer() {
        assertNotNull(userController.configurer());
    }
}
