FROM maven:3.8.6-jdk-11-slim AS dependencies

COPY pom.xml /tmp/

COPY src /tmp/src

WORKDIR /tmp/

RUN mvn clean package

FROM quay.io/keycloak/keycloak:23.0.0 AS development

USER root

COPY --from=dependencies  /tmp/target/*.jar /opt/keycloak/providers/

USER 1000

CMD ["start-dev", "--import-realm"]