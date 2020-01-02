package com.gfstabile.java.carrest.entity;

import com.gfstabile.java.carrest.utilities.ErrorMessage;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationErrorCollectionTest {

    @Test
    public void testPojo_AllTestShouldPass() {
        Assertions.assertPojoMethodsFor(ValidationErrorCollection.class)
            .testing(Method.CONSTRUCTOR, Method.GETTER)
            .areWellImplemented();
    }

    @Test
    public void toString_WithThreeErrors_ReturnsStringRepresentationWithThreeErrors() {
        ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();
        validationErrorCollection.addError(ErrorMessage.CAR_INTERNAL_CODE_BLANK);
        validationErrorCollection.addError(ErrorMessage.CAR_NAME_BLANK);
        validationErrorCollection.addError(ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK);

        String expectedString =
            "{\"errors\": [\"The internal code is mandatory\", \"The name is mandatory\", \"The company internal code is mandatory\"]}";
        assertEquals(expectedString, validationErrorCollection.toString(),
            "toString_WithThreeErrors_ReturnsStringRepresentationWithThreeErrors");
    }

    @Test
    public void toString_WithOneError_ReturnsStringRepresentationWithOneError() {
        ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();
        validationErrorCollection.addError(ErrorMessage.BODY_MANDATORY);

        String expectedString = "{\"errors\": [\"The body is mandatory or the body structure is not tolerated\"]}";
        assertEquals(expectedString, validationErrorCollection.toString(),
            "toString_WithOneError_ReturnsStringRepresentationWithOneError");
    }

    @Test
    public void toString_WithoutErrors_ReturnsStringRepresentationWithoutError() {
        ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();

        String expectedString = "{\"errors\": []}";
        assertEquals(expectedString, validationErrorCollection.toString(),
            "toString_WithoutErrors_ReturnsStringRepresentationWithoutError");
    }
}
