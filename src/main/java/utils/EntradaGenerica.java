package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class EntradaGenerica<T> {
    private T entidad;
    public EntradaGenerica (T entidad) {
        this.entidad = entidad;
    }

    public void pedirDatos (List<String> camposIgnorar) {
        for (Field field : entidad.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (camposIgnorar != null && camposIgnorar.contains(field.getName())) {
                continue;
            }
            Scanner scanner = new Scanner(System.in);
            Type type = field.getGenericType();
            Object dato = Input.getInput(scanner, type, field.getName());
            try {
                field.set(entidad, dato);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

}
