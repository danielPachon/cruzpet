package co.cruzpet.catalogos.infraestructura.persistencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import co.cruzpet.catalogos.dominio.Especie;

@Entity
@Table(name = "especie")
class EspecieEntidad {

    @Id
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int orden;

    protected EspecieEntidad() {
    }

    Especie aDominio() {
        return new Especie(codigo, nombre);
    }
}
