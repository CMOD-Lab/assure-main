package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembersDTOTest {

    @Test
    void membersDTO_defaultConstructor_createsInstance() {
        MembersDTO dto = new MembersDTO();
        assertNotNull(dto);
    }

    @Test
    void membersDTO_settersAndGetters_workCorrectly() {
        MembersDTO dto = new MembersDTO();
        dto.setName("Alice");
        dto.setRelation_with_user("self");
        dto.setDob("01-01-1993");
        dto.setGender("female");
        dto.setIs_taking_medicines(false);
        dto.setCity("Mumbai");
        dto.setMartial_status(true);
        dto.setEmail("alice@example.com");
        dto.setAadhaar("234567890123");
        dto.setMobile("9876543210");
        dto.setOccupation("Engineer");
        dto.setHeight("165cm");
        dto.setWeight(60.0F);

        assertEquals("Alice", dto.getName());
        assertEquals("self", dto.getRelation_with_user());
        assertEquals("01-01-1993", dto.getDob());
        assertEquals("female", dto.getGender());
        assertFalse(dto.getIs_taking_medicines());
        assertEquals("Mumbai", dto.getCity());
        assertTrue(dto.getMartial_status());
        assertEquals("alice@example.com", dto.getEmail());
        assertEquals("234567890123", dto.getAadhaar());
        assertEquals("9876543210", dto.getMobile());
        assertEquals("Engineer", dto.getOccupation());
        assertEquals("165cm", dto.getHeight());
        assertEquals(60.0F, dto.getWeight());
    }

    @Test
    void membersDTO_equalsAndHashCode_workCorrectly() {
        MembersDTO dto1 = new MembersDTO();
        dto1.setEmail("alice@example.com");
        dto1.setMobile("9876543210");

        MembersDTO dto2 = new MembersDTO();
        dto2.setEmail("alice@example.com");
        dto2.setMobile("9876543210");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
