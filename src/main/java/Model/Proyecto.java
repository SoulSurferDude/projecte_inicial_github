/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Factory.ProyectoFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.sql.Date;
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
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private Date fechaInicio;
    private double presupuesto;

    @ManyToOne()
    @JoinColumn(name = "desarrollador_id", nullable = false)
    private Desarrollador desarrolladorAsignado;

    @ManyToMany
    @JoinTable(name = "proyecto_tecnologia", joinColumns = @JoinColumn(name = "proyecto_id"), inverseJoinColumns = @JoinColumn(name = "tecnologia_id"))
    private Set<Tecnologia> tecnologias = new HashSet<>();

    public Set<Tecnologia> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public Proyecto() {
    }

    public Proyecto(String nombre, Date fechaInicio, double presupuesto, Desarrollador desarrolladorAsignado) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.presupuesto = presupuesto;
        this.desarrolladorAsignado = desarrolladorAsignado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Desarrollador getDesarrolladorAsignado() {
        return desarrolladorAsignado;
    }

    public void setDesarrolladorAsignado(Desarrollador desarrolladorAsignado) {
        this.desarrolladorAsignado = desarrolladorAsignado;
    }

    public static void addProject(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Indica el nombre:");
        String nombre = scanner.nextLine();

        System.out.println("Indica la fecha de inicio(AAAA-MM-DD):");
        Date fecha = Date.valueOf(scanner.nextLine());

        System.out.println("Indica el presupuesto:");
        double presupuesto = Double.parseDouble(scanner.nextLine());

        System.out.println("Indica el ID del desarrolador asignado");
        String idDesarrolladorSinParse = scanner.nextLine();

        if (nombre.isEmpty() && idDesarrolladorSinParse.isEmpty()) {
            System.out.println("Rellena todos los campos");
            return;
        }
        try (Session session = sessionFactory.openSession()) {
            long idDesarrollador = Long.parseLong(idDesarrolladorSinParse);
            Desarrollador desarrollador = session.get(Desarrollador.class, idDesarrollador);
            if (desarrollador == null) {
                System.out.println("No hay desarrollador con este ID");
                return;
            }
            ProyectoFactory factory = new ProyectoFactory(sessionFactory);
            Proyecto nuevoProyecto = factory.crearProyecto(nombre, fecha, presupuesto, desarrollador);
            session.beginTransaction();
            session.persist(nuevoProyecto);
            session.getTransaction().commit();
            System.out.println("Proyecto añadido");
            session.close();
        } catch (Exception e) {
            System.err.println("error al añadir proyecto" + e.getMessage());
        }
    }

    public static void eliminarProyecto(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indica el ID del proyecto a eliminar");
        Long id = scanner.nextLong();
        if (id == null) {
            System.out.println("Indica el ID");
            return;
        } else {
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Proyecto proyectoEliminar = session.get(Proyecto.class, id);
            if (proyectoEliminar != null) {
                session.delete(proyectoEliminar);
                session.getTransaction().commit();
                System.out.println("proyecto eliminado");
                session.close();
            } else {
                System.out.println("No se ha encontrado proyecto con el ID indicado");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar proyecto" + e.getMessage());
        }
    }

    public static void verProyectos(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
            Root<Proyecto> root = cq.from(Proyecto.class);
            cq.select(root);
            List<Proyecto> proyectos = session.createQuery(cq).getResultList();

            System.out.println("\n--PROYECTOS--");
            if (proyectos.isEmpty()) {
                System.out.println("No hay registros");
            } else {
                for (Proyecto e : proyectos) {
                    System.out.println("ID: " + e.getId() + "\n" + "Nombre: " + e.getNombre() + "\n" + "Fecha de inicio: " + e.getFechaInicio() + "\n" + "Presupuesto: " + e.getPresupuesto() + "\n" + "Desarrolador asignado: " + e.getDesarrolladorAsignado().getNombre());
                 System.out.println("Tecnologías asociadas:");
                if (e.getTecnologias().isEmpty()) {
                    System.out.println("- (Ninguna)");
                } else {
                    for (Tecnologia t : e.getTecnologias()) {
                        System.out.println("- " + t.getNombre());
                    }}
                }
                session.close();
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la lista de proyectos");
        }
    }

    public static void modificarProyecto(SessionFactory sessionFactory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indica el ID del proyecto a modificar");
        Long id = scanner.nextLong();
        scanner.nextLine();
        try (Session session = sessionFactory.openSession()) {
            Proyecto actualizarProyecto = session.get(Proyecto.class, id);

            if (actualizarProyecto != null) {
                System.out.println("\nDatos del proyecto:\n NOMBRE: " + actualizarProyecto.getNombre() + "\n FECHA INICIO: " + actualizarProyecto.getFechaInicio() + "\n PRESUPUESTO: " + actualizarProyecto.getPresupuesto() + "\n DESARROLLADOR" + actualizarProyecto.getDesarrolladorAsignado());

                System.out.println("Introduce los datos nuevos");

                System.out.println("Nombre:");
                String nombre = scanner.nextLine();
                actualizarProyecto.setNombre(nombre);

                System.out.println("Fecha Inicio (AAAA-MM-DD):");
                String fechaIniciosinParse = scanner.nextLine();
                Date fechaInicioParseada = Date.valueOf(fechaIniciosinParse);
                actualizarProyecto.setFechaInicio(fechaInicioParseada);

                System.out.println("Presupuesto:");
                Double presupuesto = Double.parseDouble(scanner.nextLine());
                actualizarProyecto.setPresupuesto(presupuesto);

                System.out.println("Desarrollador:");
                Long idDesarrollador = Long.parseLong(scanner.nextLine());
                Desarrollador nuevoDesarrollador = session.get(Desarrollador.class, idDesarrollador);
                actualizarProyecto.setDesarrolladorAsignado(nuevoDesarrollador);

                session.beginTransaction();
                session.update(actualizarProyecto);
                session.getTransaction().commit();
                System.out.println("Datos actualizados");
                session.close();
            } else {
                System.out.println("No hay proyecto con este ID");
            }

        } catch (Exception e) {
            System.out.println("Error al modificar" + e.getMessage());
        }
    }

}
