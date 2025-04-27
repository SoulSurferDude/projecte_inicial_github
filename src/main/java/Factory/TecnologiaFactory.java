/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Model.Proyecto;
import Model.Tecnologia;
import java.util.Set;

/**
 *
 * @author Bogdan G
 */
public class TecnologiaFactory {
   
    
    public static Tecnologia crearTecnologia(String nombre, String version, Set<Proyecto> proyectos)
            throws IllegalArgumentException{
        if (nombre.isEmpty() || version.isEmpty()) {
            throw new IllegalArgumentException("Indica la información completa");
        }
        Tecnologia tecnologia = new Tecnologia();
        tecnologia.setNombre(nombre);
        tecnologia.setVersion(version);
        tecnologia.setProyectos(proyectos);
        
        return tecnologia;
}
}