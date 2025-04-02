FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]