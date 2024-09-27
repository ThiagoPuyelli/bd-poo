package Entidades;
import javax.persistence.*;
@Entity
@Table(name="Gerencia")
public class Gerencia {
    // instance variables - replace the example below with your own
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column
    private int id_g;
    @Column(nullable=false,length=40)
    private String nombre;

    public int getId_g() {
        return id_g;
    }

    public void setId_g(int id_g) {
        this.id_g = id_g;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Gerencia{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
