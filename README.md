# LexiAide

LexiAide est une plateforme numérique destinée à faciliter l'accès aux services et ressources juridiques. Elle met en relation différents profils d'utilisateurs avec des ressources et services juridiques à travers une application Web et une application mobile.

Ce dépôt contient actuellement la fondation du backend REST Spring Boot.

## Prérequis

- Java 17 ou supérieur
- Maven 3.9+ (ou le wrapper Maven `./mvnw`)
- Docker avec Docker Compose v2 pour lancer l'environnement complet

## Démarrage avec Docker Compose

À la racine du dépôt :

```sh
cp .env.example .env
docker compose up --build
```

L'API écoute sur `http://localhost:8080`. Vérifiez son état avec :

```sh
curl http://localhost:8080/api/v1/health
```

Arrêtez les services avec `docker compose down`. Les données PostgreSQL restent dans le volume `postgres_data`. Pour supprimer aussi les données locales, utilisez `docker compose down -v`.

## Démarrage local

1. Démarrez PostgreSQL (par exemple avec `docker compose up -d postgres`).
2. Configurez `DB_URL`, `DB_USERNAME` et `DB_PASSWORD` dans votre environnement. Les valeurs de développement de `.env.example` sont réservées à un usage local.
3. Lancez l'API :

```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Sans Maven Wrapper, remplacez `./mvnw` par `mvn`.

## Profils Spring

- `dev` (profil par défaut) : PostgreSQL configurable par variables d'environnement, migrations Flyway activées.
- `test` : H2 en mémoire en mode compatible PostgreSQL, Flyway désactivé pour les tests.
- `prod` : PostgreSQL obligatoire ; `DB_URL`, `DB_USERNAME` et `DB_PASSWORD` doivent être fournis, sans valeur par défaut.

Variables principales :

| Variable | Valeur locale par défaut | Description |
| --- | --- | --- |
| `PORT` | `8080` | Port HTTP de l'API |
| `DB_URL` | `jdbc:postgresql://localhost:5432/lexiaide` en `dev` | URL JDBC PostgreSQL |
| `DB_USERNAME` | `lexiaide` en `dev` | Utilisateur PostgreSQL |
| `DB_PASSWORD` | `lexiaide_dev` en `dev` | Mot de passe PostgreSQL |
| `POSTGRES_DB` | `lexiaide` | Base utilisée par Compose |
| `POSTGRES_USER` | `lexiaide` | Utilisateur créé par l'image PostgreSQL |
| `POSTGRES_PASSWORD` | `lexiaide_dev` | Mot de passe local de Compose |
| `APP_PORT` | `8080` | Port hôte exposé par Compose |
| `POSTGRES_PORT` | `5432` | Port hôte PostgreSQL exposé par Compose |

Ne réutilisez pas les valeurs de développement en production et ne commitez jamais de secrets réels.

## API initiale

- `GET /api/v1/health` — renvoie un état `UP` avec son horodatage.
- Les erreurs REST sont sérialisées par le gestionnaire centralisé dans `common.exception`.

## Commandes utiles

```sh
./mvnw test
./mvnw package
docker compose logs -f backend
docker compose logs -f postgres
```

Les scripts Flyway versionnés vont dans `src/main/resources/db/migration` et suivent le format `V<version>__<description>.sql`.

## Organisation du backend

Les packages métier sont sous `com.lexiaide.modules`. Chaque module métier contient les packages `controller`, `service` et `repository`. Les DTO et les exceptions partagés sont sous `com.lexiaide.common`. Les packages Java sont en minuscules ; les classes utilisent `UpperCamelCase`, les méthodes et champs `lowerCamelCase`, et les constantes `UPPER_SNAKE_CASE`.

## Branches et Pull Requests

La convention de contribution est documentée dans [CONTRIBUTING.md](CONTRIBUTING.md), avec un modèle de Pull Request et une vérification CI. Les branches distantes `main`/`develop` et la protection de `main` doivent être créées et activées dans les paramètres GitHub du dépôt par un administrateur ; les fichiers du dépôt ne peuvent pas modifier ces paramètres.
