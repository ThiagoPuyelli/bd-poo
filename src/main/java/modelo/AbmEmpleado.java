package modelo;

import Entidades.Analista;
import Entidades.Empleado;
import Entidades.Programador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Scanner;

import static utils.Input.obtenerNumero;

public class AbmEmpleado {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction entityTransaction;
    private final static Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";

    // Menu general para ejecutar los metodos del ABM.
    public static void mostrarABM() {
        entityManagerFactory = Persistence.createEntityManagerFactory(NOMBREBD); //
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println("ABM EMPLEADO");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = obtenerNumero(scanner);
            scanner.nextLine();

            switch (opcion) {
                // caso para salir.
                case 0:
                    System.out.println("Saliendo del ABM Empleado ...!");
                    exitApp();
                    break;
                // Crear un nuevo empleado.
                case 1:
                    crearEmpleado(scanner);
            }
        }
    }

    // ALTA empleado.
    private static void crearEmpleado(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); // comienzo la transaccion
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el dni del empleado: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese el tipo de empleado (1 Analista) (2 progamador) ");
        int tipoEmpleado = obtenerNumero(scanner, 2);
        try {
            Empleado empleado = new Empleado(apellido, dni, nombre);
            Analista analista;
            Programador programador;
            entityManager.persist(empleado);
            // seteo el tipo de empleado.
            if (tipoEmpleado == 1) {
                analista = new Analista(empleado);
                entityManager.persist(analista);
            }
            else {
                programador = new Programador(empleado.getDni());
                entityManager.persist(programador);
            }
            entityTransaction.commit();
            System.out.println("Empleado creado correctamente!\n");
        } catch (Exception e) {
            e.printStackTrace(); // cambiar.
        }
    }

    // libero recursos.
    private static void exitApp() {
        if (entityManager != null) entityManager.close();
        if (entityManagerFactory != null) entityManagerFactory.close();
        scanner.close();
    }

    // Menu de opciones.
    private static void mostrarMenu() {
        System.out.println("Por favor, seleccione una opcion: ");
        System.out.println("1- Crear un empleado.");
        System.out.println("2- Mostrar todos los empleados.");
        System.out.println("3- Actualiar un empleado.");
        System.out.println("4- Borrar un empleado.");
        System.out.println("0- Exit.");
    }

}
