package Modelo;

import Entidades.Gerencia;
import utils.ABM;
import utils.EntradaGenerica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ABMGerencia implements ABM {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private final String NOMBREBD = "analista";
    private Gerencia gerencia;
    public ABMGerencia() {
    }
    @Override
    public void iniciarABM() {
        entityManagerFactory = Persistence.createEntityManagerFactory(NOMBREBD); //
        entityManager = entityManagerFactory.createEntityManager();
        gerencia = new Gerencia();
        EntradaGenerica<Gerencia> entrada = new EntradaGenerica<Gerencia>(gerencia);
        int input = -1;
        Scanner scanner = new Scanner(System.in);
        while (input != 0) {
            System.out.println("ABM gerencia");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Modificacion");
            System.out.println("4. Lectura");

            input = scanner.nextInt();
            switch (input) {
                case 0:
                    System.out.println("Cierre de ABM de Gerencia");
                    break;
                case 1:
                    alta(entrada);
                    break;
                case 2:
                    System.out.println("Ingrese la gerencia a eliminar");
                    baja(entrada);
                    break;
                case 3:
                    System.out.println("Ingrese la modificacion");
                    modificar(entrada, scanner);
                    break;
                case 4:
                    System.out.println("Gerencias ingresadas");
                    mostrarGerencias();
                    break;
                default:
                    System.out.println("El valor esta equivocado");
                    break;
            }
        }
    }

    public void alta (EntradaGenerica<Gerencia> entrada) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<String> lista = new ArrayList<>();
        lista.add("id_g");
        entrada.pedirDatos(lista);
        try {
            entityManager.persist(gerencia);
            entityTransaction.commit();
            System.out.println("Gerencia creada correctamente!\n");
            mostrarGerencia(gerencia);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
    }

    public void mostrarGerencias () {
        System.out.println("Las gerencias almacenadas");
        try {
            List<Gerencia> gerencias = entityManager.createQuery("SELECT G FROM Gerencia G", Gerencia.class).getResultList();
            System.out.println("--------------------------------------------");
            for (Gerencia g : gerencias) {
                mostrarGerencia(g);
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar (EntradaGenerica<Gerencia> entrada, Scanner scanner) {
        System.out.println("Ingrese el id de la gerencia a modificar: ");
        int id = scanner.nextInt();
        Gerencia g = entityManager.find(Gerencia.class, id);
        if (g == null) {
            System.out.println("La gerencia con ese id no existe");
            return;
        }
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<String> lista = new ArrayList<>();
        lista.add("id_g");
        entrada.pedirDatos(lista);
        try {
            entityManager.persist(gerencia);
            entityTransaction.commit();
            System.out.println("Gerencia modificada correctamente!\n");
            mostrarGerencia(gerencia);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
    }

    public void mostrarGerencia (Gerencia gerencia) {
        System.out.println("id_g: " + gerencia.getId_g());
        System.out.println("nombre: " + gerencia.getNombre());
    }

    public void baja (EntradaGenerica<Gerencia> entrada) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List<String> lista = new ArrayList<>();
        lista.add("nombre");
        entrada.pedirDatos(lista);
        try {
            Gerencia g = entityManager.find(Gerencia.class, gerencia.getId_g());
            if (g == null) {
                System.out.println("Gerencia no encontrada");
                return;
            }
            entityManager.remove(g);
            entityTransaction.commit();
            System.out.println("Gerencia eliminada correctamente!\n");
            mostrarGerencia(gerencia);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
    }
}
