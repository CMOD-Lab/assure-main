package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ToClaimDTOTest {

    @Test
    void toClaimDTO_defaultConstructor_createsInstance() {
        ToClaimDTO dto = new ToClaimDTO();
        assertNotNull(dto);
    }

    @Test
    void toClaimDTO_allArgsConstructor_setsAllFields() {
        Date date = new Date();
        ToClaimDTO dto = new ToClaimDTO("Medical", 123456789012L, "Jane", "City Hospital",
                date, date, "Processing", "Surgery", 5000.0F, true, false);
        assertEquals("Medical", dto.getType());
        assertEquals(123456789012L, dto.getAadharNumber());
        assertEquals("Jane", dto.getNameOfMember());
        assertEquals("City Hospital", dto.getHospitalName());
        assertEquals("Processing", dto.getStatus());
        assertEquals("Surgery", dto.getClaimItem());
        assertEquals(5000.0F, dto.getAmountToClaim());
        assertTrue(dto.getPreauthorizedConfirmation());
        assertFalse(dto.getFollowUpVisits());
    }

    @Test
    void toClaimDTO_settersAndGetters_workCorrectly() {
        ToClaimDTO dto = new ToClaimDTO();
        Date date = new Date();
        dto.setType("Medical");
        dto.setAadharNumber(123456789012L);
        dto.setNameOfMember("Jane");
        dto.setHospitalName("City Hospital");
        dto.setDateOfTreatment(date);
        dto.setSubmissionDate(date);
        dto.setStatus("Processing");
        dto.setClaimItem("Surgery");
        dto.setAmountToClaim(5000.0F);
        dto.setPreauthorizedConfirmation(true);
        dto.setFollowUpVisits(false);

        assertEquals("Medical", dto.getType());
        assertEquals(123456789012L, dto.getAadharNumber());
        assertEquals("Jane", dto.getNameOfMember());
        assertEquals("City Hospital", dto.getHospitalName());
        assertEquals(date, dto.getDateOfTreatment());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals("Processing", dto.getStatus());
        assertEquals("Surgery", dto.getClaimItem());
        assertEquals(5000.0F, dto.getAmountToClaim());
        assertTrue(dto.getPreauthorizedConfirmation());
        assertFalse(dto.getFollowUpVisits());
    }
}
