package utils;

public class Menu {
    // Menu de opciones.
    public static void mostrarMenu(String entidad) {
        System.out.println("Por favor, seleccione una opcion: ");
        System.out.println("1- Crear " + entidad);
        System.out.println("2- Mostrar todos los " + entidad);
        System.out.println("3- Actualizar " + entidad);
        System.out.println("4- Borrar " + entidad);
        System.out.println("0- Exit.");
    }

    public static void mostrarAtributosModificablesDeEmpleado() {
        System.out.println("1- Nombre");
        System.out.println("2- Apellido");
        System.out.println("3- Todos");
    }
}
