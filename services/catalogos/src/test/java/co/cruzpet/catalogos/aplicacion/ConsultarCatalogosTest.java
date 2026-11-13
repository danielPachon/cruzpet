package co.cruzpet.catalogos.aplicacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import co.cruzpet.catalogos.dominio.CatalogoRepositorio;
import co.cruzpet.catalogos.dominio.Especie;
import co.cruzpet.catalogos.dominio.Raza;
import co.cruzpet.catalogos.dominio.Vacuna;
import co.cruzpet.platform.web.RecursoNoEncontradoException;

class ConsultarCatalogosTest {

    private final ConsultarCatalogos consultar = new ConsultarCatalogos(new CatalogoEnMemoria());

    @Test
    void devuelveLasRazasDeLaEspeciePedida() {
        assertThat(consultar.razasDe("gato")).extracting(Raza::nombre).containsExactly("Siamés");
    }

    @Test
    void unaEspecieInexistenteLanzaRecursoNoEncontrado() {
        assertThatThrownBy(() -> consultar.razasDe("dragon"))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("dragon");
    }

    @Test
    void lasVacunasTambienValidanLaEspecie() {
        assertThatThrownBy(() -> consultar.vacunasDe("dragon")).isInstanceOf(RecursoNoEncontradoException.class);
    }

    private static final class CatalogoEnMemoria implements CatalogoRepositorio {

        private final List<Especie> especies = List.of(new Especie("perro", "Perro"), new Especie("gato", "Gato"));
        private final List<Raza> razas = List.of(new Raza(1, "perro", "Beagle"), new Raza(2, "gato", "Siamés"));

        @Override
        public List<Especie> especies() {
            return especies;
        }

        @Override
        public Optional<Especie> especie(String codigo) {
            return especies.stream().filter(e -> e.codigo().equals(codigo)).findFirst();
        }

        @Override
        public List<Raza> razasDe(String codigoEspecie) {
            return razas.stream().filter(r -> r.codigoEspecie().equals(codigoEspecie)).toList();
        }

        @Override
        public List<Vacuna> vacunasDe(String codigoEspecie) {
            return List.of();
        }
    }
}
