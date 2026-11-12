/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Propietario extends Usuario {

    public Propietario() {
    }
    
    public Propietario(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena, Finca finca) {
        super(idCedula, nombre, apellido, telefono, correo, direccion, roll, contrasena, finca);
    }

    @Override
    public String toString() {
        return "Propietario->" + super.toString();
    }
    
    
    
}
