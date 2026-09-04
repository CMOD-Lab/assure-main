package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLocationId(1L);
        location.setName("Mumbai");
    }

    @Test
    void location_defaultConstructor_createsInstance() {
        Location l = new Location();
        assertNotNull(l);
    }

    @Test
    void location_allArgsConstructor_setsAllFields() {
        List<Policy> policies = new ArrayList<>();
        List<Hospitals> hospitals = new ArrayList<>();
        Location l = new Location(1L, "Delhi", policies, hospitals);
        assertEquals(1L, l.getLocationId());
        assertEquals("Delhi", l.getName());
    }

    @Test
    void location_gettersAndSetters_workCorrectly() {
        assertEquals(1L, location.getLocationId());
        assertEquals("Mumbai", location.getName());
    }

    @Test
    void location_addHospital_addsToHospitalsList() {
        Hospitals hospital = new Hospitals();
        hospital.setHospitalId(1L);
        hospital.setName("City Hospital");

        location.addHospital(hospital);

        assertEquals(1, location.getHospitalsList().size());
        assertTrue(location.getHospitalsList().contains(hospital));
    }

    @Test
    void location_addPolicies_addsToPoliciesList() {
        Policy policy = new Policy();
        policy.setPolicyId(1L);

        location.addPolicies(policy);

        assertEquals(1, location.getPolicies().size());
        assertTrue(location.getPolicies().contains(policy));
    }

    @Test
    void location_initialHospitalsListIsEmpty() {
        Location newLocation = new Location();
        assertNotNull(newLocation.getHospitalsList());
        assertTrue(newLocation.getHospitalsList().isEmpty());
    }

    @Test
    void location_initialPoliciesListIsEmpty() {
        Location newLocation = new Location();
        assertNotNull(newLocation.getPolicies());
        assertTrue(newLocation.getPolicies().isEmpty());
    }
}
