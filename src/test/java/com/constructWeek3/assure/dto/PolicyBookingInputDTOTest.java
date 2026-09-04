package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PolicyBookingInputDTOTest {

    @Test
    void policyBookingInputDTO_defaultConstructor_createsInstance() {
        PolicyBookingInputDTO dto = new PolicyBookingInputDTO();
        assertNotNull(dto);
        assertNotNull(dto.getMembers());
    }

    @Test
    void policyBookingInputDTO_allArgsConstructor_setsAllFields() {
        HashSet<MembersDTO> members = new HashSet<>();
        PolicyBookingInputDTO dto = new PolicyBookingInputDTO(100000.0F, 1000.0F, 1, members);
        assertEquals(100000.0F, dto.getCoverAmount());
        assertEquals(1000.0F, dto.getPremium());
        assertEquals(1, dto.getCoverTenure());
        assertNotNull(dto.getMembers());
    }

    @Test
    void policyBookingInputDTO_settersAndGetters_workCorrectly() {
        PolicyBookingInputDTO dto = new PolicyBookingInputDTO();
        dto.setCoverAmount(200000.0F);
        dto.setPremium(2000.0F);
        dto.setCoverTenure(2);

        assertEquals(200000.0F, dto.getCoverAmount());
        assertEquals(2000.0F, dto.getPremium());
        assertEquals(2, dto.getCoverTenure());
    }

    @Test
    void policyBookingInputDTO_setMembers_setsCorrectly() {
        PolicyBookingInputDTO dto = new PolicyBookingInputDTO();
        HashSet<MembersDTO> members = new HashSet<>();
        MembersDTO membersDTO = new MembersDTO();
        membersDTO.setName("Alice");
        members.add(membersDTO);

        dto.setMembers(members);

        assertEquals(1, dto.getMembers().size());
    }
}
