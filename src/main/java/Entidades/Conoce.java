package Entidades;

import utils.Nivel;

import javax.persistence.*;

@Entity
@Table(name = "CONOCE")
public class Conoce {
    @Id
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni", foreignKey = @ForeignKey(name = "dni"))
    @Column(nullable = false, length = 8)
    private String dni;
    @Id
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lenguaje", foreignKey = @ForeignKey(name = "id"))
    @Column(nullable = false, length = 4)
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

    public void setNivel(int nivel) {
        switch (nivel) {
            case 1 : this.nivel = Nivel.INICIAL.toString();
            case 2 : this.nivel = Nivel.MEDIO.toString();
            case 3 : this.nivel = Nivel.AVANZADO.toString();
        }
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
