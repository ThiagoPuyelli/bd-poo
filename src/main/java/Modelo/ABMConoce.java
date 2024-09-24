package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

import Entidades.Conoce;
import Entidades.Empleado;
import Entidades.Lenguaje;
import Entidades.Programador;
import utils.ABM;
import utils.EntradaGenerica;
import utils.Nivel;

import static utils.EntradaNro.obtenerNumero;
import static utils.Menu.mostrarMenu;

public class ABMConoce implements ABM {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction et;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static final Conoce relConoce = new Conoce();
    private static final EntradaGenerica<Conoce> entradaGenerica = new EntradaGenerica<>(relConoce);

    @Override
    public void iniciarABM() {
        emf = Persistence.createEntityManagerFactory(NOMBREBD); //
        em = emf.createEntityManager();
        System.out.println("ABM DE LOS LENGUAJES DE PROGRAMACION CONOCIDOS POR UN PROGRAMADOR");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu(">> Lenguajes conocidos por el programador");
            opcion = obtenerNumero(scanner);
            scanner.nextLine();

            switch (opcion) {
                // caso para salir.
                case 0:
                    System.out.println("Saliendo del ABM Lenguaje ...!\n");
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
                    System.out.println("Entrada invalida!");
            }
        }
    }

    public void altaDeTupla(Scanner scn) {
        System.out.println("< Niveles de especialización del lenguaje: ");
        System.out.println("\tINICIAL : 0 \n\tMEDIO: 1\n\tAVANZZADO: 2\n");
        entradaGenerica.pedirDatos(null);
        try {
           Programador programador = em.find(Programador.class, relConoce.getDni());
           Lenguaje lenguaje = em.find(Lenguaje.class, relConoce.getIdLlang());
           if (programador == null) {
               System.out.println(">> El Programador no existe en la base de datos\n");
               return;
           } else if (lenguaje == null) {
               System.out.println(">> El lenguaje no existe en la base de datos\n");
               return;
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        et = em.getTransaction();
        et.begin();// comienzo la transaccion

        try {
            em.persist(relConoce);
            et.commit();
            System.out.println("La relación se ha insertado correctamente!\n");
            // hacer un query y mostrar el empleado creado.
            System.out.println(">> Lenguajes asociados al programador con DNI " + relConoce.getDni());
            mostrarRelacionesPorDNI(relConoce.getDni());
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
                System.out.println("Lenguaje no encontrado.");
                return;
            }
            System.out.println("El lenguaje a eliminar es el siguiente:");
            mostrarRelacionesPorDNI(relConoce.getDni());
            em.remove(lenguaje);
            et.commit();
            System.out.println("Lenguaje borrado con exito!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            et.rollback();
        }
    }

    public void modificarTupla(Scanner scn) {
        et = em.getTransaction();
        et.begin();
        try {
            Lenguaje lenguaje = encontrarLenguaje();
            if (lenguaje == null) {
                System.out.println("Lenguaje no encontrado");
                return;
            }
            System.out.println("Ingrese el nuevo nombre: ");
            EntradaGenerica<Lenguaje> entrada = new EntradaGenerica<>(lenguaje);
            List<String> ignorar = List.of(new String[]{"id"});
            // pedir datos.
            entrada.pedirDatos(ignorar);
            et.commit();
            System.out.println("Lenguaje modificado con exito!");
        } catch (Exception e) {
            System.out.println("An error has occurred!");
            e.printStackTrace();
        }
    }

    public void mostrarTuplas() {
        System.out.println(">> Todas las relaciones: \n");
        try {
            List<Conoce> conoceList = em.createNativeQuery("SELECT * FROM CONOCE ", Conoce.class).getResultList();
            conoceList.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
    }

    private static void mostrarRelacionesPorDNI(String dni) {
        System.out.println("Datos del Lenguaje de programación");
        try {
            List<Conoce> conoceList = em.createNativeQuery("SELECT * FROM CONOCE WHERE NOMBRE = '" + relConoce.getDni() + "'", Conoce.class).getResultList();
            conoceList.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!");
            et.rollback();
            e.printStackTrace();
        }
    }

    // Busca un lenguaje por su nombre
    private static Lenguaje encontrarLenguaje() {
        System.out.print("Ingrese el nombre del lenguaje: ");
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