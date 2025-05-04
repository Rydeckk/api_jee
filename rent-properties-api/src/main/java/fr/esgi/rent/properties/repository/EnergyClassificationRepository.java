package fr.esgi.rent.properties.repository;

import fr.esgi.rent.properties.entity.EnergyClassification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergyClassificationRepository extends CrudRepository<EnergyClassification, Long> {
    Optional<EnergyClassification> findByDesignation(String designation);
}