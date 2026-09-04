package com.constructWeek3.assure.service;

import com.constructWeek3.assure.dto.*;
import com.constructWeek3.assure.entity.*;
import com.constructWeek3.assure.exception.*;
import com.constructWeek3.assure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyBookingsServiceTest {

    @Mock
    private PolicyBookingsRepository policyBookingsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private ClaimService claimService;

    @Mock
    private PolicyService policyService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PolicyBookingsService policyBookingsService;

    private User user;
    private Policy policy;
    private PolicyBookings policyBookings;
    private MembersDTO membersDTO;
    private PolicyBookingInputDTO policyBookingInputDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");
        user.setUserEmail("john@example.com");
        user.setUserMobile("9876543210");
        user.setUserPass("pass12345");

        policy = new Policy();
        policy.setPolicyId(1L);
        policy.setPolicyName("Health Plus");
        policy.setCoverAmount1(100000.0F);
        policy.setCoverAmount2(200000.0F);
        policy.setCoverAmount3(300000.0F);
        policy.setTenure1(1);
        policy.setTenure2(2);
        policy.setTenure3(3);
        policy.setPremiumUpto18(500.0F);
        policy.setPremiumUpto45(1000.0F);
        policy.setPremiumUpto60(1500.0F);
        policy.setPremiumBeyond60(2000.0F);

        policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);
        policyBookings.setBookingDate(new Date());
        policyBookings.setPolicyName("Health Plus");
        policyBookings.setCoverAmount(100000.0F);
        policyBookings.setPremium(1000.0F);
        policyBookings.setCoverTenure(1);
        policyBookings.setPolicy(policy);

        // Use a valid email with 6+ chars before @
        membersDTO = new MembersDTO();
        membersDTO.setName("John Doe");
        membersDTO.setRelation_with_user("self");
        membersDTO.setDob("01-01-1993");
        membersDTO.setGender("male");
        membersDTO.setIs_taking_medicines(false);
        membersDTO.setCity("Mumbai");
        membersDTO.setMartial_status(false);
        membersDTO.setEmail("johndoe@example.com");
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
    }

    // ---- isValidEmail tests ----

    @Test
    void isValidEmail_validEmail_returnsEmptyString() {
        // email needs 6+ chars before @
        String result = policyBookingsService.isValidEmail("johndoe@example.com");
        assertEquals("", result);
    }

    @Test
    void isValidEmail_lessThan6CharsBeforeAt_returnsErrorMessage() {
        String result = policyBookingsService.isValidEmail("ab@example.com");
        assertTrue(result.contains("less than 6 characters"));
    }

    @Test
    void isValidEmail_twoAtSigns_returnsErrorMessage() {
        // Need 6+ chars before first @, then two @ signs
        String result = policyBookingsService.isValidEmail("johndoe@@example.com");
        assertTrue(result.contains("2 @ signs"));
    }

    @Test
    void isValidEmail_noDotAfterAt_returnsErrorMessage() {
        String result = policyBookingsService.isValidEmail("johndoe@examplecom");
        assertTrue(result.contains("Invalid domain name"));
    }

    @Test
    void isValidEmail_startsWithDigit_returnsErrorMessage() {
        String result = policyBookingsService.isValidEmail("1johndoe@example.com");
        assertTrue(result.contains("cannot start with a digit"));
    }

    // ---- toDate tests ----

    @Test
    void toDate_validDateString_returnsDate() throws ParseException {
        Date result = policyBookingsService.toDate("01-01-1990");
        assertNotNull(result);
    }

    @Test
    void toDate_invalidDateString_throwsParseException() {
        assertThrows(ParseException.class, () -> policyBookingsService.toDate("invalid-date"));
    }

    // ---- isValidMobile tests ----

    @Test
    void isValidMobile_validMobile_returnsTrue() {
        assertTrue(policyBookingsService.isValidMobile("9876543210"));
    }

    @Test
    void isValidMobile_startsWithLessThan5_returnsFalse() {
        assertFalse(policyBookingsService.isValidMobile("4876543210"));
    }

    @Test
    void isValidMobile_lessThan10Digits_returnsFalse() {
        assertFalse(policyBookingsService.isValidMobile("987654321"));
    }

    @Test
    void isValidMobile_moreThan10Digits_returnsFalse() {
        assertFalse(policyBookingsService.isValidMobile("98765432101"));
    }

    @Test
    void isValidMobile_startsWithZero_returnsFalse() {
        assertFalse(policyBookingsService.isValidMobile("0876543210"));
    }

    // ---- isValidAadhaar tests ----

    @Test
    void isValidAadhaar_validAadhaar_returnsTrue() {
        assertTrue(policyBookingsService.isValidAadhaar("234567890123"));
    }

    @Test
    void isValidAadhaar_startsWithZero_returnsFalse() {
        assertFalse(policyBookingsService.isValidAadhaar("034567890123"));
    }

    @Test
    void isValidAadhaar_startsWithOne_returnsFalse() {
        assertFalse(policyBookingsService.isValidAadhaar("134567890123"));
    }

    @Test
    void isValidAadhaar_lessThan12Digits_returnsFalse() {
        assertFalse(policyBookingsService.isValidAadhaar("23456789012"));
    }

    @Test
    void isValidAadhaar_moreThan12Digits_returnsFalse() {
        assertFalse(policyBookingsService.isValidAadhaar("2345678901234"));
    }

    // ---- isValidGender tests ----

    @Test
    void isValidGender_male_returnsTrue() {
        assertTrue(policyBookingsService.isValidGender("male"));
    }

    @Test
    void isValidGender_female_returnsTrue() {
        assertTrue(policyBookingsService.isValidGender("female"));
    }

    @Test
    void isValidGender_transgender_returnsTrue() {
        assertTrue(policyBookingsService.isValidGender("transgender"));
    }

    @Test
    void isValidGender_caseInsensitive_returnsTrue() {
        assertTrue(policyBookingsService.isValidGender("MALE"));
        assertTrue(policyBookingsService.isValidGender("Female"));
    }

    @Test
    void isValidGender_invalid_returnsFalse() {
        assertFalse(policyBookingsService.isValidGender("unknown"));
    }

    // ---- isValidName tests ----

    @Test
    void isValidName_validName_returnsTrue() {
        assertTrue(policyBookingsService.isValidName("John Doe"));
    }

    @Test
    void isValidName_nameWithDot_returnsTrue() {
        assertTrue(policyBookingsService.isValidName("Dr. Smith"));
    }

    @Test
    void isValidName_nameWithDigit_returnsFalse() {
        assertFalse(policyBookingsService.isValidName("John123"));
    }

    @Test
    void isValidName_nameWithSpecialChar_returnsFalse() {
        assertFalse(policyBookingsService.isValidName("John@Doe"));
    }

    @Test
    void isValidName_emptyName_returnsTrue() {
        assertTrue(policyBookingsService.isValidName(""));
    }

    // ---- validateMember tests ----

    @Test
    void validateMember_validMember_returnsTrue() throws Exception {
        Boolean result = policyBookingsService.validateMember(membersDTO);
        assertTrue(result);
    }

    @Test
    void validateMember_invalidMobile_throwsInvalidMobileNumberException() {
        membersDTO.setMobile("1234567890");
        assertThrows(InvalidMobileNumberException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_invalidGender_throwsInvalidGenderException() {
        membersDTO.setGender("unknown");
        assertThrows(InvalidGenderException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_invalidEmail_throwsInvalidEmailException() {
        // email with less than 6 chars before @
        membersDTO.setEmail("ab@x.com");
        assertThrows(InvalidEmailException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_invalidRelation_throwsInvalidRelationException() {
        // Need valid email first - use valid email, valid name, valid aadhaar, then invalid relation
        membersDTO.setRelation_with_user("cousin");
        assertThrows(InvalidRelationException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_invalidName_throwsInvalidNameException() {
        // Need valid email, valid relation, then invalid name
        membersDTO.setName("John123");
        assertThrows(InvalidNameException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_invalidAadhaar_throwsInvalidAadhaarNumberException() {
        // Need valid email, valid relation, valid name, then invalid aadhaar
        membersDTO.setAadhaar("123456789012");
        assertThrows(InvalidAadhaarNumberException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    @Test
    void validateMember_nullIsTakingMedicines_throwsInsufficientMemberDetailsException() {
        membersDTO.setIs_taking_medicines(null);
        assertThrows(InsufficientMemberDetailsException.class,
                () -> policyBookingsService.validateMember(membersDTO));
    }

    // ---- getBookedPolicies tests ----

    @Test
    void getBookedPolicies_userNotFound_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingsService.getBookedPolicies(99L));
    }

    @Test
    void getBookedPolicies_userWithNoPolicies_returnsEmptyList() {
        user.setPolicyBookingsList(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.getConfiguration()).thenReturn(mock(org.modelmapper.config.Configuration.class));
        when(modelMapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(new ArrayList<>());

        List<PolicyBookingsGetListDTO> result = policyBookingsService.getBookedPolicies(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---- bookPolicy tests ----

    @Test
    void bookPolicy_userNotFound_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingsService.bookPolicy(99L, 1L, policyBookingInputDTO));
    }

    @Test
    void bookPolicy_policyNotFound_throwsPolicyDoesNotExistException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(policyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PolicyDoesNotExistException.class,
                () -> policyBookingsService.bookPolicy(1L, 99L, policyBookingInputDTO));
    }

    @Test
    void bookPolicy_invalidCoverAmount_throwsCoverAmountNotSupportedException() {
        policyBookingInputDTO.setCoverAmount(999999.0F);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        assertThrows(CoverAmountNotSupportedException.class,
                () -> policyBookingsService.bookPolicy(1L, 1L, policyBookingInputDTO));
    }

    @Test
    void bookPolicy_invalidCoverTenure_throwsCoverTenureNotSupportedException() {
        policyBookingInputDTO.setCoverTenure(99);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        assertThrows(CoverTenureNotSupportedException.class,
                () -> policyBookingsService.bookPolicy(1L, 1L, policyBookingInputDTO));
    }

    // ---- fetchHospitals tests ----

    @Test
    void fetchHospitals_userNotFound_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingsService.fetchHospitals(99L));
    }

    @Test
    void fetchHospitals_userWithNoPolicies_returnsEmptyList() {
        user.setPolicyBookingsList(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<HospitalLocationDTO> result = policyBookingsService.fetchHospitals(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---- fetchLatest tests ----

    @Test
    void fetchLatest_userNotFound_throwsUserDoesNotExistException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class,
                () -> policyBookingsService.fetchLatest(99L));
    }

    @Test
    void fetchLatest_noClaimsNoBookings_returnsLatestClaimDTOWithFalseFlags() {
        user.setPolicyBookingsList(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(claimService.getAllTheClaims(1L)).thenReturn(new ArrayList<>());
        when(modelMapper.getConfiguration()).thenReturn(mock(org.modelmapper.config.Configuration.class));
        when(modelMapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(new ArrayList<>());

        LatestClaimDTO result = policyBookingsService.fetchLatest(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getUserName());
        assertFalse(result.getIsBookingThere());
        assertFalse(result.getIsClaimThere());
    }
}
