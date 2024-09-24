package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

import Entidades.Empleado;
import Entidades.Lenguaje;
import utils.ABM;
import utils.EntradaGenerica;

import static utils.EntradaNro.obtenerNumero;
import static utils.Menu.mostrarMenu;

public class ABMLenguaje implements ABM {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction et;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static final Lenguaje lenguaje = new Lenguaje();
    private static final EntradaGenerica<Lenguaje> entradaGenerica = new EntradaGenerica<>(lenguaje);

    @Override
    public void iniciarABM() {
        emf = Persistence.createEntityManagerFactory(NOMBREBD); //
        em = emf.createEntityManager();
        System.out.println("ABM LENGUAJE");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu(">> Lenguaje de programación");
            opcion = obtenerNumero(scanner);
            scanner.nextLine();

            switch (opcion) {
                // caso para salir.
                case 0:
                    System.out.println(">> Saliendo del ABM Lenguaje ...!\n");
                    exitApp();
                    break;
                // Crear un nuevo lenguaje.
                case 1:
                    altaDeTupla(scanner);
                    break;
                // Mostrar todos los lenguajes.
                case 2:
                    mostrarTuplas();
                    break;
                // Actualizar un lenguaje
                case 3:
                    modificarTupla(scanner);
                    break;
                // borrar un lenguaje
                case 4:
                    bajaDeTupla(scanner);
                    break;
                default:
                    System.out.println("!>> Entrada invalida!");
            }
        }
    }

    public void altaDeTupla(Scanner scn) {
        List<String> ignoreList = List.of(new String[]{"id"});
        entradaGenerica.pedirDatos(ignoreList);
        try {
            Lenguaje lenguaje1 = em.find(Lenguaje.class, lenguaje.getNombre());
            if (lenguaje1 != null) System.out.println("!>> El lenguaje ya existe en la tabla\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        et = em.getTransaction();
        et.begin();// comienzo la transaccion
        try {
            em.persist(lenguaje);
            et.commit();
            System.out.println(">> Lenguaje de programación insertado correctamente!\n");
            // hacer un query y mostrar el empleado creado.
            mostrarLenguajePorID(lenguaje.getId());
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    public void bajaDeTupla(Scanner scn) {
        et = em.getTransaction();
        et.begin();
        try {
            Lenguaje lenguaje = encontrarLenguaje();
            System.out.println(lenguaje);
            if (lenguaje == null) {
                System.out.println("!>> Lenguaje no encontrado.");
                return;
            }
            System.out.println("{>> El lenguaje a eliminar es el siguiente:");
            mostrarLenguajePorID(lenguaje.getId());
            em.remove(lenguaje);
            et.commit();
            System.out.println(">> Lenguaje borrado con exito!");
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error.");
            et.rollback();
        }
    }

    public void modificarTupla(Scanner scn) {
        et = em.getTransaction();
        et.begin();
        try {
            Lenguaje lenguaje = encontrarLenguaje();
            if (lenguaje == null) {
                System.out.println("!>> Lenguaje no encontrado");
                return;
            }
            System.out.println("<< Ingrese el nuevo nombre: ");
            EntradaGenerica<Lenguaje> entrada = new EntradaGenerica<>(lenguaje);
            List<String> ignorar = List.of(new String[]{"id"});
            // pedir datos.
            entrada.pedirDatos(ignorar);
            et.commit();
            System.out.println(">> Lenguaje modificado con exito!");
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            e.printStackTrace();
        }
    }

    public void mostrarTuplas() {
        System.out.println("EMPLEADOS\n");
        try {
            List<Lenguaje> lenguajes = em.createNativeQuery("SELECT * FROM LENGUAJE ", Empleado.class).getResultList();

            lenguajes.forEach(a -> {
                System.out.println("{>> LENGUAJES DE PROGRAMACION: ");
                System.out.println(a);
            });

        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error! ");
            e.printStackTrace();
        }
    }

    private static void mostrarLenguajePorID(String id) {
        System.out.println(">> Datos del Lenguaje de programación");
        try {
            Lenguaje lenguaje1 = em.find(Lenguaje.class, id);
            System.out.println(lenguaje1);
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            et.rollback();
            e.printStackTrace();
        }
    }

    // Busca un lenguaje por su nombre
    private static Lenguaje encontrarLenguaje() {
        System.out.print("<< Ingrese el nombre del lenguaje: ");
        String nombre = scanner.nextLine();
        return em.find(Lenguaje.class, nombre);
    }

    // liberar recursos
    private static void exitApp() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        scanner.close();
    }
}
