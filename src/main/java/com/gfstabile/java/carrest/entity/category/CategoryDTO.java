package com.gfstabile.java.carrest.entity.category;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Field class of the response body from the company-rest api.
 *
 * @author G. F. Stabile
 */
@Getter
@Setter
@Builder
@JsonDeserialize(builder = CategoryDTO.CategoryDTOBuilder.class)
public class CategoryDTO implements Serializable {
    private String internalCode;
    private String name;

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
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        CategoryDTO that = (CategoryDTO) object;
        return Objects.equals(internalCode, that.internalCode) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalCode, name);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CategoryDTOBuilder {

    }
}
