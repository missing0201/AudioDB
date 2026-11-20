
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn -B -f pom.xml \
    -pl Player-service \
    -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/Player-service/target/*.jar app.jar

EXPOSE 4002

ENTRYPOINT ["java", "-jar", "app.jar"]
