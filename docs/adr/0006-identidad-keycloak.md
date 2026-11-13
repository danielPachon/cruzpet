# ADR 0006 · Identidad y acceso con Keycloak

- **Estado:** aceptada
- **Fecha:** 2026-11-12

## Contexto

Hay ocho roles distintos, login social, varias aplicaciones cliente (sitio, shell, app móvil) y datos sensibles como la historia clínica. El proyecto de 2022 implementaba JWT a mano dentro de la API, junto con librerías de login social que ya no se mantienen.

## Decisión

- **Keycloak** como proveedor de identidad OIDC, con un *realm* `cruzpet`.
- Roles: `TITULAR`, `IPSA_ADMIN`, `VETERINARIO`, `PRESTADOR`, `VENDEDOR`, `AUDITOR`, `ASESOR`, `ADMIN_EPSA`.
- Clientes públicos con *Authorization Code + PKCE* para el shell, el sitio y la app; clientes confidenciales para la comunicación entre servicios.
- Login social con Google.
- El gateway y cada servicio validan el token (Spring Security OAuth2 Resource Server).
- Los permisos finos (qué IPSA puede ver qué historia clínica) se resuelven en el servicio dueño del dato según consentimientos y citas, no en Keycloak.

## Consecuencias

- ✅ Ningún servicio guarda contraseñas; MFA, recuperación de cuenta y sesiones vienen resueltos.
- ✅ El realm se versiona como JSON en `infra/keycloak/` y se importa al levantar el entorno.
- ⚠️ Keycloak consume memoria; en local corre en modo desarrollo.

## Alternativas consideradas

- **Spring Authorization Server propio:** más control, más código que mantener.
- **Auth0 / Clerk:** rápidos de integrar, pero son servicios pagos externos y ocultan la parte que interesa mostrar.
