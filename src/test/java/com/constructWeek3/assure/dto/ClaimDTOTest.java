package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClaimDTOTest {

    @Test
    void claimDTO_defaultConstructor_createsInstance() {
        ClaimDTO dto = new ClaimDTO();
        assertNotNull(dto);
    }

    @Test
    void claimDTO_allArgsConstructor_setsAllFields() {
        Date date = new Date();
        ClaimDTO dto = new ClaimDTO(1L, 2L, "John", date, 5000.0F, date, "Jane", "Processing", "Surgery", "Health Plus");
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getMemberId());
        assertEquals("John", dto.getUserName());
        assertEquals(5000.0F, dto.getAmountToClaim());
        assertEquals("Processing", dto.getStatus());
        assertEquals("Surgery", dto.getClaimItem());
        assertEquals("Health Plus", dto.getPolicyBookingName());
    }

    @Test
    void claimDTO_settersAndGetters_workCorrectly() {
        ClaimDTO dto = new ClaimDTO();
        Date date = new Date();
        dto.setId(1L);
        dto.setMemberId(2L);
        dto.setUserName("John");
        dto.setDateOfClaim(date);
        dto.setAmountToClaim(5000.0F);
        dto.setSubmissionDate(date);
        dto.setNameOfMember("Jane");
        dto.setStatus("Processing");
        dto.setClaimItem("Surgery");
        dto.setPolicyBookingName("Health Plus");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getMemberId());
        assertEquals("John", dto.getUserName());
        assertEquals(date, dto.getDateOfClaim());
        assertEquals(5000.0F, dto.getAmountToClaim());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals("Jane", dto.getNameOfMember());
        assertEquals("Processing", dto.getStatus());
        assertEquals("Surgery", dto.getClaimItem());
        assertEquals("Health Plus", dto.getPolicyBookingName());
    }
}
