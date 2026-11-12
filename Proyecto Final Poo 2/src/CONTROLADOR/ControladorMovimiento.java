/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Movimiento_logistico;
import MODELO.Usuarios;
import UTILIDADES.Ordenamientos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador CRUD de movimientos logísticos (Entrada, Salida, Traslado).
 * Las fechas se manejan como texto DD/MM/AAAA y se convierten a fecha para
 * ordenar y filtrar correctamente.
 *
 * @author Marcelo
 */
public class ControladorMovimiento implements ICRUD {

    /** Formato de las fechas de los movimientos. */
    public static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    // static: todas las ventanas comparten la misma lista
    private static ArrayList<Movimiento_logistico> listaMovimientos = new ArrayList<>();

    public ControladorMovimiento() {
    }

    @Override
    public boolean insertar(Object obj) {
        Movimiento_logistico nuevoMovimiento = (Movimiento_logistico) obj;
        if (existeId(nuevoMovimiento.getId())) return false; // ID repetido
        listaMovimientos.add(nuevoMovimiento);
        this.ordenar();
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaMovimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
        } else {
            System.out.println("--- LISTA DE MOVIMIENTOS ---");
            for (Movimiento_logistico m : listaMovimientos) {
                System.out.println(m.toString());
            }
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int idBuscado = -1;
        if (obj instanceof Integer) {
            idBuscado = (Integer) obj;
        } else if (obj instanceof Movimiento_logistico) {
            idBuscado = ((Movimiento_logistico) obj).getId();
        }
        this.ordenar(); // la búsqueda binaria exige la lista ordenada por ID
        return Ordenamientos.busquedaBinaria(listaMovimientos, idBuscado, Movimiento_logistico::getId);
    }

    @Override
    public void ordenar() {
        // Orden natural por ID
        listaMovimientos.sort(Comparator.comparingInt(Movimiento_logistico::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Algoritmo Burbuja por Fecha de movimiento (comparando fechas reales, no texto)
        List<Movimiento_logistico> ordenada = ordenarBurbujaFecha(descendente == 1);
        listaMovimientos.clear();
        listaMovimientos.addAll(ordenada);
    }

    @Override
    public Object update(Object obj) {
        Movimiento_logistico entidadActualizada = (Movimiento_logistico) obj;
        Movimiento_logistico existente = (Movimiento_logistico) consultarUno(entidadActualizada.getId());

        if (existente != null) {
            existente.setFecha(entidadActualizada.getFecha());
            existente.setTipo(entidadActualizada.getTipo());
            existente.setCantidad(entidadActualizada.getCantidad());
            if (entidadActualizada.getProductoInvolucrado() != null) {
                existente.setProductoInvolucrado(entidadActualizada.getProductoInvolucrado());
            }
            if (entidadActualizada.getOperarioResponsable() != null) {
                existente.setOperarioResponsable(entidadActualizada.getOperarioResponsable());
            }
            return existente;
        }
        return null;
    }

    @Override
    public boolean delete(Object obj) {
        Movimiento_logistico m = (Movimiento_logistico) consultarUno(obj);
        if (m != null) {
            listaMovimientos.remove(m);
            return true;
        }
        return false;
    }

    public boolean delete(Object obj, Usuarios ejecutor) {
        return ControladorUsuario.puedeEliminar(ejecutor) && delete(obj);
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    public ArrayList<Movimiento_logistico> getListaMovimientos() {
        return listaMovimientos;
    }

    /**
     * Convierte un texto DD/MM/AAAA en fecha.
     *
     * @param texto fecha en texto
     * @return la fecha, o null si el formato o la fecha no son válidos
     */
    public static LocalDate convertirFecha(String texto) {
        if (texto == null || !texto.matches("\\d{2}/\\d{2}/\\d{4}")) return null;
        try {
            return LocalDate.parse(texto, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static LocalDate fechaDe(Movimiento_logistico m) {
        LocalDate f = convertirFecha(m.getFecha());
        return f != null ? f : LocalDate.MIN;
    }

    // ---------------- Ordenamientos para la GUI (sobre copias) ----------------
    public List<Movimiento_logistico> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaMovimientos, Comparator.comparingInt(Movimiento_logistico::getId).reversed());
    }

    /** Mergesort por nombre del producto involucrado. */
    public List<Movimiento_logistico> ordenarMergesortProducto() {
        return Ordenamientos.mergesort(listaMovimientos, Comparator.comparing(
                m -> m.getProductoInvolucrado() != null ? m.getProductoInvolucrado().getNombre() : "",
                String.CASE_INSENSITIVE_ORDER));
    }

    /** Burbuja por fecha del movimiento. */
    public List<Movimiento_logistico> ordenarBurbujaFecha(boolean descendente) {
        Comparator<Movimiento_logistico> c = Comparator.comparing(ControladorMovimiento::fechaDe);
        return Ordenamientos.burbuja(listaMovimientos, descendente ? c.reversed() : c);
    }

    public List<Movimiento_logistico> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaMovimientos, Comparator.comparingInt(Movimiento_logistico::getId));
    }

    /**
     * Consulta filtrada de movimientos logísticos.
     *
     * @param tipo "Todos" o Entrada / Salida / Traslado
     * @param idProducto 0 para todos los productos
     * @param desde fecha inicial (incluida) o null
     * @param hasta fecha final (incluida) o null
     * @return movimientos que cumplen todos los filtros
     */
    public List<Movimiento_logistico> filtrar(String tipo, int idProducto, LocalDate desde, LocalDate hasta) {
        List<Movimiento_logistico> r = new ArrayList<>();
        for (Movimiento_logistico m : listaMovimientos) {
            LocalDate f = fechaDe(m);
            boolean okTipo = tipo == null || tipo.equals("Todos") || tipo.equalsIgnoreCase(m.getTipo());
            boolean okProducto = idProducto == 0
                    || (m.getProductoInvolucrado() != null && m.getProductoInvolucrado().getId() == idProducto);
            boolean okDesde = desde == null || !f.isBefore(desde);
            boolean okHasta = hasta == null || !f.isAfter(hasta);
            if (okTipo && okProducto && okDesde && okHasta) r.add(m);
        }
        return r;
    }
}
