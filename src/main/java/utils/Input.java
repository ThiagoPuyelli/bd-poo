package utils;

import exceptions.NumeroFueraRangoException;

import java.lang.reflect.Type;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.reflect.Field;

public class Input<T> {
    public static <T> T getInput(Scanner scanner, Type type, String pedido) {
        while (true) {
            try {
                System.out.print(pedido + ": ");
                if (type.getTypeName().equals("int") || type.getTypeName().equals("java.lang.Integer")) {
                    return (T) (Integer) scanner.nextInt();
                } else if (type.getTypeName().equals("java.lang.String")) {
                    return (T) (String) scanner.nextLine();
                } else if (type.getTypeName().equals("java.lang.Double") || type.getTypeName().equals("java.lang.Float")) {
                    return (T) (Double) scanner.nextDouble();
                } else {
                    return (T) ("Problema al recibir casteo");
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
