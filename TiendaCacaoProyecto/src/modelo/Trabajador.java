/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Trabajador extends Usuario{
    private String cargo;
    private double salario;
    
    public Trabajador() {
        super();
    }

    public Trabajador(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena, Finca finca, String cargo, double salario) {
        super(idCedula, nombre, apellido, telefono, correo, direccion, roll, contrasena, finca);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Trabajador->{" + "cargo=" + cargo + ", salario = $" + salario + '}';
    }
        
    
    
}
