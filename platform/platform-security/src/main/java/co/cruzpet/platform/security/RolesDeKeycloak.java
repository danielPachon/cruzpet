package co.cruzpet.platform.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Convierte los roles del realm de Keycloak ({@code realm_access.roles}) en autoridades
 * de Spring Security con prefijo {@code ROLE_}, para poder usar {@code hasRole("TITULAR")}.
 */
public class RolesDeKeycloak implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String CLAIM_ACCESO_REALM = "realm_access";
    private static final String CLAVE_ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> accesoRealm = jwt.getClaimAsMap(CLAIM_ACCESO_REALM);
        if (accesoRealm == null || !(accesoRealm.get(CLAVE_ROLES) instanceof Collection<?> roles)) {
            return List.of();
        }
        return roles.stream()
                .map(String::valueOf)
                .map(rol -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + rol))
                .toList();
    }
}
