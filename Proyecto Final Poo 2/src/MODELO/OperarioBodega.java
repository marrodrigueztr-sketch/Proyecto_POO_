/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class OperarioBodega extends Usuarios {
    private float salario;
    private String turno;
    private Almacen almacenAsignado;

    public OperarioBodega() {}

    public OperarioBodega(int id, String nombre, String apellido, String telefono, String correo, String cargo, float salario, String turno, Almacen almacenAsignado) {
        super(id, nombre, apellido, telefono, correo, cargo);
        this.salario = salario;
        this.turno = turno;
        this.almacenAsignado = almacenAsignado;
    }
    // Generar Getters y Setters

    public float getSalario() {
        return salario;
    }

    public String getTurno() {
        return turno;
    }

    public Almacen getAlmacenAsignado() {
        return almacenAsignado;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setAlmacenAsignado(Almacen almacenAsignado) {
        this.almacenAsignado = almacenAsignado;
    }

    @Override
    public String toString() {
        return "OperarioBodega{" + "salario=" + salario + ", turno=" + turno + ", almacenAsignado=" + almacenAsignado + '}';
    }
    
}
