# ADR 0001 · Microservicios por dominio de negocio

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

CruzPet combina dominios con cargas y ritmos de cambio muy distintos: afiliación y planes cambian poco y reciben poco tráfico; el rastreo GPS recibe telemetría continua; la tienda tiene picos por promociones. El proyecto también busca demostrar una arquitectura distribuida en un portafolio.

## Decisión

Separar el sistema en microservicios alineados con los bounded contexts del dominio (ver `02-arquitectura.md`, sección 4). Cada servicio:

- es dueño de su base de datos y nadie más la lee;
- expone un contrato OpenAPI versionado;
- publica eventos de dominio en Kafka mediante el patrón outbox;
- se despliega y escala de forma independiente.

Para no fragmentar de más, un dominio nuevo empieza como **un solo servicio modular** (Spring Modulith) y se divide solo cuando un módulo tiene una razón real: carga distinta, equipo distinto o ritmo de despliegue distinto.

## Consecuencias

- ✅ Escalado y despliegue independientes; un fallo en la tienda no afecta las citas.
- ✅ Límites de dominio explícitos y fáciles de explicar en una entrevista.
- ⚠️ Más infraestructura (Kafka, gateway, observabilidad) y consistencia eventual entre servicios.
- ⚠️ Las consultas que cruzan dominios necesitan composición en el gateway o vistas alimentadas por eventos.

## Alternativas consideradas

- **Monolito modular único:** más simple, pero no permite escalar el rastreo por separado ni cumple el objetivo de portafolio.
- **Un servicio por entidad** (como el proyecto original de 2022, con 33 controladores): genera acoplamiento y llamadas en cadena. Descartado.
