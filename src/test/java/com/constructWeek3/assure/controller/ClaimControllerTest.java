package com.constructWeek3.assure.controller;

import com.constructWeek3.assure.dto.ClaimDTO;
import com.constructWeek3.assure.dto.ToClaimDTO;
import com.constructWeek3.assure.entity.Claim;
import com.constructWeek3.assure.entity.Hospitals;
import com.constructWeek3.assure.entity.Members;
import com.constructWeek3.assure.entity.PolicyBookings;
import com.constructWeek3.assure.entity.User;
import com.constructWeek3.assure.service.ClaimService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    @Mock
    private ClaimService claimService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClaimController claimController;

    private ToClaimDTO toClaimDTO;
    private Claim claim;
    private Members member;
    private PolicyBookings policyBookings;
    private User user;

    @BeforeEach
    void setUp() {
        toClaimDTO = new ToClaimDTO();
        toClaimDTO.setType("Medical");
        toClaimDTO.setAadharNumber(123456789012L);
        toClaimDTO.setHospitalName("City Hospital");
        toClaimDTO.setDateOfTreatment(new Date());
        toClaimDTO.setAmountToClaim(5000.0F);
        toClaimDTO.setClaimItem("Surgery");
        toClaimDTO.setPreauthorizedConfirmation(true);
        toClaimDTO.setFollowUpVisits(false);

        member = new Members();
        member.setMember_id(1L);
        member.setName("Jane Doe");

        policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);
        policyBookings.setPolicyName("Health Plus");

        user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");

        claim = new Claim();
        claim.setId(1L);
        claim.setType("Medical");
        claim.setAmountToClaim(5000.0F);
        claim.setStatus("Processing");
        claim.setSubmissionDate(new Date());
        claim.setMember(member);
        claim.setPolicyBookings(policyBookings);
        claim.setUser(user);

        Hospitals hospital = new Hospitals();
        hospital.setHospitalId(1L);
        hospital.setName("City Hospital");
        claim.setHospitals(hospital);
    }

    // ---- claimInsurance tests ----

    @Test
    void claimInsurance_validRequest_returnsToClaimDTO() {
        when(claimService.claimInsurance(eq(1L), eq(1L), eq(1L), any(Claim.class), eq("City Hospital")))
                .thenReturn(claim);
        doAnswer(invocation -> null).when(modelMapper).map(any(ToClaimDTO.class), any(Claim.class));
        doAnswer(invocation -> null).when(modelMapper).map(any(Claim.class), any(ToClaimDTO.class));

        ToClaimDTO result = claimController.claimInsurance(1L, 1L, 1L, toClaimDTO);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getNameOfMember());
        assertEquals("City Hospital", result.getHospitalName());
    }

    @Test
    void claimInsurance_noHospital_returnsToClaimDTOWithoutHospitalName() {
        claim.setHospitals(null);
        toClaimDTO.setHospitalName(null);

        when(claimService.claimInsurance(eq(1L), eq(1L), eq(1L), any(Claim.class), eq(null)))
                .thenReturn(claim);
        doAnswer(invocation -> null).when(modelMapper).map(any(ToClaimDTO.class), any(Claim.class));
        doAnswer(invocation -> null).when(modelMapper).map(any(Claim.class), any(ToClaimDTO.class));

        ToClaimDTO result = claimController.claimInsurance(1L, 1L, 1L, toClaimDTO);

        assertNotNull(result);
        assertNull(result.getHospitalName());
    }

    // ---- getAllTheClaims tests ----

    @Test
    void getAllTheClaims_userWithClaims_returnsClaimDTOList() {
        List<Claim> claims = List.of(claim);
        when(claimService.getAllTheClaims(1L)).thenReturn(claims);
        when(claimService.getUserName(1L)).thenReturn("John Doe");
        doAnswer(invocation -> null).when(modelMapper).map(any(Claim.class), any(ClaimDTO.class));

        List<ClaimDTO> result = claimController.getAllTheClaims(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getUserName());
        assertEquals("Jane Doe", result.get(0).getNameOfMember());
        assertEquals(1L, result.get(0).getMemberId());
        assertEquals("Health Plus", result.get(0).getPolicyBookingName());
    }

    @Test
    void getAllTheClaims_userWithNoClaims_returnsEmptyList() {
        when(claimService.getAllTheClaims(1L)).thenReturn(new ArrayList<>());

        List<ClaimDTO> result = claimController.getAllTheClaims(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
