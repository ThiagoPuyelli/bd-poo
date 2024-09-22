package utils;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {

    public static List<Class<?>> findClassesInPackage(String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        // Convertimos el nombre del paquete en una ruta (separadores de directorios)
        String path = packageName.replace('.', '/');

        // Obtenemos el ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // Buscamos el recurso con la ruta del paquete
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("No se encuentra el paquete " + packageName);
        }

        // Creamos un archivo que representa el directorio del paquete
        File directory = new File(resource.getFile());

        // Verificamos que el directorio exista
        if (!directory.exists()) {
            throw new IllegalArgumentException("El paquete " + packageName + " no existe.");
        }

        // Recorremos los archivos dentro del directorio
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                // Eliminamos la extensión ".class" para obtener el nombre de la clase
                String className = file.getName().substring(0, file.getName().length() - 6);

                // Cargamos la clase usando Class.forName() y la agregamos a la lista
                classes.add(Class.forName(packageName + "." + className));
            }
        }

        return classes;
    }
}