package Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;

@Entity
public class Conoce {
    @ForeignKey
    private String dni;
    @ForeignKey
    private String idLlang;
    @Column
    private String nivel;

    public Conoce(String dni, String idLlang) {
        this.dni = dni;
        this.idLlang = idLlang;
    }

    public Conoce(String dni, String idLlang, String nivel) {
        this.dni = dni;
        this.idLlang = idLlang;
        this.nivel = nivel;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getIdLlang() {
        return idLlang;
    }

    public void setIdLlang(String idLlang) {
        this.idLlang = idLlang;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
