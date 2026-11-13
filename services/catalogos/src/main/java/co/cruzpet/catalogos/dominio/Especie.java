package co.cruzpet.catalogos.dominio;

/**
 * Especie animal que CruzPet puede afiliar.
 *
 * @param codigo identificador legible y estable, usado en las URL ({@code perro}, {@code gato})
 * @param nombre nombre para mostrar
 */
public record Especie(String codigo, String nombre) {
}
