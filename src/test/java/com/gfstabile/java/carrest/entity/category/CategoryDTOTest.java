package com.gfstabile.java.carrest.entity.category;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class CategoryDTOTest {

    private CategoryDTO dummyCategoryDTO;

    public CategoryDTOTest() {
        this.dummyCategoryDTO = CategoryDTO.builder()
            .internalCode("1")
            .name("Name")
            .build();
    }

    @Test
    public void testPojoMethods() {
        assertPojoMethodsFor(CategoryDTO.class).testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR, Method.EQUALS,
            Method.HASH_CODE)
            .areWellImplemented();
    }

    @Test
    public void toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues() {
        assertEquals("{\"internalCode\": \"1\", \"name\": \"Name\"}", this.dummyCategoryDTO.toString(),
            "toString_WithoutNullValues_ReturnsStringRepresentationWithoutNullValues");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull() {
        this.dummyCategoryDTO.setInternalCode(null);
        assertEquals("{\"internalCode\": null, \"name\": \"Name\"}", this.dummyCategoryDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithInternalCodeNull");
    }

    @Test
    public void toString_InternalCodeNull_ReturnsStringRepresentationWithNameNull() {
        this.dummyCategoryDTO.setName(null);
        assertEquals("{\"internalCode\": \"1\", \"name\": null}", this.dummyCategoryDTO.toString(),
            "toString_InternalCodeNull_ReturnsStringRepresentationWithNameNull");
    }
}
