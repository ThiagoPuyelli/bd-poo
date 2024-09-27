package Modelo;

import Entidades.Analista;
import Entidades.Empleado;
import Entidades.Programador;
import utils.ABM;
import utils.EntradaGenerica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.EntradaNro.obtenerNumero;
import static utils.Menu.mostrarAtributosModificables;
import static utils.Menu.mostrarMenu;

public class ABMEmpleado implements ABM {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction entityTransaction;
    private final static Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static Empleado empleado = new Empleado();
    private  static EntradaGenerica<Empleado> entradaGenerica = new EntradaGenerica(empleado);

    // Menu general para ejecutar los metodos del ABM.
    @Override
    public void iniciarABM() {
        entityManagerFactory = Persistence.createEntityManagerFactory(NOMBREBD); //
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println("ABM EMPLEADO");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu("empleados");
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
                    actualizarEmpleado(scanner);
                    break;
                // borrar un empleado.
                case 4:
                    borrarEmpleado(scanner);
                    break;
                default:
                    System.out.println("Opcion incorrecta!");
            }
        }
    }

    // Actualizar un empleado.
    private void actualizarEmpleado(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            Empleado empleado = encontrarEmpleado(scanner);
            if (empleado == null) {
                System.out.println("Empleado no encontrado");
                return;
            }
            mostrarEmpleadoPorDni(empleado.getDni());
            // Modificar los atributos del empleado.
            System.out.println("Elija que desea modificar: ");
            mostrarAtributosModificables();
            int opcion = obtenerNumero(scanner, 3);
            EntradaGenerica<Empleado> entrada = new EntradaGenerica<>(empleado);
            List<String> ignorar = new ArrayList<>();
            ignorar.add("dni");
            // pedir datos.
            if (opcion == 1) {
                ignorar.add("apellido");
                entrada.pedirDatos(ignorar);
            } else if (opcion == 2){
                ignorar.add("nombre");
                entrada.pedirDatos(ignorar);
            } else {
                entrada.pedirDatos(ignorar);
            }
            entityTransaction.commit();
            System.out.println("Empleado modificado con exito!");
            mostrarEmpleadoPorDni(empleado.getDni()); // muestro los datos del empleado modificado
        } catch (Exception e) {
            System.out.println("An error has occurred!");
            //entityTransaction.rollback();
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
            //entityTransaction.rollback();
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
            @SuppressWarnings("unchecked")
            List<Empleado> analistas = entityManager.createNativeQuery("SELECT E.NOMBRE, E.APELLIDO, E.DNI FROM EMPLEADO E INNER JOIN ANALISTA A ON E.DNI = A.DNI", Empleado.class).getResultList();
            @SuppressWarnings("unchecked")
            List<Empleado> programadores = entityManager.createNativeQuery("SELECT E.NOMBRE, E.APELLIDO, E.DNI FROM EMPLEADO E INNER JOIN PROGRAMADOR P ON E.DNI = P.DNI", Empleado.class).getResultList();

            analistas.forEach(a -> {
                System.out.println("ANALISTAS");
                System.out.println(a);
            });

            programadores.forEach(p -> {
                System.out.println("PROGRAMADORES");
                System.out.println(p);
            });

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
        }
    }

    // ALTA empleado.
    private static void crearEmpleado(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); // comienzo la transaccion
        entradaGenerica.pedirDatos(null);
        System.out.print("Ingrese el tipo de empleado (1 Analista) (2 programador) ");
        int tipoEmpleado = obtenerNumero(scanner, 2);
        try {
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
            //entityTransaction.rollback();
            System.out.println("Ha ocurrido un error, revise los datos ingrsados.");
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
            //entityTransaction.rollback();
            System.out.println("Ha ocurrido un error inesperado!");
        }
    }

    // libero recursos.
    private static void exitApp() {
        if (entityManager != null) entityManager.close();
        if (entityManagerFactory != null) entityManagerFactory.close();
    }
}