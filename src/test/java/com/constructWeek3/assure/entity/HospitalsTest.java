package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HospitalsTest {

    private Hospitals hospitals;

    @BeforeEach
    void setUp() {
        hospitals = new Hospitals();
        hospitals.setHospitalId(1L);
        hospitals.setName("City Hospital");
    }

    @Test
    void hospitals_defaultConstructor_createsInstance() {
        Hospitals h = new Hospitals();
        assertNotNull(h);
    }

    @Test
    void hospitals_gettersAndSetters_workCorrectly() {
        assertEquals(1L, hospitals.getHospitalId());
        assertEquals("City Hospital", hospitals.getName());
    }

    @Test
    void hospitals_setLocation_setsCorrectly() {
        Location location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");

        hospitals.setLocation(location);

        assertEquals(location, hospitals.getLocation());
    }

    @Test
    void hospitals_addClaim_addsToClaimsList() {
        Claim claim = new Claim();
        claim.setId(1L);

        hospitals.addClaim(claim);

        assertEquals(1, hospitals.getClaims().size());
        assertTrue(hospitals.getClaims().contains(claim));
    }

    @Test
    void hospitals_initialClaimsListIsEmpty() {
        Hospitals newHospital = new Hospitals();
        assertNotNull(newHospital.getClaims());
        assertTrue(newHospital.getClaims().isEmpty());
    }
}
