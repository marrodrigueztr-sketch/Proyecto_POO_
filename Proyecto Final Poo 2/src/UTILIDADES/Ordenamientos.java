package UTILIDADES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Algoritmos de ordenamiento genéricos usados por todos los controladores.
 * Todos trabajan sobre una COPIA de la lista recibida, así la lista original
 * sigue ordenada por ID y la búsqueda binaria funciona siempre.
 * <ul>
 * <li>Quicksort: ordenamientos por ID (descendente).</li>
 * <li>Mergesort: ordenamientos por nombre.</li>
 * <li>Burbuja: criterios secundarios (stock, fecha, capacidad...).</li>
 * </ul>
 *
 * @author Grupo 1
 */
public final class Ordenamientos {

    private Ordenamientos() {
    }

    /**
     * Ordena con Quicksort (partición de Lomuto).
     *
     * @param <T> tipo de los elementos
     * @param lista lista original (no se modifica)
     * @param criterio comparador que define el orden
     * @return una nueva lista ordenada
     */
    public static <T> List<T> quicksort(List<T> lista, Comparator<? super T> criterio) {
        List<T> copia = new ArrayList<>(lista);
        quicksort(copia, 0, copia.size() - 1, criterio);
        return copia;
    }

    private static <T> void quicksort(List<T> l, int inicio, int fin, Comparator<? super T> c) {
        if (inicio < fin) {
            int p = particion(l, inicio, fin, c);
            quicksort(l, inicio, p - 1, c);
            quicksort(l, p + 1, fin, c);
        }
    }

    private static <T> int particion(List<T> l, int inicio, int fin, Comparator<? super T> c) {
        T pivote = l.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (c.compare(l.get(j), pivote) < 0) {
                i++;
                Collections.swap(l, i, j);
            }
        }
        Collections.swap(l, i + 1, fin);
        return i + 1;
    }

    /**
     * Ordena con Mergesort (divide y vencerás, estable).
     *
     * @param <T> tipo de los elementos
     * @param lista lista original (no se modifica)
     * @param criterio comparador que define el orden
     * @return una nueva lista ordenada
     */
    public static <T> List<T> mergesort(List<T> lista, Comparator<? super T> criterio) {
        if (lista.size() <= 1) {
            return new ArrayList<>(lista);
        }
        int medio = lista.size() / 2;
        List<T> izquierda = mergesort(lista.subList(0, medio), criterio);
        List<T> derecha = mergesort(lista.subList(medio, lista.size()), criterio);

        List<T> resultado = new ArrayList<>(lista.size());
        int i = 0, j = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (criterio.compare(izquierda.get(i), derecha.get(j)) <= 0) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }
        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));
        return resultado;
    }

    /**
     * Ordena con el método Burbuja.
     *
     * @param <T> tipo de los elementos
     * @param lista lista original (no se modifica)
     * @param criterio comparador que define el orden
     * @return una nueva lista ordenada
     */
    public static <T> List<T> burbuja(List<T> lista, Comparator<? super T> criterio) {
        List<T> copia = new ArrayList<>(lista);
        int n = copia.size();
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (criterio.compare(copia.get(j), copia.get(j + 1)) > 0) {
                    Collections.swap(copia, j, j + 1);
                    huboCambio = true;
                }
            }
            if (!huboCambio) break;
        }
        return copia;
    }

    /**
     * Ordenamiento nativo de Java ({@code List.sort}), usado para almacenes por ID ascendente.
     *
     * @param <T> tipo de los elementos
     * @param lista lista original (no se modifica)
     * @param criterio comparador que define el orden
     * @return una nueva lista ordenada
     */
    public static <T> List<T> nativo(List<T> lista, Comparator<? super T> criterio) {
        List<T> copia = new ArrayList<>(lista);
        copia.sort(criterio);
        return copia;
    }

    /**
     * Búsqueda binaria por ID sobre una lista ordenada ascendentemente por ID.
     *
     * @param <T> tipo de los elementos
     * @param lista lista ordenada por ID ascendente
     * @param id ID buscado
     * @param obtenerId función que extrae el ID de un elemento
     * @return el elemento encontrado o null
     */
    public static <T> T busquedaBinaria(List<T> lista, int id, java.util.function.ToIntFunction<T> obtenerId) {
        int inicio = 0;
        int fin = lista.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            T actual = lista.get(medio);
            int idActual = obtenerId.applyAsInt(actual);
            if (idActual == id) return actual;
            if (idActual < id) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }
}
