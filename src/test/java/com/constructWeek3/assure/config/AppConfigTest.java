package com.constructWeek3.assure.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void appConfig_defaultConstructor_createsInstance() {
        AppConfig appConfig = new AppConfig();
        assertNotNull(appConfig);
    }

    @Test
    void modelMapper_returnsNonNullModelMapper() {
        AppConfig appConfig = new AppConfig();
        ModelMapper modelMapper = appConfig.modelMapper();
        assertNotNull(modelMapper);
    }

    @Test
    void modelMapper_returnsModelMapperInstance() {
        AppConfig appConfig = new AppConfig();
        ModelMapper modelMapper = appConfig.modelMapper();
        assertTrue(modelMapper instanceof ModelMapper);
    }

    @Test
    void modelMapper_hasDefaultConfiguration() {
        AppConfig appConfig = new AppConfig();
        ModelMapper modelMapper = appConfig.modelMapper();
        assertNotNull(modelMapper.getConfiguration());
    }
}
