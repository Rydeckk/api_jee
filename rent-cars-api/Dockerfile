FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY rent-cars-api/pom.xml rent-cars-api/pom.xml
COPY rent-cars-api/src rent-cars-api/src

RUN mvn -f rent-cars-api/pom.xml clean package -DskipTests

FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

COPY . .

WORKDIR /app/rent-cars-api

EXPOSE 8082

CMD ["mvn", "spring-boot:run"]