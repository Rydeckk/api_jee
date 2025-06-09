CREATE TABLE rental_car
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand                VARCHAR(100) NOT NULL,
    model                VARCHAR(100) NOT NULL,
    rent_amount DOUBLE NOT NULL,
    security_deposit_amount DOUBLE NOT NULL,
    number_of_seats      INT NOT NULL,
    number_of_doors      INT NOT NULL,
    has_air_conditioning BOOLEAN NOT NULL
);