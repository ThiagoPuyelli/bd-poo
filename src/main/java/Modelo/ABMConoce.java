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
        System.out.println("< Niveles de especialización del lenguaje: ");
        System.out.println("\tINICIAL : 0 \n\tMEDIO: 1\n\tAVANZZADO: 2\n");
        entradaGenerica.pedirDatos(null);
        try {
           Programador programador = em.find(Programador.class, relConoce.getDni());
           Lenguaje lenguaje = em.find(Lenguaje.class, relConoce.getIdLlang());
           if (programador == null) {
               System.out.println("!>> El Programador no existe en la base de datos\n");
               return;
           } else if (lenguaje == null) {
               System.out.println("!>> El lenguaje no existe en la base de datos\n");
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
            System.out.println(">> La relación se ha insertado correctamente!\n");
            // hacer un query y mostrar el empleado creado.
            System.out.println("{>> Lenguajes asociados al programador con DNI " + relConoce.getDni());
            mostrarRelacionesPorDNI(relConoce.getDni());
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    public void bajaDeTupla(Scanner scn) {
        Conoce conoce = encontrarRelacion();
        System.out.println(conoce);
        if (conoce == null) {
            System.out.println("!>> Lenguaje no encontrado.");
            return;
        }

        System.out.println("{>> El lenguaje a eliminar es el siguiente:");
        System.out.println(conoce);
        et = em.getTransaction();
        et.begin();

        try {
            em.remove(conoce);
            et.commit();
            System.out.println(">> Relación borrada con exito!");
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error.");
            et.rollback();
        }
    }

    public void modificarTupla(Scanner scn) {
        Conoce conoce = encontrarRelacion();
        if (conoce == null) {
            System.out.println("!>> La relación no existe en la base de datos");
            return;
        }
        System.out.println("Relacion a modificar: ");
        System.out.println(conoce);
        System.out.println("<< Ingrese el nuevo nivel de conocimiento: ");
        EntradaGenerica<Conoce> entrada = new EntradaGenerica<>(conoce);
        List<String> ignorar = List.of(new String[]{"dni","idLang"});
        // pedir datos.
        entrada.pedirDatos(ignorar);

        et = em.getTransaction();
        et.begin();
        try {
            et.commit();
            System.out.println(">> Relación modificada con exito!");
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error! ");
            e.printStackTrace();
        }
    }

    public void mostrarTuplas() {
        System.out.println("{>> Todas las relaciones: \n");
        try {
            List<Conoce> conoceList = em.createNativeQuery("SELECT * FROM CONOCE", Conoce.class).getResultList();
            conoceList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            e.printStackTrace();
        }
    }

    private static void mostrarRelacionesPorDNI(String dni) {
        System.out.println(">> Lenguajes de programación registradas al programador con DNI " + dni + ": ");
        try {
            List<Conoce> conoceList = em.createNativeQuery("SELECT * FROM CONOCE WHERE DNI = '" + relConoce.getDni() + "'", Conoce.class).getResultList();
            conoceList.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            et.rollback();
            e.printStackTrace();
        }
    }

    // Busca un relacion por dni y idlang
    private static Conoce encontrarRelacion() {
        System.out.print("<< Ingrese el DNI del programador: ");
        String proDNI = scanner.nextLine();
        System.out.println("<< Ingrese el ID del lenguaje: ");
        String lenID = scanner.nextLine();
        List<Conoce> resultado = em.createNativeQuery("SELECT * FROM CONOCE WHERE DNI = '" + proDNI + "' AND ID = '" + lenID + "'", Conoce.class).getResultList();
        return resultado.get(0);
    }

    // liberar recursos
    private static void exitApp() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        scanner.close();
    }
}