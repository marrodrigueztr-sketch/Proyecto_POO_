/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Marcelo
 */
public class Movimiento_logistico {
    private int id;
    private String fecha;
    private String tipo; // Entrada, Salida, Traslado[cite: 1]
    private int cantidad;
    private Productos productoInvolucrado;
    private OperarioBodega operarioResponsable;

    public Movimiento_logistico() {}

    public Movimiento_logistico(int id, String fecha, String tipo, int cantidad, Productos productoInvolucrado, OperarioBodega operarioResponsable) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.productoInvolucrado = productoInvolucrado;
        this.operarioResponsable = operarioResponsable;
    }
    // Generar Getters y Setters

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Productos getProductoInvolucrado() {
        return productoInvolucrado;
    }

    public OperarioBodega getOperarioResponsable() {
        return operarioResponsable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setProductoInvolucrado(Productos productoInvolucrado) {
        this.productoInvolucrado = productoInvolucrado;
    }

    public void setOperarioResponsable(OperarioBodega operarioResponsable) {
        this.operarioResponsable = operarioResponsable;
    }
    @Override
    public String toString() {
        String nombreProducto = (productoInvolucrado != null) ? productoInvolucrado.getNombre() : "N/A";
        String nombreOperario = (operarioResponsable != null) ? operarioResponsable.getNombre() : "N/A";

        return "Movimiento_logistico{" + "id=" + id + ", fecha=" + fecha + 
               ", tipo=" + tipo + ", cantidad=" + cantidad + 
               ", productoInvolucrado=" + nombreProducto + 
               ", operarioResponsable=" + nombreOperario + '}';
    }
}