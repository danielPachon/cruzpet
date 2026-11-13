package co.cruzpet.platform.web;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Traduce las excepciones de dominio a respuestas {@code application/problem+json} (RFC 9457).
 * Los errores propios de Spring MVC (validación, método no permitido, etc.) los resuelve la clase base.
 */
@RestControllerAdvice
public class ManejadorDeErrores extends ResponseEntityExceptionHandler {

    private static final String BASE_TIPOS = "https://cruzpet.co/errores/";

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail recursoNoEncontrado(RecursoNoEncontradoException ex) {
        var problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Recurso no encontrado");
        problema.setType(URI.create(BASE_TIPOS + "recurso-no-encontrado"));
        return problema;
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ProblemDetail reglaDeNegocio(ReglaDeNegocioException ex) {
        var problema = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
        problema.setTitle("Regla de negocio no cumplida");
        problema.setType(URI.create(BASE_TIPOS + ex.codigo()));
        problema.setProperty("codigo", ex.codigo());
        return problema;
    }
}
