package co.cruzpet.catalogos.infraestructura.persistencia;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.cruzpet.catalogos.dominio.CatalogoRepositorio;
import co.cruzpet.catalogos.dominio.Especie;
import co.cruzpet.catalogos.dominio.Raza;
import co.cruzpet.catalogos.dominio.Vacuna;

/**
 * Adaptador de persistencia: implementa el puerto del dominio con Spring Data JPA.
 */
@Repository
class CatalogoRepositorioJpa implements CatalogoRepositorio {

    private final EspeciesJpa especies;
    private final RazasJpa razas;
    private final VacunasJpa vacunas;

    CatalogoRepositorioJpa(EspeciesJpa especies, RazasJpa razas, VacunasJpa vacunas) {
        this.especies = especies;
        this.razas = razas;
        this.vacunas = vacunas;
    }

    @Override
    public List<Especie> especies() {
        return especies.findAllByOrderByOrdenAsc().stream().map(EspecieEntidad::aDominio).toList();
    }

    @Override
    public Optional<Especie> especie(String codigo) {
        return especies.findById(codigo).map(EspecieEntidad::aDominio);
    }

    @Override
    public List<Raza> razasDe(String codigoEspecie) {
        return razas.findByCodigoEspecieOrderByNombreAsc(codigoEspecie).stream().map(RazaEntidad::aDominio).toList();
    }

    @Override
    public List<Vacuna> vacunasDe(String codigoEspecie) {
        return vacunas.findByCodigoEspecieOrderByNombreAsc(codigoEspecie).stream().map(VacunaEntidad::aDominio).toList();
    }
}
