/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class AdministradorSistema extends Usuarios {
    // Atributos específicos de la clase hija
    private int nivel;
    private String contrasena;

    // Constructor vacío
    public AdministradorSistema() {
        super();
    }

    // Constructor completo
    public AdministradorSistema(int id, String nombre, String apellido, String telefono, String correo, String cargo, int nivel, String contrasena) {
        super(id, nombre, apellido, telefono, correo, cargo);
        this.nivel = nivel;
        this.contrasena = contrasena;
    }

    // Getters y Setters específicos
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Reportes en consola[cite: 1]
    @Override
    public String toString() {
        return super.toString() + " AdministradorSistema{" + "nivel=" + nivel + ", contrasena=" + contrasena + '}';
    }
}