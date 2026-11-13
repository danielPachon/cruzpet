package co.cruzpet.platform.security;

import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Seguridad por defecto de todo microservicio: API sin estado protegida con JWT emitidos por Keycloak.
 * Un servicio puede reemplazarla declarando su propio {@link SecurityFilterChain}.
 */
@AutoConfiguration
@EnableConfigurationProperties(PropiedadesDeSeguridad.class)
public class SeguridadAutoConfiguration {

    private static final String[] RUTAS_TECNICAS = {
        "/actuator/health/**", "/actuator/info", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Bean
    @ConditionalOnMissingBean
    JwtAuthenticationConverter conversorDeAutenticacion() {
        var conversor = new JwtAuthenticationConverter();
        conversor.setJwtGrantedAuthoritiesConverter(new RolesDeKeycloak());
        conversor.setPrincipalClaimName("preferred_username");
        return conversor;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    SecurityFilterChain cadenaDeSeguridad(HttpSecurity http, PropiedadesDeSeguridad propiedades,
            JwtAuthenticationConverter conversor) throws Exception {
        String[] rutasPublicas = Stream.concat(Stream.of(RUTAS_TECNICAS), propiedades.rutasPublicas().stream())
                .toArray(String[]::new);

        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(peticiones -> peticiones
                        .requestMatchers(rutasPublicas).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(servidor -> servidor
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(conversor)))
                .build();
    }
}
