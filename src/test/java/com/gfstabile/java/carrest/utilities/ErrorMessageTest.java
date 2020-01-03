package com.gfstabile.java.carrest.utilities;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class ErrorMessageTest {

    @Test
    public void testPojo_AllTestShouldPass() {
        Assertions.assertPojoMethodsFor(ErrorMessage.class)
            .testing(Method.CONSTRUCTOR)
            .areWellImplemented();
    }
}
