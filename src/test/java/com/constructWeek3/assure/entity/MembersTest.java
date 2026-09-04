package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MembersTest {

    private Members members;

    @BeforeEach
    void setUp() {
        members = new Members();
        members.setMember_id(1L);
        members.setName("Alice");
        members.setRelation_with_user("self");
        members.setDOB(new Date());
        members.setGender("female");
        members.setIs_taking_medicines(false);
        members.setCity("Mumbai");
        members.setMartial_status(true);
        members.setEmail("alice@example.com");
        members.setAadhaar("234567890123");
        members.setMobile("9876543210");
        members.setOccupation("Engineer");
        members.setHeight("165cm");
        members.setWeight(60.0F);
    }

    @Test
    void members_defaultConstructor_createsInstance() {
        Members m = new Members();
        assertNotNull(m);
    }

    @Test
    void members_gettersAndSetters_workCorrectly() {
        assertEquals(1L, members.getMember_id());
        assertEquals("Alice", members.getName());
        assertEquals("self", members.getRelation_with_user());
        assertNotNull(members.getDOB());
        assertEquals("female", members.getGender());
        assertFalse(members.getIs_taking_medicines());
        assertEquals("Mumbai", members.getCity());
        assertTrue(members.getMartial_status());
        assertEquals("alice@example.com", members.getEmail());
        assertEquals("234567890123", members.getAadhaar());
        assertEquals("9876543210", members.getMobile());
        assertEquals("Engineer", members.getOccupation());
        assertEquals("165cm", members.getHeight());
        assertEquals(60.0F, members.getWeight());
    }

    @Test
    void members_addClaim_addsToClaimsList() {
        Claim claim = new Claim();
        claim.setId(1L);

        members.addClaim(claim);

        assertEquals(1, members.getClaims().size());
        assertTrue(members.getClaims().contains(claim));
    }

    @Test
    void members_initialClaimsListIsEmpty() {
        Members newMembers = new Members();
        assertNotNull(newMembers.getClaims());
        assertTrue(newMembers.getClaims().isEmpty());
    }

    @Test
    void members_setPolicyBookings_setsCorrectly() {
        PolicyBookings policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);

        members.setPolicyBookings(policyBookings);

        assertEquals(policyBookings, members.getPolicyBookings());
    }
}
