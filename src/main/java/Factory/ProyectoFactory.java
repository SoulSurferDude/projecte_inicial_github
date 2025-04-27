/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Model.Desarrollador;
import Model.Proyecto;
import java.sql.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Bogdan G
 */
public class ProyectoFactory {
    
    private final SessionFactory sessionFactory;
    public ProyectoFactory(SessionFactory sessionFactory){
    this.sessionFactory=sessionFactory;
    }
    public Proyecto crearProyecto(String nombre, Date fechaInicio, double presupuesto, Desarrollador desarrolladorAsignado)
            throws IllegalArgumentException{
        if (nombre.isEmpty() || fechaInicio==null || desarrolladorAsignado==null) {
            throw new IllegalArgumentException("Indica la información completa");
        }
        if (presupuesto<0) {
            throw new IllegalArgumentException("El presupuesto no puede ser negativo");
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setFechaInicio(fechaInicio);
        proyecto.setPresupuesto(presupuesto);
        proyecto.setDesarrolladorAsignado(desarrolladorAsignado);
        
        try (Session session = sessionFactory.openSession()){
        session.beginTransaction();
        session.persist(proyecto);
        session.getTransaction().commit();
        }
        
        return proyecto;
                
}
}