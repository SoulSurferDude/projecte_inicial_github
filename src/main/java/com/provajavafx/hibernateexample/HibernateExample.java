/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.provajavafx.hibernateexample;

//import Model.Desarrollador;
//import Model.Proyecto;
//import java.util.Date;
import Model.Desarrollador;
import Model.Proyecto;
import Model.Tecnologia;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author gualb
 */
public class HibernateExample {

    public static void main(String[] args) {
        SessionFactory sessionFactory = null;
        Scanner scanner = new Scanner(System.in);
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            boolean salir = false;

            while (!salir) {
                System.out.println("\n--GESTIÓN--\n 1-Desarrolladores\n 2-Proyectos\n 3-Tecnologías\n 4.Salir\n");
                int grupo;
                try {
                    grupo = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Indica un número válido");
                    scanner.nextLine();
                    continue;
                }

                switch (grupo) {
                    case 1 -> {
                        boolean volver = false;
                        while (!volver) {
                            System.out.println("--Gestion desarrolladores--");
                            System.out.println("\n 1-Añadir\n 2- Eliminar\n 3-Consultar\n 4-Modificar\n 5-Salir\n");

                            int opcion;
                            try {
                                opcion = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Indica un número válido");
                                scanner.nextLine();
                                continue;
                            }
                            switch (opcion) {
                                case 1 ->
                                    Desarrollador.añadirDesarrollador(sessionFactory);

                                case 2 ->
                                    Desarrollador.eliminarDesarrollador(sessionFactory);

                                case 3 ->
                                    Desarrollador.verDesarrolladores(sessionFactory);

                                case 4 ->
                                    Desarrollador.modificarDesarrollador(sessionFactory);

                                case 5 ->
                                    volver = true;
                                default ->
                                    System.out.print("Opción no válida");

                            }
                        }
                    }

                    case 2 -> {
                        boolean volver = false;
                        while (!volver) {
                            System.out.println("--Gestion proyectos--");
                            System.out.println("\n 1-Añadir\n 2- Eliminar\n 3-Consultar\n 4-Modificar\n 5-Salir\n");

                            int opcion;
                            try {
                                opcion = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Indica un número válido");
                                scanner.nextLine();
                                continue;
                            }
                            switch (opcion) {
                                case 1 ->
                                    Proyecto.añadirProyecto(sessionFactory);

                                case 2 ->
                                    Proyecto.eliminarProyecto(sessionFactory);

                                case 3 ->
                                    Proyecto.verProyectos(sessionFactory);

                                case 4 ->
                                    Proyecto.modificarProyecto(sessionFactory);

                                case 5 ->
                                    volver = true;
                                default ->
                                    System.out.print("Opción no válida");
                            }

                        }

                    }
                    case 3 -> {
                        boolean volver = false;
                        while (!volver) {
                            System.out.println("--Gestion tecnologias--");
                            System.out.println("\n 1-Añadir\n 2- Eliminar\n 3-Consultar\n 4-Asociar a proyecto\n 5-Salir\n");

                            int opcion;
                            try {
                                opcion = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Indica un número válido");
                                scanner.nextLine();
                                continue;
                            }
                            switch (opcion) {
                                case 1 ->
                                    Tecnologia.añadirTecnologia(sessionFactory);

                                case 2 ->
                                    Tecnologia.eliminarTecnologia(sessionFactory);

                                case 3 ->
                                    Tecnologia.verTecnologia(sessionFactory);

                                case 4 ->
                                    Tecnologia.asociarAProyecto(sessionFactory);

                                case 5 ->
                                    volver = true;

                                default ->
                                    System.out.print("Opción no válida");
                            }
                        }
                    }
                    case 4 ->
                        salir = true;
                    default ->
                        System.out.print("Opción no válida");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
            scanner.close();
        }
    }
}
