package com.mobility.india.rentalservice.controller;

import com.mobility.india.rentalservice.bikeclient.BikeAPIClient;
import com.mobility.india.rentalservice.model.Rental;
import com.mobility.india.rentalservice.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    @Autowired
    RentalRepository repository;

    @Autowired
    private BikeAPIClient client;
    public boolean isBikeAvailable(Long id) {
        return client.isBikeFree(id);
    }

    public List<Rental> getCompletedRentalRecords() {
        //TODO handle null by using optional
        List<Rental> res = repository.findAllCompletedOrCancelledRentals();
        return res;
    }
}
