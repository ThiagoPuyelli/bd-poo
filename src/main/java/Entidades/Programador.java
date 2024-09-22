package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "PROGRAMADOR")
public class Programador {
    @Id
    private String dni;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    public Programador() {
    }

    public Programador(String dni) {
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
        return "Programador{" +
                "dni='" + dni + '\'' +
                '}';
    }
}
