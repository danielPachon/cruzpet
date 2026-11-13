# 01 · Visión y alcance

> Estado: **borrador v0.1** · Fase del ciclo de vida: Concepción · Última revisión: 2026-11-12

## 1. Problema

En Colombia, la salud de una mascota se paga de bolsillo y por evento. Una cirugía de urgencia puede costar varios salarios mínimos y llega sin aviso. Los seguros para mascotas que existen cubren reembolsos, no una red de atención, y el dueño termina coordinando por su cuenta veterinario, laboratorio, vacunas, paseador, cuidador y tienda, cada uno en un canal distinto y sin una historia clínica que los conecte.

## 2. Visión

**CruzPet replica el modelo de EPS de las personas para los animales.** El dueño afilia a su mascota a un plan, paga una cuota mensual y recibe atención en una red de clínicas en convenio, con autorizaciones, copagos e historia clínica única. Sobre esa base de salud, CruzPet reúne todo lo que un dueño necesita: cuidadores y paseadores verificados, una tienda de productos, una cédula de identidad animal y rastreo GPS desde la web o la app.

Frase de producto: *la salud y la vida de tu mascota en un solo lugar.*

## 3. Glosario del dominio

| Término | Significado | Equivalente en salud humana |
|---|---|---|
| **EPSA** | Entidad Promotora de Salud Animal. Afilia, recauda, define planes y contrata la red. CruzPet es la EPSA | EPS |
| **IPSA** | Institución Prestadora de Servicios de Salud Animal: clínica, hospital veterinario, laboratorio, centro de imagen | IPS |
| **Afiliado titular** | Persona dueña que firma el contrato y paga la cuota | Cotizante |
| **Beneficiario** | Cada mascota afiliada bajo un titular | Beneficiario |
| **PBA** | Plan de Beneficios Animal: lo que cubre cada plan | PBS / POS |
| **Cuota moderadora** | Valor fijo por servicio de bajo costo (consulta general) | Cuota moderadora |
| **Copago** | Porcentaje del costo que paga el titular en servicios de mayor costo | Copago |
| **Periodo de carencia** | Tiempo desde la afiliación antes de poder usar ciertos servicios | Semanas mínimas de cotización |
| **Autorización** | Aprobación previa de la EPSA para un procedimiento | Autorización de servicios |
| **Referencia / contrarreferencia** | Remisión de una IPSA a otra de mayor complejidad, y el retorno | Igual |
| **RIPSA** | Registro Individual de Prestación de Servicios Animales: lo que la IPSA reporta por cada atención | RIPS |
| **Cédula animal** | Documento digital de identidad de la mascota con número único, foto, QR y microchip asociado | Cédula / registro civil |

## 4. Actores

| Actor | Qué hace en la plataforma |
|---|---|
| **Titular (dueño)** | Se afilia, registra mascotas, paga, agenda citas, ve historia clínica y carnet, contrata cuidadores, compra en la tienda, rastrea a su mascota |
| **IPSA (clínica)** | Gestiona sedes, servicios y agenda; atiende; registra historia clínica, fórmulas y vacunas; pide autorizaciones; reporta RIPSA |
| **Veterinario** | Médico general o especialista vinculado a una o varias IPSA. Atiende consultas y teleconsultas |
| **Prestador de cuidado** | Paseador, cuidador a domicilio (pet sitter), guardería u hotel, peluquería (groomer), adiestrador, transporte de mascotas, servicio funerario. Publica servicios, recibe reservas y reseñas |
| **Vendedor** | CruzPet o tiendas aliadas que publican productos en la tienda |
| **Auditor médico (EPSA)** | Revisa y decide autorizaciones; audita atenciones y cuentas médicas de las IPSA |
| **Asesor comercial (EPSA)** | Vende planes, gestiona retención y recuperación de afiliados |
| **Administrador (EPSA)** | Configura planes, coberturas, tarifas, red de prestadores y catálogos |

## 5. Módulos del producto

### 5.1 Salud (núcleo EPSA/IPSA)
1. **Afiliación:** registro del titular con verificación de identidad, registro de mascotas (especie, raza, sexo, fecha de nacimiento, esterilización), selección de plan, declaración de preexistencias, contrato digital.
2. **Planes y coberturas (PBA):** planes por niveles, reglas de cobertura por servicio, cuotas moderadoras, copagos, topes anuales, carencias y exclusiones.
3. **Red de prestadores:** IPSA con sedes, nivel de complejidad, servicios habilitados, tarifas en convenio y calificación.
4. **Agenda y citas:** disponibilidad por veterinario y sede, citas presenciales, a domicilio y teleconsulta, recordatorios.
5. **Historia clínica única:** consultas, anamnesis, diagnósticos, procedimientos, exámenes, fórmulas y adjuntos. La consulta cualquier IPSA de la red con permiso del titular.
6. **Carnet de vacunación y desparasitación:** esquema por especie, próximas dosis y alertas.
7. **Autorizaciones:** solicitud desde la IPSA, reglas automáticas por plan y revisión del auditor cuando aplica.
8. **Facturación y recaudo:** cuotas mensuales, pagos en línea, mora, suspensión y reactivación; cuentas médicas IPSA → EPSA.
9. **Urgencias:** IPSA de urgencias más cercanas abiertas ahora, y atención sin autorización previa.

