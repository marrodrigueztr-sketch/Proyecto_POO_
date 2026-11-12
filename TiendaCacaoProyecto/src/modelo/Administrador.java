/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Administrador extends Usuario{

    public Administrador() {
        super();
    }
    
    // se llama al constructor que no tiene finca
    public Administrador(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena) {
        super(idCedula, nombre, apellido, telefono, correo, direccion, roll, contrasena);
    }
    
    @Override
    public String toString() {
        return "Administrador -> " + super.toString();
    }
    
    
}
