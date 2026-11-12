/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Especie implements Comparable<Especie>{
    private int id;
    private String nombreComun;
    private String nombreCientifico;
    private String foto; // Ruta de la foto
    private Patologia patologia; // no es obligatorio

    public Especie() {
    }

    public Especie(int id, String nombreComun) {
        this.id = id;
        this.nombreComun = nombreComun;
    }

    public Especie(int id, String nombreComun, String nombreCientifico) {
        this.id = id;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
    }
    
    public Especie(int id, String nombreComun, String nombreCientifico, String foto, Patologia patologia) {
        this.id = id;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.foto = foto;
        this.patologia = patologia;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Patologia getPatologia() {
        return patologia;
    }

    public void setPatologia(Patologia patologia) {
        this.patologia = patologia;
    }

    @Override
    public String toString() {
        return "Especie{" + "nombreComun=" + nombreComun + ", nombreCientifico=" + nombreCientifico + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
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
        final Especie other = (Especie) obj;
        return this.id == other.id;
    }
    
    @Override
    public int compareTo(Especie otraEspecie) {
        // comparamos los IDs de las especies para ordenarlas de menor a mayor
        if (this.id < otraEspecie.getId()) {
            return -1; // Yo voy antes
        } else if (this.id > otraEspecie.getId()) {
            return 1;  // Yo voy después
        } else {
            return 0;  // Somos iguales
        }
    }
}
