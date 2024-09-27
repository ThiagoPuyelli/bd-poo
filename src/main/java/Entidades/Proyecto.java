package Entidades;

import javax.persistence.*;

@Entity
@Table(name="PROYECTO")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private int id_proyecto;
    @Column(nullable = false, length = 20)
    private String nombre;
    @Column(nullable = false)
    private Double presupuesto;

    @ManyToOne
    @JoinColumn(name = "id_g")
    private Gerencia gerencia;


    public Gerencia getGerencia() {
        return gerencia;
    }

    public void setGerencia(Gerencia gerencia) {
        this.gerencia = gerencia;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }


    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }


    @Override
    public String toString() {
        return "Proyecto{" +
                "id_proyecto=" + id_proyecto +'\''+
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +'\''+
                ", gerencia = " +gerencia.toString()+
                '}';
    }
}