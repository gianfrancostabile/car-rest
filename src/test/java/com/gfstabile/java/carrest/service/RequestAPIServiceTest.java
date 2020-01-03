package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.category.CategoryDTO;
import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RequestAPIServiceTest {

    @InjectMocks
    private RequestAPIService requestAPIService;

    @Mock
    private RestTemplate restTemplate;

    private final static CompanyDTO DUMMY_COMPANY_DTO = CompanyDTO.builder()
        .internalCode("1")
        .name("Name")
        .country("AR")
        .categoryDTO(CategoryDTO.builder()
            .internalCode("1")
            .name("Name")
            .build())
        .build();

    @Test
    public void findCompanyDTO_SendExistingInternalCode_ReturnsOptionalWithCompanyDTO() {
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(DUMMY_COMPANY_DTO, HttpStatus.OK);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("1");
        if (optionalCompanyDTO.isPresent()) {
            assertEquals(DUMMY_COMPANY_DTO, optionalCompanyDTO.get(),
                "findCompanyDTO_SendExistingInternalCode_ReturnsCompanyDTO");
        } else {
            fail();
        }
    }

    @Test
    public void findCompanyDTO_SendNullInternalCode_ReturnsEmptyOptional() {
        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO(null);
        assertFalse(optionalCompanyDTO.isPresent(), "findCompanyDTO_SendNullInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findCompanyDTO_SendBlankInternalCode_ReturnsEmptyOptional() {
        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("");
        assertFalse(optionalCompanyDTO.isPresent(), "findCompanyDTO_SendBlankInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findCompanyDTO_SendNonExistingInternalCode_ReturnsEmptyOptional() {
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("invalid-code");
        assertFalse(optionalCompanyDTO.isPresent(), "findCompanyDTO_SendNonExistingInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findCompanyDTO_SendInternalCodeButStatusCodeIsNot2XXWithBody_ReturnsEmptyOptional() {
        ResponseEntity<CompanyDTO> dummyResponseEntity =
            new ResponseEntity<>(DUMMY_COMPANY_DTO, HttpStatus.BAD_REQUEST);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("code");
        assertFalse(optionalCompanyDTO.isPresent(),
            "findCompanyDTO_SendInternalCodeButStatusCodeIsNot2XXWithBody_ReturnsEmptyOptional");
    }

    @Test
    public void findCompanyDTO_SendInternalCodeButStatusCodeIs2XXWithoutBody_ReturnsEmptyOptional() {
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("code");
        assertFalse(optionalCompanyDTO.isPresent(),
            "findCompanyDTO_SendInternalCodeButStatusCodeIs2XXWithoutBody_ReturnsEmptyOptional");
    }

    @Test
    public void findCompanyDTO_SendInternalCodeButStatusCodeIsNot2XXWithoutBody_ReturnsEmptyOptional() {
        ResponseEntity<CompanyDTO> dummyResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(this.restTemplate.getForEntity(anyString(), eq(CompanyDTO.class))).thenReturn(dummyResponseEntity);

        Optional<CompanyDTO> optionalCompanyDTO = this.requestAPIService.findCompanyDTO("code");
        assertFalse(optionalCompanyDTO.isPresent(),
            "findCompanyDTO_SendInternalCodeButStatusCodeIsNot2XXWithoutBody_ReturnsEmptyOptional");
    }

    @Test
    public void isValidCompanyInternalCode_SendValidInternalCode_ReturnsTrue() {
        RequestAPIService requestAPIServiceSpy = spy(this.requestAPIService);
        when(requestAPIServiceSpy.findCompanyDTO(anyString())).thenReturn(Optional.of(DUMMY_COMPANY_DTO));

        boolean actualResponse = requestAPIServiceSpy.isValidCompanyInternalCode("1");
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
        RequestAPIService requestAPIServiceSpy = spy(this.requestAPIService);
        when(requestAPIServiceSpy.findCompanyDTO(anyString())).thenReturn(Optional.empty());

        boolean actualResponse = requestAPIServiceSpy.isValidCompanyInternalCode("invalidCode");
        assertFalse(actualResponse, "isValidCompanyInternalCode_SendNonExistingInternalCode_ReturnsFalse");
    }
}
