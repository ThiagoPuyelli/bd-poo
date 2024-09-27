package Modelo;


import Entidades.Empleado;
import Entidades.Gerencia;
import Entidades.Proyecto;
import utils.ABM;
import utils.EntradaGenerica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utils.EntradaNro.obtenerNumero;
import static utils.Menu.mostrarAtributosModificables;
import static utils.Menu.mostrarMenu;

public class ABMProyecto implements ABM {


    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction entityTransaction;
    private final static Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static Proyecto proyecto = new Proyecto();
    private  static EntradaGenerica<Proyecto> entradaGenerica = new EntradaGenerica(proyecto);

    // Menu general para ejecutar los metodos del ABM.
    @Override
    public void iniciarABM() {
        entityManagerFactory = Persistence.createEntityManagerFactory(NOMBREBD); //
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println("ABM PROYECTO");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu("proyectos");
            opcion = obtenerNumero(scanner);
            scanner.nextLine();

            switch (opcion) {
                // caso para salir.
                case 0:
                    System.out.println("Saliendo del ABM Proyecto ...!");
                    exitApp();
                    break;
                // Crear un nuevo proyecto.
                case 1:
                    crearProyecto(scanner);
                    break;
                // Mostrar todos los proyectos.
                case 2:
                    mostrarProyectos(scanner);
                    break;
                // Actualizar un proyecto
                case 3:
                    actualizarProyecto(scanner);
                    break;
                // borrar un proyecto.
                case 4:
                    borrarProyecto(scanner);
                    break;
                default:
                    System.out.println("Opcion incorrecta!");
            }
        }
    }

    // Actualizar un proyecto.
    private void actualizarProyecto(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            Proyecto proyecto1 = encontrarProyecto(scanner);
            if (proyecto1 == null) {
                System.out.println("Proyecto no encontrado");
                return;
            }

            System.out.println("Elija que desea modificar: ");
            mostrarAtributosModificables();
            int opcion = obtenerNumero(scanner, 3);
            EntradaGenerica<Proyecto> entrada = new EntradaGenerica<>(proyecto1);
            List<String> ignorar = new ArrayList<>();
            ignorar.add("id_proyecto");
            ignorar.add("gerencia");
            // pedir datos.
            if (opcion == 1) {
                ignorar.add("presupuesto");
                entrada.pedirDatos(ignorar);
            } else if (opcion == 2){
                ignorar.add("nombre");
                entrada.pedirDatos(ignorar);
            } else {
                entrada.pedirDatos(ignorar);
            }

            entityTransaction.commit();
            System.out.println("Proyecto modificado con exito!");
        } catch (Exception e) {
            System.out.println("An error has occurred!");
            e.printStackTrace();
        }
    }

    public static void mostrarAtributosModificables() {
        System.out.println("1- Nombre");
        System.out.println("2- Presupuesto");
        System.out.println("3- Todos");
    }

    private static void borrarProyecto(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            Proyecto proyecto1 = encontrarProyecto(scanner);
            System.out.println(proyecto1);
            if (proyecto1 == null) {
                System.out.println("Proyecto no encontrado.");
                return;
            }
            System.out.println("El proyecto a ser borrado es el siguiente:");
            mostrarProyectoPorId(proyecto1.getId_proyecto());
            entityManager.remove(proyecto1);
            entityTransaction.commit();
            System.out.println("Proyecto borrado con exito!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            //entityTransaction.rollback();
        }
    }

    // Busca un proyecto.
    private static Proyecto encontrarProyecto(Scanner scanner) {
        System.out.print("Ingrese el id del proyecto: ");
        int id = obtenerNumero(scanner);
        return entityManager.find(Proyecto.class,id);
    }

    // Muestra todos los proyectos.
    private static void mostrarProyectos(Scanner scanner) {
        System.out.println("PROYECTOS\n");
        try {
            List<Proyecto> proyectos = entityManager.createNativeQuery("SELECT * from PROYECTO", Proyecto.class).getResultList();
            proyectos.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
            e.printStackTrace();
        }
    }

    // ALTA Proyecto.
    private static void crearProyecto(Scanner scanner) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); // comienzo la transaccion

        List<String> ignoreList = new ArrayList<String>();
        ignoreList.add("id_proyecto");
        ignoreList.add("gerencia");
        entradaGenerica.pedirDatos(ignoreList);
        Gerencia gerencia;
        do {
            System.out.println("id_g");
            int id_g = obtenerNumero(scanner);
            gerencia = entityManager.find(Gerencia.class, id_g);
            if (gerencia == null) {
                System.out.println("La gerencia no existe");
            }
        } while (gerencia == null);
        proyecto.setGerencia(gerencia);

        try {
            entityManager.persist(proyecto);
            entityTransaction.commit();
            System.out.println("Proyecto creado correctamente!\n");
            mostrarProyectoPorId(proyecto.getId_proyecto());
        } catch (Exception e) {
            //entityTransaction.rollback();
            e.printStackTrace(); // cambiar.
        }
    }

    // Dado un Id lo muestra por pantalla.
    private static void mostrarProyectoPorId(int id) {
        System.out.println("Datos del Proyecto");
        try {
            Proyecto proyecto1 = entityManager.find(Proyecto.class, id);
            System.out.println(proyecto1);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!");
            //entityTransaction.rollback();
            e.printStackTrace();
        }
    }

    // libero recursos.
    private static void exitApp() {
        if (entityManager != null) entityManager.close();
        if (entityManagerFactory != null) entityManagerFactory.close();
    }
}