package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Scanner;

public class ABMLenguaje implements ABM {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction et;
    private final static Scanner scanner = new Scanner(System.in);
    private static final String NOMBREBD = "analista";


}
