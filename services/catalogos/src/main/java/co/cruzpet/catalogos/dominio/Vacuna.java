package co.cruzpet.catalogos.dominio;

/**
 * Vacuna del esquema de una especie.
 *
 * @param intervaloRefuerzoMeses meses entre refuerzos; el carnet lo usa para calcular la próxima dosis
 */
public record Vacuna(long id, String codigoEspecie, String nombre, String previene, int intervaloRefuerzoMeses) {
}
