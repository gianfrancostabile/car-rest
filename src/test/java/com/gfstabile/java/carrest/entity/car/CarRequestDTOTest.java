package com.gfstabile.java.carrest.entity.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarRequestDTOTest {
    private CarRequestDTO dummyCarRequestDTO;

    @BeforeEach
    public void setUp() {
        this.dummyCarRequestDTO = CarRequestDTO.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build();
    }

    @Test
    public void testPojo_AllTestShouldPass() {
        Assertions.assertPojoMethodsFor(CarRequestDTO.class)
            .testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE)
            .areWellImplemented();
    }

    @Test
    public void toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues() {
        String expectedValue =
            "{\"internalCode\": \"chev-corsa\", \"name\": \"Corsa\", \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCarRequestDTO.toString(),
            "toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCarRequestDTO.setInternalCode(null);
        String expectedValue = "{\"internalCode\": null, \"name\": \"Corsa\", \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCarRequestDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_NameNull_ReturnsStringRepresentationWithNameNull() {
        this.dummyCarRequestDTO.setName(null);
        String expectedValue =
            "{\"internalCode\": \"chev-corsa\", \"name\": null, \"companyInternalCode\": \"chevrolet\"}";
        assertEquals(expectedValue, this.dummyCarRequestDTO.toString(),
            "toString_NameNull_ReturnsStringRepresentationWithNameNull");
    }

    @Test
    public void toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull() {
        this.dummyCarRequestDTO.setCompanyInternalCode(null);
        String expectedValue = "{\"internalCode\": \"chev-corsa\", \"name\": \"Corsa\", \"companyInternalCode\": null}";
        assertEquals(expectedValue, this.dummyCarRequestDTO.toString(),
            "toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull");
    }

    @Test
    public void toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull() {
        CarRequestDTO dummyCarRequestDTO = CarRequestDTO.builder()
            .build();
        String expectedValue = "{\"internalCode\": null, \"name\": null, \"companyInternalCode\": null}";
        assertEquals(expectedValue, dummyCarRequestDTO.toString(),
            "toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull");
    }
}
