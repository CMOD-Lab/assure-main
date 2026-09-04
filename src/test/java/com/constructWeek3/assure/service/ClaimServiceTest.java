package com.constructWeek3.assure.service;

import com.constructWeek3.assure.entity.*;
import com.constructWeek3.assure.exception.UserDoesNotExistException;
import com.constructWeek3.assure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimServiceTest {

    @Mock
    private ClaimsRepository claimsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private PolicyBookingsRepository policyBookingsRepository;

    @Mock
    private HospitalsRepository hospitalsRepository;

    @InjectMocks
    private ClaimService claimService;

    private User user;
    private Members member;
    private PolicyBookings policyBookings;
    private Claim claim;
    private Hospitals hospital;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");
        user.setUserEmail("john@example.com");
        user.setUserMobile("9876543210");
        user.setUserPass("pass12345");

        member = new Members();
        member.setMember_id(1L);
        member.setName("Jane Doe");

        policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);

        claim = new Claim();
        claim.setId(1L);
        claim.setType("Medical");
        claim.setAmountToClaim(5000.0F);

        hospital = new Hospitals();
        hospital.setHospitalId(1L);
        hospital.setName("City Hospital");
    }

    // ---- getUserName tests ----

    @Test
    void getUserName_validUserId_returnsUserName() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = claimService.getUserName(1L);

        assertEquals("John Doe", result);
    }

    @Test
    void getUserName_invalidUserId_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> claimService.getUserName(99L));
    }

    // ---- claimInsurance tests ----

    @Test
    void claimInsurance_withHospitalName_setsHospitalAndReturnsClaim() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));
        when(policyBookingsRepository.findById(1L)).thenReturn(Optional.of(policyBookings));
        when(hospitalsRepository.findAll()).thenReturn(List.of(hospital));
        when(claimsRepository.save(any(Claim.class))).thenReturn(claim);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(membersRepository.save(any(Members.class))).thenReturn(member);
        when(policyBookingsRepository.save(any(PolicyBookings.class))).thenReturn(policyBookings);
        when(hospitalsRepository.save(any(Hospitals.class))).thenReturn(hospital);

        Claim result = claimService.claimInsurance(1L, 1L, 1L, claim, "City Hospital");

        assertNotNull(result);
        assertEquals("Processing", result.getStatus());
        assertNotNull(result.getSubmissionDate());
        verify(claimsRepository, atLeastOnce()).save(any(Claim.class));
    }

    @Test
    void claimInsurance_withoutHospitalName_doesNotSetHospital() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));
        when(policyBookingsRepository.findById(1L)).thenReturn(Optional.of(policyBookings));
        when(claimsRepository.save(any(Claim.class))).thenReturn(claim);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(membersRepository.save(any(Members.class))).thenReturn(member);
        when(policyBookingsRepository.save(any(PolicyBookings.class))).thenReturn(policyBookings);

        Claim result = claimService.claimInsurance(1L, 1L, 1L, claim, null);

        assertNotNull(result);
        assertEquals("Processing", result.getStatus());
        verify(hospitalsRepository, never()).findAll();
    }

    @Test
    void claimInsurance_hospitalNameNotFound_doesNotSetHospital() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));
        when(policyBookingsRepository.findById(1L)).thenReturn(Optional.of(policyBookings));
        when(hospitalsRepository.findAll()).thenReturn(List.of(hospital));
        when(claimsRepository.save(any(Claim.class))).thenReturn(claim);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(membersRepository.save(any(Members.class))).thenReturn(member);
        when(policyBookingsRepository.save(any(PolicyBookings.class))).thenReturn(policyBookings);

        Claim result = claimService.claimInsurance(1L, 1L, 1L, claim, "Unknown Hospital");

        assertNotNull(result);
        assertNull(result.getHospitals());
    }

    // ---- getAllTheClaims tests ----

    @Test
    void getAllTheClaims_userHasClaims_returnsClaimsInReverseOrder() {
        Claim claim1 = new Claim();
        claim1.setId(1L);
        claim1.setUser(user);

        Claim claim2 = new Claim();
        claim2.setId(2L);
        claim2.setUser(user);

        User otherUser = new User();
        otherUser.setUserId(2L);
        Claim claim3 = new Claim();
        claim3.setId(3L);
        claim3.setUser(otherUser);

        when(claimsRepository.findAll()).thenReturn(List.of(claim1, claim2, claim3));

        List<Claim> result = claimService.getAllTheClaims(1L);

        assertEquals(2, result.size());
        // reversed order: claim2 first, claim1 second
        assertEquals(2L, result.get(0).getId());
        assertEquals(1L, result.get(1).getId());
    }

    @Test
    void getAllTheClaims_userHasNoClaims_returnsEmptyList() {
        User otherUser = new User();
        otherUser.setUserId(2L);
        Claim claim1 = new Claim();
        claim1.setUser(otherUser);

        when(claimsRepository.findAll()).thenReturn(List.of(claim1));

        List<Claim> result = claimService.getAllTheClaims(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllTheClaims_noClaimsInDb_returnsEmptyList() {
        when(claimsRepository.findAll()).thenReturn(new ArrayList<>());

        List<Claim> result = claimService.getAllTheClaims(1L);

        assertTrue(result.isEmpty());
    }
}
