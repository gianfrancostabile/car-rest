package com.gfstabile.java.carrest.entity.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gfstabile.java.carrest.entity.category.CategoryDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

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
    @JsonProperty("category")
    private CategoryDTO categoryDTO;

    @Override
    public String toString() {
        char quotes = '\"';
        final StringBuilder stringBuilder = new StringBuilder("{\"internalCode\": ");
        if (Objects.nonNull(internalCode)) {
            stringBuilder.append(quotes)
                .append(internalCode)
                .append(quotes);
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"name\": ");
        if (Objects.nonNull(name)) {
            stringBuilder.append(quotes)
                .append(name)
                .append(quotes);
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"country\": ");
        if (Objects.nonNull(country)) {
            stringBuilder.append(quotes)
                .append(country)
                .append(quotes);
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"category\": ")
            .append(categoryDTO)
            .append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        CompanyDTO that = (CompanyDTO) object;
        return Objects.equals(internalCode, that.internalCode) && Objects.equals(name, that.name) &&
            Objects.equals(country, that.country) && Objects.equals(categoryDTO, that.categoryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, name, country, categoryDTO);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDTOBuilder {

    }
}
