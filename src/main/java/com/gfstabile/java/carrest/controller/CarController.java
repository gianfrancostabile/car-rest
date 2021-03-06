package com.gfstabile.java.carrest.controller;

import com.gfstabile.java.carrest.entity.ValidationErrorCollection;
import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarRequestDTO;
import com.gfstabile.java.carrest.entity.car.CarResponseDTO;
import com.gfstabile.java.carrest.entity.company.CompanyDTO;
import com.gfstabile.java.carrest.exception.AbstractServiceException;
import com.gfstabile.java.carrest.mapper.CarMapper;
import com.gfstabile.java.carrest.service.CarService;
import com.gfstabile.java.carrest.service.RequestAPIService;
import com.gfstabile.java.carrest.utilities.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The controller class related to Car entity class.
 *
 * @author G. F. Stabile
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RequestAPIService requestAPIService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationErrorCollection> save(@RequestBody @Valid CarRequestDTO carRequestDTO)
        throws AbstractServiceException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationErrorCollection errorCollection = null;
        boolean isValidCompanyInternalCode =
            this.requestAPIService.isValidCompanyInternalCode(carRequestDTO.getCompanyInternalCode());
        if (isValidCompanyInternalCode) {
            this.carService.save(this.carMapper.fromDtoToEntity(carRequestDTO));
            httpStatus = HttpStatus.CREATED;
        } else {
            errorCollection = this.buildCompanyInternalCodeError();
        }
        return new ResponseEntity<>(errorCollection, httpStatus);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationErrorCollection> update(@RequestBody @Valid CarRequestDTO carRequestDTO)
        throws AbstractServiceException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationErrorCollection errorCollection = null;
        boolean isValidCompanyInternalCode =
            this.requestAPIService.isValidCompanyInternalCode(carRequestDTO.getCompanyInternalCode());
        if (isValidCompanyInternalCode) {
            this.carService.update(this.carMapper.fromDtoToEntity(carRequestDTO));
            httpStatus = HttpStatus.NO_CONTENT;
        } else {
            errorCollection = this.buildCompanyInternalCodeError();
        }
        return new ResponseEntity<>(errorCollection, httpStatus);
    }

    @DeleteMapping("/{internalCode}")
    public ResponseEntity<Void> deleteByInternalCode(@PathVariable @NotBlank String internalCode)
        throws AbstractServiceException {
        this.carService.deleteByInternalCode(internalCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{internalCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CarResponseDTO> findByInternalCode(@PathVariable @NotBlank String internalCode) {
        ResponseEntity<CarResponseDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Optional<Car> carOptional = this.carService.findByInternalCode(internalCode);
        if (carOptional.isPresent()) {
            Optional<CarResponseDTO> optionalCarResponseDTO = this.buildCarResponseDTOFromEntity(carOptional.get());
            if (optionalCarResponseDTO.isPresent()) {
                responseEntity = new ResponseEntity<>(optionalCarResponseDTO.get(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CarResponseDTO>> findAll() {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        List<CarResponseDTO> carResponseDTOList = new ArrayList<>();

        Optional<List<Car>> optionalCarList = this.carService.findAll();
        if (optionalCarList.isPresent()) {
            carResponseDTOList.addAll(optionalCarList.get()
                .stream()
                .map(this::buildCarResponseDTOFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
            if (!carResponseDTOList.isEmpty()) {
                httpStatus = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(carResponseDTOList, httpStatus);
    }

    /**
     * Maps the given {@link Car} instance to {@link CarResponseDTO}.
     * <p>
     * Unlike {@link CarMapper} the CompanyDTO is required, for that
     * the {@link RequestAPIService#findCompanyDTO(String)} is used.
     * <br>
     * If the car instance is null or the company internal code do not
     * exists, an empty optional will be returned.
     * </p>
     *
     * @param car the car instance
     * @return an Optional with the {@link CarResponseDTO} instance.
     */
    private Optional<CarResponseDTO> buildCarResponseDTOFromEntity(Car car) {
        CarResponseDTO carResponseDTO = null;
        if (Objects.nonNull(car)) {
            Optional<CompanyDTO> optionalCompanyDTO =
                this.requestAPIService.findCompanyDTO(car.getCompanyInternalCode());
            if (optionalCompanyDTO.isPresent()) {
                carResponseDTO = this.carMapper.fromEntityToResponseDto(car);
                carResponseDTO.setCompanyDTO(optionalCompanyDTO.get());
            }
        }
        return Optional.ofNullable(carResponseDTO);
    }

    /**
     * Returns a {@link ValidationErrorCollection} instance with
     * {@link ErrorMessage#COMPANY_INTERNAL_CODE_DO_NOT_EXISTS} error message.
     *
     * @return a {@link ValidationErrorCollection} instance.
     */
    private ValidationErrorCollection buildCompanyInternalCodeError() {
        ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();
        validationErrorCollection.addError(ErrorMessage.COMPANY_INTERNAL_CODE_DO_NOT_EXISTS);
        return validationErrorCollection;
    }
}
