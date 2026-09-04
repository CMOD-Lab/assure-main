package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PolicyBookingsTest {

    private PolicyBookings policyBookings;

    @BeforeEach
    void setUp() {
        policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);
        policyBookings.setBookingDate(new Date());
        policyBookings.setPolicyName("Health Plus");
        policyBookings.setCoverAmount(100000.0F);
        policyBookings.setPremium(1000.0F);
        policyBookings.setCoverTenure(1);
    }

    @Test
    void policyBookings_defaultConstructor_createsInstance() {
        PolicyBookings pb = new PolicyBookings();
        assertNotNull(pb);
    }

    @Test
    void policyBookings_gettersAndSetters_workCorrectly() {
        assertEquals(1L, policyBookings.getBookingId());
        assertNotNull(policyBookings.getBookingDate());
        assertEquals("Health Plus", policyBookings.getPolicyName());
        assertEquals(100000.0F, policyBookings.getCoverAmount());
        assertEquals(1000.0F, policyBookings.getPremium());
        assertEquals(1, policyBookings.getCoverTenure());
    }

    @Test
    void policyBookings_addMember_addsToMembersSet() {
        Members member = new Members();
        member.setMember_id(1L);
        member.setName("Alice");

        policyBookings.addMember(member);

        assertTrue(policyBookings.getMembers().contains(member));
    }

    @Test
    void policyBookings_addClaim_addsToClaimsList() {
        Claim claim = new Claim();
        claim.setId(1L);

        policyBookings.addClaim(claim);

        assertTrue(policyBookings.getListOfClaims().contains(claim));
    }

    @Test
    void policyBookings_setPolicy_setsCorrectly() {
        Policy policy = new Policy();
        policy.setPolicyId(1L);
        policy.setPolicyName("Health Plus");

        policyBookings.setPolicy(policy);

        assertEquals(policy, policyBookings.getPolicy());
    }

    @Test
    void policyBookings_setUser_setsCorrectly() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("John");

        policyBookings.setUser(user);

        assertEquals(user, policyBookings.getUser());
    }

    @Test
    void policyBookings_initialMembersSetIsEmpty() {
        PolicyBookings newPb = new PolicyBookings();
        assertNotNull(newPb.getMembers());
        assertTrue(newPb.getMembers().isEmpty());
    }

    @Test
    void policyBookings_initialClaimsListIsEmpty() {
        PolicyBookings newPb = new PolicyBookings();
        assertNotNull(newPb.getListOfClaims());
        assertTrue(newPb.getListOfClaims().isEmpty());
    }
}
