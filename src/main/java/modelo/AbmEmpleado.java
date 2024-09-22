package modelo;

import Entidades.Analista;
import Entidades.Empleado;
import Entidades.Programador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
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
                    break;
                // Mostrar todos los empleados.
                case 2:
                    mostrarEmpleados(scanner);
                    break;
                // Actualizar un empleado
                case 3:
                    break;
                // borrar un empleado.
                case 4:
                    borrarEmpleado(scanner);
                    break;

            }
        }
    }

    private static void borrarEmpleado(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            Empleado empleado = encontrarEmpleado(scanner);
            System.out.println(empleado);
            if (empleado == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            System.out.println("El empleado a ser borrado es el siguiente:");
            mostrarEmpleadoPorDni(empleado.getDni());
            entityManager.remove(empleado);
            entityTransaction.commit();
            System.out.println("Empleado borrado con exito!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            entityTransaction.rollback();
        }
    }

    // Busca un empleado.
    private static Empleado encontrarEmpleado(Scanner scanner) {
        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();
        return entityManager.find(Empleado.class, dni);
    }

    // Muestra todos los empleados.
    private static void mostrarEmpleados(Scanner scanner) {
        System.out.println("EMPLEADOS\n");
        try {
            // busco los empleados con JPQL
            List<Empleado> empleados = entityManager.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
            // fix this query.
            String esAnalista = entityManager.createNativeQuery("SELECT DNI FROM EMPLEADO E  WHERE E.DNI IN (SELECT DNI FROM ANALISTA A WHERE A.DNI = E.DNI)").toString();
            String tipo = esAnalista.isEmpty() ? "Programador" : "Analista";
            System.out.println("--------------------------------");
            empleados.forEach(empleado -> {
                System.out.println(tipo);
                System.out.println(empleado);
                System.out.println("--------------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            entityTransaction.rollback();
            e.printStackTrace();
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
                programador = new Programador(empleado);
                entityManager.persist(programador);
            }
            entityTransaction.commit();
            System.out.println("Empleado creado correctamente!\n");
            // hacer un query y mostrar el empleado creado.
            mostrarEmpleadoPorDni(empleado.getDni());
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace(); // cambiar.
        }
    }

    // Dado un DNI lo muestra por pantalla.
    private static void mostrarEmpleadoPorDni(String dni) {
        System.out.println("Datos del empleado");
        try {
            Empleado empleado = entityManager.find(Empleado.class, dni);
            System.out.println(empleado);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!");
            entityTransaction.rollback();
            e.printStackTrace();
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
        System.out.println("3- Actualizar un empleado.");
        System.out.println("4- Borrar un empleado.");
        System.out.println("0- Exit.");
    }

}
