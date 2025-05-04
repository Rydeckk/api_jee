package fr.esgi.rent.properties.repository;

import fr.esgi.rent.properties.entity.PropertyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
    Optional<PropertyType> findByDesignation(String designation);
}