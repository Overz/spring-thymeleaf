FROM maven:3.9.9 AS builder

WORKDIR /app

ADD . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/app.jar app.jar

ENV SPRING_PROFILES_ACTIVE "prd"
ENV APP_PORT "8080"
ENV APP_ADDRESS "0.0.0.0"
ENV APP_LOCALE "en"
ENV APP_MANAGMENT_PORT "9090"

EXPOSE "${APP_PORT}"

ENTRYPOINT [ "java", "-jar", "app.jar" ]
