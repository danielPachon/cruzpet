package co.cruzpet.platform.web;

/**
 * El recurso pedido no existe. Se traduce a HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Object identificador) {
        super("No existe %s con identificador '%s'".formatted(recurso, identificador));
    }
}
