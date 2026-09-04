package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClaimTest {

    private Claim claim;

    @BeforeEach
    void setUp() {
        claim = new Claim();
        claim.setId(1L);
        claim.setType("Medical");
        claim.setName("John Doe");
        claim.setAadharNumber(123456789012L);
        claim.setDateOfTreatment(new Date());
        claim.setSubmissionDate(new Date());
        claim.setStatus("Processing");
        claim.setClaimItem("Surgery");
        claim.setAmountToClaim(5000.0F);
        claim.setPreauthorizedConfirmation(true);
        claim.setFollowUpVisits(false);
    }

    @Test
    void claim_defaultConstructor_createsInstance() {
        Claim c = new Claim();
        assertNotNull(c);
    }

    @Test
    void claim_gettersAndSetters_workCorrectly() {
        assertEquals(1L, claim.getId());
        assertEquals("Medical", claim.getType());
        assertEquals("John Doe", claim.getName());
        assertEquals(123456789012L, claim.getAadharNumber());
        assertNotNull(claim.getDateOfTreatment());
        assertNotNull(claim.getSubmissionDate());
        assertEquals("Processing", claim.getStatus());
        assertEquals("Surgery", claim.getClaimItem());
        assertEquals(5000.0F, claim.getAmountToClaim());
        assertTrue(claim.getPreauthorizedConfirmation());
        assertFalse(claim.getFollowUpVisits());
    }

    @Test
    void claim_addDocuments_addsToDocumentsList() {
        Document document = new Document();

        claim.addDocuments(document);

        assertEquals(1, claim.getDocuments().size());
        assertTrue(claim.getDocuments().contains(document));
    }

    @Test
    void claim_initialDocumentsListIsEmpty() {
        Claim newClaim = new Claim();
        assertNotNull(newClaim.getDocuments());
        assertTrue(newClaim.getDocuments().isEmpty());
    }

    @Test
    void claim_setMember_setsCorrectly() {
        Members member = new Members();
        member.setMember_id(1L);
        member.setName("Alice");

        claim.setMember(member);

        assertEquals(member, claim.getMember());
    }

    @Test
    void claim_setUser_setsCorrectly() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("John");

        claim.setUser(user);

        assertEquals(user, claim.getUser());
    }

    @Test
    void claim_setHospitals_setsCorrectly() {
        Hospitals hospital = new Hospitals();
        hospital.setHospitalId(1L);
        hospital.setName("City Hospital");

        claim.setHospitals(hospital);

        assertEquals(hospital, claim.getHospitals());
    }

    @Test
    void claim_setPolicyBookings_setsCorrectly() {
        PolicyBookings policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);

        claim.setPolicyBookings(policyBookings);

        assertEquals(policyBookings, claim.getPolicyBookings());
    }
}
