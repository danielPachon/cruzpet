# Cómo trabajar en CruzPet

## Ramas

- `main`: siempre desplegable. Solo entra código por pull request.
- `feat/<servicio-o-app>-<tema>`, `fix/...`, `docs/...`, `chore/...` para el trabajo diario.

## Commits

Se usa [Conventional Commits](https://www.conventionalcommits.org/es/) con el componente como *scope*:

```text
feat(agenda): permitir citas a domicilio
fix(mf-titular): corregir fecha del carnet en Safari
docs(adr): registrar decisión sobre geocercas
```

## Definición de terminado

Una historia de usuario está terminada cuando:

1. Cumple sus criterios de aceptación.
2. Tiene pruebas unitarias y, si cruza servicios, pruebas de contrato.
3. El contrato OpenAPI o el esquema del evento está actualizado.
4. El pipeline de CI pasa.
5. La documentación afectada en `docs/` está al día.

## Decisiones de arquitectura

Toda decisión que sea difícil de revertir se registra como ADR en `docs/adr/`, numerada y con contexto, decisión, consecuencias y alternativas.
