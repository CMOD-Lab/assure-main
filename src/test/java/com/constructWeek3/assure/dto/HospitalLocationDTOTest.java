package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HospitalLocationDTOTest {

    @Test
    void hospitalLocationDTO_defaultConstructor_createsInstance() {
        HospitalLocationDTO dto = new HospitalLocationDTO();
        assertNotNull(dto);
    }

    @Test
    void hospitalLocationDTO_allArgsConstructor_setsAllFields() {
        HospitalLocationDTO dto = new HospitalLocationDTO(1L, "Health Plus", "Mumbai", "City Hospital");
        assertEquals(1L, dto.getBookingId());
        assertEquals("Health Plus", dto.getPolicyName());
        assertEquals("Mumbai", dto.getLocation());
        assertEquals("City Hospital", dto.getHospital());
    }

    @Test
    void hospitalLocationDTO_settersAndGetters_workCorrectly() {
        HospitalLocationDTO dto = new HospitalLocationDTO();
        dto.setBookingId(1L);
        dto.setPolicyName("Health Plus");
        dto.setLocation("Mumbai");
        dto.setHospital("City Hospital");

        assertEquals(1L, dto.getBookingId());
        assertEquals("Health Plus", dto.getPolicyName());
        assertEquals("Mumbai", dto.getLocation());
        assertEquals("City Hospital", dto.getHospital());
    }
}
