FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
# Copy source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

# Install wget for healthcheck
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/product-management-0.0.1-SNAPSHOT.jar .

# Run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "product-management-0.0.1-SNAPSHOT.jar"]
