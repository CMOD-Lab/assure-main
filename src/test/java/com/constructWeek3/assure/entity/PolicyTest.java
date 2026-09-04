package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {

    private Policy policy;

    @BeforeEach
    void setUp() {
        policy = new Policy();
        policy.setPolicyId(1L);
        policy.setPolicyName("Health Plus");
        policy.setRoomRentLimit("5000");
        policy.setClaimBonus(10.0F);
        policy.setPedWaitingPeriod("2 years");
        policy.setCopayPercent(20.0F);
        policy.setIsCriticalIllnessCovered(true);
        policy.setIsMaternityCovered(false);
        policy.setIsRestorationBenefitsCovered(true);
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
    }

    @Test
    void policy_defaultConstructor_createsInstance() {
        Policy p = new Policy();
        assertNotNull(p);
    }

    @Test
    void policy_gettersAndSetters_workCorrectly() {
        assertEquals(1L, policy.getPolicyId());
        assertEquals("Health Plus", policy.getPolicyName());
        assertEquals("5000", policy.getRoomRentLimit());
        assertEquals(10.0F, policy.getClaimBonus());
        assertEquals("2 years", policy.getPedWaitingPeriod());
        assertEquals(20.0F, policy.getCopayPercent());
        assertTrue(policy.getIsCriticalIllnessCovered());
        assertFalse(policy.getIsMaternityCovered());
        assertTrue(policy.getIsRestorationBenefitsCovered());
        assertEquals(500.0F, policy.getPremiumUpto18());
        assertEquals(1000.0F, policy.getPremiumUpto45());
        assertEquals(1500.0F, policy.getPremiumUpto60());
        assertEquals(2000.0F, policy.getPremiumBeyond60());
        assertEquals(100000.0F, policy.getCoverAmount1());
        assertEquals(200000.0F, policy.getCoverAmount2());
        assertEquals(300000.0F, policy.getCoverAmount3());
        assertEquals(1, policy.getTenure1());
        assertEquals(2, policy.getTenure2());
        assertEquals(3, policy.getTenure3());
    }

    @Test
    void policy_addPolicyBooking_addsToPolicyBookingsSet() {
        PolicyBookings policyBookings = new PolicyBookings();
        policyBookings.setBookingId(1L);

        policy.addPolicyBooking(policyBookings);

        assertTrue(policy.getPolicyBookings().contains(policyBookings));
    }

    @Test
    void policy_addLocation_addsToLocationsList() {
        Location location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");

        policy.addLocation(location);

        assertTrue(policy.locations.contains(location));
    }

    @Test
    void policy_addHospital_addsToHospitalsSet() {
        Hospitals hospital = new Hospitals();
        hospital.setHospitalId(1L);
        hospital.setName("City Hospital");

        policy.addHospital(hospital);

        assertTrue(policy.getHospitals().contains(hospital));
    }

    @Test
    void policy_initialHospitalsSetIsEmpty() {
        Policy newPolicy = new Policy();
        assertNotNull(newPolicy.getHospitals());
        assertTrue(newPolicy.getHospitals().isEmpty());
    }

    @Test
    void policy_initialLocationsListIsEmpty() {
        Policy newPolicy = new Policy();
        assertNotNull(newPolicy.locations);
        assertTrue(newPolicy.locations.isEmpty());
    }

    @Test
    void policy_initialPolicyBookingsSetIsEmpty() {
        Policy newPolicy = new Policy();
        assertNotNull(newPolicy.getPolicyBookings());
        assertTrue(newPolicy.getPolicyBookings().isEmpty());
    }
}
