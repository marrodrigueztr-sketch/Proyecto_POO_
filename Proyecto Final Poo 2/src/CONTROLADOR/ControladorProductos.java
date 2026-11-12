/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import java.util.ArrayList;
import MODELO.Productos;
import MODELO.Usuarios;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ControladorProductos implements ICRUD {
    
    // static: todas las ventanas comparten la misma lista de productos
    private static ArrayList<Productos> listaProductos = new ArrayList<>();

    public ControladorProductos() {
    }

    @Override
    public boolean insertar(Object obj) {
        // Casting: Convertimos el Object genérico a Productos
        Productos nuevoProducto = (Productos) obj;
        
        for (Productos p : listaProductos) {
            if (p.equals(nuevoProducto)) {
                return false; // El SKU ya existe
            }
        }
        listaProductos.add(nuevoProducto);
        this.ordenar(); // Llama al ordenamiento por defecto exigido por la profesora
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("--- LISTA DE PRODUCTOS ---");
            for (Productos p : listaProductos) {
                System.out.println(p.toString()); // Imprime en consola según la interfaz
            }
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int idBuscado = -1;
        
        // Verificamos si la vista nos mandó el ID directamente como un número
        if (obj instanceof Integer) {
            idBuscado = (Integer) obj;
        } 
        // O si la vista nos mandó un objeto producto completo
        else if (obj instanceof Productos) {
            idBuscado = ((Productos) obj).getId();
        }

        // La búsqueda binaria exige la lista ordenada por ID ascendente
        this.ordenar();

        // Búsqueda Binaria obligatoria[cite: 1]
        int inicio = 0;
        int fin = listaProductos.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Productos p = listaProductos.get(medio);

            if (p.getId() == idBuscado) return p;
            if (p.getId() < idBuscado) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null; // No lo encontró
    }

    @Override
    public void ordenar() {
        // Orden natural ascendente por ID
        Collections.sort(listaProductos);
    }

    @Override
    public void ordenar(int descendente) {
        // Algoritmo Burbuja por stock (Criterio secundario)
        int n = listaProductos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                
                boolean intercambiar = false;
                
                if (descendente == 1) { // 1 = Descendente (Mayor a menor)
                    if (listaProductos.get(j).getStockactual() < listaProductos.get(j + 1).getStockactual()) {
                        intercambiar = true;
                    }
                } else { // 0 u otro número = Ascendente (Menor a mayor)
                    if (listaProductos.get(j).getStockactual() > listaProductos.get(j + 1).getStockactual()) {
                        intercambiar = true;
                    }
                }

                if (intercambiar) {
                    Productos temp = listaProductos.get(j);
                    listaProductos.set(j, listaProductos.get(j + 1));
                    listaProductos.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public Object update(Object obj) {
        Productos entidadActualizada = (Productos) obj;
        
        // Reutilizamos el método consultarUno para buscarlo con búsqueda binaria
        Productos existente = (Productos) consultarUno(entidadActualizada.getId());
        
        if (existente != null) {
            existente.setNombre(entidadActualizada.getNombre());
            existente.setDescripcion(entidadActualizada.getDescripcion());
            existente.setPreciouniitario(entidadActualizada.getPreciouniitario());
            existente.setStockactual(entidadActualizada.getStockactual());
            existente.setProveedor(entidadActualizada.getProveedor());
            if (entidadActualizada.getTipo() != null && !entidadActualizada.getTipo().isEmpty()) {
                existente.setTipo(entidadActualizada.getTipo());
            }
            return existente; // Retorna el objeto modificado
        }
        return null;
    }

    @Override
    public boolean delete(Object obj) {
        // Aprovechamos consultarUno para que nos resuelva si mandaron un Integer o un Objeto
        Productos p = (Productos) consultarUno(obj);
        
        if (p != null) {
            // Dependencias: se quita de las zonas y se eliminan sus movimientos
            for (MODELO.ZonaPasillo z : new ControladorZona().getListaZonas()) {
                z.getListaProductos().remove(p);
            }
            new ControladorMovimiento().getListaMovimientos().removeIf(
                    m -> m.getProductoInvolucrado() != null && m.getProductoInvolucrado().getId() == p.getId());
            listaProductos.remove(p);
            return true;
        }
        return false;
    }
    public ArrayList<Productos> getListaProductos() {
        return this.listaProductos;
    }

    // Método sobrecargado para aplicar las reglas de negocio por rol
    public boolean delete(Object obj, Usuarios usuarioEjecutor) {
        if (!ControladorUsuario.puedeEliminar(usuarioEjecutor)) {
            return false; // Solo el administrador puede eliminar
        }
        return this.delete(obj);
    }

    /** @return true si ya existe un producto con ese ID (búsqueda binaria) */
    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    /** @return true si ya existe un producto con ese SKU (usa equals de Productos) */
    public boolean existeSku(String sku) {
        Productos temp = new Productos(0, sku, "", "", 0, 0, "", "");
        return listaProductos.contains(temp);
    }

    // ============================================================
    // ORDENAMIENTOS PARA LA INTERFAZ GRÁFICA
    // Trabajan sobre una COPIA de la lista, para que la lista original
    // siga ordenada por ID y la búsqueda binaria funcione siempre.
    // ============================================================

    /** Quicksort por ID de forma descendente. */
    public List<Productos> ordenarQuicksortIdDesc() {
        List<Productos> copia = new ArrayList<>(listaProductos);
        quicksort(copia, 0, copia.size() - 1);
        return copia;
    }

    private void quicksort(List<Productos> lista, int inicio, int fin) {
        if (inicio < fin) {
            int indicePivote = particion(lista, inicio, fin);
            quicksort(lista, inicio, indicePivote - 1);
            quicksort(lista, indicePivote + 1, fin);
        }
    }

    private int particion(List<Productos> lista, int inicio, int fin) {
        int pivote = lista.get(fin).getId();
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j).getId() > pivote) { // ">" => descendente
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, fin);
        return i + 1;
    }

    /** Mergesort por nombre (A-Z, sin distinguir mayúsculas). */
    public List<Productos> ordenarMergesortNombre() {
        return mergesort(new ArrayList<>(listaProductos));
    }

    private List<Productos> mergesort(List<Productos> lista) {
        if (lista.size() <= 1) {
            return lista;
        }
        int medio = lista.size() / 2;
        List<Productos> izquierda = mergesort(new ArrayList<>(lista.subList(0, medio)));
        List<Productos> derecha = mergesort(new ArrayList<>(lista.subList(medio, lista.size())));
        return mezclar(izquierda, derecha);
    }

    private List<Productos> mezclar(List<Productos> izquierda, List<Productos> derecha) {
        List<Productos> resultado = new ArrayList<>();
        int i = 0, j = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getNombre().compareToIgnoreCase(derecha.get(j).getNombre()) <= 0) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }
        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));
        return resultado;
    }

    /** Burbuja por stock actual. */
    public List<Productos> ordenarBurbujaStock(boolean descendente) {
        List<Productos> copia = new ArrayList<>(listaProductos);
        int n = copia.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int a = copia.get(j).getStockactual();
                int b = copia.get(j + 1).getStockactual();
                if (descendente ? a < b : a > b) {
                    Collections.swap(copia, j, j + 1);
                }
            }
        }
        return copia;
    }

    /** Ordenamiento nativo de Java (List.sort) por ID ascendente. */
    public List<Productos> ordenarSortIdAsc() {
        List<Productos> copia = new ArrayList<>(listaProductos);
        copia.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        return copia;
    }

    /**
     * Consulta filtrada de existencias.
     *
     * @param tipo tipo de producto, o null / "Todos" para no filtrar por tipo
     * @param stockMaximo muestra solo productos con stock menor o igual; null para no filtrar
     * @return productos que cumplen los filtros
     */
    public List<Productos> filtrar(String tipo, Integer stockMaximo) {
        List<Productos> resultado = new ArrayList<>();
        for (Productos p : listaProductos) {
            boolean cumpleTipo = tipo == null || tipo.equals("Todos") || tipo.equalsIgnoreCase(p.getTipo());
            boolean cumpleStock = stockMaximo == null || p.getStockactual() <= stockMaximo;
            if (cumpleTipo && cumpleStock) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
