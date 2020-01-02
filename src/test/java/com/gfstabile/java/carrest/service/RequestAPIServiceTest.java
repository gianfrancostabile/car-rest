package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RequestAPIServiceTest {

    @InjectMocks
    private RequestAPIService requestAPIService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void isValidCompanyInternalCode_SendValidInternalCode_ReturnsTrue() {
        CompanyDTO dummyCompanyDTO = CompanyDTO.builder()
            .internalCode("1")
            .name("Name")
            .country("AR")
            .categoryInternalCode("1")
            .build();
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(dummyCompanyDTO, HttpStatus.OK);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        boolean actualResponse = this.requestAPIService.isValidCompanyInternalCode("1");
        assertTrue(actualResponse, "isValidCompanyInternalCode_SendValidInternalCode_ReturnsTrue");
    }

    @Test
    public void isValidCompanyInternalCode_SendNullInternalCode_ReturnsFalse() {
        boolean actualResponse = this.requestAPIService.isValidCompanyInternalCode(null);
        assertFalse(actualResponse, "isValidCompanyInternalCode_SendNullInternalCode_ReturnsFalse");
    }

    @Test
    public void isValidCompanyInternalCode_SendBlankInternalCode_ReturnsFalse() {
        boolean actualResponse = this.requestAPIService.isValidCompanyInternalCode("");
        assertFalse(actualResponse, "isValidCompanyInternalCode_SendBlankInternalCode_ReturnsFalse");
    }

    @Test
    public void isValidCompanyInternalCode_SendNonExistingInternalCode_ReturnsFalse() {
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        boolean actualResponse = this.requestAPIService.isValidCompanyInternalCode("invalidCode");
        assertFalse(actualResponse, "isValidCompanyInternalCode_SendNonExistingInternalCode_ReturnsFalse");
    }
}
