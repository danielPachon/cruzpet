# ADR 0002 · Microfrontends con Module Federation 2.0 y Rsbuild

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

CruzPet tiene usuarios con necesidades muy distintas (titulares, IPSA, prestadores, personal EPSA). Cada portal crece a su ritmo y debe poder desplegarse sin tocar los demás.

## Decisión

- Un **shell** (host) en React 19 + TypeScript resuelve sesión, navegación y layout, y carga los remotos según el rol.
- Un **microfrontend remoto por dominio**: `mf-titular`, `mf-ipsa`, `mf-epsa`, `mf-marketplace`, `mf-tienda`, `mf-rastreo`.
- Composición en tiempo de ejecución con **Module Federation 2.0** (estable desde abril de 2026) y **Rsbuild/Rspack** como bundler. React, React DOM y el design system se comparten como *singleton*.
- Comunicación entre microfrontends solo por **eventos del navegador** (`event-bus`) y por la URL; no hay estado global compartido.
- El **sitio público** va aparte en Next.js porque necesita SEO y renderizado en servidor.

## Consecuencias

- ✅ Despliegue independiente por portal; tipos de TypeScript compartidos entre remotos gracias a MF 2.0.
- ✅ Una falla en un remoto se aísla con un *error boundary* y no tumba el shell.
- ⚠️ Hay que gobernar versiones de dependencias compartidas y el tamaño de los bundles.
- ⚠️ La consistencia visual depende de que todos usen el mismo design system.

## Alternativas consideradas

- **single-spa:** permite mezclar frameworks, algo que no necesitamos; MF tiene mejor soporte de tipos.
- **@module-federation/vite:** viable, pero la integración con Rsbuild es más madura para compilaciones de producción.
- **Un solo SPA con rutas por rol:** más simple, pero no demuestra despliegue independiente.
