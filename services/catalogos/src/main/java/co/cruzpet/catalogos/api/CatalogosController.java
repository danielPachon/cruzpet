package co.cruzpet.catalogos.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.cruzpet.catalogos.aplicacion.ConsultarCatalogos;
import co.cruzpet.catalogos.dominio.Especie;
import co.cruzpet.catalogos.dominio.Raza;
import co.cruzpet.catalogos.dominio.Vacuna;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/especies")
@Tag(name = "Catálogos", description = "Especies, razas y vacunas")
class CatalogosController {

    private final ConsultarCatalogos consultar;

    CatalogosController(ConsultarCatalogos consultar) {
        this.consultar = consultar;
    }

    @GetMapping
    @Operation(summary = "Lista las especies que se pueden afiliar")
    List<EspecieRespuesta> especies() {
        return consultar.especies().stream().map(EspecieRespuesta::de).toList();
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Detalle de una especie")
    EspecieRespuesta especie(@PathVariable String codigo) {
        return EspecieRespuesta.de(consultar.especie(codigo));
    }

    @GetMapping("/{codigo}/razas")
    @Operation(summary = "Razas de una especie, en orden alfabético")
    List<RazaRespuesta> razas(@PathVariable String codigo) {
        return consultar.razasDe(codigo).stream().map(RazaRespuesta::de).toList();
    }

    @GetMapping("/{codigo}/vacunas")
    @Operation(summary = "Vacunas del esquema de una especie")
    List<VacunaRespuesta> vacunas(@PathVariable String codigo) {
        return consultar.vacunasDe(codigo).stream().map(VacunaRespuesta::de).toList();
    }

    record EspecieRespuesta(String codigo, String nombre) {
        static EspecieRespuesta de(Especie especie) {
            return new EspecieRespuesta(especie.codigo(), especie.nombre());
        }
    }

    record RazaRespuesta(long id, String nombre) {
        static RazaRespuesta de(Raza raza) {
            return new RazaRespuesta(raza.id(), raza.nombre());
        }
    }

    record VacunaRespuesta(long id, String nombre, String previene, int intervaloRefuerzoMeses) {
        static VacunaRespuesta de(Vacuna vacuna) {
            return new VacunaRespuesta(vacuna.id(), vacuna.nombre(), vacuna.previene(), vacuna.intervaloRefuerzoMeses());
        }
    }
}
