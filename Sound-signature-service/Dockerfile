# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy the entire AudioDB project
COPY . .

# Build Sound Signature and its required Maven modules
RUN mvn -B -f pom.xml \
    -pl Sound-signature-service \
    -am clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the generated executable JAR
COPY --from=builder /app/Sound-signature-service/target/*.jar app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]