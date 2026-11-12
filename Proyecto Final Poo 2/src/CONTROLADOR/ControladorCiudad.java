package CONTROLADOR;

import MODELO.Almacen;
import MODELO.Ciudad;
import MODELO.Usuarios;
import UTILIDADES.Ordenamientos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador CRUD de ciudades (cada ciudad pertenece a un país).
 *
 * @author Grupo 1
 */
public class ControladorCiudad implements ICRUD {

    private static ArrayList<Ciudad> listaCiudades = new ArrayList<>();

    @Override
    public boolean insertar(Object obj) {
        Ciudad nueva = (Ciudad) obj;
        if (existeId(nueva.getId())) return false;
        listaCiudades.add(nueva);
        ordenar();
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaCiudades.isEmpty()) {
            System.out.println("No hay ciudades registradas.");
        } else {
            for (Ciudad c : listaCiudades) System.out.println(c);
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int id = (obj instanceof Ciudad) ? ((Ciudad) obj).getId() : (Integer) obj;
        ordenar();
        return Ordenamientos.busquedaBinaria(listaCiudades, id, Ciudad::getId);
    }

    @Override
    public void ordenar() {
        listaCiudades.sort(Comparator.comparingInt(Ciudad::getId));
    }

    @Override
    public void ordenar(int descendente) {
        Comparator<Ciudad> c = Comparator.comparing(Ciudad::getNombre, String.CASE_INSENSITIVE_ORDER);
        List<Ciudad> ordenada = Ordenamientos.burbuja(listaCiudades, descendente == 1 ? c.reversed() : c);
        listaCiudades.clear();
        listaCiudades.addAll(ordenada);
    }

    @Override
    public Object update(Object obj) {
        Ciudad datos = (Ciudad) obj;
        Ciudad existente = (Ciudad) consultarUno(datos.getId());
        if (existente == null) return null;
        existente.setNombre(datos.getNombre());
        existente.setPaisReferencia(datos.getPaisReferencia());
        return existente;
    }

    /** Elimina la ciudad y, en cascada, sus almacenes. */
    @Override
    public boolean delete(Object obj) {
        Ciudad c = (Ciudad) consultarUno(obj);
        if (c == null) return false;
        ControladorAlmacen ca = new ControladorAlmacen();
        for (Almacen a : new ArrayList<>(ca.getListaAlmacenes())) {
            if (a.getCiudad() != null && a.getCiudad().getId() == c.getId()) {
                ca.delete(a.getId());
            }
        }
        listaCiudades.remove(c);
        return true;
    }

    public boolean delete(Object obj, Usuarios ejecutor) {
        return ControladorUsuario.puedeEliminar(ejecutor) && delete(obj);
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    public ArrayList<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    // ---------------- Ordenamientos para la GUI ----------------
    public List<Ciudad> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaCiudades, Comparator.comparingInt(Ciudad::getId).reversed());
    }

    public List<Ciudad> ordenarMergesortNombre() {
        return Ordenamientos.mergesort(listaCiudades, Comparator.comparing(Ciudad::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    public List<Ciudad> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaCiudades, Comparator.comparingInt(Ciudad::getId));
    }

    /** Consulta filtrada por país (idPais = 0 para todos). */
    public List<Ciudad> filtrarPorPais(int idPais) {
        List<Ciudad> r = new ArrayList<>();
        for (Ciudad c : listaCiudades) {
            if (idPais == 0 || (c.getPaisReferencia() != null && c.getPaisReferencia().getId() == idPais)) r.add(c);
        }
        return r;
    }
}
