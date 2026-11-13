package co.cruzpet.platform.events;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Sobre común de todo evento de dominio que viaja por Kafka (ver ADR 0003).
 *
 * @param id         identificador único; los consumidores lo usan para ser idempotentes
 * @param tipo       nombre del evento en pasado, por ejemplo {@code CitaAgendada}
 * @param version    versión del esquema de {@code datos}
 * @param ocurridoEn momento en que ocurrió el hecho en el dominio
 * @param traceId    traza de OpenTelemetry que originó el evento, si existe
 * @param datos      contenido propio del evento
 */
public record SobreDeEvento<T>(UUID id, String tipo, int version, Instant ocurridoEn, String traceId, T datos) {

    public SobreDeEvento {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(ocurridoEn, "ocurridoEn");
        Objects.requireNonNull(datos, "datos");
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de evento es obligatorio");
        }
        if (version < 1) {
            throw new IllegalArgumentException("La versión del evento empieza en 1");
        }
    }

    public static <T> SobreDeEvento<T> de(String tipo, int version, T datos, String traceId, Clock reloj) {
        return new SobreDeEvento<>(UUID.randomUUID(), tipo, version, Instant.now(reloj), traceId, datos);
    }
}
