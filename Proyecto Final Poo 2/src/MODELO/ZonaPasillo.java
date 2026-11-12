/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;
import java.util.ArrayList;
/**
 *
 * @author Marcelo
 */
public class ZonaPasillo {
    private int id;
    private String codigoArea;
    private int capacidadMaxima;
    private Almacen almacenPertenece;
    private ArrayList<Productos> listaProductos; // Agregación de productos[cite: 1, 2]

    public ZonaPasillo() {
        this.listaProductos = new ArrayList<>();
    }

    public ZonaPasillo(int id, String codigoArea, int capacidadMaxima, Almacen almacenPertenece, ArrayList<Productos> listaProductos) {
        this.id = id;
        this.codigoArea = codigoArea;
        this.capacidadMaxima = capacidadMaxima;
        this.almacenPertenece = almacenPertenece;
        this.listaProductos = listaProductos;
    }
    // Generar Getters y Setters

    public int getId() {
        return id;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public Almacen getAlmacenPertenece() {
        return almacenPertenece;
    }

    public ArrayList<Productos> getListaProductos() {
        return listaProductos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setAlmacenPertenece(Almacen almacenPertenece) {
        this.almacenPertenece = almacenPertenece;
    }

    public void setListaProductos(ArrayList<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }
    @Override
    public String toString() {
        String nombreAlmacen = (almacenPertenece != null) ? almacenPertenece.getNombre() : "Sin almacén";
        int totalProductos = (listaProductos != null) ? listaProductos.size() : 0;

        return "ZonaPasillo{" + "id=" + id + ", codigoArea=" + codigoArea + 
               ", capacidadMaxima=" + capacidadMaxima + 
               ", almacenPertenece=" + nombreAlmacen + 
               ", cantidadProductosAlmacenados=" + totalProductos + '}';
    }
    }
