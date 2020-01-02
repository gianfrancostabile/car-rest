package com.gfstabile.java.carrest.controller;

import com.gfstabile.java.carrest.entity.ValidationErrorCollection;
import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.entity.car.CarDTO;
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
    public ResponseEntity<ValidationErrorCollection> save(@RequestBody @Valid CarDTO carDTO)
        throws AbstractServiceException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationErrorCollection errorCollection = null;
        boolean isValidCompanyInternalCode =
            this.requestAPIService.isValidCompanyInternalCode(carDTO.getCompanyInternalCode());
        if (isValidCompanyInternalCode) {
            this.carService.save(this.carMapper.fromDtoToEntity(carDTO));
            httpStatus = HttpStatus.CREATED;
        } else {
            errorCollection = this.buildCompanyInternalCodeError();
        }
        return new ResponseEntity<>(errorCollection, httpStatus);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationErrorCollection> update(@RequestBody @Valid CarDTO carDTO)
        throws AbstractServiceException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationErrorCollection errorCollection = null;
        boolean isValidCompanyInternalCode =
            this.requestAPIService.isValidCompanyInternalCode(carDTO.getCompanyInternalCode());
        if (isValidCompanyInternalCode) {
            this.carService.update(this.carMapper.fromDtoToEntity(carDTO));
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
    public ResponseEntity<CarDTO> findByInternalCode(@PathVariable @NotBlank String internalCode) {
        Optional<Car> carOptional = this.carService.findByInternalCode(internalCode);
        return carOptional.map(car -> new ResponseEntity<>(this.carMapper.fromEntityToDto(car), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CarDTO>> findAll() {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        List<CarDTO> carDTOList = new ArrayList<>();

        Optional<List<Car>> optionalCarList = this.carService.findAll();
        if (optionalCarList.isPresent()) {
            carDTOList.addAll(optionalCarList.get()
                .stream()
                .map(this.carMapper::fromEntityToDto)
                .collect(Collectors.toList()));
            if (!carDTOList.isEmpty()) {
                httpStatus = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(carDTOList, httpStatus);
    }

    private ValidationErrorCollection buildCompanyInternalCodeError() {
        ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();
        validationErrorCollection.addError(ErrorMessage.COMPANY_INTERNAL_CODE_DO_NOT_EXISTS);
        return validationErrorCollection;
    }
}
