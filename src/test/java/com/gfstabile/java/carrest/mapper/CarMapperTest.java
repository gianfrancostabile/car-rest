package com.gfstabile.java.carrest.mapper;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {

    private final static CarDTO DUMMY_CAR_DTO = CarDTO.builder()
        .internalCode("chev-corsa")
        .name("corsa")
        .companyInternalCode("1")
        .build();
    private final static Car DUMMY_CAR = Car.builder()
        .internalCode("chev-corsa")
        .name("corsa")
        .companyInternalCode("1")
        .build();

    private CarMapper carMapper;

    public CarMapperTest() {
        this.carMapper = new CarMapper();
    }

    @Test
    public void fromDtoToEntity_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData() {
        Car actualCar = this.carMapper.fromDtoToEntity(DUMMY_CAR_DTO);
        assertEquals(DUMMY_CAR, actualCar, "fromDtoToEntity_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData");
    }

    @Test
    public void fromDtoToEntity_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance() {
        Car expectedCar = Car.builder()
            .build();
        Car actualCar = this.carMapper.fromDtoToEntity(CarDTO.builder()
            .build());
        assertEquals(actualCar, expectedCar, "fromDtoToEntity_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance");
    }

    @Test
    public void fromDtoToEntity_SendNull_ReturnsNull() {
        Car expectedCar = this.carMapper.fromDtoToEntity(null);
        assertEquals(expectedCar, null, "fromDtoToEntity_SendNull_ReturnsNull");
    }

    @Test
    public void fromEntityToDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData() {
        CarDTO actualCarDTO = this.carMapper.fromEntityToDto(DUMMY_CAR);
        assertEquals(DUMMY_CAR_DTO, actualCarDTO,
            "fromEntityToDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData");
    }

    @Test
    public void fromEntityToDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance() {
        CarDTO expectedCarDTO = CarDTO.builder()
            .build();
        CarDTO actualCarDTO = this.carMapper.fromEntityToDto(Car.builder()
            .build());
        assertEquals(actualCarDTO, expectedCarDTO, "fromEntityToDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance");
    }

    @Test
    public void fromEntityToDto_SendNull_ReturnsNull() {
        CarDTO expectedCarDTO = this.carMapper.fromEntityToDto(null);
        assertEquals(expectedCarDTO, null, "fromEntityToDto_SendNull_ReturnsNull");
    }
}
