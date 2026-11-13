package co.cruzpet.platform.web;

/**
 * La operación es válida en forma pero viola una regla del negocio
 * (por ejemplo, agendar con la afiliación suspendida). Se traduce a HTTP 422.
 */
public class ReglaDeNegocioException extends RuntimeException {

    private final String codigo;

    public ReglaDeNegocioException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String codigo() {
        return codigo;
    }
}
