# Etapa 1: Build com maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime sem o maven
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
