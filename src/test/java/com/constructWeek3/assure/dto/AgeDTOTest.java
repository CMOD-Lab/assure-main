package com.constructWeek3.assure.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgeDTOTest {

    @Test
    void ageDTO_defaultConstructor_createsInstance() {
        AgeDTO ageDTO = new AgeDTO();
        assertNotNull(ageDTO);
    }

    @Test
    void ageDTO_allArgsConstructor_setsAllFields() {
        AgeDTO ageDTO = new AgeDTO(30, 5, 3, 28, 55, 52);
        assertEquals(30, ageDTO.getAgeOfSelf());
        assertEquals(5, ageDTO.getAgeOfSon());
        assertEquals(3, ageDTO.getAgeOfDaughter());
        assertEquals(28, ageDTO.getAgeOfSpouse());
        assertEquals(55, ageDTO.getAgeOfFather());
        assertEquals(52, ageDTO.getAgeOfMother());
    }

    @Test
    void ageDTO_settersAndGetters_workCorrectly() {
        AgeDTO ageDTO = new AgeDTO();
        ageDTO.setAgeOfSelf(30);
        ageDTO.setAgeOfSon(5);
        ageDTO.setAgeOfDaughter(3);
        ageDTO.setAgeOfSpouse(28);
        ageDTO.setAgeOfFather(55);
        ageDTO.setAgeOfMother(52);

        assertEquals(30, ageDTO.getAgeOfSelf());
        assertEquals(5, ageDTO.getAgeOfSon());
        assertEquals(3, ageDTO.getAgeOfDaughter());
        assertEquals(28, ageDTO.getAgeOfSpouse());
        assertEquals(55, ageDTO.getAgeOfFather());
        assertEquals(52, ageDTO.getAgeOfMother());
    }

    @Test
    void ageDTO_toString_returnsNonNull() {
        AgeDTO ageDTO = new AgeDTO(30, 5, 3, 28, 55, 52);
        assertNotNull(ageDTO.toString());
    }
}
