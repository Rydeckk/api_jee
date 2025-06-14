FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY rent-properties-api/pom.xml rent-properties-api/pom.xml
COPY rent-properties-api/src rent-properties-api/src

RUN mvn -f rent-properties-api/pom.xml clean package -DskipTests

FROM maven:3.9-eclipse-temurin-21

WORKDIR /app
COPY . .

WORKDIR /app/rent-properties-api

EXPOSE 8081

CMD ["mvn", "spring-boot:run"]