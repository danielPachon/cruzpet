package co.cruzpet.catalogos.infraestructura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface RazasJpa extends JpaRepository<RazaEntidad, Long> {

    List<RazaEntidad> findByCodigoEspecieOrderByNombreAsc(String codigoEspecie);
}
