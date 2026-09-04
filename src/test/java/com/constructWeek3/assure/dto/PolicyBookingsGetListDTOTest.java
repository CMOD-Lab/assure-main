package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolicyBookingsGetListDTOTest {

    @Test
    void policyBookingsGetListDTO_defaultConstructor_createsInstance() {
        PolicyBookingsGetListDTO dto = new PolicyBookingsGetListDTO();
        assertNotNull(dto);
        assertNotNull(dto.getMembers());
    }

    @Test
    void policyBookingsGetListDTO_settersAndGetters_workCorrectly() {
        PolicyBookingsGetListDTO dto = new PolicyBookingsGetListDTO();
        Date date = new Date();
        dto.setBookingId(1L);
        dto.setUserName("John");
        dto.setBookingDate(date);
        dto.setValidTillDate(date);
        dto.setCoverAmount(100000.0F);
        dto.setPremium(1000.0F);
        dto.setCoverTenure(1);
        dto.setPolicyName("Health Plus");
        dto.setRoomRentLimit("5000");
        dto.setClaimBonus(10.0F);
        dto.setPedWaitingPeriod("2 years");
        dto.setCopayPercent(20.0F);
        dto.setIsCriticalIllnessCovered(true);
        dto.setIsMaternityCovered(false);
        dto.setIsRestorationBenefitsCovered(true);

        assertEquals(1L, dto.getBookingId());
        assertEquals("John", dto.getUserName());
        assertEquals(date, dto.getBookingDate());
        assertEquals(date, dto.getValidTillDate());
        assertEquals(100000.0F, dto.getCoverAmount());
        assertEquals(1000.0F, dto.getPremium());
        assertEquals(1, dto.getCoverTenure());
        assertEquals("Health Plus", dto.getPolicyName());
        assertEquals("5000", dto.getRoomRentLimit());
        assertEquals(10.0F, dto.getClaimBonus());
        assertEquals("2 years", dto.getPedWaitingPeriod());
        assertEquals(20.0F, dto.getCopayPercent());
        assertTrue(dto.getIsCriticalIllnessCovered());
        assertFalse(dto.getIsMaternityCovered());
        assertTrue(dto.getIsRestorationBenefitsCovered());
    }

    @Test
    void policyBookingsGetListDTO_addMember_addsToMembersList() {
        PolicyBookingsGetListDTO dto = new PolicyBookingsGetListDTO();
        ProfileMemberDTO memberDTO = new ProfileMemberDTO();

        dto.addMember(memberDTO);

        assertEquals(1, dto.getMembers().size());
    }

    @Test
    void policyBookingsGetListDTO_toString_returnsNonNull() {
        PolicyBookingsGetListDTO dto = new PolicyBookingsGetListDTO();
        assertNotNull(dto.toString());
    }
}
