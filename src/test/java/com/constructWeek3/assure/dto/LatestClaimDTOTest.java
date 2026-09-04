package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LatestClaimDTOTest {

    @Test
    void latestClaimDTO_defaultConstructor_createsInstance() {
        LatestClaimDTO dto = new LatestClaimDTO();
        assertNotNull(dto);
    }

    @Test
    void latestClaimDTO_settersAndGetters_workCorrectly() {
        LatestClaimDTO dto = new LatestClaimDTO();
        Date date = new Date();
        dto.setId(1L);
        dto.setBookingId(2L);
        dto.setUserName("John");
        dto.setIsBookingThere(true);
        dto.setPolicyName("Health Plus");
        dto.setIsClaimThere(false);
        dto.setStatus("Processing");
        dto.setClaimItem("Surgery");
        dto.setSubmissionDate(date);
        dto.setMemberId(3L);
        dto.setValidTillDate(date);
        dto.setCoverAmount(100000.0F);
        dto.setMemberCount(2);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getBookingId());
        assertEquals("John", dto.getUserName());
        assertTrue(dto.getIsBookingThere());
        assertEquals("Health Plus", dto.getPolicyName());
        assertFalse(dto.getIsClaimThere());
        assertEquals("Processing", dto.getStatus());
        assertEquals("Surgery", dto.getClaimItem());
        assertEquals(date, dto.getSubmissionDate());
        assertEquals(3L, dto.getMemberId());
        assertEquals(date, dto.getValidTillDate());
        assertEquals(100000.0F, dto.getCoverAmount());
        assertEquals(2, dto.getMemberCount());
    }

    @Test
    void latestClaimDTO_allArgsConstructor_setsAllFields() {
        Date date = new Date();
        LatestClaimDTO dto = new LatestClaimDTO(1L, 2L, "John", true, "Health Plus", false,
                "Processing", "Surgery", date, 3L, date, 100000.0F, 2);
        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getUserName());
        assertTrue(dto.getIsBookingThere());
    }
}
