package co.cruzpet.catalogos.dominio;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: de dónde salen los catálogos. El dominio no sabe que hay una base de datos detrás.
 */
public interface CatalogoRepositorio {

    List<Especie> especies();

    Optional<Especie> especie(String codigo);

    List<Raza> razasDe(String codigoEspecie);

    List<Vacuna> vacunasDe(String codigoEspecie);
}
