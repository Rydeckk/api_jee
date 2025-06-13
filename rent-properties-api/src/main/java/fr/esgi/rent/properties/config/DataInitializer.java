package fr.esgi.rent.properties.config;

import fr.esgi.rent.properties.entity.EnergyClassification;
import fr.esgi.rent.properties.entity.PropertyType;
import fr.esgi.rent.properties.repository.EnergyClassificationRepository;
import fr.esgi.rent.properties.repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer implements ApplicationRunner {

    private final PropertyTypeRepository propertyTypeRepository;
    private final EnergyClassificationRepository energyClassificationRepository;

    public DataInitializer(PropertyTypeRepository propertyTypeRepository,
                           EnergyClassificationRepository energyClassificationRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.energyClassificationRepository = energyClassificationRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Initialize property types if the table is empty
        if (propertyTypeRepository.count() == 0) {
            List<PropertyType> propertyTypes = new ArrayList<>();
            propertyTypes.add(new PropertyType(null, "FLAT"));
            propertyTypes.add(new PropertyType(null, "HOUSE"));
            propertyTypeRepository.saveAll(propertyTypes);
        }

        // Initialize energy classifications if the table is empty
        if (energyClassificationRepository.count() == 0) {
            List<EnergyClassification> energyClassifications = new ArrayList<>();
            energyClassifications.add(new EnergyClassification(null, "A"));
            energyClassifications.add(new EnergyClassification(null, "B"));
            energyClassifications.add(new EnergyClassification(null, "C"));
            energyClassifications.add(new EnergyClassification(null, "D"));
            energyClassifications.add(new EnergyClassification(null, "E"));
            energyClassifications.add(new EnergyClassification(null, "F"));
            energyClassifications.add(new EnergyClassification(null, "G"));
            energyClassificationRepository.saveAll(energyClassifications);
        }
    }
}