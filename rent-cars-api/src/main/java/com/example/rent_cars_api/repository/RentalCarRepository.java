package com.example.rent_cars_api.repository;

import com.example.rent_cars_api.model.RentalCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalCarRepository extends CrudRepository<RentalCar, Long> {

}
