package com.constructWeek3.assure.service;

import com.constructWeek3.assure.dto.AgeDTO;
import com.constructWeek3.assure.dto.PolicyDTO;
import com.constructWeek3.assure.entity.Location;
import com.constructWeek3.assure.entity.Policy;
import com.constructWeek3.assure.exception.InvalidAgeOfMemberException;
import com.constructWeek3.assure.exception.PolicyDoesNotExistException;
import com.constructWeek3.assure.exception.UserExists;
import com.constructWeek3.assure.repository.LocationRepository;
import com.constructWeek3.assure.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PolicyService policyService;

    private Policy policy;
    private AgeDTO ageDTO;

    @BeforeEach
    void setUp() {
        policy = new Policy();
        policy.setPolicyId(1L);
        policy.setPolicyName("Test Policy");
        policy.setPremiumUpto18(500.0F);
        policy.setPremiumUpto45(1000.0F);
        policy.setPremiumUpto60(1500.0F);
        policy.setPremiumBeyond60(2000.0F);
        policy.setCoverAmount1(100000.0F);
        policy.setCoverAmount2(200000.0F);
        policy.setCoverAmount3(300000.0F);
        policy.setTenure1(1);
        policy.setTenure2(2);
        policy.setTenure3(3);

        ageDTO = new AgeDTO();
        ageDTO.setAgeOfSelf(30);
    }

    // ---- ageToPremium tests ----

    @Test
    void ageToPremium_nullAge_returnsZero() {
        Float result = policyService.ageToPremium(policy, null);
        assertEquals(0.0F, result);
    }

    @Test
    void ageToPremium_zeroAge_returnsZero() {
        Float result = policyService.ageToPremium(policy, 0);
        assertEquals(0.0F, result);
    }

    @Test
    void ageToPremium_ageLessThan18_returnsPremiumUpto18() {
        Float result = policyService.ageToPremium(policy, 15);
        assertEquals(500.0F, result);
    }

    @Test
    void ageToPremium_ageBetween18And44_returnsPremiumUpto45() {
        Float result = policyService.ageToPremium(policy, 30);
        assertEquals(1000.0F, result);
    }

    @Test
    void ageToPremium_ageBetween45And59_returnsPremiumUpto60() {
        Float result = policyService.ageToPremium(policy, 50);
        assertEquals(1500.0F, result);
    }

    @Test
    void ageToPremium_age60AndAbove_returnsPremiumBeyond60() {
        Float result = policyService.ageToPremium(policy, 65);
        assertEquals(2000.0F, result);
    }

    @Test
    void ageToPremium_exactAge18_returnsPremiumUpto45() {
        Float result = policyService.ageToPremium(policy, 18);
        assertEquals(1000.0F, result);
    }

    @Test
    void ageToPremium_exactAge45_returnsPremiumUpto60() {
        Float result = policyService.ageToPremium(policy, 45);
        assertEquals(1500.0F, result);
    }

    @Test
    void ageToPremium_exactAge60_returnsPremiumBeyond60() {
        Float result = policyService.ageToPremium(policy, 60);
        assertEquals(2000.0F, result);
    }

    // ---- isAgeProvided (two params) tests ----

    @Test
    void isAgeProvided_twoParams_bothValid_returnsTrue() {
        assertTrue(policyService.isAgeProvided(30, 55));
    }

    @Test
    void isAgeProvided_twoParams_firstNull_returnsFalse() {
        assertFalse(policyService.isAgeProvided(null, 55));
    }

    @Test
    void isAgeProvided_twoParams_secondNull_returnsFalse() {
        assertFalse(policyService.isAgeProvided(30, null));
    }

    @Test
    void isAgeProvided_twoParams_firstZero_returnsFalse() {
        assertFalse(policyService.isAgeProvided(0, 55));
    }

    @Test
    void isAgeProvided_twoParams_secondZero_returnsFalse() {
        assertFalse(policyService.isAgeProvided(30, 0));
    }

    @Test
    void isAgeProvided_twoParams_bothZero_returnsFalse() {
        assertFalse(policyService.isAgeProvided(0, 0));
    }

    // ---- isAgeProvided (one param) tests ----

    @Test
    void isAgeProvided_oneParam_validAge_returnsTrue() {
        assertTrue(policyService.isAgeProvided(30));
    }

    @Test
    void isAgeProvided_oneParam_nullAge_returnsFalse() {
        assertFalse(policyService.isAgeProvided((Integer) null));
    }

    @Test
    void isAgeProvided_oneParam_zeroAge_returnsFalse() {
        assertFalse(policyService.isAgeProvided(0));
    }

    // ---- getPolicies tests ----

    @Test
    void getPolicies_negativeAgeSelf_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfSelf(-1);
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_negativeAgeFather_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfFather(-5);
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_fatherYoungerThan18YearsOlderThanSelf_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfSelf(30);
        ageDTO.setAgeOfFather(40); // only 10 years older
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_motherYoungerThan18YearsOlderThanSelf_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfSelf(30);
        ageDTO.setAgeOfMother(40); // only 10 years older
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_sonOlderThan18YearsYoungerThanSelf_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfSelf(30);
        ageDTO.setAgeOfSon(20); // only 10 years younger
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_daughterOlderThan18YearsYoungerThanSelf_throwsInvalidAgeOfMemberException() {
        ageDTO.setAgeOfSelf(30);
        ageDTO.setAgeOfDaughter(20); // only 10 years younger
        assertThrows(InvalidAgeOfMemberException.class, () -> policyService.getPolicies(ageDTO));
    }

    @Test
    void getPolicies_validAges_returnsPolicyDTOList() {
        ageDTO.setAgeOfSelf(30);
        List<Policy> policies = List.of(policy);
        List<PolicyDTO> policyDTOs = new ArrayList<>();
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setPolicyId(1L);
        policyDTOs.add(policyDTO);

        when(policyRepository.findAll()).thenReturn(policies);
        when(modelMapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(policyDTOs);

        List<PolicyDTO> result = policyService.getPolicies(ageDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getPolicies_onlySelfAge_calculatesCorrectPremium() {
        ageDTO.setAgeOfSelf(30);
        List<Policy> policies = List.of(policy);
        List<PolicyDTO> policyDTOs = new ArrayList<>();
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTOs.add(policyDTO);

        when(policyRepository.findAll()).thenReturn(policies);
        when(modelMapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(policyDTOs);

        List<PolicyDTO> result = policyService.getPolicies(ageDTO);

        assertNotNull(result);
        // premium for age 30 = premiumUpto45 = 1000.0F
        assertEquals(1000.0F, result.get(0).getPremium1());
        assertEquals(1000.0F * 1.2F, result.get(0).getPremium2());
        assertEquals(1000.0F * 1.4F, result.get(0).getPremium3());
    }

    // ---- addLocatonForPolicy tests ----

    @Test
    void addLocatonForPolicy_policyNotFound_throwsPolicyDoesNotExistException() {
        when(policyRepository.findById(99L)).thenReturn(Optional.empty());
        Location location = new Location();
        location.setLocationId(1L);

        assertThrows(PolicyDoesNotExistException.class,
                () -> policyService.addLocatonForPolicy(99L, location));
    }

    @Test
    void addLocatonForPolicy_newLocation_savesAndReturnsSuccessMessage() {
        Location location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        String result = policyService.addLocatonForPolicy(1L, location);

        assertEquals("Location has been successfully added", result);
        verify(locationRepository, atLeastOnce()).save(any(Location.class));
        verify(policyRepository, atLeastOnce()).save(any(Policy.class));
    }

    @Test
    void addLocatonForPolicy_existingLocationNotLinkedToPolicy_linksAndReturnsSuccessMessage() {
        Location location = new Location();
        location.setLocationId(1L);
        location.setName("Delhi");
        location.setPolicies(new ArrayList<>());

        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(locationRepository.findById(1L))
                .thenReturn(Optional.of(location))
                .thenReturn(Optional.of(location));

        String result = policyService.addLocatonForPolicy(1L, location);

        assertEquals("Location has been successfully added", result);
    }

    @Test
    void addLocatonForPolicy_existingLocationAlreadyLinkedToPolicy_throwsUserExists() {
        Policy existingPolicy = new Policy();
        existingPolicy.setPolicyId(1L);

        Location location = new Location();
        location.setLocationId(1L);
        location.setName("Delhi");
        List<Policy> linkedPolicies = new ArrayList<>();
        linkedPolicies.add(existingPolicy);
        location.setPolicies(linkedPolicies);

        when(policyRepository.findById(1L)).thenReturn(Optional.of(existingPolicy));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        assertThrows(UserExists.class,
                () -> policyService.addLocatonForPolicy(1L, location));
    }
}
