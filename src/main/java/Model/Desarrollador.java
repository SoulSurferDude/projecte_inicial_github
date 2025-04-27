/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Factory.DesarrolladorFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 *
 * @author Bogdan G
 */
@Entity
@Table(name = "desarrolladores")
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String correo;
    private String especialidad;
    private String telefono;

    public Desarrollador() {
    }

    public Desarrollador(String nombre, String correo, String especialidad, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

       public Desarrollador(String nombre, String correo, String especialidad, String telefono, int edad) {
        this.nombre = nombre;
        this.correo = correo;
        this.especialidad = especialidad;
        this.telefono = telefono;
       
        
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    

    
    public static void addDeveloper(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--Añadir desarrollador--");
        System.out.println("Indica el nombre del desarrollador:");
        String nombre = scanner.nextLine();
        System.out.println("Indica el correo del desarrollador:");
        String correo = scanner.nextLine();
        System.out.println("Indica la especialidad del desarrollador:");
        String especialidad = scanner.nextLine();
        System.out.println("Indica el teléfono del desarrollador:");
        String telefono = scanner.nextLine();
        if (nombre.isEmpty() || correo.isEmpty() || especialidad.isEmpty() || telefono.isEmpty()) {
            System.out.println("Rellena todos los campos");
            return;
        }            
        Desarrollador dev = DesarrolladorFactory.crearDesarrollador(nombre, correo, especialidad,telefono);
        

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(dev);
            session.getTransaction().commit();
            System.out.println("Desarrollador añadido");
        } catch (Exception e) {
            System.err.println("error al añadir desarrollador" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void eliminarDesarrollador(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indica el ID del desarrolador a eliminar");
        Long id = scanner.nextLong();
        if (id == null) {
            System.out.println("Indica el ID");
            return;
        } else {
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Desarrollador desarrolladorEliminar = session.get(Desarrollador.class, id);
            if (desarrolladorEliminar != null) {
                session.delete(desarrolladorEliminar);
                session.getTransaction().commit();
                System.out.println("Desarrollador eliminado");
                session.close();
            } else {
                System.out.println("No se ha encontrado desarrollador con el ID indicado");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar desarrollador" + e.getMessage());
        }
    }

    public static void verDesarrolladores(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb=session.getCriteriaBuilder();
            CriteriaQuery<Desarrollador> cq = cb.createQuery(Desarrollador.class);
            Root<Desarrollador> root = cq.from(Desarrollador.class);
            cq.select(root);
            List<Desarrollador> desarrolladores=session.createQuery(cq).getResultList();
            
            System.out.println("\n--DESARROLLADORES--");
            if (desarrolladores.isEmpty()) {
                System.out.println("No hay registros");
            } else {
                for (Desarrollador e : desarrolladores) {
                    System.out.println("ID:" + e.getId() + "\n" + "Nombre:" + e.getNombre() + "\n" + "Correo:" + e.getCorreo() + "\n" + "Especialidad:" + e.getEspecialidad() + "\n" + "Telefono: " + e.getTelefono());
                    session.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la lista de desarrolladores");
        }
    }

    public static void modificarDesarrollador(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indica el ID del desarrollador a modificar");
        Long id = scanner.nextLong();
        scanner.nextLine();
                
        if (id == null) {
            System.out.println("Indica un ID");
            return;
        } else {
            try (Session session = sessionFactory.openSession()) {
                Desarrollador actualizarDesarrollador = session.get(Desarrollador.class, id);
                if (actualizarDesarrollador != null) {
                    System.out.println("\nDatos del desarrollador:\n NOMBRE: " + actualizarDesarrollador.getNombre() + "\n CORREO: " + actualizarDesarrollador.getCorreo() + "\n ESPECIALIDAD: " + actualizarDesarrollador.getEspecialidad());
                    System.out.println("Introduce los datos nuevos");

                    System.out.println("Nombre del desarrollador:");
                    String nombre = scanner.nextLine();
                    actualizarDesarrollador.setNombre(nombre);

                    System.out.println("Correo del desarrollador:");
                    String correo = scanner.nextLine();
                    actualizarDesarrollador.setCorreo(correo);

                    System.out.println("Especialidad del desarrollador:");
                    String especialidad = scanner.nextLine();
                    actualizarDesarrollador.setEspecialidad(especialidad);
                    
                      System.out.println("Teléfono del desarrollador:");
                    String telefono = scanner.nextLine();
                    actualizarDesarrollador.setTelefono(telefono);

                    session.beginTransaction();
                    session.update(actualizarDesarrollador);
                    session.getTransaction().commit();
                    System.out.println("Datos actualizados");
                    session.close();
                } else {
                    System.out.println("No hay desarrollador con este ID");
                }

            } catch (Exception e) {
                System.out.println("Error al modificar" + e.getMessage());
            }
        }
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
