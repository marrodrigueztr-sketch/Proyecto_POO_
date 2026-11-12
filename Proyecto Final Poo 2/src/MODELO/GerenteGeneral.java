/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class GerenteGeneral extends Usuarios {
  
    private int nivel;
    private String contrasena;

  
    public GerenteGeneral() {
        super();
    }

    
    public GerenteGeneral(int id, String nombre, String apellido, String telefono, String correo, String cargo, int nivel, String contrasena) {
        super(id, nombre, apellido, telefono, correo, cargo);
        this.nivel = nivel;
        this.contrasena = contrasena;
    }

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

    @Override
    public String toString() {
        return super.toString() + " GerenteGeneral{" + "nivel=" + nivel + ", contrasena=" + contrasena + '}';
    }
}