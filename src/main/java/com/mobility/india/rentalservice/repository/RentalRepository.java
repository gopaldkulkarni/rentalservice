package com.mobility.india.rentalservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobility.india.rentalservice.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByTenantId(String tenantId);
    
    List<Rental> findByUserId(String userId);
    Optional<Rental> findFirstByBikeIdAndEndTimeIsNull(String bikeId);
    Optional<Rental> findByRentalId(String rentalId);
}
