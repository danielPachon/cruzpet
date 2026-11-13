package co.cruzpet.platform.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class RolesDeKeycloakTest {

    private final RolesDeKeycloak conversor = new RolesDeKeycloak();

    @Test
    void convierteLosRolesDelRealmEnAutoridadesConPrefijo() {
        var jwt = jwtCon(Map.of("realm_access", Map.of("roles", List.of("TITULAR", "VETERINARIO"))));

        var autoridades = conversor.convert(jwt).stream().map(GrantedAuthority::getAuthority).toList();

        assertThat(autoridades).containsExactlyInAnyOrder("ROLE_TITULAR", "ROLE_VETERINARIO");
    }

    @Test
    void sinRealmAccessNoHayAutoridades() {
        assertThat(conversor.convert(jwtCon(Map.of("sub", "123")))).isEmpty();
    }

    @Test
    void ignoraUnClaimDeRolesMalFormado() {
        var jwt = jwtCon(Map.of("realm_access", Map.of("roles", "TITULAR")));

        assertThat(conversor.convert(jwt)).isEmpty();
    }

    private static Jwt jwtCon(Map<String, Object> claims) {
        var constructor = Jwt.withTokenValue("token").header("alg", "none");
        claims.forEach(constructor::claim);
        return constructor.build();
    }
}
