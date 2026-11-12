/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class Almacen {
    private int id;
    private String nombre;
    private Ciudad ciudad;
    private GerenteGeneral gerenteAsignado;

    public Almacen() {}

    public Almacen(int id, String nombre, Ciudad ciudad, GerenteGeneral gerenteAsignado) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.gerenteAsignado = gerenteAsignado;
    }
    // Generar Getters y Setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public GerenteGeneral getGerenteAsignado() {
        return gerenteAsignado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public void setGerenteAsignado(GerenteGeneral gerenteAsignado) {
        this.gerenteAsignado = gerenteAsignado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Almacen other = (Almacen) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        // Se extrae el nombre de la ciudad y del gerente para un reporte más limpio
        String nombreCiudad = (ciudad != null) ? ciudad.getNombre() : "Sin ciudad";
        String nombreGerente = (gerenteAsignado != null) ? gerenteAsignado.getNombre() : "Sin asignar";

        return "Almacen{" + "id=" + id + ", nombre=" + nombre + 
               ", ciudad=" + nombreCiudad + 
               ", gerenteAsignado=" + nombreGerente + '}';
    }
}