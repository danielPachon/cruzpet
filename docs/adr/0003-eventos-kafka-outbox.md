# ADR 0003 · Eventos de dominio con Kafka y patrón outbox

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

Los procesos de negocio cruzan servicios: una autorización aprobada habilita una cita, que genera una atención, que genera una cuenta médica. Encadenar llamadas REST para esto acopla los servicios y propaga las caídas.

## Decisión

- Cada cambio de estado relevante se publica como **evento de dominio** en Apache Kafka (modo KRaft, sin ZooKeeper).
- Para que la escritura en base de datos y la publicación del evento no se desincronicen, se usa el **patrón outbox**: el evento se guarda en la misma transacción y un proceso lo publica después (Spring Modulith *event publication registry*).
- Los procesos largos se modelan como **sagas por coreografía**, con eventos de compensación cuando un paso falla.
- Los eventos llevan esquema versionado (JSON Schema en `docs/eventos/`) y un `traceId` para observabilidad.
- Los consumidores son **idempotentes**: procesar dos veces el mismo evento no cambia el resultado.

## Consecuencias

- ✅ Servicios desacoplados en el tiempo; si notificaciones cae, los eventos esperan en Kafka.
- ✅ Los eventos alimentan analítica e IA sin tocar las bases de datos operativas.
- ⚠️ Consistencia eventual: la interfaz debe mostrar estados intermedios ("autorización en revisión").
- ⚠️ Kafka añade operación; en local corre como un solo nodo.

## Alternativas consideradas

- **RabbitMQ:** más simple, pero sin retención ni reprocesamiento de eventos, que se necesitan para analítica.
- **Orquestador de sagas (Temporal):** potente, pero excesivo para la etapa actual. Se reevaluará si las sagas crecen.
