package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.exception.AbstractServiceException;
import com.gfstabile.java.carrest.exception.impl.NullInternalCodeException;
import com.gfstabile.java.carrest.exception.impl.car.CarAlreadyExistsException;
import com.gfstabile.java.carrest.exception.impl.car.CarNotFoundException;
import com.gfstabile.java.carrest.exception.impl.car.InvalidCarException;
import com.gfstabile.java.carrest.exception.impl.car.NullCarException;
import com.gfstabile.java.carrest.repository.CarRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The service class related to Car entity class.
 *
 * @author G. F. Stabile
 */
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void save(Car car) throws AbstractServiceException {
        if (Objects.isNull(car)) {
            throw new NullCarException();
        } else if (!car.isValid()) {
            throw new InvalidCarException();
        }
        try {
            this.carRepository.save(car);
        } catch (ConstraintViolationException exception) {
            throw new CarAlreadyExistsException();
        }
    }

    public void update(Car car) throws AbstractServiceException {
        if (Objects.isNull(car)) {
            throw new NullCarException();
        } else if (!car.isValid()) {
            throw new InvalidCarException();
        } else if (!this.carRepository.existsByInternalCode(car.getInternalCode())) {
            throw new CarNotFoundException();
        }
        this.carRepository.update(car.getInternalCode(), car.getName(), car.getCompanyInternalCode());
    }

    public void deleteByInternalCode(String internalCode) throws AbstractServiceException {
        if (Strings.isBlank(internalCode)) {
            throw new NullInternalCodeException();
        } else if (!this.carRepository.existsByInternalCode(internalCode)) {
            throw new CarNotFoundException();
        }
        this.carRepository.deleteByInternalCode(internalCode);
    }

    public Optional<Car> findByInternalCode(String internalCode) {
        return Strings.isNotBlank(internalCode)
            ? this.carRepository.findByInternalCode(internalCode)
            : Optional.empty();
    }

    public Optional<List<Car>> findAll() {
        return Optional.of(this.carRepository.findAll());
    }
}
