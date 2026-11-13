package co.cruzpet.catalogos.infraestructura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface VacunasJpa extends JpaRepository<VacunaEntidad, Long> {

    List<VacunaEntidad> findByCodigoEspecieOrderByNombreAsc(String codigoEspecie);
}
