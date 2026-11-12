/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Patologia implements Comparable<Patologia>{
    private int id;
    private String nombreComun;
    private String nombreCientifico;

    public Patologia() {
    }

    public Patologia(int id, String nombreComun, String nombreCientifico) {
        this.id = id;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    @Override
    public String toString() {
        return "Patologia{" + "id=" + id + ", nombreComun=" + nombreComun + ", nombreCientifico=" + nombreCientifico + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
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
        final Patologia other = (Patologia) obj;
        return this.id == other.id;
    }
    
    @Override
    public int compareTo(Patologia otraPatologia) {
        // Comparamos el ID de esta patología con el ID de la otra
        return Integer.compare(this.id, otraPatologia.getId());
    }
    
    
}
