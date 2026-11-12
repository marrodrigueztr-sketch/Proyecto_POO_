/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;
import java.util.Objects;
/**
 *
 * @author Marcelo
 */
public abstract class Usuarios {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String cargo;

    public Usuarios(){
    }

    public Usuarios(int id, String nombre, String apellido, String telefono, String correo, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " " + apellido + " | Tel: " + telefono
                + " | Correo: " + correo + " | Cargo: " + cargo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // instanceof (y no getClass) para que el correo no se repita entre roles distintos
        if (!(obj instanceof Usuarios usuario)) return false;
        return correo != null && usuario.correo != null && correo.equalsIgnoreCase(usuario.correo);
    }

    @Override
    public int hashCode() {
        return correo == null ? 0 : correo.toLowerCase().hashCode();
    }
}