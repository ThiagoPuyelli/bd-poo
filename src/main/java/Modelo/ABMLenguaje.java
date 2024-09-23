package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
    private final static Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";
    private static Lenguaje lenguaje = new Lenguaje();
    private  static EntradaGenerica<Lenguaje> entradaGenerica = new EntradaGenerica(lenguaje);

    @Override
    public void iniciarABM() {
        emf = Persistence.createEntityManagerFactory(NOMBREBD); //
        em = emf.createEntityManager();
        System.out.println("ABM LENGUAJE");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu("lenguaje de programación");
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

    public void altaDeTupla() {

    }

    public void bajaDeTupla() {

    }

    public void modificarTupla() {

    }
}
