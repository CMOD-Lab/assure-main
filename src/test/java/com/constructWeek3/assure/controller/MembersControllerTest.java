package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.dto.MembersDTO;
import com.constructWeek3.assure.exception.UserExists;
import com.constructWeek3.assure.service.MembersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembersControllerTest {

    @Mock
    private MembersService membersService;

    @InjectMocks
    private MembersController membersController;

    private MembersDTO membersDTO;

    @BeforeEach
    void setUp() {
        membersDTO = new MembersDTO();
        membersDTO.setName("Alice");
        membersDTO.setEmail("alice@example.com");
        membersDTO.setMobile("9876543210");
        membersDTO.setGender("female");
    }

    // ---- getallmembers tests ----

    @Test
    void getallmembers_returnsListWithOkStatus() {
        List<MembersDTO> membersDTOList = List.of(membersDTO);
        when(membersService.getallmembers()).thenReturn(membersDTOList);

        ResponseEntity<List<MembersDTO>> response = membersController.getallmembers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getallmembers_emptyList_returnsEmptyListWithOkStatus() {
        when(membersService.getallmembers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<MembersDTO>> response = membersController.getallmembers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // ---- getmemberbyid tests ----

    @Test
    void getmemberbyid_validId_returnsMemberWithFoundStatus() {
        when(membersService.memberbyid(1L)).thenReturn(membersDTO);

        ResponseEntity<MembersDTO> response = membersController.getmemberbyid(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().getName());
    }

    // ---- postmember tests ----

    @Test
    void postmember_newMember_returnsCreatedStatus() {
        when(membersService.postmember(membersDTO)).thenReturn("saved");

        ResponseEntity<String> response = membersController.postmember(membersDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("saved", response.getBody());
    }

    @Test
    void postmember_duplicateEmail_propagatesException() {
        when(membersService.postmember(membersDTO)).thenThrow(new UserExists("Email Already Registered"));

        assertThrows(UserExists.class, () -> membersController.postmember(membersDTO));
    }

    // ---- updatemember tests ----

    @Test
    void updatemember_existingMember_returnsUpdatedMembersDTO() {
        when(membersService.updatemember(membersDTO)).thenReturn(membersDTO);

        MembersDTO result = membersController.updatemember(membersDTO);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    void updatemember_memberNotFound_propagatesException() {
        when(membersService.updatemember(membersDTO)).thenThrow(new UserExists("member doesn't exist"));

        assertThrows(UserExists.class, () -> membersController.updatemember(membersDTO));
    }

    // ---- deletemember tests ----

    @Test
    void deletemember_validId_returnsDeletedMessage() {
        when(membersService.deletemember(1L)).thenReturn("Deleted");

        String result = membersController.deletemember(1L);

        assertEquals("Deleted", result);
        verify(membersService, times(1)).deletemember(1L);
    }

    @Test
    void deletemember_errorOccurs_returnsErrorMessage() {
        when(membersService.deletemember(1L)).thenReturn("Error");

        String result = membersController.deletemember(1L);

        assertEquals("Error", result);
    }
}
