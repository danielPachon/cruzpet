package co.cruzpet.catalogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Servicio de catálogos: especies, razas y vacunas que usan los demás servicios y portales.
 */
@SpringBootApplication
public class CatalogosApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogosApplication.class, args);
    }
}
