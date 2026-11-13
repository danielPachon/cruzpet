# ADR 0004 · Rastreo con MQTT, TimescaleDB + PostGIS y WebSocket

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

El rastreo necesita recibir posiciones de muchos collares GPS con conexiones intermitentes y poca batería, guardarlas como serie de tiempo, evaluar geocercas y mostrar la posición en vivo en el navegador.

El microchip subcutáneo (ISO 11784/11785) es RFID pasivo, sin batería ni GPS: **identifica pero no rastrea**. El rastreo depende de un collar o placa GPS con conectividad celular IoT (LTE-M / NB-IoT) o LoRaWAN.

## Decisión

- Los dispositivos publican por **MQTT** (protocolo liviano pensado para IoT) en un broker **EMQX**, en el tópico `cruzpet/dispositivos/{id}/posicion`.
- El servicio `rastreo` consume esos mensajes, guarda los puntos en **TimescaleDB** (series de tiempo sobre PostgreSQL) y evalúa geocercas con **PostGIS**.
- Las posiciones llegan al navegador por **WebSocket (STOMP)** a través del gateway.
- Al cruzar una geocerca o activar el modo perdido se publica un evento en Kafka para notificaciones.
- Un **simulador de collares** en `tools/simulador-gps` genera recorridos realistas para desarrollo, pruebas de carga y la demo del portafolio.

## Consecuencias

- ✅ El servicio de rastreo escala horizontalmente sin afectar al resto.
- ✅ Consultas espaciales y temporales eficientes (últimas 24 h, recorridos, distancia a la casa).
- ⚠️ Dos piezas nuevas de infraestructura (broker MQTT y extensión Timescale).
- ⚠️ La integración con hardware real queda para después: hoy el contrato es el mensaje MQTT, y cualquier dispositivo que lo cumpla funciona.

## Alternativas consideradas

- **HTTP polling desde el collar:** gasta más batería y datos.
- **Guardar posiciones en PostgreSQL normal:** funciona al inicio, pero degrada con millones de puntos.
