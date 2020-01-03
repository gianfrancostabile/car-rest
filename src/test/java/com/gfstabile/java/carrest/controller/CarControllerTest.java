package com.gfstabile.java.carrest.controller;

import com.gfstabile.java.carrest.entity.ValidationErrorCollection;
import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarRequestDTO;
import com.gfstabile.java.carrest.entity.car.CarResponseDTO;
import com.gfstabile.java.carrest.entity.category.CategoryDTO;
import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import com.gfstabile.java.carrest.exception.impl.car.CarAlreadyExistsException;
import com.gfstabile.java.carrest.exception.impl.car.CarNotFoundException;
import com.gfstabile.java.carrest.mapper.CarMapper;
import com.gfstabile.java.carrest.service.CarService;
import com.gfstabile.java.carrest.service.RequestAPIService;
import com.gfstabile.java.carrest.utilities.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    private static final String URI_PREFIX = "/car/";
    private static final String URI_PREFIX_INTERNAL_CODE = URI_PREFIX + "{internalCode}";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;
    @MockBean
    private CarMapper carMapper;
    @MockBean
    private RequestAPIService requestAPIService;

    @Autowired
    private CarController carController;

    private Car dummyCar;
    private CarRequestDTO dummyCarRequestDTO;
    private CarResponseDTO dummyCarResponseDTO;
    private CarResponseDTO dummyCarResponseDTOWithoutCompany;
    private CompanyDTO dummyCompanyDTO;
    private ValidationErrorCollection errorCollection;

    @BeforeEach
    public void setUp() {
        this.errorCollection = new ValidationErrorCollection();
        this.dummyCompanyDTO = CompanyDTO.builder()
            .internalCode("1")
            .name("Name")
            .country("AR")
            .categoryDTO(CategoryDTO.builder()
                .internalCode("1")
                .name("Name")
                .build())
            .build();
        this.dummyCar = Car.builder()
            .id(0L)
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build();
        this.dummyCarRequestDTO = CarRequestDTO.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build();
        this.dummyCarResponseDTO = CarResponseDTO.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyDTO(this.dummyCompanyDTO)
            .build();
        this.dummyCarResponseDTOWithoutCompany = CarResponseDTO.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .build();

        when(this.carMapper.fromDtoToEntity(any(CarRequestDTO.class))).thenReturn(this.dummyCar);
        when(this.carMapper.fromEntityToRequestDto(any(Car.class))).thenReturn(this.dummyCarRequestDTO);
        when(this.carMapper.fromEntityToResponseDto(any(Car.class))).thenReturn(this.dummyCarResponseDTOWithoutCompany);
    }

    @Test
    public void save_SendValidCar_Returns201StatusCode() throws Exception {
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(true);
        doNothing().when(this.carService)
            .save(any(Car.class));

        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isCreated());
    }

    @Test
    public void save_SendNullCar_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.BODY_MANDATORY);

        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_InternalCodeNull_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setInternalCode(null);
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_InternalCodeBlank_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setInternalCode("");
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_NameNull_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_NAME_BLANK);

        this.dummyCarRequestDTO.setName(null);
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_NameBlank_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_NAME_BLANK);

        this.dummyCarRequestDTO.setName("");
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_CompanyInternalCodeNull_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setCompanyInternalCode(null);
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendInvalidCar_CompanyInternalCodeBlank_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setCompanyInternalCode("");
        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendCarButInternalCodeAlreadyExists_Returns400StatusCodeWithErrorMessage() throws Exception {
        this.dummyCarRequestDTO.setInternalCode("invalid-code");
        this.dummyCar.setInternalCode("invalid-code");
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(true);
        doThrow(CarAlreadyExistsException.class).when(this.carService)
            .save(any(Car.class));

        errorCollection.addError(ErrorMessage.CAR_ALREADY_EXISTS);

        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void save_SendCarButCompanyInternalCodeDoNotExists_Returns400StatusCodeWithErrorMessage() throws Exception {
        this.dummyCarRequestDTO.setCompanyInternalCode("invalid-code");
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(false);

        errorCollection.addError(ErrorMessage.COMPANY_INTERNAL_CODE_DO_NOT_EXISTS);

        this.mockMvc.perform(post(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendValidAndExistingCar_Returns204StatusCode() throws Exception {
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(true);
        doNothing().when(this.carService)
            .update(any(Car.class));

        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isNoContent());
    }

    @Test
    public void update_SendNullCar_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.BODY_MANDATORY);

        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_InternalCodeNull_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setInternalCode(null);
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_InternalCodeBlank_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setInternalCode("");
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_NameNull_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_NAME_BLANK);

        this.dummyCarRequestDTO.setName(null);
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_NameBlank_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_NAME_BLANK);

        this.dummyCarRequestDTO.setName("");
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_CompanyInternalCodeNull_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setCompanyInternalCode(null);
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendInvalidCar_CompanyInternalCodeBlank_Returns400WithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.CAR_COMPANY_INTERNAL_CODE_BLANK);

        this.dummyCarRequestDTO.setCompanyInternalCode("");
        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendNonExistingCar_Returns400WithErrorMessage() throws Exception {
        this.dummyCarRequestDTO.setInternalCode("invalid-code");
        this.dummyCar.setInternalCode("invalid-code");
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(true);
        doThrow(CarNotFoundException.class).when(this.carService)
            .update(this.dummyCar);

        errorCollection.addError(ErrorMessage.CAR_NOT_FOUND);

        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void update_SendValidCarButCompanyInternalCodeDoNotExists_Returns400WithErrorMessage() throws Exception {
        this.dummyCarRequestDTO.setCompanyInternalCode("invalid-code");
        when(this.requestAPIService.isValidCompanyInternalCode(anyString())).thenReturn(false);

        errorCollection.addError(ErrorMessage.COMPANY_INTERNAL_CODE_DO_NOT_EXISTS);

        this.mockMvc.perform(put(URI_PREFIX).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.dummyCarRequestDTO.toString()))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void deleteByInternalCode_SendExistingInternalCode_Returns204StatusCode() throws Exception {
        doNothing().when(this.carService)
            .deleteByInternalCode(anyString());

        this.mockMvc.perform(delete(URI_PREFIX_INTERNAL_CODE, "chev-corsa"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByInternalCode_SendNullInternalCode_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.DELETE_NOT_SUPPORTED);

        this.mockMvc.perform(delete(URI_PREFIX))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void deleteByInternalCode_SendBlankInternalCode_Returns400StatusCodeWithErrorMessage() throws Exception {
        errorCollection.addError(ErrorMessage.DELETE_NOT_SUPPORTED);

        this.mockMvc.perform(delete(URI_PREFIX_INTERNAL_CODE, ""))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void deleteByInternalCode_SendNonExistingInternalCode_Returns400StatusCodeWithErrorMessage()
        throws Exception {
        doThrow(CarNotFoundException.class).when(this.carService)
            .deleteByInternalCode(anyString());

        errorCollection.addError(ErrorMessage.CAR_NOT_FOUND);

        this.mockMvc.perform(delete(URI_PREFIX_INTERNAL_CODE, "invalid-code"))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(errorCollection.toString()));
    }

    @Test
    public void findByInternalCode_SendExistingInternalCode_Returns200StatusCodeWithCar() throws Exception {
        when(this.carService.findByInternalCode(anyString())).thenReturn(Optional.of(this.dummyCar));
        when(this.requestAPIService.findCompanyDTO(anyString())).thenReturn(Optional.of(this.dummyCompanyDTO));
        when(this.carMapper.fromEntityToResponseDto(this.dummyCar)).thenReturn(this.dummyCarResponseDTO);
        this.mockMvc.perform(get(URI_PREFIX_INTERNAL_CODE, "chev-corsa"))
            .andExpect(status().isOk())
            .andExpect(content().json(this.dummyCarResponseDTO.toString()));
    }

    @Test
    public void findByInternalCode_SendExistingInternalCodeButNonExistingCompany_Returns204StatusCodeWithCar()
        throws Exception {
        when(this.carService.findByInternalCode(anyString())).thenReturn(Optional.of(this.dummyCar));
        when(this.requestAPIService.findCompanyDTO(anyString())).thenReturn(Optional.empty());
        when(this.carMapper.fromEntityToResponseDto(this.dummyCar)).thenReturn(this.dummyCarResponseDTO);
        this.mockMvc.perform(get(URI_PREFIX_INTERNAL_CODE, "chev-corsa-2"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void findByInternalCode_SendNonExistingInternalCode_Returns204StatusCode() throws Exception {
        when(this.carService.findByInternalCode(anyString())).thenReturn(Optional.empty());
        this.mockMvc.perform(get(URI_PREFIX_INTERNAL_CODE, "invalid-code"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void findAll_ServiceFindAllReturnsOptionalWithList_Returns200StatusCodeWithThreeCars() throws Exception {
        Car carOne = Car.builder()
            .id(0L)
            .internalCode("car-1")
            .name("car-one")
            .companyInternalCode("1")
            .build();
        Car carTwo = Car.builder()
            .id(0L)
            .internalCode("car-2")
            .name("car-two")
            .companyInternalCode("1")
            .build();
        Car carThree = Car.builder()
            .id(0L)
            .internalCode("car-3")
            .name("car-three")
            .companyInternalCode("1")
            .build();

        CarResponseDTO carResponseDTOOne = CarResponseDTO.builder()
            .internalCode("car-1")
            .name("car-one")
            .companyDTO(this.dummyCompanyDTO)
            .build();
        CarResponseDTO carResponseDTOTwo = CarResponseDTO.builder()
            .internalCode("car-2")
            .name("car-two")
            .companyDTO(this.dummyCompanyDTO)
            .build();
        CarResponseDTO carResponseDTOThree = CarResponseDTO.builder()
            .internalCode("car-3")
            .name("car-three")
            .companyDTO(this.dummyCompanyDTO)
            .build();

        List<Car> dummyCarList = new ArrayList<>();
        dummyCarList.add(carOne);
        dummyCarList.add(carTwo);
        dummyCarList.add(carThree);

        List<CarResponseDTO> expectedCarResponseDTOList = new ArrayList<>();
        expectedCarResponseDTOList.add(carResponseDTOOne);
        expectedCarResponseDTOList.add(carResponseDTOTwo);
        expectedCarResponseDTOList.add(carResponseDTOThree);

        when(this.carService.findAll()).thenReturn(Optional.of(dummyCarList));
        when(this.requestAPIService.findCompanyDTO("1")).thenReturn(Optional.of(this.dummyCompanyDTO));

        when(this.carMapper.fromEntityToResponseDto(carOne)).thenReturn(carResponseDTOOne);
        when(this.carMapper.fromEntityToResponseDto(carTwo)).thenReturn(carResponseDTOTwo);
        when(this.carMapper.fromEntityToResponseDto(carThree)).thenReturn(carResponseDTOThree);

        this.mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedCarResponseDTOList.toString()));
    }

    @Test
    public void findAll_ServiceFindAllReturnsOptionalWithList_Returns200StatusCodeWithOneCar() throws Exception {
        Car carOne = Car.builder()
            .id(0L)
            .internalCode("car-1")
            .name("car-one")
            .companyInternalCode("1")
            .build();

        CarResponseDTO carResponseDTOOne = CarResponseDTO.builder()
            .internalCode("car-1")
            .name("car-one")
            .companyDTO(this.dummyCompanyDTO)
            .build();

        List<Car> dummyCarList = new ArrayList<>();
        dummyCarList.add(carOne);

        List<CarResponseDTO> expectedCarResponseDTOList = new ArrayList<>();
        expectedCarResponseDTOList.add(carResponseDTOOne);

        when(this.carService.findAll()).thenReturn(Optional.of(dummyCarList));
        when(this.requestAPIService.findCompanyDTO("1")).thenReturn(Optional.of(this.dummyCompanyDTO));
        when(this.carMapper.fromEntityToResponseDto(carOne)).thenReturn(carResponseDTOOne);

        this.mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedCarResponseDTOList.toString()));
    }

    @Test
    public void findAll_ServiceFindAllReturnsOptionalWithListButCompanyDoNotExists_Returns204StatusCodeWithoutCars()
        throws Exception {
        Car carOne = Car.builder()
            .id(0L)
            .internalCode("car-1")
            .name("car-one")
            .companyInternalCode("invalid-code")
            .build();

        List<Car> dummyCarList = new ArrayList<>();
        dummyCarList.add(carOne);

        when(this.carService.findAll()).thenReturn(Optional.of(dummyCarList));
        when(this.requestAPIService.findCompanyDTO("invalid-code")).thenReturn(Optional.empty());

        this.mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isNoContent())
            .andExpect(content().json(new ArrayList<>().toString()));
    }

    @Test
    public void findAll_ServiceFindAllReturnsOptionalWithList_Returns204StatusCodeWithoutCars() throws Exception {
        List<Car> dummyCarList = new ArrayList<>();
        List<CarResponseDTO> expectedCarResponseDTOList = new ArrayList<>();

        when(this.carService.findAll()).thenReturn(Optional.of(dummyCarList));

        this.mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isNoContent())
            .andExpect(content().json(expectedCarResponseDTOList.toString()));
    }

    @Test
    public void findAll_ServiceFindAllReturnsEmptyOptional_Returns204StatusCodeWithoutCars() throws Exception {
        List<CarResponseDTO> expectedCarResponseDTOList = new ArrayList<>();

        when(this.carService.findAll()).thenReturn(Optional.empty());

        this.mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isNoContent())
            .andExpect(content().json(expectedCarResponseDTOList.toString()));
    }
}
