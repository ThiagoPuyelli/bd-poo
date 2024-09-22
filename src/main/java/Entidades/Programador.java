package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "PROGRAMADOR")
public class Programador {
    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dni")
    private Empleado empleado;

    public Programador() {
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Programador(Empleado empleado) {
        this.empleado = empleado;
    }

}
