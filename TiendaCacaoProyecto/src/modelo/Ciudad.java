/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Ciudad implements Comparable<Ciudad>{
    private int id; 
    private String nombreCiudad;
    private Pais pais;

    public Ciudad() {
    }

    public Ciudad(int id, String nombreCiudad) {
        this.id = id;
        this.nombreCiudad = nombreCiudad;
    }

    public Ciudad(int id, String nombreCiudad, Pais pais) {
        this.id = id;
        this.nombreCiudad = nombreCiudad;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Ciudad{" + "id=" + id + ", nombreCiudad=" + nombreCiudad + ", pais=" + pais + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
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
        final Ciudad other = (Ciudad) obj;
        return this.id == other.id;
    }
    
    @Override
    public int compareTo(Ciudad otraCiudad) {
        // comparamos los IDs de las ciudades para ordenarlas de menor a mayor
        if (this.id < otraCiudad.getId()) {
            return -1; // Yo voy antes
        } else if (this.id > otraCiudad.getId()) {
            return 1;  // Yo voy después
        } else {
            return 0;  // Somos iguales
        }
    }
    
    
}
