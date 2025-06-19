# FROM eclipse-temurin:21-alpine

# COPY target/app.jar /app.jar

# COPY nozama.db .

# COPY .env .

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "/app.jar"]

# FROM maven:3.9-eclipse-temurin-21

# WORKDIR /app

# COPY pom.xml .
# COPY src ./src
# COPY nozama.db .
# COPY .env .

# RUN mvn clean package -DskipTests

# EXPOSE 8080 
# ENTRYPOINT ["java", "-jar", "target/app.jar"]

# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/app.jar .
COPY nozama.db .
COPY .env .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]