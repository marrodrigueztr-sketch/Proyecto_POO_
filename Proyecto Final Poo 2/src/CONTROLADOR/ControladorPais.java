package CONTROLADOR;

import MODELO.Ciudad;
import MODELO.Pais;
import MODELO.Usuarios;
import UTILIDADES.Ordenamientos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador CRUD de países.
 *
 * @author Grupo 1
 */
public class ControladorPais implements ICRUD {

    // static: todas las ventanas comparten la misma lista
    private static ArrayList<Pais> listaPaises = new ArrayList<>();

    @Override
    public boolean insertar(Object obj) {
        Pais nuevo = (Pais) obj;
        if (existeId(nuevo.getId())) return false;
        listaPaises.add(nuevo);
        ordenar();
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaPaises.isEmpty()) {
            System.out.println("No hay países registrados.");
        } else {
            for (Pais p : listaPaises) System.out.println(p);
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int id = (obj instanceof Pais) ? ((Pais) obj).getId() : (Integer) obj;
        ordenar(); // la búsqueda binaria exige la lista ordenada por ID
        return Ordenamientos.busquedaBinaria(listaPaises, id, Pais::getId);
    }

    @Override
    public void ordenar() {
        listaPaises.sort(Comparator.comparingInt(Pais::getId));
    }

    @Override
    public void ordenar(int descendente) {
        Comparator<Pais> c = Comparator.comparing(Pais::getNombre, String.CASE_INSENSITIVE_ORDER);
        List<Pais> ordenada = Ordenamientos.burbuja(listaPaises, descendente == 1 ? c.reversed() : c);
        listaPaises.clear();
        listaPaises.addAll(ordenada);
    }

    @Override
    public Object update(Object obj) {
        Pais datos = (Pais) obj;
        Pais existente = (Pais) consultarUno(datos.getId());
        if (existente == null) return null;
        existente.setNombre(datos.getNombre());
        return existente;
    }

    /** Elimina el país y, en cascada, sus ciudades (y lo que dependa de ellas). */
    @Override
    public boolean delete(Object obj) {
        Pais p = (Pais) consultarUno(obj);
        if (p == null) return false;
        ControladorCiudad cc = new ControladorCiudad();
        for (Ciudad c : new ArrayList<>(cc.getListaCiudades())) {
            if (c.getPaisReferencia() != null && c.getPaisReferencia().getId() == p.getId()) {
                cc.delete(c.getId());
            }
        }
        listaPaises.remove(p);
        return true;
    }

    /** Eliminación con regla de negocio: solo el administrador puede borrar. */
    public boolean delete(Object obj, Usuarios ejecutor) {
        return ControladorUsuario.puedeEliminar(ejecutor) && delete(obj);
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    public ArrayList<Pais> getListaPaises() {
        return listaPaises;
    }

    // ---------------- Ordenamientos para la GUI (sobre copias) ----------------
    public List<Pais> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaPaises, Comparator.comparingInt(Pais::getId).reversed());
    }

    public List<Pais> ordenarMergesortNombre() {
        return Ordenamientos.mergesort(listaPaises, Comparator.comparing(Pais::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    public List<Pais> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaPaises, Comparator.comparingInt(Pais::getId));
    }

    /** Consulta filtrada: países cuyo nombre contiene el texto. */
    public List<Pais> filtrarPorNombre(String texto) {
        List<Pais> r = new ArrayList<>();
        for (Pais p : listaPaises) {
            if (texto == null || p.getNombre().toLowerCase().contains(texto.toLowerCase())) r.add(p);
        }
        return r;
    }
}
