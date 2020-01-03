package com.gfstabile.java.carrest.entity.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * The response DTO class related to Car entity class.
 *
 * @author G. F. Stabile
 */
@Getter
@Setter
@Builder
@JsonDeserialize(builder = CarResponseDTO.CarResponseDTOBuilder.class)
public class CarResponseDTO implements Serializable {
    private String internalCode;
    private String name;
    @JsonProperty("company")
    private CompanyDTO companyDTO;

    @Override
    public String toString() {
        char quotes = '\"';
        final StringBuilder stringBuilder = new StringBuilder("{\"internalCode\": ");
        if (Objects.nonNull(this.internalCode)) {
            stringBuilder.append(quotes)
                .append(this.internalCode)
                .append(quotes);
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"name\": ");
        if (Objects.nonNull(this.name)) {
            stringBuilder.append(quotes)
                .append(this.name)
                .append(quotes);
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"company\": ")
            .append(this.companyDTO)
            .append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarResponseDTO that = (CarResponseDTO) o;
        return Objects.equals(internalCode, that.internalCode) && Objects.equals(name, that.name) &&
            Objects.equals(companyDTO, that.companyDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, name, companyDTO);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CarResponseDTOBuilder {

    }
}
