/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 * @author sergio martinez y camilo londoño
 */


import java.util.ArrayList;
import java.util.Collections;
import modelo.Especie;

public class ControladorEspecie implements ICRUD<Especie> {

    private static ArrayList<Especie> listaEspecies;
    
    public ArrayList<Especie> listaEspecies() {
    return listaEspecies; 
    }
    public ControladorEspecie() {
        if (listaEspecies == null) {
            listaEspecies = new ArrayList<>();
        }
    }

    @Override
    public boolean insertar(Especie obj) {
        if (listaEspecies.contains(obj)) {
            System.out.println("Error: Ya existe una Especie con ese ID.");
            return false;
        }
        listaEspecies.add(obj);
        return true;
    }

    @Override
    public Especie consultarUno(Especie obj) {
        ordenar();
        int inicio = 0;
        int fin = listaEspecies.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Especie especieMedio = listaEspecies.get(medio);

            if (especieMedio.getId() == obj.getId()) {
                return especieMedio;
            }
            if (especieMedio.getId() < obj.getId()) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    @Override
    public void consultarTodos() {
        if (listaEspecies.isEmpty()) {
            System.out.println("No hay especies registradas.");
        } else {
            System.out.println("--- LISTA DE ESPECIES ---");
            for (Especie e : listaEspecies) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public boolean modificar(Especie obj) {
        int index = listaEspecies.indexOf(obj);
        if (index != -1) {
            listaEspecies.set(index, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean borrar(Especie obj) {
        return listaEspecies.remove(obj);
    }

    @Override
    public void ordenar() {
        Collections.sort(listaEspecies);
    }

    @Override
    public void ordenarBurbuja() {
        int n = listaEspecies.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listaEspecies.get(j).getId() > listaEspecies.get(j + 1).getId()) {
                    Especie temporal = listaEspecies.get(j);
                    listaEspecies.set(j, listaEspecies.get(j + 1));
                    listaEspecies.set(j + 1, temporal);
                }
            }
        }
        System.out.println("-> Lista de Especies ordenada (Burbuja).");
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
