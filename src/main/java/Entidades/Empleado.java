package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "EMPLEADO")
public class Empleado {
    @Id
    @Column(length = 8, nullable = false, unique = true)
    private String dni;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    public Empleado() {
    }

    public Empleado(String apellido, String dni, String nombre) {
        this.apellido = apellido;
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format(
                        " Nombre: %s\n" +
                        " Apellido: %s\n" +
                        " DNI: %s\n",
                nombre, apellido, dni
        );
    }
}
