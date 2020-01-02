package com.gfstabile.java.carrest.mapper;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CarMapper {

    /**
     * Converts the DTO instance into a Entity instance.
     * <p>
     * If the given CarDTO instance is {@literal null},
     * <b>null</b> will be returned.
     * Otherwise the Car instance.
     * </p>
     *
     * @param carDTO the dto instance
     * @return the entity instance
     */
    public Car fromDtoToEntity(CarDTO carDTO) {
        return Objects.nonNull(carDTO) ? Car.builder()
            .internalCode(carDTO.getInternalCode())
            .name(carDTO.getName())
            .companyInternalCode(carDTO.getCompanyInternalCode())
            .build() : null;
    }

    /**
     * Converts the Entity instance into a DTO instance.
     * <p>
     * If the given Car instance is {@literal null},
     * <b>null</b> will be returned.
     * Otherwise the CarDTO instance.
     * </p>
     *
     * @param car the entity instance
     * @return the dto instance
     */
    public CarDTO fromEntityToDto(Car car) {
        return Objects.nonNull(car) ? CarDTO.builder()
            .internalCode(car.getInternalCode())
            .name(car.getName())
            .companyInternalCode(car.getCompanyInternalCode())
            .build() : null;
    }
}
