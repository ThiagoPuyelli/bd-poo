package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "PROGRAMADOR")
public class Programador {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni")
    private Empleado empleado;

    public Programador() {
    }

    public Programador(Empleado empleado) {
        this.empleado = empleado;
    }

}
