package Entidades;


import javax.persistence.*;

@Entity
@Table( name = "TRABAJA")
public class Trabaja {
    @Id
    @ManyToMany
    @JoinColumn(name="dni", foreignKey = @ForeignKey(name="dni"))
    @Column(nullable = false)
    private String dni;


    @Id
    @ManyToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "id_proyecto"))
    @Column(nullable = false, name = "id_proyecto")
    private int id;

    @Column(nullable = false, name="horas_semanales")
    private int horasSemanales;


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

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
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
