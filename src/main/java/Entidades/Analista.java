package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "ANALISTA")
public class Analista {
    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dni")
    private Empleado empleado;

    public Analista() {
    }

    public Analista(Empleado empleado) {
    this.empleado = empleado;
    }
}

