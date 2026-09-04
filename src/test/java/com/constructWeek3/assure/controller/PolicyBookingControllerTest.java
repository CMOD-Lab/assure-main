package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.dto.*;
import com.constructWeek3.assure.exception.UserDoesNotExistException;
import com.constructWeek3.assure.service.PolicyBookingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyBookingControllerTest {

    @Mock
    private PolicyBookingsService policyBookingsService;

    @InjectMocks
    private PolicyBookingController policyBookingController;

    private PolicyBookingInputDTO policyBookingInputDTO;
    private PolicyBookingsGetListDTO policyBookingsGetListDTO;
    private MembersDTO membersDTO;
    private HospitalLocationDTO hospitalLocationDTO;
    private LatestClaimDTO latestClaimDTO;

    @BeforeEach
    void setUp() {
        membersDTO = new MembersDTO();
        membersDTO.setName("John Doe");
        membersDTO.setRelation_with_user("self");
        membersDTO.setDob("01-01-1993");
        membersDTO.setGender("male");
        membersDTO.setIs_taking_medicines(false);
        membersDTO.setCity("Mumbai");
        membersDTO.setMartial_status(false);
        membersDTO.setEmail("john@example.com");
        membersDTO.setAadhaar("234567890123");
        membersDTO.setMobile("9876543210");
        membersDTO.setOccupation("Engineer");
        membersDTO.setHeight("175cm");
        membersDTO.setWeight(70.0F);

        policyBookingInputDTO = new PolicyBookingInputDTO();
        policyBookingInputDTO.setCoverAmount(100000.0F);
        policyBookingInputDTO.setPremium(1000.0F);
        policyBookingInputDTO.setCoverTenure(1);
        HashSet<MembersDTO> members = new HashSet<>();
        members.add(membersDTO);
        policyBookingInputDTO.setMembers(members);

        policyBookingsGetListDTO = new PolicyBookingsGetListDTO();
        policyBookingsGetListDTO.setBookingId(1L);
        policyBookingsGetListDTO.setUserName("John Doe");
        policyBookingsGetListDTO.setBookingDate(new Date());
        policyBookingsGetListDTO.setPolicyName("Health Plus");
        policyBookingsGetListDTO.setCoverAmount(100000.0F);
        policyBookingsGetListDTO.setPremium(1000.0F);
        policyBookingsGetListDTO.setCoverTenure(1);

        hospitalLocationDTO = new HospitalLocationDTO();
        hospitalLocationDTO.setBookingId(1L);
        hospitalLocationDTO.setPolicyName("Health Plus");
        hospitalLocationDTO.setLocation("Mumbai");
        hospitalLocationDTO.setHospital("City Hospital");

        latestClaimDTO = new LatestClaimDTO();
        latestClaimDTO.setUserName("John Doe");
        latestClaimDTO.setIsBookingThere(true);
        latestClaimDTO.setIsClaimThere(false);
    }

    // ---- getBookedPolicies tests ----

    @Test
    void getBookedPolicies_validUserId_returnsPoliciesWithFoundStatus() {
        List<PolicyBookingsGetListDTO> policies = List.of(policyBookingsGetListDTO);
        when(policyBookingsService.getBookedPolicies(1L)).thenReturn(policies);

        ResponseEntity<List<PolicyBookingsGetListDTO>> response = policyBookingController.getBookedPolicies(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getBookedPolicies_userNotFound_propagatesException() {
        when(policyBookingsService.getBookedPolicies(99L)).thenThrow(new UserDoesNotExistException("User not found"));

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingController.getBookedPolicies(99L));
    }

    @Test
    void getBookedPolicies_emptyList_returnsEmptyListWithFoundStatus() {
        when(policyBookingsService.getBookedPolicies(1L)).thenReturn(new ArrayList<>());

        ResponseEntity<List<PolicyBookingsGetListDTO>> response = policyBookingController.getBookedPolicies(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // ---- bookPolicy tests ----

    @Test
    void bookPolicy_validRequest_returnsCreatedStatus() throws Exception {
        when(policyBookingsService.bookPolicy(1L, 1L, policyBookingInputDTO)).thenReturn(policyBookingInputDTO);

        ResponseEntity<PolicyBookingInputDTO> response = policyBookingController.bookPolicy(1L, 1L, policyBookingInputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(100000.0F, response.getBody().getCoverAmount());
    }

    @Test
    void bookPolicy_userNotFound_propagatesException() throws Exception {
        when(policyBookingsService.bookPolicy(eq(99L), eq(1L), any(PolicyBookingInputDTO.class)))
                .thenThrow(new UserDoesNotExistException("User not found"));

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingController.bookPolicy(99L, 1L, policyBookingInputDTO));
    }

    // ---- validateMember tests ----

    @Test
    void validateMember_validMember_returnsTrueWithOkStatus() throws Exception {
        when(policyBookingsService.validateMember(membersDTO)).thenReturn(true);

        ResponseEntity<Boolean> response = policyBookingController.validateMember(membersDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    // ---- fetchHospitals tests ----

    @Test
    void fetchHospitals_validUserId_returnsHospitalsWithFoundStatus() {
        List<HospitalLocationDTO> hospitals = List.of(hospitalLocationDTO);
        when(policyBookingsService.fetchHospitals(1L)).thenReturn(hospitals);

        ResponseEntity<List<HospitalLocationDTO>> response = policyBookingController.fetchHospitals(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("City Hospital", response.getBody().get(0).getHospital());
    }

    @Test
    void fetchHospitals_userNotFound_propagatesException() {
        when(policyBookingsService.fetchHospitals(99L)).thenThrow(new UserDoesNotExistException("User not found"));

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingController.fetchHospitals(99L));
    }

    // ---- fetchLatest tests ----

    @Test
    void fetchLatest_validUserId_returnsLatestClaimDTOWithFoundStatus() {
        when(policyBookingsService.fetchLatest(1L)).thenReturn(latestClaimDTO);

        ResponseEntity<LatestClaimDTO> response = policyBookingController.fetchLatest(1L);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getUserName());
    }

    @Test
    void fetchLatest_userNotFound_propagatesException() {
        when(policyBookingsService.fetchLatest(99L)).thenThrow(new UserDoesNotExistException("User not found"));

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingController.fetchLatest(99L));
    }
}
