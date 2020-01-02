package com.gfstabile.java.carrest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CarRestApplicationTests {

    @Test
    public void contextLoads() {
        CarRestApplication.main(new String[] { });
        Assertions.assertTrue(true, "contextLoads");
    }

}
