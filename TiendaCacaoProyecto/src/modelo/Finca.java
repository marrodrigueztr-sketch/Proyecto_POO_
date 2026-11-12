/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.ArrayList;
/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Finca implements Comparable<Finca>{
    private int idFinca;
    private String nombreFinca; 
    private String direccionFinca; 
    private Ciudad ciudad;
    private ArrayList<Especie> arregloespecies;

    public Finca() {
    }

    public Finca(int idFinca, String nombreFinca, String direccionFinca, Ciudad ciudad, ArrayList<Especie> arregloespecies) {
        this.idFinca = idFinca;
        this.nombreFinca = nombreFinca;
        this.direccionFinca = direccionFinca;
        this.ciudad = ciudad;
        this.arregloespecies = arregloespecies;
    }
    
    // Es el contructor mas basico, por si el usuario no sabe a que ciudad asociarla o no tiene una especie sembrada poder crearla con la informacion minima
    public Finca(int idFinca, String nombreFinca, String direccionFinca) {
        this.idFinca = idFinca;
        this.nombreFinca = nombreFinca;
        this.direccionFinca = direccionFinca;
    }

    public int getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(int idFinca) {
        this.idFinca = idFinca;
    }

    public String getNombreFinca() {
        return nombreFinca;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca = nombreFinca;
    }

    public String getDireccionFinca() {
        return direccionFinca;
    }

    public void setDireccionFinca(String direccionFinca) {
        this.direccionFinca = direccionFinca;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public ArrayList<Especie> getArregloespecies() {
        return arregloespecies;
    }

    public void setArregloespecies(ArrayList<Especie> arregloespecies) {
        this.arregloespecies = arregloespecies;
    }
    
    @Override
    public String toString() {
        return "Finca{" + "idFinca=" + idFinca + ", nombreFinca=" + nombreFinca + ", direccionFinca=" + direccionFinca + ", ciudad=" + ciudad + ", arregloespecies=" + arregloespecies + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idFinca;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final Finca other = (Finca) obj;
        return this.idFinca == other.idFinca;
    }
    @Override
    public int compareTo(Finca otraFinca) {
        // ordena las fincas de menor a mayor usando su idFinca"
        if (this.idFinca < otraFinca.getIdFinca()) {
            return -1; // Yo voy antes
        } else if (this.idFinca > otraFinca.getIdFinca()) {
            return 1;  // Yo voy después
        } else {
            return 0;  // Somos iguales
        }
    }
    
    
    
}
