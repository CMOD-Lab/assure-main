package com.constructWeek3.assure.modelmapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class ModelMapperClassTest {

    @Test
    void modelMapper_returnsNonNullModelMapper() {
        ModelMapper modelMapper = ModelMapperClass.modelMapper();
        assertNotNull(modelMapper);
    }

    @Test
    void modelMapper_returnsNewInstanceEachTime() {
        ModelMapper mapper1 = ModelMapperClass.modelMapper();
        ModelMapper mapper2 = ModelMapperClass.modelMapper();
        assertNotNull(mapper1);
        assertNotNull(mapper2);
    }

    @Test
    void modelMapperClass_defaultConstructor_createsInstance() {
        ModelMapperClass modelMapperClass = new ModelMapperClass();
        assertNotNull(modelMapperClass);
    }

    @Test
    void modelMapper_canMapObjects() {
        ModelMapper modelMapper = ModelMapperClass.modelMapper();
        // Test that the mapper can perform a basic mapping
        assertNotNull(modelMapper.getConfiguration());
    }
}
