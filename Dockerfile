# Étape 1 : Image Maven pour compiler le projet
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier tout le projet et compiler
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Image légère pour exécuter l'application
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copier le fichier jar depuis l'étape de construction
COPY --from=builder /app/target/calypso-0.0.1-SNAPSHOT.jar app.jar

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
