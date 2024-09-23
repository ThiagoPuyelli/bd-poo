package Modelo;

import Entidades.Gerencia;
import utils.ABM;
import utils.EntradaGenerica;

public class ABMGerencia implements ABM {
    public ABMGerencia() {
    }
    @Override
    public void iniciarABM() {
        Gerencia gerencia = new Gerencia();
        EntradaGenerica<Gerencia> entrada = new EntradaGenerica<Gerencia>(gerencia);
        int input = -1;
        while (input != 0) {
            System.out.println("Ingrese lo que quiere hacer con la gerencia");
            System.out.println("1. Alta");
            System.out.println("1. Baja");
            System.out.println("1. Modificacion");
            System.out.println("1. Lectura");


        }

    }
}
