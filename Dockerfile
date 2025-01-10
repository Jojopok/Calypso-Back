# Utilise l'image Java 21 basée sur Ubuntu Jammy
FROM eclipse-temurin:21-jdk-jammy

# Définit un répertoire de travail
WORKDIR /app

# Copie le fichier jar dans le conteneur
COPY target/calypso-0.0.1-SNAPSHOT.jar app.jar

# Définit la commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]