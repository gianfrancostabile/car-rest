package com.gfstabile.java.carrest.entity.company;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class CompanyDTOTest {
    @Test
    public void testPojo_AllTestShouldPass() {
        assertPojoMethodsFor(CompanyDTO.class).testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER)
            .areWellImplemented();
    }
}
