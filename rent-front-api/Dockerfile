FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY rent-front-api/pom.xml rent-front-api/pom.xml
COPY rent-front-api/src rent-front-api/src

RUN mvn -f rent-front-api/pom.xml clean package -DskipTests

FROM payara/micro:6.2025.6-jdk21

COPY --from=build /app/rent-front-api/target/front-api.war $DEPLOY_DIR/front-api.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploy", "/opt/payara/deployments/front-api.war", "--contextRoot", "/front-api"]