/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Model.Desarrollador;

/**
 *
 * @author Bogdan G
 */
public class DesarrolladorFactory {
    public static Desarrollador crearDesarrollador(String nombre, String correo, String especialidad, String telefono)
            throws IllegalArgumentException{
        if (nombre.isEmpty() || correo.isEmpty() || especialidad.isEmpty() || telefono.isEmpty()) {
            throw new IllegalArgumentException("Indica la información completa");
        }
        Desarrollador desarrollador = new Desarrollador();
        desarrollador.setNombre(nombre);
        desarrollador.setCorreo(correo);
        desarrollador.setEspecialidad(especialidad);
        desarrollador.setTelefono(telefono);
        
        return desarrollador;
    }
    
}
