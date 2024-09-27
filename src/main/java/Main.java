import utils.ABM;
import utils.ClassFinder;
import utils.EntradaGenerica;
import Entidades.Gerencia;
import utils.Input;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String arg[]) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        int i = 1;
        while (input != 0) {
            //Gerencia gerencia = new Gerencia();
            //gerencia.setId_g(2);
            //gerencia.setNombre("Sega");
            //EntradaGenerica<Gerencia> entrada = new EntradaGenerica<Gerencia>(gerencia);
            //entrada.pedirDatos();
            //input = Input.getInput(scanner, Integer.class, "Ingrese una opción");

            try {
                List<Class<?>> clases = ClassFinder.findClassesInPackage("Modelo");

                System.out.println("Elige una opción:");
                System.out.println("0. Salir");
                i = 1;
                for (Class<?> c : clases) {
                    System.out.println(i + ". " + c.getName().split("Modelo.")[1]);
                    i++;
                }
                input = scanner.nextInt();
                if (input > clases.size() || input < 0) {
                    System.out.println("La opción es incorrecta");
                } else if (input > 0) {
                    Class<?> clase = clases.get(input - 1);
                    ABM instancia = (ABM) clase.getDeclaredConstructor().newInstance();
                    instancia.iniciarABM();
                }

                if (input == 0) {
                    System.out.println("Cierre de aplicacion");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); // Muestra la traza del error
            } catch (Exception e) {
                e.printStackTrace();
            }
            i = 1;
        }
        scanner.close();
    }
}