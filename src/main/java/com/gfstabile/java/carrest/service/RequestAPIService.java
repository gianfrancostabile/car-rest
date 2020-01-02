package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * The service class that interact with company-rest
 * application.
 *
 * @author G. F. Stabile
 */
@Service
public class RequestAPIService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${company.rest.base.url.company}")
    private String companyBaseUrl;

    /**
     * Validates if the given company internal code is valid.
     * <p>
     * Is valid if the company internal code's parameter is
     * not blank and if company-rest API returns a companyDTO.
     * Otherwise is not valid.
     * </p>
     *
     * @param companyInternalCode the company internal code
     * @return {@literal true} if is valid, {@literal false} if it is not
     */
    public boolean isValidCompanyInternalCode(String companyInternalCode) {
        boolean result = false;
        if (Strings.isNotBlank(companyInternalCode)) {
            ResponseEntity<CompanyDTO> responseEntity =
                this.restTemplate.getForEntity(this.companyBaseUrl + companyInternalCode, CompanyDTO.class);
            result = responseEntity.getStatusCode()
                .is2xxSuccessful() && Objects.nonNull(responseEntity.getBody());
        }
        return result;
    }
}
