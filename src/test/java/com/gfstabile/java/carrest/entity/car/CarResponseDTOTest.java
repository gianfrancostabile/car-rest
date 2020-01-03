package com.gfstabile.java.carrest.entity.car;

import com.gfstabile.java.carrest.entity.category.CategoryDTO;
import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class CarResponseDTOTest {

    private final AbstractFieldValueChanger valueChanger =
        DefaultFieldValueChanger.INSTANCE.attachNext(new CarResponseDTOTest.CompanyDTOFieldsValuesChanger());
    private CarResponseDTO dummyCarResponseDTO;

    @BeforeEach
    public void setUp() {
        this.dummyCarResponseDTO = CarResponseDTO.builder()
            .internalCode("1")
            .name("Name")
            .companyDTO(CompanyDTO.builder()
                .internalCode("1")
                .name("Name")
                .country("AR")
                .categoryDTO(CategoryDTO.builder()
                    .internalCode("1")
                    .name("Name")
                    .build())
                .build())
            .build();
    }

    @Test
    public void testPojo_AllTestShouldPass() {
        assertPojoMethodsFor(CarResponseDTO.class).using(this.valueChanger)
            .testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE)
            .areWellImplemented();
    }

    @Test
    public void toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues() {
        String expectedValue =
            "{\"internalCode\": \"1\", \"name\": \"Name\", \"company\": {\"internalCode\": \"1\", \"name\": \"Name\", \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}}";
        assertEquals(expectedValue, this.dummyCarResponseDTO.toString(),
            "toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCarResponseDTO.setInternalCode(null);
        String expectedValue =
            "{\"internalCode\": null, \"name\": \"Name\", \"company\": {\"internalCode\": \"1\", \"name\": \"Name\", \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}}";
        assertEquals(expectedValue, this.dummyCarResponseDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_NameNull_ReturnsStringRepresentationWithNameNull() {
        this.dummyCarResponseDTO.setName(null);
        String expectedValue =
            "{\"internalCode\": \"1\", \"name\": null, \"company\": {\"internalCode\": \"1\", \"name\": \"Name\", \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}}";
        assertEquals(expectedValue, this.dummyCarResponseDTO.toString(),
            "toString_NameNull_ReturnsStringRepresentationWithNameNull");
    }

    @Test
    public void toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull() {
        this.dummyCarResponseDTO.setCompanyDTO(null);
        String expectedValue = "{\"internalCode\": \"1\", \"name\": \"Name\", \"company\": null}";
        assertEquals(expectedValue, this.dummyCarResponseDTO.toString(),
            "toString_CompanyInternalCodeNull_ReturnsStringRepresentationWithCompanyInternalCodeNull");
    }

    @Test
    public void toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull() {
        CarResponseDTO dummyCarResponseDTO = CarResponseDTO.builder()
            .build();
        String expectedValue = "{\"internalCode\": null, \"name\": null, \"company\": null}";
        assertEquals(expectedValue, dummyCarResponseDTO.toString(),
            "toString_AllFieldsNull_ReturnsStringRepresentationWithAllFieldsNull");
    }


    protected class CompanyDTOFieldsValuesChanger extends AbstractFieldValueChanger<CompanyDTO> {

        @Override
        protected CompanyDTO increaseValue(CompanyDTO value, Class<?> type) {
            value.setInternalCode(value.getInternalCode() + "++increased");
            value.setName(value.getName() + "++increased");
            value.setCountry(value.getCountry() + "++increased");
            value.getCategoryDTO()
                .setInternalCode(value.getCategoryDTO()
                    .getInternalCode() + "++increased");
            value.getCategoryDTO()
                .setName(value.getCategoryDTO()
                    .getName() + "++increased");
            return value;
        }

        @Override
        protected boolean canChange(Class<?> type) {
            return type.equals(CompanyDTO.class);
        }
    }
}
