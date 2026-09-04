package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.dto.AgeDTO;
import com.constructWeek3.assure.dto.PolicyDTO;
import com.constructWeek3.assure.entity.Location;
import com.constructWeek3.assure.exception.InvalidAgeOfMemberException;
import com.constructWeek3.assure.exception.PolicyDoesNotExistException;
import com.constructWeek3.assure.service.PolicyService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyControllerTest {

    @Mock
    private PolicyService policyService;

    @InjectMocks
    private PolicyController policyController;

    private AgeDTO ageDTO;
    private PolicyDTO policyDTO;
    private Location location;

    @BeforeEach
    void setUp() {
        ageDTO = new AgeDTO();
        ageDTO.setAgeOfSelf(30);

        policyDTO = new PolicyDTO();
        policyDTO.setPolicyId(1L);
        policyDTO.setPolicyName("Health Plus");
        policyDTO.setPremium1(1000.0F);
        policyDTO.setPremium2(1200.0F);
        policyDTO.setPremium3(1400.0F);

        location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");
    }

    // ---- getPolicies tests ----

    @Test
    void getPolicies_validAges_returnsPolicyListWithFoundStatus() {
        List<PolicyDTO> policyDTOList = List.of(policyDTO);
        when(policyService.getPolicies(ageDTO)).thenReturn(policyDTOList);

        ResponseEntity<List<PolicyDTO>> response = policyController.getPolicies(ageDTO);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Health Plus", response.getBody().get(0).getPolicyName());
    }

    @Test
    void getPolicies_emptyPolicies_returnsEmptyListWithFoundStatus() {
        when(policyService.getPolicies(ageDTO)).thenReturn(new ArrayList<>());

        ResponseEntity<List<PolicyDTO>> response = policyController.getPolicies(ageDTO);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getPolicies_invalidAge_propagatesException() {
        ageDTO.setAgeOfSelf(-1);
        when(policyService.getPolicies(ageDTO)).thenThrow(new InvalidAgeOfMemberException("Invalid age"));

        assertThrows(InvalidAgeOfMemberException.class, () -> policyController.getPolicies(ageDTO));
    }

    // ---- addLocatonForPolicy tests ----

    @Test
    void addLocatonForPolicy_validPolicyAndLocation_returnsSuccessMessageWithOkStatus() {
        when(policyService.addLocatonForPolicy(1L, location)).thenReturn("Location has been successfully added");

        ResponseEntity<String> response = policyController.addLocatonForPolicy(1L, location);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Location has been successfully added", response.getBody());
    }

    @Test
    void addLocatonForPolicy_policyNotFound_propagatesException() {
        when(policyService.addLocatonForPolicy(99L, location)).thenThrow(new PolicyDoesNotExistException("Policy not found"));

        assertThrows(PolicyDoesNotExistException.class,
                () -> policyController.addLocatonForPolicy(99L, location));
    }
}
