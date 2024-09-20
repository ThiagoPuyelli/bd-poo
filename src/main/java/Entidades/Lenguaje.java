package Entidades;

import javax.persistence.*;

@Entity
public class Lenguaje {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private String id;
    @Column(length = 16, columnDefinition = "")
    private String nombre;

    public Lenguaje(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
