package utils;

import exceptions.NumeroFueraRangoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntradaNro {
    // valida que se ingrese un entero.
    public static int obtenerNumero(Scanner scanner) {
        int nro = 1;
        while (true) {
            try {
                System.out.print("Ingrese un nro: ");
                nro = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor ingrese un nro valido!!");
                scanner.nextLine();
            }
        }
        return nro;
    }

    // valida que se ingrese un entero hasta el rango especificado
    public static int obtenerNumero(Scanner scanner, int rango) {
        int nro = 1;
        while (true) {
            try {
                System.out.print("Ingrese un nro entre 1 y " + rango + " : ");
                nro = scanner.nextInt();
                if (nro < 1 || nro > rango) throw new NumeroFueraRangoException();
                break;
            } catch (InputMismatchException | NumeroFueraRangoException e) {
                System.out.println("Por favor ingrese un nro valido!!");
                scanner.nextLine();
            }
        }
        return nro;
    }
}