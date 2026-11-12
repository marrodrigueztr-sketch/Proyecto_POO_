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

public class Productos implements Comparable<Productos> {
    
    private int id;
    private String sku;
    private String nombre;
    private String descripcion;
    private float preciouniitario;
    private int stockactual;
    private String proveedor;
    private String tipo; // Reemplaza por completo a la herencia
    private int idUsuarioRegistro; // id del usuario (operario/admin) que insertó el producto

    public Productos(int id, String sku, String nombre, String descripcion, float preciouniitario, int stockactual, String proveedor, String tipo) {
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preciouniitario = preciouniitario;
        this.stockactual = stockactual;
        this.proveedor = proveedor;
        this.tipo = tipo;
    }

    // Constructor sobrecargado: incluye el id de quien registró el producto
    public Productos(int id, String sku, String nombre, String descripcion, float preciouniitario, int stockactual, String proveedor, String tipo, int idUsuarioRegistro) {
        this(id, sku, nombre, descripcion, preciouniitario, stockactual, proveedor, tipo);
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    // Getters y Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSku() { return sku; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public float getPreciouniitario() { return preciouniitario; }
    public void setPreciouniitario(float preciouniitario) { this.preciouniitario = preciouniitario; }
    
    public int getStockactual() { return stockactual; }
    public void setStockactual(int stockactual) { this.stockactual = stockactual; }
    
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdUsuarioRegistro() { return idUsuarioRegistro; }
    public void setIdUsuarioRegistro(int idUsuarioRegistro) { this.idUsuarioRegistro = idUsuarioRegistro; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Productos producto = (Productos) obj;
        return sku.equals(producto.sku);
    }

    @Override
    public int compareTo(Productos otro) {
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | SKU: " + sku + " | Nombre: " + nombre + 
               " | Tipo: " + tipo + " | Stock: " + stockactual + " | Precio: $" + preciouniitario +
               " | Registrado por: " + idUsuarioRegistro;
    }
}