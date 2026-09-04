package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolicyDTOTest {

    @Test
    void policyDTO_defaultConstructor_createsInstance() {
        PolicyDTO dto = new PolicyDTO();
        assertNotNull(dto);
    }

    @Test
    void policyDTO_settersAndGetters_workCorrectly() {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyId(1L);
        dto.setPolicyName("Health Plus");
        dto.setRoomRentLimit("5000");
        dto.setClaimBonus(10.0F);
        dto.setPedWaitingPeriod("2 years");
        dto.setCopayPercent(20.0F);
        dto.setIsCriticalIllnessCovered(true);
        dto.setIsMaternityCovered(false);
        dto.setIsRestorationBenefitsCovered(true);
        dto.setPremium1(1000.0F);
        dto.setPremium2(1200.0F);
        dto.setPremium3(1400.0F);
        dto.setCoverAmount1(100000.0F);
        dto.setCoverAmount2(200000.0F);
        dto.setCoverAmount3(300000.0F);
        dto.setTenure1(1);
        dto.setTenure2(2);
        dto.setTenure3(3);

        assertEquals(1L, dto.getPolicyId());
        assertEquals("Health Plus", dto.getPolicyName());
        assertEquals("5000", dto.getRoomRentLimit());
        assertEquals(10.0F, dto.getClaimBonus());
        assertEquals("2 years", dto.getPedWaitingPeriod());
        assertEquals(20.0F, dto.getCopayPercent());
        assertTrue(dto.getIsCriticalIllnessCovered());
        assertFalse(dto.getIsMaternityCovered());
        assertTrue(dto.getIsRestorationBenefitsCovered());
        assertEquals(1000.0F, dto.getPremium1());
        assertEquals(1200.0F, dto.getPremium2());
        assertEquals(1400.0F, dto.getPremium3());
        assertEquals(100000.0F, dto.getCoverAmount1());
        assertEquals(200000.0F, dto.getCoverAmount2());
        assertEquals(300000.0F, dto.getCoverAmount3());
        assertEquals(1, dto.getTenure1());
        assertEquals(2, dto.getTenure2());
        assertEquals(3, dto.getTenure3());
    }
}
