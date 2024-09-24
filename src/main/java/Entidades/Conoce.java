package Entidades;

import javax.persistence.*;

@Entity
@Table(name = "CONOCE")
public class Conoce {
    @ManyToMany
    @JoinColumn(name = "dni", foreignKey = @ForeignKey(name = "dni"))
    @Column(nullable = false)
    private String dni;
    @ManyToMany
    @JoinColumn(name = "id_lenguaje", foreignKey = @ForeignKey(name = "id"))
    @Column(nullable = false)
    private String idLang;
    @Column(length = 10, nullable = false)
    private String nivel;

    public Conoce() {}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getIdLlang() {
        return idLang;
    }

    public void setIdLlang(String idLlang) {
        this.idLang = idLlang;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Conoce{" +
                "dni='" + dni + '\'' +
                ", idLlang='" + idLang + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
