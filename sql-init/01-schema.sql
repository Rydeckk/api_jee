-- Create databases if they don't exist
CREATE DATABASE IF NOT EXISTS rent_db;
CREATE DATABASE IF NOT EXISTS rent_car;

-- Create user accounts for each API
CREATE USER IF NOT EXISTS 'rent_properties_user'@'%' IDENTIFIED BY 'rootpassword';
CREATE USER IF NOT EXISTS 'rent_cars_user'@'%' IDENTIFIED BY 'password';

-- Grant privileges
GRANT ALL PRIVILEGES ON rent_db.* TO 'rent_properties_user'@'%';
GRANT ALL PRIVILEGES ON rent_car.* TO 'rent_cars_user'@'%';
FLUSH PRIVILEGES;

-- Setup rent_db database for rent-properties-api
USE rent_db;

-- Create property_type table
CREATE TABLE IF NOT EXISTS property_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    designation VARCHAR(255) NOT NULL UNIQUE
);

-- Create energy_classification table
CREATE TABLE IF NOT EXISTS energy_classification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    designation VARCHAR(255) NOT NULL UNIQUE
);

-- Create rental_property table
CREATE TABLE IF NOT EXISTS rental_property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description TEXT NOT NULL,
    town VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    property_type_id BIGINT,
    rent_amount DECIMAL(10,2) NOT NULL,
    security_deposit_amount DECIMAL(10,2) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    number_of_bedrooms INT,
    floor_number INT,
    number_of_floors INT,
    construction_year VARCHAR(4),
    energy_classification_id BIGINT,
    has_elevator BOOLEAN DEFAULT FALSE,
    has_intercom BOOLEAN DEFAULT FALSE,
    has_balcony BOOLEAN DEFAULT FALSE,
    has_parking_space BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (property_type_id) REFERENCES property_type(id),
    FOREIGN KEY (energy_classification_id) REFERENCES energy_classification(id)
);

-- Insert some reference data
INSERT IGNORE INTO property_type (designation) VALUES 
('Appartement'),
('Maison'),
('Studio'),
('Loft');

INSERT IGNORE INTO energy_classification (designation) VALUES 
('A'),
('B'),
('C'),
('D'),
('E'),
('F'),
('G');

-- Insert sample rental properties
INSERT IGNORE INTO rental_property (
    description, town, address, property_type_id, rent_amount, security_deposit_amount, area,
    number_of_bedrooms, floor_number, number_of_floors, construction_year, energy_classification_id,
    has_elevator, has_intercom, has_balcony, has_parking_space
) VALUES 
(
    'Bel appartement lumineux avec vue sur parc',
    'Paris',
    '123 Rue de la Paix',
    1,
    1200.00,
    2400.00,
    65.5,
    2,
    3,
    5,
    '2010',
    2,
    TRUE,
    TRUE,
    TRUE,
    FALSE
),
(
    'Studio moderne dans quartier dynamique',
    'Lyon',
    '45 Avenue de la République',
    3,
    650.00,
    1300.00,
    25.0,
    0,
    1,
    4,
    '2018',
    1,
    TRUE,
    TRUE,
    FALSE,
    FALSE
),
(
    'Maison familiale avec jardin',
    'Marseille',
    '78 Chemin des Oliviers',
    2,
    1800.00,
    3600.00,
    120.0,
    4,
    NULL,
    2,
    '1995',
    3,
    FALSE,
    FALSE,
    TRUE,
    TRUE
);