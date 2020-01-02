package com.gfstabile.java.carrest.entity.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

/**
 * Car entity class.
 *
 * @author G. F. Stabile
 */
@Entity
@Table(name = "cars")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = Car.CarBuilder.class)
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String internalCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String companyInternalCode;

    @Transient
    @JsonIgnore
    public boolean isValid() {
        return Strings.isNotBlank(this.internalCode) && Strings.isNotBlank(this.name) &&
            Strings.isNotBlank(this.companyInternalCode);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("{\"id\": ").append(this.id)
            .append(", \"internalCode\": ");
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
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Car car = (Car) object;
        return Objects.equals(id, car.id) && Objects.equals(internalCode, car.internalCode) &&
            Objects.equals(name, car.name) && Objects.equals(companyInternalCode, car.companyInternalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, internalCode, name, companyInternalCode);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CarBuilder {

    }
}
