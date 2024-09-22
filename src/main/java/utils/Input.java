package utils;

import exceptions.NumeroFueraRangoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input<T> {
    public static <T> T getInput(Scanner scanner, Class<T> type, String pedido) {
        while (true) {
            try {
                System.out.print(pedido + ": ");

                if (type == Integer.class) {
                    return type.cast(scanner.nextInt());
                } else if (type == Double.class) {
                    return type.cast(scanner.nextDouble());
                } else if (type == Float.class) {
                    return type.cast(scanner.nextFloat());
                } else if (type == Long.class) {
                    return type.cast(scanner.nextLong());
                } else if (type == Boolean.class) {
                    return type.cast(scanner.nextBoolean());
                } else if (type == String.class) {
                    return type.cast(scanner.nextLine());
                } else {
                    throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor ingrese bien el dato!");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

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