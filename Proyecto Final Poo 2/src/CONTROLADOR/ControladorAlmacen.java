/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Almacen;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Marcelo
 */


public class ControladorAlmacen implements ICRUD {

    private ArrayList<Almacen> listaAlmacenes;

    public ControladorAlmacen() {
        this.listaAlmacenes = new ArrayList<>();
    }

    @Override
    public boolean insertar(Object obj) {
        Almacen nuevoAlmacen = (Almacen) obj;
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

        // Búsqueda Binaria
        int inicio = 0;
        int fin = listaAlmacenes.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Almacen a = listaAlmacenes.get(medio);

            if (a.getId() == idBuscado) return a;
            if (a.getId() < idBuscado) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }

    @Override
    public void ordenar() {
        listaAlmacenes.sort(Comparator.comparingInt(Almacen::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Implementación de Burbuja secundaria (Ej. por Nombre) para cumplir la interfaz
        int n = listaAlmacenes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean intercambiar = false;
                if (descendente == 1) {
                    if (listaAlmacenes.get(j).getNombre().compareToIgnoreCase(listaAlmacenes.get(j + 1).getNombre()) < 0) {
                        intercambiar = true;
                    }
                } else {
                    if (listaAlmacenes.get(j).getNombre().compareToIgnoreCase(listaAlmacenes.get(j + 1).getNombre()) > 0) {
                        intercambiar = true;
                    }
                }
                if (intercambiar) {
                    Almacen temp = listaAlmacenes.get(j);
                    listaAlmacenes.set(j, listaAlmacenes.get(j + 1));
                    listaAlmacenes.set(j + 1, temp);
                }
            }
        }
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

    @Override
    public boolean delete(Object obj) {
        Almacen a = (Almacen) consultarUno(obj);
        if (a != null) {
            listaAlmacenes.remove(a);
            return true;
        }
        return false;
    }
    
    public ArrayList<Almacen> getListaAlmacenes() {
        return this.listaAlmacenes;
    }
}