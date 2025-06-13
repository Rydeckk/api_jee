package fr.esgi.rent.properties.repository;

import fr.esgi.rent.properties.entity.RentalProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPropertyRepository extends CrudRepository<RentalProperty, Long> {
}