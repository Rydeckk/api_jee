# Rent APIs

This project provides REST APIs for managing rental properties and rental cars, built with Spring Boot and Jakarta EE.

## Prerequisites

- **Java 21**
- **Maven 3.x**
- **Docker & Docker Compose**

## Quick Start

### 1. Start the Database

```bash
docker-compose up -d
```

This starts a MySQL 8.0 database with:
- Container: `rent-mysql`
- Port: `3306`
- Databases: `rent_db` (properties), `rent_car` (cars)

### 2. Start the APIs

**Properties API (Port 8081):**
```bash
cd rent-properties-api
mvn spring-boot:run
```

**Cars API (Port 8082):**
```bash
cd rent-cars-api
mvn spring-boot:run
```

## API Endpoints

### Properties API - `http://localhost:8081/rent-properties-api`

#### Get All Properties
```bash
curl -X GET http://localhost:8081/rent-properties-api/rental-properties
```

#### Get Property by ID
```bash
curl -X GET http://localhost:8081/rent-properties-api/rental-properties/1
```

#### Create Property
```bash
curl -X POST http://localhost:8081/rent-properties-api/rental-properties \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Beautiful apartment in city center",
    "town": "Paris",
    "address": "123 Rue de Rivoli",
    "propertyType": "Appartement",
    "rentAmount": 1200.0,
    "securityDepositAmount": 2400.0,
    "area": 75.0,
    "numberOfBedrooms": 2,
    "floorNumber": 3,
    "numberOfFloors": 5,
    "constructionYear": "2020",
    "energyClassification": "B",
    "hasElevator": true,
    "hasIntercom": false,
    "hasBalcony": true,
    "hasParkingSpace": false
  }'
```

#### Update Property (Full)
```bash
curl -X PUT http://localhost:8081/rent-properties-api/rental-properties/1 \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Updated description",
    "town": "Lyon",
    "address": "456 Avenue des Frères Lumière",
    "propertyType": "Maison",
    "rentAmount": 1500.0,
    "securityDepositAmount": 3000.0,
    "area": 100.0,
    "numberOfBedrooms": 3,
    "energyClassification": "A"
  }'
```

#### Update Property (Partial)
```bash
curl -X PATCH http://localhost:8081/rent-properties-api/rental-properties/1 \
  -H "Content-Type: application/json" \
  -d '{
    "rentAmount": 1300.0,
    "town": "Marseille"
  }'
```

#### Delete Property
```bash
curl -X DELETE http://localhost:8081/rent-properties-api/rental-properties/1
```

### Cars API - `http://localhost:8082`

#### Get All Cars
```bash
curl -X GET http://localhost:8082/api/rent-cars-api/rental-cars
```

#### Get Car by ID
```bash
curl -X GET http://localhost:8082/api/rent-cars-api/rental-cars/1
```

#### Create Car
```bash
curl -X POST http://localhost:8082/api/rent-cars-api/rental-cars \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Peugeot",
    "model": "308",
    "rentAmount": 45.0,
    "securityDepositAmount": 500.0,
    "numberOfSeats": 5,
    "numberOfDoors": 5,
    "hasAirConditioning": true
  }'
```

#### Update Car (Full)
```bash
curl -X PUT http://localhost:8082/api/rent-cars-api/rental-cars/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Renault",
    "model": "Clio",
    "rentAmount": 40.0,
    "securityDepositAmount": 400.0,
    "numberOfSeats": 5,
    "numberOfDoors": 5,
    "hasAirConditioning": true
  }'
```

#### Update Car (Partial)
```bash
curl -X PATCH http://localhost:8082/api/rent-cars-api/rental-cars/1 \
  -H "Content-Type: application/json" \
  -d '{
    "rentAmount": 50.0
  }'
```

#### Delete Car
```bash
curl -X DELETE http://localhost:8082/api/rent-cars-api/rental-cars/1
```

## Testing

Run tests for each API:

```bash
# Properties API tests
cd rent-properties-api
mvn test

# Cars API tests  
cd rent-cars-api
mvn test
```

## Stopping the Services

```bash
# Stop APIs with Ctrl+C in their respective terminals

# Stop database
docker-compose down
```