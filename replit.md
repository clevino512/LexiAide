# Instructions Replit

## Lancer le backend

Le workflow **LexiAide Backend** exécute `scripts/start-replit.sh` et expose l'API sur le port `5000` de l'aperçu Replit. Le script démarre PostgreSQL localement sur le port `55432` et Spring Boot avec le profil `dev`. La base locale est conservée dans `.data/postgres` ; ce dossier est ignoré par Git et par le déploiement.

L'endpoint de vérification est `GET /api/v1/health`.

## Environnement local et Docker

Pour tester le même profil avec PostgreSQL en conteneurs, utilisez `docker compose up --build` comme décrit dans le README. Cette méthode expose l'API sur le port `8080` et utilise le volume Compose `postgres_data`.

Pour les tests automatisés, exécutez `./mvnw test`. Le profil `test` utilise H2 en mémoire ; les profils `dev` et `prod` utilisent PostgreSQL.

N'utilisez pas les valeurs d'accès de développement en production. Les paramètres obligatoires de production sont `DB_URL`, `DB_USERNAME` et `DB_PASSWORD`.
