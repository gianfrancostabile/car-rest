package com.gfstabile.java.carrest.entity.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Response body from the company-rest api.
 *
 * @author G. F. Stabile
 */
@Getter
@Setter
@Builder
@JsonDeserialize(builder = CompanyDTO.CompanyDTOBuilder.class)
public class CompanyDTO implements Serializable {
    private String internalCode;
    private String name;
    private String country;
    private String categoryInternalCode;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDTOBuilder {

    }
}
