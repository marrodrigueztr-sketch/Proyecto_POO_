/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import java.util.ArrayList;
import MODELO.Productos;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ControladorProductos implements ICRUD {
    
    private ArrayList<Productos> listaProductos;

    public ControladorProductos() {
        this.listaProductos = new ArrayList<>();
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
            return existente; // Retorna el objeto modificado
        }
        return null;
    }

    @Override
    public boolean delete(Object obj) {
        // Aprovechamos consultarUno para que nos resuelva si mandaron un Integer o un Objeto
        Productos p = (Productos) consultarUno(obj);
        
        if (p != null) {
            listaProductos.remove(p);
            return true;
        }
        return false;
    }
    public ArrayList<Productos> getListaProductos() {
        return this.listaProductos;
    }
}