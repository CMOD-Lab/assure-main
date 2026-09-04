package com.constructWeek3.assure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneOTPTest {

    private PhoneOTP phoneOTP;

    @BeforeEach
    void setUp() {
        phoneOTP = new PhoneOTP("9876543210", "1234");
    }

    @Test
    void phoneOTP_twoArgConstructor_setsFields() {
        assertEquals("9876543210", phoneOTP.getUserMobile());
        assertEquals("1234", phoneOTP.getOtp());
    }

    @Test
    void phoneOTP_defaultConstructor_createsInstance() {
        PhoneOTP p = new PhoneOTP();
        assertNotNull(p);
    }

    @Test
    void phoneOTP_allArgsConstructor_setsAllFields() {
        PhoneOTP p = new PhoneOTP(1L, "9876543210", "5678");
        assertEquals(1L, p.getId());
        assertEquals("9876543210", p.getUserMobile());
        assertEquals("5678", p.getOtp());
    }

    @Test
    void phoneOTP_settersAndGetters_workCorrectly() {
        phoneOTP.setId(1L);
        phoneOTP.setUserMobile("1234567890");
        phoneOTP.setOtp("9999");

        assertEquals(1L, phoneOTP.getId());
        assertEquals("1234567890", phoneOTP.getUserMobile());
        assertEquals("9999", phoneOTP.getOtp());
    }

    @Test
    void phoneOTP_toString_returnsNonNull() {
        assertNotNull(phoneOTP.toString());
    }
}
