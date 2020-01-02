package com.gfstabile.java.carrest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Used as rest response if an error occurs,
 * during rest method execution.
 *
 * @author G. F. Stabile
 */
@Getter
@AllArgsConstructor
public class ValidationErrorCollection implements Serializable {
    private List<String> errors;

    public ValidationErrorCollection() {
        this.errors = new ArrayList<>();
    }

    /**
     * Adds new error into the error list.
     * <p>
     * If the error param is null, it would not
     * be added.
     * </p>
     *
     * @param error the error message to add.
     */
    public void addError(String error) {
        if (Objects.nonNull(error)) {
            this.errors.add(error);
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("{\"errors\": ");
        if (Objects.nonNull(this.errors)) {
            stringBuilder.append('[')
                .append(this.errors.stream()
                    .map(error -> '\"' + error + '\"')
                    .collect(Collectors.joining(", ")))
                .append(']');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
