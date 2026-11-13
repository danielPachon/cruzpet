package co.cruzpet.platform.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuración de seguridad por servicio.
 *
 * @param rutasPublicas patrones de ruta que no exigen token, por ejemplo la ficha pública de una mascota
 */
@ConfigurationProperties("cruzpet.seguridad")
public record PropiedadesDeSeguridad(List<String> rutasPublicas) {

    public PropiedadesDeSeguridad {
        rutasPublicas = rutasPublicas == null ? List.of() : List.copyOf(rutasPublicas);
    }
}
