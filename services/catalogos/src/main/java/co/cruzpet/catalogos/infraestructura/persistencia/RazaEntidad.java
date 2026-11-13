package co.cruzpet.catalogos.infraestructura.persistencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import co.cruzpet.catalogos.dominio.Raza;

@Entity
@Table(name = "raza")
class RazaEntidad {

    @Id
    private Long id;

    @Column(name = "codigo_especie", nullable = false)
    private String codigoEspecie;

    @Column(nullable = false)
    private String nombre;

    protected RazaEntidad() {
    }

    Raza aDominio() {
        return new Raza(id, codigoEspecie, nombre);
    }
}
