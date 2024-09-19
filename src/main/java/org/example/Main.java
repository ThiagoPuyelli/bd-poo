package org.example;
import utils.Input;

import javax.persistence.*;
import java.util.Scanner;

public class Main {
    public static void main (String arg[]) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;

        while (input != 0) {
            displayMenu();
            input = Input.getInput(scanner, Integer.class, "Ingrese una opción");
            scanner.nextLine();

            switch (input) {
                case 0:
                    System.out.println("Gracias pibe");
                    break;
                // Create a new product.
                case 1:
                    System.out.println("Gracias pibe 1");
                    break;
                // Display entities.
                case 2:
                    System.out.println("Gracias pibe 2");
                    break;
                // Update a product.
                case 3:
                    System.out.println("Gracias pibe 3");
                    break;
                case 4:
                    System.out.println("Gracias pibe 43");
                    break;
                case 5:
                    System.out.println("Gracias pibe 5");
                    break;
                case 6:
                    System.out.println("Gracias pibe 6");
                    break;
                default:
                    System.out.println("Wrong option, try again!");
            }
        }
        scanner.close();
    }

    private static void displayMenu () {
        System.out.println("Base de datos de analista juejue");
        System.out.println("0. Salir \n" +
                "1. Gerencia \n" +
                "2. Proyecto \n" +
                "3. Trabaja\n" +
                "4. Empleado \n" +
                "5. Lenguaje \n" +
                "6. Conoce \n");
    }
}