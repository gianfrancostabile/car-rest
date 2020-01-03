package com.gfstabile.java.carrest.mapper;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarRequestDTO;
import com.gfstabile.java.carrest.entity.car.CarResponseDTO;
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
     * @param carRequestDTO the dto instance
     * @return the entity instance
     */
    public Car fromDtoToEntity(CarRequestDTO carRequestDTO) {
        return Objects.nonNull(carRequestDTO) ? Car.builder()
            .internalCode(carRequestDTO.getInternalCode())
            .name(carRequestDTO.getName())
            .companyInternalCode(carRequestDTO.getCompanyInternalCode())
            .build() : null;
    }

    /**
     * Converts the Entity instance into a RequestDTO instance.
     * <p>
     * If the given Car instance is {@literal null},
     * <b>null</b> will be returned.
     * Otherwise the CarRequestDTO instance.
     * </p>
     *
     * @param car the entity instance
     * @return the request dto instance
     */
    public CarRequestDTO fromEntityToRequestDto(Car car) {
        return Objects.nonNull(car) ? CarRequestDTO.builder()
            .internalCode(car.getInternalCode())
            .name(car.getName())
            .companyInternalCode(car.getCompanyInternalCode())
            .build() : null;
    }

    /**
     * Converts the Entity instance into a ResponseDTO instance.
     * <p>
     * If the given Car instance is {@literal null},
     * <b>null</b> will be returned.
     * Otherwise the CarResponseDTO instance.
     * </p>
     *
     * @param car the entity instance
     * @return the response dto instance
     */
    public CarResponseDTO fromEntityToResponseDto(Car car) {
        return Objects.nonNull(car) ? CarResponseDTO.builder()
            .internalCode(car.getInternalCode())
            .name(car.getName())
            .build() : null;
    }
}
