-- Setup rent_car database for rent-cars-api
USE rent_car;

-- Create rental_car table
CREATE TABLE IF NOT EXISTS rental_car (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    rent_amount DOUBLE NOT NULL,
    security_deposit_amount DOUBLE NOT NULL,
    number_of_seats INT NOT NULL,
    number_of_doors INT NOT NULL,
    has_air_conditioning BOOLEAN NOT NULL
);

-- Insert sample rental cars
INSERT IGNORE INTO rental_car (
    brand, model, rent_amount, security_deposit_amount, number_of_seats, number_of_doors, has_air_conditioning
) VALUES 
('Peugeot', '208', 35.00, 200.00, 5, 5, true),
('Renault', 'Clio', 32.00, 180.00, 5, 5, true),
('Citroën', 'C3', 38.00, 220.00, 5, 5, true),
('BMW', 'Série 3', 75.00, 500.00, 5, 4, true),
('Mercedes', 'Classe A', 68.00, 450.00, 5, 5, true),
('Volkswagen', 'Golf', 45.00, 300.00, 5, 5, true),
('Toyota', 'Yaris', 40.00, 250.00, 5, 5, true),
('Ford', 'Fiesta', 36.00, 200.00, 5, 5, false);