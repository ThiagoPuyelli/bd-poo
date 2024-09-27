package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

import Entidades.;
import utils.ABM;
import utils.EntradaGenerica;

import static utils.EntradaNro.obtenerNumero;
import static utils.Menu.mostrarMenu;

public class ABMTrabaja implements ABM {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction et;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static final Trabaja relTrabaja = new Trabaja();
    private static final EntradaGenerica<Trabaja> entradaGenerica = new EntradaGenerica<>(relTrabaja);

    @Override
    public void iniciarABM() {
        emf = Persistence.createEntityManagerFactory(NOMBREBD); //
        em = emf.createEntityManager();
        System.out.println("ABM Trabaja");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu("Trabajadores");
            opcion = obtenerNumero(scanner);
            scanner.nextLine();

            switch (opcion) {
                // caso para salir.
                case 0:
                    System.out.println(">> Saliendo del ABM Trabaja ...!\n");
                    exitApp();
                    break;
                // Crear un nueva relacion Trabaja.
                case 1:
                    altaDeTupla(scanner);
                    break;
                // Mostrar todos las relaciones Trabaja.
                case 2:
                    mostrarTuplas();
                    break;
                // Actualizar un Trabaja
                case 3:
                    modificarTupla(scanner);
                    break;
                // borrar un Trabaja
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
            Empleado  empleado1= em.find(Empleado.class, relTrabaja.getDni());
            Proyecto  proyecto1= em.find(Proyecto.class,relTrabaja.getId());

            if (empleado1 != null) System.out.println("!>> El Empleado no existe en la base de datos \n");
            if (proyecto1 != null) System.out.println("!>> El Proyecto no existe en la base de datos \n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        et = em.getTransaction();
        et.begin();// comienzo la transaccion

        try {
            em.persist(relTrabaja);
            et.commit();
            System.out.println(">> La relación se ha insertado correctamente!\n");
            // hacer un query y mostrar el trabajo creado.
            System.out.println("{>> Proyectos asociados al empleado con DNI " + relTrabaja.getDni());
            mostrarRelacionesPorDNI(relTrabaja.getDni());
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    public void bajaDeTupla(Scanner scn) {
        Trabaja trabaja1 = encontrarRelacion();
        System.out.println(trabaja1);
        if (trabaja1 == null) {
            System.out.println("!>> Lenguaje no encontrado.");
            return;
        }

        System.out.println("{>> El lenguaje a eliminar es el siguiente:");
        System.out.println(trabaja1);
        et = em.getTransaction();
        et.begin();

        try {
            em.remove(trabaja1);
            et.commit();
            System.out.println(">> Relación borrada con exito!");
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error.");
            et.rollback();
        }
    }

    public void modificarTupla(Scanner scn) {
        Trabaja trabaja1 = encontrarRelacion();
        if (trabaja1 == null) {
            System.out.println("!>> La relación no existe en la base de datos");
            return;
        }
        System.out.println("Relacion a modificar: ");
        System.out.println(trabaja1);
        System.out.println("<< Ingrese el nuevo nivel de : ");
        EntradaGenerica<Trabaja> entrada = new EntradaGenerica<>(trabaja1);
        List<String> ignorar = List.of(new String[]{"dni","id_proyecto"});
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
            List<Trabaja> conoceList = em.createQuery("SELECT * FROM Trabaja", Trabaja.class).getResultList();
            conoceList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            e.printStackTrace();
        }
    }

    private static void mostrarRelacionesPorDNI(String dni) {
        System.out.println(">> Lenguajes de Trabaja registradas al programador con DNI " + dni + ": ");
        try {
            List<Trabaja> conoceList = em.createQuery("SELECT c FROM Trabaja c WHERE c.dni = :dni", Trabaja.class)
                    .setParameter("dni", relTrabaja.getDni())
                    .getResultList();
            conoceList.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("!>> Ha ocurrido un error!");
            et.rollback();
            e.printStackTrace();
        }
    }

    // Busca un relacion por dni y por id del proyecto
    private static Trabaja encontrarRelacion() {
        System.out.print("<< Ingrese el DNI del programador: ");
        String proDNI = scanner.nextLine();
        System.out.println("<< Ingrese el ID del Proyecto: ");
        int id = obtenerNumero(scanner);
        List<Trabaja> resultado = em.createQuery("SELECT c FROM Trabaja c WHERE c.dni = :dni AND c.id_proyecto = :id", Trabaja.class)
                .setParameter("dni", proDNI).setParameter("id_proyecto",id )
                .getResultList();
        return resultado.get(0);
    }

    // liberar recursos
    private static void exitApp() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        scanner.close();
    }
}