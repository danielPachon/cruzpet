# ADR 0005 · Monorepo con Gradle y pnpm + Turborepo

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

El proyecto mezcla servicios Java, un servicio Python y varios frontends TypeScript. Lo mantiene una sola persona y debe leerse fácil en un portafolio.

## Decisión

- **Un solo repositorio** (`danielPachon/cruzpet`) con la estructura `apps/`, `services/`, `packages/`, `platform/`, `infra/`, `tools/` y `docs/`.
- **Gradle** (Kotlin DSL, *version catalog*) para los servicios Java y las librerías de `platform/`.
- **pnpm workspaces + Turborepo** para frontends y paquetes TS, con caché de tareas.
- El servicio de IA usa **uv** para dependencias de Python.
- Cada servicio y microfrontend tiene su propio workflow de CI filtrado por ruta, para que el monorepo no obligue a desplegar todo junto.
- Commits con **Conventional Commits** y versionado por componente.

## Consecuencias

- ✅ Un cambio que toca contrato, servicio y frontend va en un solo pull request.
- ✅ Un reclutador ve todo el sistema en un lugar.
- ⚠️ Dos sistemas de build (Gradle y Turborepo); se unifican detrás de un `Makefile` con comandos comunes.

## Alternativas consideradas

- **Un repositorio por servicio:** más realista para equipos grandes, pero difícil de mantener y de mostrar para una persona.
- **Nx para todo:** soporta varios lenguajes, pero su integración con Gradle añade complejidad sin beneficio claro aquí.
