/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Bogdan G
 */
@Entity
@Table(name = "tecnologias")
public class Tecnologia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String version;
    
   @ManyToMany(mappedBy = "tecnologias")
    private Set<Proyecto> proyectos = new HashSet<>();

    public Tecnologia(String nombre, String version) {
        this.nombre = nombre;
        this.version = version;
    }
   
   public Tecnologia(){}


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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
   
   public static void addTechnology(SessionFactory sessionFactory){
       Scanner scanner = new Scanner(System.in);
       System.out.println("Indica el nombre de la tecnología a añadir");
       String nombre = scanner.nextLine();
       System.out.println("Indica la versión");
       String version = scanner.nextLine();
       if (nombre.isEmpty()||version.isEmpty()) {
           System.out.println("Indica el nombre y la versión de la tecnología");
       }
       try(Session session = sessionFactory.openSession()){
       session.beginTransaction();
       Tecnologia tecnologia = new Tecnologia(nombre, version);
       session.persist(tecnologia);
           System.out.println("Tecnologia añadida");
           session.close();
       }catch(Exception e){
           System.out.println("Error al añadir tecnología"+e.getMessage());
       }
   }
   
   public static void eliminarTecnologia(SessionFactory sessionFactory){
   Scanner scanner = new Scanner(System.in);
       System.out.println("Indica el Id de la tecnologia a eliminar");
      Long id = scanner.nextLong();
       if (id==null) {
           System.out.println("Indica el ID");
       }
       try(Session session = sessionFactory.openSession()){
       session.beginTransaction();
       Tecnologia tecnologiaEliminar = session.get(Tecnologia.class, id);
       session.delete(tecnologiaEliminar);
       session.getTransaction().commit();
       session.close();
       }
   }
   
   public static void verTecnologia(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            
             CriteriaBuilder cb=session.getCriteriaBuilder();
            CriteriaQuery<Tecnologia> cq = cb.createQuery(Tecnologia.class);
            Root<Tecnologia> root = cq.from(Tecnologia.class);
            cq.select(root);
            List<Tecnologia> tecnologias=session.createQuery(cq).getResultList();
            
            System.out.println("\n--TECNOLOGIAS--");
            if (tecnologias.isEmpty()) {
                System.out.println("No hay registros");
            } else {
                for (Tecnologia e : tecnologias) {
                    System.out.println("ID:" + e.getId() + "\n" + "Nombre:" + e.getNombre() + "\n" + "Version:" + e.getVersion());
                    session.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la lista de tecnologías");
        }
    }
   
   public static void asociarAProyecto(SessionFactory sessionFactory){
       Scanner scanner = new Scanner(System.in);
       System.out.println("Indica ID de la tecnologia que queires asociar");
       Long idTecnologia= scanner.nextLong();
       scanner.nextLine();
       System.out.println("Indica a que proyecto quieres asociar");
       Long idProyecto = scanner.nextLong();
       scanner.nextLine();
       
       try(Session session = sessionFactory.openSession()){
       session.beginTransaction();
       Tecnologia tecnologia = session.get(Tecnologia.class, idTecnologia);
       Proyecto proyecto = session.get(Proyecto.class, idProyecto);
           if (tecnologia !=null && proyecto !=null) {
               tecnologia.getProyectos().add(proyecto);
               proyecto.getTecnologias().add(tecnologia);
               session.merge(tecnologia);
               session.getTransaction().commit();
               System.out.println("Tecnologia asociada correctamente");
               session.close();
           }else{
               System.out.println("Proyecto o tecnologia no existe");
               session.getTransaction().rollback();
           }
       }catch(Exception e) {
           System.out.println("Error en la operación" + e.getMessage());
       }     
   }
}
