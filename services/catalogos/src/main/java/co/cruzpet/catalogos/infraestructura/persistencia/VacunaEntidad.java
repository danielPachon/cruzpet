package co.cruzpet.catalogos.infraestructura.persistencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import co.cruzpet.catalogos.dominio.Vacuna;

@Entity
@Table(name = "vacuna")
class VacunaEntidad {

    @Id
    private Long id;

    @Column(name = "codigo_especie", nullable = false)
    private String codigoEspecie;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String previene;

    @Column(name = "intervalo_refuerzo_meses", nullable = false)
    private int intervaloRefuerzoMeses;

    protected VacunaEntidad() {
    }

    Vacuna aDominio() {
        return new Vacuna(id, codigoEspecie, nombre, previene, intervaloRefuerzoMeses);
    }
}
