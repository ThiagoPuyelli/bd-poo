package Entidades;

import javax.persistence.*;

@Entity
public class Proyecto {
    @Id
    @Column(nullable = false)
    private int id_proyecto;
    @Column(nullable = false, length = 20)
    private String nombre;
    @Column(nullable = false)
    private double presupuesto;

    @ManyToOne
    @JoinColumn(name = "id_gerencia", foreignKey = @ForeignKey(name = "id_g"))
    private int id_gerencia;



    public int getId_gerencia() {
        return id_gerencia;
    }

    public void setId_gerencia(int id_gerencia) {
        this.id_gerencia = id_gerencia;
    }

    public void setPresupuesto(double presupuesto) {
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

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }


    @Override
    public String toString() {
        return "Proyecto{" +
                "id_proyecto=" + id_proyecto +'\''+
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +
                '}';
    }
}
