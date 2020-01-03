package com.gfstabile.java.carrest.mapper;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarRequestDTO;
import com.gfstabile.java.carrest.entity.car.CarResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {

    private final static Car DUMMY_CAR = Car.builder()
        .internalCode("chev-corsa")
        .name("corsa")
        .companyInternalCode("1")
        .build();
    private final static CarRequestDTO DUMMY_CAR_REQUEST_DTO = CarRequestDTO.builder()
        .internalCode("chev-corsa")
        .name("corsa")
        .companyInternalCode("1")
        .build();
    private final static CarResponseDTO DUMMY_CAR_RESPONSE_DTO = CarResponseDTO.builder()
        .internalCode("chev-corsa")
        .name("corsa")
        .build();

    private CarMapper carMapper;

    public CarMapperTest() {
        this.carMapper = new CarMapper();
    }

    @Test
    public void fromDtoToEntity_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData() {
        Car actualCar = this.carMapper.fromDtoToEntity(DUMMY_CAR_REQUEST_DTO);
        assertEquals(DUMMY_CAR, actualCar, "fromDtoToEntity_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData");
    }

    @Test
    public void fromDtoToEntity_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance() {
        Car expectedCar = Car.builder()
            .build();
        Car actualCar = this.carMapper.fromDtoToEntity(CarRequestDTO.builder()
            .build());
        assertEquals(actualCar, expectedCar, "fromDtoToEntity_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance");
    }

    @Test
    public void fromDtoToEntity_SendNull_ReturnsNull() {
        Car expectedCar = this.carMapper.fromDtoToEntity(null);
        assertEquals(expectedCar, null, "fromDtoToEntity_SendNull_ReturnsNull");
    }

    @Test
    public void fromEntityToRequestDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData() {
        CarRequestDTO actualCarRequestDTO = this.carMapper.fromEntityToRequestDto(DUMMY_CAR);
        assertEquals(DUMMY_CAR_REQUEST_DTO, actualCarRequestDTO,
            "fromEntityToRequestDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData");
    }

    @Test
    public void fromEntityToRequestDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance() {
        CarRequestDTO expectedCarRequestDTO = CarRequestDTO.builder()
            .build();
        CarRequestDTO actualCarRequestDTO = this.carMapper.fromEntityToRequestDto(Car.builder()
            .build());
        assertEquals(expectedCarRequestDTO, actualCarRequestDTO,
            "fromEntityToRequestDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance");
    }

    @Test
    public void fromEntityToRequestDto_SendNull_ReturnsNull() {
        CarRequestDTO actualCarRequestDTO = this.carMapper.fromEntityToRequestDto(null);
        assertEquals(null, actualCarRequestDTO, "fromEntityToRequestDto_SendNull_ReturnsNull");
    }

    @Test
    public void fromEntityToResponseDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData() {
        CarResponseDTO actualCarResponseDTO = this.carMapper.fromEntityToResponseDto(DUMMY_CAR);
        assertEquals(DUMMY_CAR_RESPONSE_DTO, actualCarResponseDTO,
            "fromEntityToRequestDto_SendCarDTOInstance_ReturnsCarInstanceWithCarDTOData");
    }

    @Test
    public void fromEntityToResponseDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance() {
        CarResponseDTO expectedCarResponseDTO = CarResponseDTO.builder()
            .build();
        CarResponseDTO actualCarResponseDTO = this.carMapper.fromEntityToResponseDto(Car.builder()
            .build());
        assertEquals(expectedCarResponseDTO, actualCarResponseDTO,
            "fromEntityToResponseDto_SendEmptyCarDTOInstance_ReturnsEmptyCarInstance");
    }

    @Test
    public void fromEntityToResponseDto_SendNull_ReturnsNull() {
        CarResponseDTO actualCarResponseDTO = this.carMapper.fromEntityToResponseDto(null);
        assertEquals(null, actualCarResponseDTO, "fromEntityToResponseDto_SendNull_ReturnsNull");
    }
}
