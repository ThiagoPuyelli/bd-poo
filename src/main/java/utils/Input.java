package utils;

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
}