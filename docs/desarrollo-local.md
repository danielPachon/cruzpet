# Desarrollo local

## Requisitos

| Herramienta | Versión | Para qué |
|---|---|---|
| JDK | 25 (Temurin recomendado) | Compilar y ejecutar los servicios |
| Docker Desktop | reciente | PostgreSQL, Keycloak, Kafka, MinIO y pruebas con Testcontainers |
| Git | reciente | Control de versiones |

Si el JDK 25 no está instalado, Gradle lo descarga solo la primera vez (toolchains).

## 1. Levantar la infraestructura

```bash
cp infra/docker/.env.example infra/docker/.env
docker compose -f infra/docker/compose.yaml --profile core up -d
```

| Servicio | URL | Usuario |
|---|---|---|
| Keycloak | http://localhost:8080 | `admin` / `admin` |
| PostgreSQL | `localhost:5432` | `cruzpet` / `cruzpet` |
| Kafka | `localhost:9092` | — |
| MinIO | http://localhost:9001 | `cruzpet` / `cruzpet-local` |

El realm `cruzpet` se importa solo desde `infra/keycloak/realm-cruzpet.json`, con los ocho roles y tres usuarios de prueba (contraseña `cruzpet-demo`): `titular.demo@cruzpet.co`, `veterinario.demo@cruzpet.co` y `admin.demo@cruzpet.co`.

## 2. Compilar y probar

```bash
./gradlew build          # Linux / macOS / Git Bash
gradlew.bat build        # Windows (PowerShell o CMD)
```

Las pruebas de integración levantan su propio PostgreSQL con Testcontainers, así que Docker debe estar encendido.

## 3. Ejecutar un servicio

```bash
./gradlew :services:catalogos:bootRun
```

- API: http://localhost:8101/api/v1/especies
- Documentación OpenAPI: http://localhost:8101/swagger-ui.html
- Salud: http://localhost:8101/actuator/health

## 4. Probar la API con un token

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/realms/cruzpet/protocol/openid-connect/token \
  -d grant_type=password -d client_id=cruzpet-dev-cli \
  -d username=titular.demo@cruzpet.co -d password=cruzpet-demo | jq -r .access_token)

curl -H "Authorization: Bearer $TOKEN" http://localhost:8101/api/v1/especies/gato/razas
```

El cliente `cruzpet-dev-cli` usa *password grant* y existe **solo para desarrollo local**. Los portales usan Authorization Code + PKCE con el cliente `cruzpet-shell`.

## Estructura del backend

```text
build-logic/            plugins de convención de Gradle (Java 25, BOM de Spring, pruebas)
platform/
  platform-web/         errores problem+json (RFC 9457)
  platform-security/    servidor de recursos JWT con roles de Keycloak
  platform-events/      sobre común de eventos de dominio
services/
  catalogos/            especies, razas y vacunas (plantilla para los demás servicios)
infra/
  docker/               Docker Compose por perfiles
  keycloak/             realm versionado
```
