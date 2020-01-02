package com.gfstabile.java.carrest.service;

import com.gfstabile.java.carrest.entity.car.Car;
import com.gfstabile.java.carrest.exception.AbstractServiceException;
import com.gfstabile.java.carrest.exception.impl.NullInternalCodeException;
import com.gfstabile.java.carrest.exception.impl.car.CarAlreadyExistsException;
import com.gfstabile.java.carrest.exception.impl.car.CarNotFoundException;
import com.gfstabile.java.carrest.exception.impl.car.InvalidCarException;
import com.gfstabile.java.carrest.exception.impl.car.NullCarException;
import com.gfstabile.java.carrest.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    private Car dummyCar;

    @BeforeEach
    public void setUp() {
        this.dummyCar = Car.builder()
            .internalCode("1")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build();
    }

    @Test
    public void save_SendValidCar_DoNotThrowException() throws AbstractServiceException {
        when(this.carRepository.save(this.dummyCar)).thenReturn(this.dummyCar);
        this.carService.save(this.dummyCar);
        assertTrue(true, "save_SendValidCar_DoNotThrowException");
    }

    @Test
    public void save_SendNullCar_ThrowNullCarException() {
        assertThrows(NullCarException.class, () -> this.carService.save(null),
            "save_SendNullCar_ThrowNullCarException");
    }

    @Test
    public void save_SendInvalidCar_InternalCodeNull_ThrowInvalidCarException() {
        this.dummyCar.setInternalCode(null);
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_InternalCodeNull_ThrowInvalidCarException");
    }

    @Test
    public void save_SendInvalidCar_InternalCodeBlank_ThrowInvalidCarException() {
        this.dummyCar.setInternalCode("");
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_InternalCodeBlank_ThrowInvalidCarException");
    }

    @Test
    public void save_SendInvalidCar_NameNull_ThrowInvalidCarException() {
        this.dummyCar.setName(null);
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_NameNull_ThrowInvalidCarException");
    }

    @Test
    public void save_SendInvalidCar_NameBlank_ThrowInvalidCarException() {
        this.dummyCar.setName("");
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_NameBlank_ThrowInvalidCarException");
    }

    @Test
    public void save_SendInvalidCar_CompanyInternalCodeNull_ThrowInvalidCarException() {
        this.dummyCar.setCompanyInternalCode(null);
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_CompanyInternalCodeNull_ThrowInvalidCarException");
    }

    @Test
    public void save_SendInvalidCar_CompanyInternalCodeBlank_ThrowInvalidCarException() {
        this.dummyCar.setCompanyInternalCode("");
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "save_SendInvalidCar_CompanyInternalCodeBlank_ThrowInvalidCarException");
    }

    @Test
    public void save_SendCarWithExistingInternalCode_ThrowCarAlreadyExistsException() throws AbstractServiceException {
        String sameInternalCode = "blazer";
        this.dummyCar.setInternalCode(sameInternalCode);
        Car newDummyCar = Car.builder()
            .internalCode(sameInternalCode)
            .name("Blazer")
            .companyInternalCode("chevrolet")
            .build();

        when(this.carRepository.save(this.dummyCar)).thenReturn(this.dummyCar);
        when(this.carRepository.save(newDummyCar)).thenThrow(ConstraintViolationException.class);

        this.carService.save(this.dummyCar);
        assertThrows(CarAlreadyExistsException.class, () -> this.carService.save(newDummyCar),
            "save_SendCarWithExistingInternalCode_ThrowCarAlreadyExistsException");
    }

    @Test
    public void update_SendValidCar_DoNotThrowException() throws AbstractServiceException {
        when(this.carRepository.save(this.dummyCar)).thenReturn(this.dummyCar);
        when(this.carRepository.existsByInternalCode(anyString())).thenReturn(true);
        doNothing().when(this.carRepository)
            .update(anyString(), anyString(), anyString());

        this.carService.save(this.dummyCar);

        this.dummyCar.setName("new name");
        this.carService.update(this.dummyCar);
        assertTrue(true, "update_SendValidCar_DoNotThrowException");
    }

    @Test
    public void update_SendNullCar_ThrowNullCarException() {
        assertThrows(NullCarException.class, () -> this.carService.update(null),
            "update_SendNullCar_ThrowNullCarException");
    }

    @Test
    public void update_SendInvalidCar_InternalCodeNull_ThrowInvalidCarException() {
        this.dummyCar.setInternalCode(null);
        assertThrows(InvalidCarException.class, () -> this.carService.update(this.dummyCar),
            "update_SendInvalidCar_InternalCodeNull_ThrowInvalidCarException");
    }

    @Test
    public void update_SendInvalidCar_InternalCodeBlank_ThrowInvalidCarException() {
        this.dummyCar.setInternalCode("");
        assertThrows(InvalidCarException.class, () -> this.carService.update(this.dummyCar),
            "update_SendInvalidCar_InternalCodeBlank_ThrowInvalidCarException");
    }

    @Test
    public void update_SendInvalidCar_NameNull_ThrowInvalidCarException() {
        this.dummyCar.setName(null);
        assertThrows(InvalidCarException.class, () -> this.carService.update(this.dummyCar),
            "update_SendInvalidCar_NameNull_ThrowInvalidCarException");
    }

    @Test
    public void update_SendInvalidCar_NameBlank_ThrowInvalidCarException() {
        this.dummyCar.setName("");
        assertThrows(InvalidCarException.class, () -> this.carService.update(this.dummyCar),
            "update_SendInvalidCar_NameBlank_ThrowInvalidCarException");
    }

    @Test
    public void update_SendInvalidCar_CompanyInternalCodeNull_ThrowInvalidCarException() {
        this.dummyCar.setCompanyInternalCode(null);
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "update_SendInvalidCar_CompanyInternalCodeNull_ThrowInvalidCarException");
    }

    @Test
    public void update_SendInvalidCar_CompanyInternalCodeBlank_ThrowInvalidCarException() {
        this.dummyCar.setCompanyInternalCode("");
        assertThrows(InvalidCarException.class, () -> this.carService.save(this.dummyCar),
            "update_SendInvalidCar_CompanyInternalCodeBlank_ThrowInvalidCarException");
    }

    @Test
    public void update_SendCarWithNonExistingInternalCode_ThrowCarNotFoundException() {
        when(this.carRepository.existsByInternalCode(anyString())).thenReturn(false);
        assertThrows(CarNotFoundException.class, () -> this.carService.update(this.dummyCar),
            "update_SendCarWithNonExistingInternalCode_ThrowCarNotFoundException");
    }

    @Test
    public void delete_SendValidInternalCode_DoNotThrowException() throws AbstractServiceException {
        when(this.carRepository.save(this.dummyCar)).thenReturn(this.dummyCar);
        when(this.carRepository.existsByInternalCode(anyString())).thenReturn(true);
        doNothing().when(this.carRepository)
            .deleteByInternalCode(anyString());

        this.carService.save(this.dummyCar);
        this.carService.deleteByInternalCode("chev-corsa");
        assertTrue(true, "delete_SendValidInternalCode_DoNotThrowException");
    }

    @Test
    public void delete_SendNullInternalCode_ThrowNullInternalCodeException() {
        assertThrows(NullInternalCodeException.class, () -> this.carService.deleteByInternalCode(null),
            "delete_SendNullInternalCode_ThrowNullInternalCodeException");
    }

    @Test
    public void delete_SendBlankInternalCode_ThrowNullInternalCodeException() {
        assertThrows(NullInternalCodeException.class, () -> this.carService.deleteByInternalCode(""),
            "delete_SendBlankInternalCode_ThrowNullInternalCodeException");
    }

    @Test
    public void delete_SendNonExistingInternalCode_ThrowCarNotFoundException() {
        when(this.carRepository.existsByInternalCode(anyString())).thenReturn(false);
        assertThrows(CarNotFoundException.class, () -> this.carService.deleteByInternalCode("invalid-code"),
            "delete_SendNonExistingInternalCode_ThrowCarNotFoundException");
    }

    @Test
    public void findByInternalCode_SendValidInternalCode_ReturnsOptionalWithTheCar() throws AbstractServiceException {
        when(this.carRepository.save(this.dummyCar)).thenReturn(this.dummyCar);
        when(this.carRepository.findByInternalCode(anyString())).thenReturn(Optional.of(this.dummyCar));

        this.carService.save(this.dummyCar);
        Optional<Car> actualOptional = this.carService.findByInternalCode(this.dummyCar.getInternalCode());
        if (actualOptional.isPresent()) {
            assertEquals(actualOptional.get(), this.dummyCar,
                "findByInternalCode_SendValidInternalCode_ReturnsOptionalWithTheCar");
        } else {
            fail("findByInternalCode_SendValidInternalCode_ReturnsOptionalWithTheCar");
        }
    }

    @Test
    public void findByInternalCode_SendNullInternalCode_ReturnsEmptyOptional() {
        Optional<Car> actualOptional = this.carService.findByInternalCode(null);
        assertFalse(actualOptional.isPresent(), "findByInternalCode_SendNullInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findByInternalCode_SendBlankInternalCode_ReturnsEmptyOptional() {
        Optional<Car> actualOptional = this.carService.findByInternalCode("");
        assertFalse(actualOptional.isPresent(), "findByInternalCode_SendBlankInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findByInternalCode_SendNonExistingInternalCode_ReturnsEmptyOptional() {
        when(this.carRepository.findByInternalCode(anyString())).thenReturn(Optional.empty());
        Optional<Car> actualOptional = this.carService.findByInternalCode("invalid-code");
        assertFalse(actualOptional.isPresent(), "findByInternalCode_SendNonExistingInternalCode_ReturnsEmptyOptional");
    }

    @Test
    public void findAll_ThreeCarsAreStored_ReturnsListWithThreeCars() {
        List<Car> dummyCarList = new ArrayList<>();
        dummyCarList.add(Car.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build());
        dummyCarList.add(Car.builder()
            .internalCode("chev-blazer")
            .name("Blazer")
            .companyInternalCode("chevrolet")
            .build());
        dummyCarList.add(Car.builder()
            .internalCode("chev-cruze")
            .name("Cruze")
            .companyInternalCode("chevrolet")
            .build());
        when(this.carRepository.findAll()).thenReturn(dummyCarList);

        Optional<List<Car>> actualCarList = this.carService.findAll();
        if (actualCarList.isPresent()) {
            assertEquals(dummyCarList, actualCarList.get(), "findAll_ThreeCarsAreStored_ReturnsListWithThreeCars");
        } else {
            fail("findAll_ThreeCarsAreStored_ReturnsListWithThreeCars");
        }
    }

    @Test
    public void findAll_OneCarAreStored_ReturnsListWithTheCar() {
        List<Car> dummyCarList = new ArrayList<>();
        dummyCarList.add(Car.builder()
            .internalCode("chev-corsa")
            .name("Corsa")
            .companyInternalCode("chevrolet")
            .build());
        when(this.carRepository.findAll()).thenReturn(dummyCarList);

        Optional<List<Car>> actualCarList = this.carService.findAll();
        if (actualCarList.isPresent()) {
            assertEquals(dummyCarList, actualCarList.get(), "findAll_OneCarAreStored_ReturnsListWithTheCar");
        } else {
            fail("findAll_OneCarAreStored_ReturnsListWithTheCar");
        }
    }

    @Test
    public void findAll_NoCarsAreStored_ReturnsEmptyList() {
        List<Car> dummyCarList = new ArrayList<>();
        when(this.carRepository.findAll()).thenReturn(dummyCarList);

        Optional<List<Car>> actualCarList = this.carService.findAll();
        if (actualCarList.isPresent()) {
            assertEquals(dummyCarList, actualCarList.get(), "findAll_NoCarsAreStored_ReturnsEmptyList");
        } else {
            fail("findAll_NoCarsAreStored_ReturnsEmptyList");
        }
    }
}
