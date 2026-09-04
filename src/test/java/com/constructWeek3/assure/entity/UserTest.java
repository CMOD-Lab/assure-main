package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");
        user.setUserEmail("john@example.com");
        user.setUserMobile("9876543210");
        user.setUserPass("password123");
    }

    @Test
    void user_defaultConstructor_createsInstance() {
        User u = new User();
        assertNotNull(u);
    }

    @Test
    void user_allArgsConstructor_setsAllFields() {
        List<PolicyBookings> bookings = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
        User u = new User(1L, "Alice", "alice@example.com", "9876543210", "pass123", bookings, claims);
        assertEquals(1L, u.getUserId());
        assertEquals("Alice", u.getUserName());
        assertEquals("alice@example.com", u.getUserEmail());
    }

    @Test
    void user_gettersAndSetters_workCorrectly() {
        assertEquals(1L, user.getUserId());
        assertEquals("John Doe", user.getUserName());
        assertEquals("john@example.com", user.getUserEmail());
        assertEquals("9876543210", user.getUserMobile());
        assertEquals("password123", user.getUserPass());
    }

    @Test
    void user_setPolicyBookings_addsToPolicyBookingsList() {
        PolicyBookings policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);

        user.setPolicyBookings(policyBookings);

        assertEquals(1, user.getPolicyBookingsList().size());
        assertTrue(user.getPolicyBookingsList().contains(policyBookings));
    }

    @Test
    void user_removePolicyBookings_removesFromList() {
        PolicyBookings policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);
        user.setPolicyBookings(policyBookings);

        user.removePolicyBookings(policyBookings);

        assertTrue(user.getPolicyBookingsList().isEmpty());
    }

    @Test
    void user_addClaim_addsToClaimsList() {
        Claim claim = new Claim();
        claim.setId(1L);

        user.addClaim(claim);

        assertEquals(1, user.getClaims().size());
        assertTrue(user.getClaims().contains(claim));
    }

    @Test
    void user_toString_returnsNonNull() {
        assertNotNull(user.toString());
    }

    @Test
    void user_initialPolicyBookingsListIsEmpty() {
        User newUser = new User();
        assertNotNull(newUser.getPolicyBookingsList());
        assertTrue(newUser.getPolicyBookingsList().isEmpty());
    }

    @Test
    void user_initialClaimsListIsEmpty() {
        User newUser = new User();
        assertNotNull(newUser.getClaims());
        assertTrue(newUser.getClaims().isEmpty());
    }
}
