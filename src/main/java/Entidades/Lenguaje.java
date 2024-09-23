package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "LENGUAJE")
public class Lenguaje {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(length = 30, nullable = false, unique = true)
    private String id;
    @Column(length = 30, columnDefinition = "", nullable = false, unique = true)
    private String nombre;

    public Lenguaje() {}

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

    @Override
    public String toString() {
        return "Lenguaje{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
