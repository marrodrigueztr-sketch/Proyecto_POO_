/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Movimiento_logistico;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Marcelo
 */
public class ControladorMovimiento implements ICRUD {

    private ArrayList<Movimiento_logistico> listaMovimientos;

    public ControladorMovimiento() {
        this.listaMovimientos = new ArrayList<>();
    }

    @Override
    public boolean insertar(Object obj) {
        Movimiento_logistico nuevoMovimiento = (Movimiento_logistico) obj;
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

        int inicio = 0;
        int fin = listaMovimientos.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Movimiento_logistico m = listaMovimientos.get(medio);

            if (m.getId() == idBuscado) return m;
            if (m.getId() < idBuscado) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }

    @Override
    public void ordenar() {
        // Orden natural por ID
        listaMovimientos.sort(Comparator.comparingInt(Movimiento_logistico::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Algoritmo Burbuja por Fecha de movimiento 
        int n = listaMovimientos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean intercambiar = false;
                
                if (descendente == 1) { 
                    if (listaMovimientos.get(j).getFecha().compareTo(listaMovimientos.get(j + 1).getFecha()) < 0) {
                        intercambiar = true;
                    }
                } else { 
                    if (listaMovimientos.get(j).getFecha().compareTo(listaMovimientos.get(j + 1).getFecha()) > 0) {
                        intercambiar = true;
                    }
                }

                if (intercambiar) {
                    Movimiento_logistico temp = listaMovimientos.get(j);
                    listaMovimientos.set(j, listaMovimientos.get(j + 1));
                    listaMovimientos.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public Object update(Object obj) {
        Movimiento_logistico entidadActualizada = (Movimiento_logistico) obj;
        Movimiento_logistico existente = (Movimiento_logistico) consultarUno(entidadActualizada.getId());
        
        if (existente != null) {
            existente.setFecha(entidadActualizada.getFecha());
            existente.setTipo(entidadActualizada.getTipo());
            existente.setCantidad(entidadActualizada.getCantidad());
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
}