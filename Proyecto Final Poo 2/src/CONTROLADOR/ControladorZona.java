package CONTROLADOR;

import MODELO.Productos;
import MODELO.Usuarios;
import MODELO.ZonaPasillo;
import UTILIDADES.Ordenamientos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador CRUD de zonas/pasillos. Cada zona pertenece a un almacén y
 * agrega una lista de productos almacenados (hasta su capacidad máxima).
 *
 * @author Grupo 1
 */
public class ControladorZona implements ICRUD {

    private static ArrayList<ZonaPasillo> listaZonas = new ArrayList<>();

    @Override
    public boolean insertar(Object obj) {
        ZonaPasillo nueva = (ZonaPasillo) obj;
        if (existeId(nueva.getId())) return false;
        if (nueva.getListaProductos() == null) nueva.setListaProductos(new ArrayList<>());
        listaZonas.add(nueva);
        ordenar();
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaZonas.isEmpty()) {
            System.out.println("No hay zonas registradas.");
        } else {
            for (ZonaPasillo z : listaZonas) System.out.println(z);
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int id = (obj instanceof ZonaPasillo) ? ((ZonaPasillo) obj).getId() : (Integer) obj;
        ordenar();
        return Ordenamientos.busquedaBinaria(listaZonas, id, ZonaPasillo::getId);
    }

    @Override
    public void ordenar() {
        listaZonas.sort(Comparator.comparingInt(ZonaPasillo::getId));
    }

    @Override
    public void ordenar(int descendente) {
        Comparator<ZonaPasillo> c = Comparator.comparingInt(ZonaPasillo::getCapacidadMaxima);
        List<ZonaPasillo> ordenada = Ordenamientos.burbuja(listaZonas, descendente == 1 ? c.reversed() : c);
        listaZonas.clear();
        listaZonas.addAll(ordenada);
    }

    /**
     * Actualiza código, capacidad y almacén (conserva los productos).
     *
     * @return la zona actualizada, o null si no existe o la nueva capacidad es menor
     *         que la cantidad de productos que ya tiene
     */
    @Override
    public Object update(Object obj) {
        ZonaPasillo datos = (ZonaPasillo) obj;
        ZonaPasillo existente = (ZonaPasillo) consultarUno(datos.getId());
        if (existente == null) return null;
        if (datos.getCapacidadMaxima() < existente.getListaProductos().size()) return null;
        existente.setCodigoArea(datos.getCodigoArea());
        existente.setCapacidadMaxima(datos.getCapacidadMaxima());
        existente.setAlmacenPertenece(datos.getAlmacenPertenece());
        return existente;
    }

    @Override
    public boolean delete(Object obj) {
        ZonaPasillo z = (ZonaPasillo) consultarUno(obj);
        if (z == null) return false;
        listaZonas.remove(z);
        return true;
    }

    public boolean delete(Object obj, Usuarios ejecutor) {
        return ControladorUsuario.puedeEliminar(ejecutor) && delete(obj);
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    public ArrayList<ZonaPasillo> getListaZonas() {
        return listaZonas;
    }

    /**
     * Agrega un producto a la zona.
     *
     * @return null si se agregó, o el mensaje de error
     */
    public String agregarProducto(int idZona, Productos producto) {
        ZonaPasillo z = (ZonaPasillo) consultarUno(idZona);
        if (z == null) return "No existe la zona con ID " + idZona + ".";
        if (producto == null) return "Seleccione un producto.";
        if (z.getListaProductos().contains(producto)) return "El producto ya está en esta zona.";
        if (z.getListaProductos().size() >= z.getCapacidadMaxima()) {
            return "La zona alcanzó su capacidad máxima (" + z.getCapacidadMaxima() + " productos).";
        }
        z.getListaProductos().add(producto);
        return null;
    }

    /** @return true si el producto estaba en la zona y se quitó */
    public boolean quitarProducto(int idZona, Productos producto) {
        ZonaPasillo z = (ZonaPasillo) consultarUno(idZona);
        return z != null && producto != null && z.getListaProductos().remove(producto);
    }

    // ---------------- Ordenamientos para la GUI ----------------
    public List<ZonaPasillo> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaZonas, Comparator.comparingInt(ZonaPasillo::getId).reversed());
    }

    /** Mergesort por código de área (el "nombre" de la zona). */
    public List<ZonaPasillo> ordenarMergesortCodigo() {
        return Ordenamientos.mergesort(listaZonas, Comparator.comparing(ZonaPasillo::getCodigoArea, String.CASE_INSENSITIVE_ORDER));
    }

    public List<ZonaPasillo> ordenarBurbujaCapacidad(boolean descendente) {
        Comparator<ZonaPasillo> c = Comparator.comparingInt(ZonaPasillo::getCapacidadMaxima);
        return Ordenamientos.burbuja(listaZonas, descendente ? c.reversed() : c);
    }

    public List<ZonaPasillo> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaZonas, Comparator.comparingInt(ZonaPasillo::getId));
    }

    /** Consulta filtrada por almacén (idAlmacen = 0 para todos). */
    public List<ZonaPasillo> filtrarPorAlmacen(int idAlmacen) {
        List<ZonaPasillo> r = new ArrayList<>();
        for (ZonaPasillo z : listaZonas) {
            if (idAlmacen == 0 || (z.getAlmacenPertenece() != null && z.getAlmacenPertenece().getId() == idAlmacen)) r.add(z);
        }
        return r;
    }
}
