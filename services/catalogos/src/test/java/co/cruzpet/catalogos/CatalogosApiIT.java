package co.cruzpet.catalogos;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

/**
 * Prueba de integración: servicio completo contra un PostgreSQL real con las migraciones de Flyway.
 */
@SpringBootTest(properties = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost/no-se-usa")
@AutoConfigureMockMvc
@Testcontainers
class CatalogosApiIT {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:17-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listaLasEspeciesEnElOrdenDefinido() throws Exception {
        mockMvc.perform(get("/api/v1/especies").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("perro"))
                .andExpect(jsonPath("$[1].codigo").value("gato"));
    }

    @Test
    void lasRazasDeGatoSoloIncluyenRazasDeGato() throws Exception {
        mockMvc.perform(get("/api/v1/especies/gato/razas").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nombre", hasItem("Siamés")))
                .andExpect(jsonPath("$[*].nombre", not(hasItem("Beagle"))));
    }

    @Test
    void lasVacunasTraenElIntervaloDeRefuerzo() throws Exception {
        mockMvc.perform(get("/api/v1/especies/perro/vacunas").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].intervaloRefuerzoMeses", everyItem(org.hamcrest.Matchers.greaterThan(0))))
                .andExpect(jsonPath("$[*].nombre", hasItem("Antirrábica")));
    }

    @Test
    void unaEspecieInexistenteRespondeProblemJson404() throws Exception {
        mockMvc.perform(get("/api/v1/especies/dragon").with(jwt()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Recurso no encontrado"));
    }

    @Test
    void sinTokenRespondeNoAutorizado() throws Exception {
        mockMvc.perform(get("/api/v1/especies"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void laSaludEsPublica() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }
}