### 5.2 Marketplace de cuidado
Perfiles verificados de prestadores de cuidado, búsqueda por zona y servicio, reservas, pagos, reseñas y beneficios por plan (por ejemplo, 2 paseos al mes incluidos en el plan Premium).

### 5.3 Tienda
Catálogo de alimento, medicamentos de venta libre, accesorios, collares GPS y planes. Carrito, órdenes, inventario y envíos. Recomendaciones según especie, raza y edad de las mascotas del titular, y descuentos por plan.

### 5.4 Identidad animal (cédula)
- Número único CruzPet por mascota, con foto, datos del titular y QR verificable.
- Vínculo con el **microchip de identificación** (estándar ISO 11784/11785, 15 dígitos) que implanta una IPSA de la red.
- Consulta pública limitada: quien encuentra una mascota perdida escanea el QR o lee el chip y puede contactar al titular sin ver sus datos personales.
- Registro de traspasos (cambio de dueño) y de fallecimiento.

### 5.5 Rastreo
- Ubicación en tiempo real en un mapa (web y app), historial de recorridos y geocercas ("avísame si sale de la casa").
- **Modo perdido:** alerta a la red, a prestadores y a usuarios cercanos, y activa la ficha pública de la cédula.
- Integración con dispositivos GPS por API y un **simulador de dispositivos** para desarrollo y demo.

> **Aclaración técnica importante.** El microchip que se implanta bajo la piel es un chip RFID pasivo: no tiene batería ni GPS, y solo lo lee un lector a pocos centímetros. Sirve para **identificar**, no para **rastrear**. El rastreo en vivo necesita un dispositivo con GPS y conectividad (collar o placa con LTE-M, NB-IoT o LoRaWAN). Por eso CruzPet maneja dos productos: microchip para la cédula y collar GPS para el rastreo. Ambos se vinculan a la misma mascota.

### 5.6 Analítica e IA
- Tableros para la EPSA: afiliados, recaudo, siniestralidad (costo médico / ingresos), uso de la red, retención.
- Modelos: predicción de cancelación de afiliados, detección de anomalías en cuentas médicas, y un asistente de triaje que orienta al dueño (urgencia, cita prioritaria o cita normal) **sin reemplazar al veterinario**.

## 6. Alcance por entregas (roadmap)

| Entrega | Contenido | Objetivo |
|---|---|---|
| **R0 · Fundaciones** | Monorepo, CI/CD, infraestructura local, identidad y roles, API gateway, shell de microfrontends, design system | Esqueleto que corre de punta a punta |
| **R1 · MVP Salud** | Afiliación, planes, red IPSA, agenda, historia clínica, carnet de vacunación, cédula animal | Una mascota se afilia, agenda y es atendida |
| **R2 · Operación EPSA** | Autorizaciones, copagos, facturación y pagos (sandbox), notificaciones, urgencias | Ciclo económico completo |
| **R3 · Marketplace** | Prestadores de cuidado, reservas, reseñas, beneficios por plan | Ecosistema alrededor del dueño |
| **R4 · Tienda** | Catálogo, carrito, órdenes, inventario, búsqueda | Ingreso adicional |
| **R5 · Rastreo** | Telemetría, mapa en vivo, geocercas, modo perdido, simulador | Diferenciador |
| **R6 · Datos, IA y móvil** | Tableros, modelos, asistente de triaje, app móvil | Inteligencia y alcance |

**Fuera de alcance por ahora:** integración con lectores físicos de microchip, fabricación de hardware GPS, pagos reales (todo en sandbox) y validación legal formal de la cédula animal.

## 7. Supuestos y riesgos

| Tipo | Descripción | Mitigación |
|---|---|---|
| Supuesto | Los dueños pagarían una cuota mensual a cambio de red y descuentos | Validar con encuesta y landing de lista de espera |
| Riesgo | Siniestralidad alta por preexistencias o fraude | Carencias, exclusiones, auditoría y detección de anomalías |
| Riesgo | La cédula animal no es un documento oficial | Presentarla como registro privado CruzPet, interoperable si se crea un registro público |
| Riesgo | Complejidad de microservicios para un equipo pequeño | Entregas incrementales y servicios que empiezan grandes y se dividen solo cuando hace falta |
| Legal | Tratamiento de datos personales de los titulares (Ley 1581 de 2012) | Consentimiento, minimización y la ficha pública de mascota perdida sin datos personales |

## 8. Métricas de éxito (producto)

- Tiempo de afiliación menor a 5 minutos.
- 90 % de las autorizaciones resueltas automáticamente en menos de 1 minuto.
- Siniestralidad objetivo por debajo del 75 %.
- Mascota perdida con collar GPS ubicada en el mapa en menos de 10 segundos desde que se activa el modo perdido.

## 9. Próximos documentos

1. `docs/02-arquitectura.md`: arquitectura técnica y decisiones (ADR).
2. `docs/03-requisitos.md`: requisitos funcionales y no funcionales, historias de usuario de R1.
3. `docs/04-modelo-de-datos.md`: modelo de dominio por servicio.
4. `docs/05-plan-de-pruebas.md`.
