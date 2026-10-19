#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
POSTGRES_DIR="${LOCAL_PGDATA:-$ROOT_DIR/.data/postgres}"
DATA_DIR="$POSTGRES_DIR/data"
SOCKET_DIR="$POSTGRES_DIR/socket"
LOG_FILE="$POSTGRES_DIR/postgres.log"
DATABASE="${POSTGRES_DB:-lexiaide}"
DATABASE_USER="${POSTGRES_USER:-lexiaide}"
PG_PORT="${PG_PORT:-55432}"
PG_STARTED=0

mkdir -p "$POSTGRES_DIR" "$SOCKET_DIR"

if [[ ! -s "$DATA_DIR/PG_VERSION" ]]; then
    initdb -D "$DATA_DIR" \
        --username="$DATABASE_USER" \
        --auth-local=trust \
        --auth-host=trust
fi

if ! pg_ctl -D "$DATA_DIR" status >/dev/null 2>&1; then
    pg_ctl -D "$DATA_DIR" \
        -o "-k $SOCKET_DIR -h 127.0.0.1 -p $PG_PORT" \
        -l "$LOG_FILE" \
        -w start
    PG_STARTED=1
fi

cleanup() {
    if [[ "$PG_STARTED" -eq 1 ]]; then
        pg_ctl -D "$DATA_DIR" -m fast -w stop
    fi
}
trap cleanup EXIT INT TERM

if ! psql -h 127.0.0.1 -p "$PG_PORT" -U "$DATABASE_USER" -d "$DATABASE" -Atqc 'SELECT 1' >/dev/null 2>&1; then
    createdb -h 127.0.0.1 -p "$PG_PORT" -U "$DATABASE_USER" "$DATABASE"
fi

export DB_URL="${DB_URL:-jdbc:postgresql://127.0.0.1:${PG_PORT}/${DATABASE}}"
export DB_USERNAME="${DB_USERNAME:-$DATABASE_USER}"
export DB_PASSWORD="${DB_PASSWORD:-${POSTGRES_PASSWORD:-lexiaide_dev}}"
export PORT="${PORT:-5000}"

cd "$ROOT_DIR"
./mvnw --batch-mode --no-transfer-progress -Dspring-boot.run.profiles=dev spring-boot:run
