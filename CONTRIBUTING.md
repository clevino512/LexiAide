# Contribuer

## Branches

- `main` : branche stable ; toute modification passe par une Pull Request.
- `develop` : branche d'intégration des changements prêts à être testés.
- `feature/<description>` : nouvelle fonctionnalité, créée depuis `develop`.
- `fix/<description>` : correction, créée depuis `develop` ou depuis `main` pour un correctif urgent.
- `chore/<description>` : maintenance et outillage, créée depuis `develop`.

Les branches `feature/*`, `fix/*` et `chore/*` sont fusionnées dans `develop`. Les versions validées sont ensuite fusionnées de `develop` vers `main`. Pour un correctif urgent, fusionner d'abord dans `main`, puis répercuter le correctif dans `develop`.

## Pull Requests et revue

- Ouvrir une Pull Request vers `develop`, sauf publication ou correctif urgent destiné à `main`.
- Demander une revue au Tech Lead et obtenir son approbation avant la fusion.
- Attendre la réussite de la vérification CI, décrire le changement et ses tests, puis résoudre les conversations ouvertes.
- Utiliser une fusion squash et supprimer la branche après fusion.
- Protéger `main` dans les paramètres GitHub : exiger une Pull Request, une approbation, les vérifications CI requises et l'approbation des conversations ; interdire les pushs directs et les suppressions/force-pushs.

La protection doit être activée par un administrateur GitHub dans les paramètres du dépôt ; elle ne peut pas être imposée par ce fichier seul. Les règles d'approbation sont proposées comme base et doivent être confirmées avec le Tech Lead.

## Vérifications locales

```sh
./mvnw test
./mvnw package
```
