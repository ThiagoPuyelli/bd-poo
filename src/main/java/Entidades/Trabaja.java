package Entidades;


import javax.persistence.*;

@Entity
@Table( name = "TRABAJA")
public class Trabaja {
    @ManyToMany
    @JoinColumn(name="dni", foreignKey = @ForeignKey(name="dni"))
    @Column(nullable = false)
    private String dni;
    @ManyToMany
    @JoinColumn(name = "id_proyecto", foreignKey = @ForeignKey(name = "id_proyecto"))
    private int id;

    private double horasSemanales;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(double horasSemanales) {
        this.horasSemanales = horasSemanales;
    }


    @Override
    public String toString() {
        return "Trabaja{" +'\''+
        "dni='" + dni + '\'' +
                ", id=" + id +
                ", horasSemanales=" + horasSemanales +
                '}';
    }
}
