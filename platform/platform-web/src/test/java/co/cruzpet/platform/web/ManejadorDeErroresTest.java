package co.cruzpet.platform.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class ManejadorDeErroresTest {

    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ControladorDePrueba())
                .setControllerAdvice(new ManejadorDeErrores())
                .build();
    }

    @Test
    void recursoNoEncontradoResponde404ConProblemJson() throws Exception {
        mockMvc.perform(get("/no-existe"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Recurso no encontrado"))
                .andExpect(jsonPath("$.detail").value("No existe especie con identificador 'dragon'"));
    }

    @Test
    void reglaDeNegocioResponde422ConCodigo() throws Exception {
        mockMvc.perform(get("/regla"))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.codigo").value("afiliacion-suspendida"))
                .andExpect(jsonPath("$.type").value("https://cruzpet.co/errores/afiliacion-suspendida"));
    }

    @RestController
    static class ControladorDePrueba {

        @GetMapping("/no-existe")
        void noExiste() {
            throw new RecursoNoEncontradoException("especie", "dragon");
        }

        @GetMapping("/regla")
        void regla() {
            throw new ReglaDeNegocioException("afiliacion-suspendida", "La afiliación está suspendida");
        }
    }
}
