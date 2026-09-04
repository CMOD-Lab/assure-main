package com.constructWeek3.assure.service;

import com.constructWeek3.assure.dto.LoginDTO;
import com.constructWeek3.assure.dto.UserDTO;
import com.constructWeek3.assure.entity.PhoneOTP;
import com.constructWeek3.assure.entity.User;
import com.constructWeek3.assure.exception.*;
import com.constructWeek3.assure.modelmapper.ModelMapperClass;
import com.constructWeek3.assure.repository.PhoneOTP_Repository;
import com.constructWeek3.assure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapperClass modelMapperClass;

    @Mock
    private PhoneOTP_Repository phoneOTP_repository;

    @InjectMocks
    private UserService userService;

    private User user;
    private LoginDTO loginDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");
        user.setUserEmail("john.doe@example.com");
        user.setUserMobile("9876543210");
        user.setUserPass("password123");

        loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@example.com");
        loginDTO.setPass("password123");

        userDTO = new UserDTO();
        userDTO.setUserName("John Doe");
        userDTO.setUserEmail("john.doe@example.com");
        userDTO.setUserMobile("9876543210");
        userDTO.setUserPass("password123");
        userDTO.setOtp("1234");
    }

    // ---- getUserName tests ----

    @Test
    void getUserName_validUserId_returnsUserName() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.getUserName(1L);

        assertEquals("John Doe", result);
    }

    @Test
    void getUserName_invalidUserId_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> userService.getUserName(99L));
    }

    // ---- getUserDetails tests ----

    @Test
    void getUserDetails_emptyEmail_throwsEmptyInputException() {
        loginDTO.setEmail("");
        assertThrows(EmptyInputException.class, () -> userService.getUserDetails(loginDTO));
    }

    @Test
    void getUserDetails_invalidEmail_throwsInvalidEmailException() {
        loginDTO.setEmail("invalidemail");
        assertThrows(InvalidEmailException.class, () -> userService.getUserDetails(loginDTO));
    }

    @Test
    void getUserDetails_emptyPassword_throwsEmptyInputException() {
        loginDTO.setPass("");
        assertThrows(EmptyInputException.class, () -> userService.getUserDetails(loginDTO));
    }

    @Test
    void getUserDetails_shortPassword_throwsEmptyInputException() {
        loginDTO.setPass("abc");
        assertThrows(EmptyInputException.class, () -> userService.getUserDetails(loginDTO));
    }

    @Test
    void getUserDetails_correctCredentials_returnsUserId() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        Long result = userService.getUserDetails(loginDTO);

        assertEquals(1L, result);
    }

    @Test
    void getUserDetails_wrongPassword_throwsIncorrectPasswordAndEmail() {
        loginDTO.setPass("wrongpassword");
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertThrows(IncorrectPasswordAndEmail.class, () -> userService.getUserDetails(loginDTO));
    }

    @Test
    void getUserDetails_emailNotRegistered_throwsIncorrectPasswordAndEmail() {
        loginDTO.setEmail("notregistered@example.com");
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(IncorrectPasswordAndEmail.class, () -> userService.getUserDetails(loginDTO));
    }

    // ---- removePhoneOTP tests ----

    @Test
    void removePhoneOTP_matchingMobile_deletesPhoneOTP() {
        PhoneOTP phoneOTP = new PhoneOTP("9876543210", "1234");
        when(phoneOTP_repository.findAll()).thenReturn(List.of(phoneOTP));

        userService.removePhoneOTP(userDTO);

        verify(phoneOTP_repository, times(1)).delete(phoneOTP);
    }

    @Test
    void removePhoneOTP_noMatchingMobile_doesNotDelete() {
        PhoneOTP phoneOTP = new PhoneOTP("1111111111", "1234");
        when(phoneOTP_repository.findAll()).thenReturn(List.of(phoneOTP));

        userService.removePhoneOTP(userDTO);

        verify(phoneOTP_repository, never()).delete(any());
    }

    @Test
    void removePhoneOTP_emptyList_doesNotDelete() {
        when(phoneOTP_repository.findAll()).thenReturn(new ArrayList<>());

        userService.removePhoneOTP(userDTO);

        verify(phoneOTP_repository, never()).delete(any());
    }

    // ---- registerUser tests ----

    @Test
    void registerUser_incorrectOTP_throwsIncorrectOTP() {
        PhoneOTP phoneOTP = new PhoneOTP("9876543210", "9999");
        when(phoneOTP_repository.findAll()).thenReturn(List.of(phoneOTP));

        assertThrows(IncorrectOTP.class, () -> userService.registerUser(userDTO));
    }

    @Test
    void registerUser_correctOTP_savesUserAndReturnsId() {
        PhoneOTP phoneOTP = new PhoneOTP("9876543210", "1234");
        when(phoneOTP_repository.findAll()).thenReturn(List.of(phoneOTP));

        // Use MockedStatic to mock the static method ModelMapperClass.modelMapper()
        try (MockedStatic<ModelMapperClass> mockedStatic = Mockito.mockStatic(ModelMapperClass.class)) {
            ModelMapper realMapper = new ModelMapper();
            mockedStatic.when(ModelMapperClass::modelMapper).thenReturn(realMapper);

            when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                User savedUser = invocation.getArgument(0);
                savedUser.setUserId(1L);
                return savedUser;
            });

            Long result = userService.registerUser(userDTO);

            assertNotNull(result);
            verify(userRepository, atLeastOnce()).save(any(User.class));
            verify(phoneOTP_repository, times(1)).delete(phoneOTP);
        }
    }
}
