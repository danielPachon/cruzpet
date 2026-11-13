package co.cruzpet.platform.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

class SobreDeEventoTest {

    private final Clock reloj = Clock.fixed(Instant.parse("2026-12-01T15:00:00Z"), ZoneOffset.UTC);

    @Test
    void creaElSobreConIdentificadorYFechaDelReloj() {
        var sobre = SobreDeEvento.de("CitaAgendada", 1, "datos", "trace-123", reloj);

        assertThat(sobre.id()).isNotNull();
        assertThat(sobre.ocurridoEn()).isEqualTo(Instant.parse("2026-12-01T15:00:00Z"));
        assertThat(sobre.tipo()).isEqualTo("CitaAgendada");
        assertThat(sobre.traceId()).isEqualTo("trace-123");
    }

    @Test
    void rechazaUnTipoVacio() {
        assertThatThrownBy(() -> SobreDeEvento.de(" ", 1, "datos", null, reloj))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rechazaUnaVersionMenorAUno() {
        assertThatThrownBy(() -> SobreDeEvento.de("CitaAgendada", 0, "datos", null, reloj))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
