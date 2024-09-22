package Entidades;

import javax.persistence.*;

@Entity
public class Analista {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String dni;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    public Analista() {
    }

    public Analista(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Analista{" +
                "dni='" + dni + '\'' +
                '}';
    }
}
