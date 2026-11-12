/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class Ciudad {
    private int id;
    private String nombre;
    private Pais paisReferencia;

    public Ciudad() {}

    public Ciudad(int id, String nombre, Pais paisReferencia) {
        this.id = id;
        this.nombre = nombre;
        this.paisReferencia = paisReferencia;
    }
    // Generar Getters y Setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pais getPaisReferencia() {
        return paisReferencia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPaisReferencia(Pais paisReferencia) {
        this.paisReferencia = paisReferencia;
    }

    @Override
    public String toString() {
        return "Ciudad{" + "id=" + id + ", nombre=" + nombre + ", paisReferencia=" + paisReferencia + '}';
    }
    
}