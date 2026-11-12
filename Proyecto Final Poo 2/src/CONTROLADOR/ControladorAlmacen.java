/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Almacen;
import MODELO.OperarioBodega;
import MODELO.Usuarios;
import MODELO.ZonaPasillo;
import UTILIDADES.Ordenamientos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador CRUD de almacenes. Por requisito, los almacenes se ordenan con el
 * ordenamiento nativo de Java (List.sort) por ID ascendente.
 *
 * @author Marcelo
 */
public class ControladorAlmacen implements ICRUD {

    // static: todas las ventanas comparten la misma lista
    private static ArrayList<Almacen> listaAlmacenes = new ArrayList<>();

    public ControladorAlmacen() {
    }

    @Override
    public boolean insertar(Object obj) {
        Almacen nuevoAlmacen = (Almacen) obj;
        if (existeId(nuevoAlmacen.getId())) return false; // ID repetido
        listaAlmacenes.add(nuevoAlmacen);
        this.ordenar(); // Ordenamiento nativo
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaAlmacenes.isEmpty()) {
            System.out.println("No hay almacenes registrados.");
        } else {
            System.out.println("\n--- LISTA DE ALMACENES ---");
            for (Almacen a : listaAlmacenes) {
                System.out.println(a.toString());
            }
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int idBuscado = -1;
        if (obj instanceof Integer) {
            idBuscado = (Integer) obj;
        } else if (obj instanceof Almacen) {
            idBuscado = ((Almacen) obj).getId();
        }
        this.ordenar(); // la búsqueda binaria exige la lista ordenada por ID
        // Búsqueda Binaria
        return Ordenamientos.busquedaBinaria(listaAlmacenes, idBuscado, Almacen::getId);
    }

    @Override
    public void ordenar() {
        listaAlmacenes.sort(Comparator.comparingInt(Almacen::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Burbuja secundaria por Nombre (se usa desde la consola)
        Comparator<Almacen> c = Comparator.comparing(Almacen::getNombre, String.CASE_INSENSITIVE_ORDER);
        List<Almacen> ordenada = Ordenamientos.burbuja(listaAlmacenes, descendente == 1 ? c.reversed() : c);
        listaAlmacenes.clear();
        listaAlmacenes.addAll(ordenada);
    }

    @Override
    public Object update(Object obj) {
        Almacen entidadActualizada = (Almacen) obj;
        Almacen existente = (Almacen) consultarUno(entidadActualizada.getId());
        if (existente != null) {
            existente.setNombre(entidadActualizada.getNombre());
            existente.setCiudad(entidadActualizada.getCiudad());
            existente.setGerenteAsignado(entidadActualizada.getGerenteAsignado());
            return existente;
        }
        return null;
    }

    /** Elimina el almacén, sus zonas, y deja sin almacén a los operarios asignados. */
    @Override
    public boolean delete(Object obj) {
        Almacen a = (Almacen) consultarUno(obj);
        if (a == null) return false;

        new ControladorZona().getListaZonas().removeIf(
                z -> z.getAlmacenPertenece() != null && z.getAlmacenPertenece().getId() == a.getId());

        for (Usuarios u : new ControladorUsuario().getListaUsuarios()) {
            if (u instanceof OperarioBodega o && o.getAlmacenAsignado() != null
                    && o.getAlmacenAsignado().getId() == a.getId()) {
                o.setAlmacenAsignado(null);
            }
        }
        listaAlmacenes.remove(a);
        return true;
    }

    public boolean delete(Object obj, Usuarios ejecutor) {
        return ControladorUsuario.puedeEliminar(ejecutor) && delete(obj);
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    public ArrayList<Almacen> getListaAlmacenes() {
        return this.listaAlmacenes;
    }

    /** @return cuántas zonas tiene el almacén */
    public int contarZonas(int idAlmacen) {
        int n = 0;
        for (ZonaPasillo z : new ControladorZona().getListaZonas()) {
            if (z.getAlmacenPertenece() != null && z.getAlmacenPertenece().getId() == idAlmacen) n++;
        }
        return n;
    }

    // ---------------- Ordenamientos para la GUI (sobre copias) ----------------
    /** Ordenamiento obligatorio de almacenes: nativo (List.sort) por ID ascendente. */
    public List<Almacen> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaAlmacenes, Comparator.comparingInt(Almacen::getId));
    }

    public List<Almacen> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaAlmacenes, Comparator.comparingInt(Almacen::getId).reversed());
    }

    public List<Almacen> ordenarMergesortNombre() {
        return Ordenamientos.mergesort(listaAlmacenes, Comparator.comparing(Almacen::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    /** Consulta filtrada por ciudad (idCiudad = 0 para todas). */
    public List<Almacen> filtrarPorCiudad(int idCiudad) {
        List<Almacen> r = new ArrayList<>();
        for (Almacen a : listaAlmacenes) {
            if (idCiudad == 0 || (a.getCiudad() != null && a.getCiudad().getId() == idCiudad)) r.add(a);
        }
        return r;
    }
}
