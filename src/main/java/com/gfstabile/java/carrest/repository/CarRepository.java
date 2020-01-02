package com.gfstabile.java.carrest.repository;

import com.gfstabile.java.carrest.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * The repository interface related to Car entity class.
 *
 * @author G. F. Stabile
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(name = "update-cars",
        value = "UPDATE cars c SET c.name = :name, c.company_internal_code = :company_internal_code WHERE c.internal_code = :internal_code",
        nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("internal_code") String internalCode, @Param("name") String name,
        @Param("company_internal_code") String companyInternalCode);

    @Transactional
    void deleteByInternalCode(String internalCode);

    Optional<Car> findByInternalCode(String internalCode);

    boolean existsByInternalCode(String internalCode);
}
