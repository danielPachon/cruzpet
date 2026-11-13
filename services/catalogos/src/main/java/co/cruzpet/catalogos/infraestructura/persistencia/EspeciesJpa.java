package co.cruzpet.catalogos.infraestructura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface EspeciesJpa extends JpaRepository<EspecieEntidad, String> {

    List<EspecieEntidad> findAllByOrderByOrdenAsc();
}
