package com.constructWeek3.assure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionClassesTest {

    @Test
    void invalidGenderException_createsWithMessage() {
        InvalidGenderException ex = new InvalidGenderException("Invalid gender");
        assertEquals("Invalid gender", ex.getMessage());
    }

    @Test
    void invalidPremiumException_createsWithMessage() {
        InvalidPremiumException ex = new InvalidPremiumException("Invalid premium");
        assertEquals("Invalid premium", ex.getMessage());
    }

    @Test
    void incorrectOTP_createsWithMessage() {
        IncorrectOTP ex = new IncorrectOTP("Incorrect OTP");
        assertEquals("Incorrect OTP", ex.getMessage());
    }

    @Test
    void insufficientMemberDetailsException_createsWithMessage() {
        InsufficientMemberDetailsException ex = new InsufficientMemberDetailsException("Insufficient details");
        assertEquals("Insufficient details", ex.getMessage());
    }

    @Test
    void invalidRelationException_createsWithMessage() {
        InvalidRelationException ex = new InvalidRelationException("Invalid relation");
        assertEquals("Invalid relation", ex.getMessage());
    }

    @Test
    void userExists_createsWithMessage() {
        UserExists ex = new UserExists("User exists");
        assertEquals("User exists", ex.getMessage());
    }

    @Test
    void emptyInputException_createsWithMessage() {
        EmptyInputException ex = new EmptyInputException("Empty input");
        assertEquals("Empty input", ex.getMessage());
    }

    @Test
    void emptyOTP_createsWithMessage() {
        EmptyOTP ex = new EmptyOTP("Empty OTP");
        assertEquals("Empty OTP", ex.getMessage());
    }

    @Test
    void locationDoesNotExistException_createsWithMessage() {
        LocationDoesNotExistException ex = new LocationDoesNotExistException("Location not found");
        assertEquals("Location not found", ex.getMessage());
    }

    @Test
    void invalidEmailException_createsWithMessage() {
        InvalidEmailException ex = new InvalidEmailException("Invalid email");
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    void duplicateMemberException_createsWithMessage() {
        DuplicateMemberException ex = new DuplicateMemberException("Duplicate member");
        assertEquals("Duplicate member", ex.getMessage());
    }

    @Test
    void userDoesNotExistException_createsWithMessage() {
        UserDoesNotExistException ex = new UserDoesNotExistException("User not found");
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void invalidMobileNumberException_createsWithMessage() {
        InvalidMobileNumberException ex = new InvalidMobileNumberException("Invalid mobile");
        assertEquals("Invalid mobile", ex.getMessage());
    }

    @Test
    void invalidNameException_createsWithMessage() {
        InvalidNameException ex = new InvalidNameException("Invalid name");
        assertEquals("Invalid name", ex.getMessage());
    }

    @Test
    void invalidAadhaarNumberException_createsWithMessage() {
        InvalidAadhaarNumberException ex = new InvalidAadhaarNumberException("Invalid aadhaar");
        assertEquals("Invalid aadhaar", ex.getMessage());
    }

    @Test
    void policyDoesNotExistException_createsWithMessage() {
        PolicyDoesNotExistException ex = new PolicyDoesNotExistException("Policy not found");
        assertEquals("Policy not found", ex.getMessage());
    }

    @Test
    void invalidAgeOfMemberException_createsWithMessage() {
        InvalidAgeOfMemberException ex = new InvalidAgeOfMemberException("Invalid age");
        assertEquals("Invalid age", ex.getMessage());
    }

    @Test
    void incorrectPasswordAndEmail_createsWithMessage() {
        IncorrectPasswordAndEmail ex = new IncorrectPasswordAndEmail("Incorrect credentials");
        assertEquals("Incorrect credentials", ex.getMessage());
    }

    @Test
    void coverAmountNotSupportedException_createsWithMessage() {
        CoverAmountNotSupportedException ex = new CoverAmountNotSupportedException("Cover amount not supported");
        assertEquals("Cover amount not supported", ex.getMessage());
    }

    @Test
    void coverTenureNotSupportedException_createsWithMessage() {
        CoverTenureNotSupportedException ex = new CoverTenureNotSupportedException("Cover tenure not supported");
        assertEquals("Cover tenure not supported", ex.getMessage());
    }

    @Test
    void invalidGenderException_isRuntimeException() {
        InvalidGenderException ex = new InvalidGenderException("test");
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void userDoesNotExistException_isRuntimeException() {
        UserDoesNotExistException ex = new UserDoesNotExistException("test");
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void invalidAgeOfMemberException_isRuntimeException() {
        InvalidAgeOfMemberException ex = new InvalidAgeOfMemberException("test");
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void policyDoesNotExistException_isRuntimeException() {
        PolicyDoesNotExistException ex = new PolicyDoesNotExistException("test");
        assertTrue(ex instanceof RuntimeException);
    }
}
