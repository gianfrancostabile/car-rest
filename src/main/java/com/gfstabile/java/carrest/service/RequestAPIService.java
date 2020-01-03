package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

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
     * Returns the an Optional with the CompanyDTO
     * instance from the given internal code.
     * <p>
     * If the internal code is not blank and
     * and the api response with a 200 and with a
     * the {@link CompanyDTO} instance into the body, an
     * Optional with the {@link CompanyDTO} will be returned.
     * Otherwise, an empty optional will be returned instead.
     * </p>
     *
     * @param internalCode the company internal code to find
     * @return an Optional with the CompanyDTO instance.
     */
    public Optional<CompanyDTO> findCompanyDTO(String internalCode) {
        CompanyDTO companyDTO = null;
        if (Strings.isNotBlank(internalCode)) {
            ResponseEntity<CompanyDTO> responseEntity =
                this.restTemplate.getForEntity(this.companyBaseUrl + internalCode, CompanyDTO.class);
            if (responseEntity.getStatusCode()
                .is2xxSuccessful() && Objects.nonNull(responseEntity.getBody())) {
                companyDTO = responseEntity.getBody();
            }
        }
        return Optional.ofNullable(companyDTO);
    }

    /**
     * Validates if the given company internal code is valid.
     * <p>
     * Is valid if the the optional obtained from
     * {@link RequestAPIService#findCompanyDTO(String)} contains
     * a {@link CompanyDTO}. If not, returns false.
     * </p>
     *
     * @param companyInternalCode the company internal code
     * @return {@literal true} if is valid, {@literal false} if it is not
     */
    public boolean isValidCompanyInternalCode(String companyInternalCode) {
        boolean result = false;
        if (Strings.isNotBlank(companyInternalCode)) {
            result = this.findCompanyDTO(companyInternalCode)
                .isPresent();
        }
        return result;
    }
}
