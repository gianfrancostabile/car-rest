package com.gfstabile.java.carrest.entity.company;

import com.gfstabile.java.carrest.entity.category.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class CompanyDTOTest {
    private final AbstractFieldValueChanger valueChanger =
        DefaultFieldValueChanger.INSTANCE.attachNext(new CompanyDTOTest.CategoryDTOFieldsValuesChanger());
    private CompanyDTO dummyCompanyDTO;

    @BeforeEach
    public void setUp() {
        this.dummyCompanyDTO = CompanyDTO.builder()
            .internalCode("1")
            .name("Name")
            .country("AR")
            .categoryDTO(CategoryDTO.builder()
                .internalCode("1")
                .name("Name")
                .build())
            .build();
    }

    @Test
    public void testPojoMethods() {
        assertPojoMethodsFor(CompanyDTO.class).using(this.valueChanger)
            .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR, Method.EQUALS, Method.HASH_CODE)
            .areWellImplemented();
    }

    @Test
    public void toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues() {
        assertEquals(
            "{\"internalCode\": \"1\", \"name\": \"Name\", \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}",
            this.dummyCompanyDTO.toString(), "toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCompanyDTO.setInternalCode(null);
        assertEquals(
            "{\"internalCode\": null, \"name\": \"Name\", \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}",
            this.dummyCompanyDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithNameNull() {
        this.dummyCompanyDTO.setName(null);
        assertEquals(
            "{\"internalCode\": \"1\", \"name\": null, \"country\": \"AR\", \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}",
            this.dummyCompanyDTO.toString(), "toString_InternalCodeNull_ReturnsStringRepresentationWithNameNull");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithCountryNull() {
        this.dummyCompanyDTO.setCountry(null);
        assertEquals(
            "{\"internalCode\": \"1\", \"name\": \"Name\", \"country\": null, \"category\": {\"internalCode\": \"1\", \"name\": \"Name\"}}",
            this.dummyCompanyDTO.toString(), "toString_InternalCodeNull_ReturnsStringRepresentationWithCountryNull");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithCategoryDTONull() {
        this.dummyCompanyDTO.setCategoryDTO(null);
        assertEquals("{\"internalCode\": \"1\", \"name\": \"Name\", \"country\": \"AR\", \"category\": null}",
            this.dummyCompanyDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithCategoryDTONull");
    }

    protected class CategoryDTOFieldsValuesChanger extends AbstractFieldValueChanger<CategoryDTO> {

        @Override
        protected CategoryDTO increaseValue(CategoryDTO value, Class<?> type) {
            value.setInternalCode(value.getInternalCode() + "++increased");
            value.setName(value.getName() + "++increased");
            return value;
        }

        @Override
        protected boolean canChange(Class<?> type) {
            return type.equals(CategoryDTO.class);
        }
    }
}
