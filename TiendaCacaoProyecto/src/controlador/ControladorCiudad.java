/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author sergio martinez y camilo londoño
 */


import java.util.ArrayList;
import java.util.Collections;
import modelo.Ciudad;
import modelo.Pais;

public class ControladorCiudad implements ICRUD<Ciudad> {

    private static ArrayList<Ciudad> listaCiudades;

    public ControladorCiudad() {
        
        if (listaCiudades == null) {
            listaCiudades = new ArrayList<>();
        }
    }

    public ArrayList<Ciudad> getListaCiudad() {
        return listaCiudades;
    }
    
    @Override
    public boolean insertar(Ciudad obj) {
        if (listaCiudades.contains(obj)) {
            System.out.println("Error: Ya existe una Ciudad con ese ID.");
            return false;
        }
        listaCiudades.add(obj);
        return true;
    }

    @Override
    public Ciudad consultarUno(Ciudad obj) {
        ordenar();
        int inicio = 0;
        int fin = listaCiudades.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Ciudad ciudadMedio = listaCiudades.get(medio);

            if (ciudadMedio.getId() == obj.getId()) {
                return ciudadMedio;
            }
            if (ciudadMedio.getId() < obj.getId()) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    @Override
    public void consultarTodos() {
        if (listaCiudades.isEmpty()) {
            System.out.println("No hay ciudades registradas.");
        } else {
            System.out.println("--- LISTA DE CIUDADES ---");
            for (Ciudad c : listaCiudades) {
                System.out.println(c.toString());
            }
        }
    }

    @Override
    public boolean modificar(Ciudad obj) {
        int index = listaCiudades.indexOf(obj);
        if (index != -1) {
            listaCiudades.set(index, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean borrar(Ciudad obj) {
        return listaCiudades.remove(obj);
    }

    @Override
    public void ordenar() {
        Collections.sort(listaCiudades);
    }

    @Override
    public void ordenarBurbuja() {
        int n = listaCiudades.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listaCiudades.get(j).getId() > listaCiudades.get(j + 1).getId()) {
                    Ciudad temporal = listaCiudades.get(j);
                    listaCiudades.set(j, listaCiudades.get(j + 1));
                    listaCiudades.set(j + 1, temporal);
                }
            }
        }
        System.out.println("-> Lista de Ciudades ordenada (Burbuja).");
    }

    @Override
    public void ordenarQuicksort() {
        System.out.println("QuickSort en construcción...");
    }

    @Override
    public void ordenarMersort() {
        System.out.println("MergeSort en construcción...");
    }
}
