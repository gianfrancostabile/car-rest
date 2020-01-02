package com.gfstabile.java.carrest.entity.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarTest {

    private Car dummyCar;

    @BeforeEach
    public void setUp() {
        this.dummyCar = Car.builder()
            .id(1L)
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build();
    }

    @Test
    public void testPojo_AllTestShouldPass() {
        Assertions.assertPojoMethodsFor(Car.class)
            .testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE)
            .areWellImplemented();
    }

    @Test
    public void isValid_AllFieldValid_ReturnsTrue() {
        assertTrue(this.dummyCar.isValid(), "isValid_AllFieldValid_ReturnsTrue");
    }

    @Test
    public void isValid_InternalCodeNull_ReturnsFalse() {
        this.dummyCar.setInternalCode(null);
        assertFalse(this.dummyCar.isValid(), "isValid_InternalCodeNull_ReturnsFalse");
    }

    @Test
    public void isValid_InternalCodeBlank_ReturnsFalse() {
        this.dummyCar.setInternalCode("");
        assertFalse(this.dummyCar.isValid(), "isValid_InternalCodeBlank_ReturnsFalse");
    }

    @Test
    public void isValid_NameNull_ReturnsFalse() {
        this.dummyCar.setName(null);
        assertFalse(this.dummyCar.isValid(), "isValid_NameNull_ReturnsFalse");
    }

    @Test
    public void isValid_NameBlank_ReturnsFalse() {
        this.dummyCar.setName("");
        assertFalse(this.dummyCar.isValid(), "isValid_NameBlank_ReturnsFalse");
    }

    @Test
    public void isValid_CompanyInternalCodeNull_ReturnsFalse() {
        this.dummyCar.setCompanyInternalCode(null);
        assertFalse(this.dummyCar.isValid(), "isValid_CompanyInternalCodeNull_ReturnsFalse");
    }

    @Test
    public void isValid_CompanyInternalCodeBlank_ReturnsFalse() {
        this.dummyCar.setCompanyInternalCode("");
        assertFalse(this.dummyCar.isValid(), "isValid_CompanyInternalCodeBlank_ReturnsFalse");
    }

    @Test
    public void toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues() {
        String expectedValue =
            "{\"id\": 1, \"internalCode\": \"chev-corsa\", \"name\": \"Corsa\", \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCar.toString(),
            "toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues");
    }

    @Test
    public void toString_IdNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCar.setId(null);
        String expectedValue =
            "{\"id\": null, \"internalCode\": \"chev-corsa\", \"name\": \"Corsa\", \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCar.toString(),
            "toString_IdNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCar.setInternalCode(null);
        String expectedValue =
            "{\"id\": 1, \"internalCode\": null, \"name\": \"Corsa\", \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCar.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_NameNull_ReturnsStringRepresentationWithNameNull() {
        this.dummyCar.setName(null);
        String expectedValue =
            "{\"id\": 1, \"internalCode\": \"chev-corsa\", \"name\": null, \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCar.toString(),
            "toString_NameNull_ReturnsStringRepresentationWithNameNull");
    }

    @Test
    public void toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull() {
        this.dummyCar.setCompanyInternalCode(null);
        String expectedValue =
            "{\"id\": 1, \"internalCode\": \"chev-corsa\", \"name\": \"Corsa\", \"companyInternalCode\": null}";
        assertEquals(expectedValue, this.dummyCar.toString(),
            "toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull");
    }

    @Test
    public void toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull() {
        Car dummyCar = Car.builder()
            .build();
        String expectedValue = "{\"id\": null, \"internalCode\": null, \"name\": null, \"companyInternalCode\": null}";
        assertEquals(expectedValue, dummyCar.toString(),
            "toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull");
    }
}
