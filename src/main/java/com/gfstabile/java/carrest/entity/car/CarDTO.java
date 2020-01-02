package com.gfstabile.java.carrest.entity.car;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gfstabile.java.carrest.utilities.ErrorMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * The DTO class related to Car entity class.
 *
 * @author G. F. Stabile
 */
@Getter
@Setter
@Builder
@JsonDeserialize(builder = CarDTO.CarDTOBuilder.class)
public class CarDTO {

    @NotBlank(message = ErrorMessage.CAR_INTERNAL_CODE_BLANK)
    private String internalCode;

    @NotBlank(message = ErrorMessage.CAR_NAME_BLANK)
    private String name;

    @NotBlank(message = ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK)
    private String companyInternalCode;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("{\"internalCode\": ");
        if (Objects.nonNull(this.internalCode)) {
            stringBuilder.append('\"')
                .append(this.internalCode)
                .append('\"');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"name\": ");
        if (Objects.nonNull(this.name)) {
            stringBuilder.append('\"')
                .append(this.name)
                .append('\"');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(", \"companyInternalCode\": ");
        if (Objects.nonNull(this.companyInternalCode)) {
            stringBuilder.append('\"')
                .append(this.companyInternalCode)
                .append('\"');
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarDTO carDTO = (CarDTO) o;
        return Objects.equals(internalCode, carDTO.internalCode) && Objects.equals(name, carDTO.name) &&
            Objects.equals(companyInternalCode, carDTO.companyInternalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, name, companyInternalCode);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CarDTOBuilder {

    }
}
