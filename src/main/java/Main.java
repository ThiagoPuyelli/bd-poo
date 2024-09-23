import utils.ABM;
import utils.ClassFinder;
import utils.EntradaGenerica;
import Entidades.Gerencia;
import utils.Input;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Scanner;

import static utils.EntradaNro.obtenerNumero;

public class Main {
    public static void main (String arg[]) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        int i = 1;

        while (input != 0) {
            try {
                List<Class<?>> clases = ClassFinder.findClassesInPackage("Modelo");

                System.out.println("Elige una opción (0 para salir):");
                i = 1;
                for (Class<?> c : clases) {
                    System.out.println(i + ". " + c.getName().split("Modelo.")[1]);
                    i++;
                }
                input  = obtenerNumero(scanner);
                if (input == 0) continue;
                if (input > clases.size()) {
                    System.out.println("La opción es incorrecta");
                } else {
                    Class<?> clase = clases.get(input - 1);
                    ABM instancia = (ABM) clase.getDeclaredConstructor().newInstance();
                    instancia.iniciarABM();
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