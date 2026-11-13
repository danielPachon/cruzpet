package co.cruzpet.catalogos.aplicacion;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.cruzpet.catalogos.dominio.CatalogoRepositorio;
import co.cruzpet.catalogos.dominio.Especie;
import co.cruzpet.catalogos.dominio.Raza;
import co.cruzpet.catalogos.dominio.Vacuna;
import co.cruzpet.platform.web.RecursoNoEncontradoException;

/**
 * Caso de uso de lectura de catálogos.
 */
@Service
@Transactional(readOnly = true)
public class ConsultarCatalogos {

    private final CatalogoRepositorio repositorio;

    public ConsultarCatalogos(CatalogoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Especie> especies() {
        return repositorio.especies();
    }

    public Especie especie(String codigo) {
        return repositorio.especie(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("especie", codigo));
    }

    public List<Raza> razasDe(String codigoEspecie) {
        return repositorio.razasDe(especie(codigoEspecie).codigo());
    }

    public List<Vacuna> vacunasDe(String codigoEspecie) {
        return repositorio.vacunasDe(especie(codigoEspecie).codigo());
    }
}
