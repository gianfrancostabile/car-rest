package com.gfstabile.java.carrest.entity.car;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.gfstabile.java.carrest.utilities.ErrorMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * The request DTO class related to Car entity class.
 *
 * @author G. F. Stabile
 */
@Getter
@Setter
@Builder
@JsonDeserialize(builder = CarRequestDTO.CarRequestDTOBuilder.class)
public class CarRequestDTO implements Serializable {

    @NotBlank(message = ErrorMessage.CAR_INTERNAL_CODE_BLANK)
    private String internalCode;

    @NotBlank(message = ErrorMessage.CAR_NAME_BLANK)
    private String name;

    @NotBlank(message = ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK)
    private String companyInternalCode;

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
        stringBuilder.append(", \"companyInternalCode\": ");
        if (Objects.nonNull(this.companyInternalCode)) {
            stringBuilder.append(quotes)
                .append(this.companyInternalCode)
                .append(quotes);
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
        CarRequestDTO carRequestDTO = (CarRequestDTO) o;
        return Objects.equals(internalCode, carRequestDTO.internalCode) && Objects.equals(name, carRequestDTO.name) &&
            Objects.equals(companyInternalCode, carRequestDTO.companyInternalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, name, companyInternalCode);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CarRequestDTOBuilder {

    }
}
