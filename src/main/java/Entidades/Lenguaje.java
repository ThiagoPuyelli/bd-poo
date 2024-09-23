package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "LENGUAJE")
public class Lenguaje {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(length = 30, nullable = false)
    private String id;
    @Column(length = 30, columnDefinition = "")
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
