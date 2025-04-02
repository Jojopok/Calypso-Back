# Calypso Application

## Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop) installé et en cours d'exécution

## Configuration

1. **Cloner le dépôt** :

   ```sh
   git clone https://github.com/jojopok/Calypso.git
   cd Calypso
   ```

2. **Configurer `application.properties`** :  
   Créez un fichier `src/main/resources/application.properties` en copiant le contenu de `src/main/resources/application-example.properties` et en modifiant les informations avec vos propres paramètres :

   ```properties
   spring.application.name=Calypso

   spring.datasource.url=jdbc:mysql://db:3306/appdb?createDatabaseIfNotExist=true
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password

   spring.jpa.hibernate.ddl-auto=update

   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   # Le secret doit contenir au moins 32 caractères
   security.jwt.secret-key=yourManuallyGeneratedSecureSecretKeyyourManuallyGeneratedSecureSecretKey

   # 1h in millisecond
   security.jwt.expiration-time=3600000
   ```

3. **Lancer Docker Compose** :  
   Assurez-vous que Docker Desktop est en cours d'exécution, puis exécutez la commande suivante pour démarrer les conteneurs Docker :

   ```sh
   docker compose up
   ```
   
# Accéder à la base de données MySQL
## Connexion au conteneur MySQL

1. **Ouvrir une session dans le conteneur :** :

    ```sh
   docker exec -it calypso-back-db-1 bash
   ```

2. **Lancer le client MySQL :** Une fois dans le conteneur, exécutez la commande suivante pour vous connecter à la base de données  :

    ```sh
   mysql -u root -p
   ```
   Entrez le mot de passe défini dans votre fichier docker-compose.yml (par défaut : password).

3. **Afficher les bases de données :** Une fois connecté, exécutez  :

    ```sql
   SHOW DATABASES;
   ```

4. **Basculer sur la base de données appdb :** 

    ```sql
   USE appdb;
   ```
   
5. **Afficher les tables :** 

    ```sql
   SHOW TABLES;
   ```

## Insérer des données dans les tables

1. **Ajouter un rôle ADMI et un rôle USER :** :

    ```sql
   INSERT INTO t_auth_role (role) VALUES ('ADMIN');
   INSERT INTO t_auth_role (role) VALUES ('USER');
   ```


## Accéder à l'application

L'application sera accessible à l'adresse suivante : [http://localhost:8080](http://localhost:8080)

## Remarques

- Si vous rencontrez des problèmes, vérifiez que Docker Desktop est en cours d'exécution et que les ports `8080` et `3307` ne sont pas utilisés par d'autres applications.
- Assurez-vous que les informations de connexion à la base de données dans `application.properties` sont correctes.
