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
import modelo.Finca; 

public class ControladorFinca implements ICRUD<Finca> {

    // Nuestra lista en memoria solo para Fincas
    private static ArrayList<Finca> listaFincas;

    public ControladorFinca() {
        if (listaFincas == null) {
            listaFincas = new ArrayList<>();
        }
    }

    @Override
    public boolean insertar(Finca obj) {
        if (listaFincas.contains(obj)) {
            System.out.println("Error: Ya existe una Finca con ese ID.");
            return false;
        }
        listaFincas.add(obj);
        return true;
    }

    @Override
    public Finca consultarUno(Finca obj) {
        ordenar(); 
        int inicio = 0;
        int fin = listaFincas.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Finca fincaMedio = listaFincas.get(medio);

            if (fincaMedio.getIdFinca() == obj.getIdFinca()) {
                return fincaMedio;
            }
            if (fincaMedio.getIdFinca() < obj.getIdFinca()) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null; 
    }

    @Override
    public void consultarTodos() {
        if (listaFincas.isEmpty()) {
            System.out.println("No hay fincas registradas.");
        } else {
            System.out.println("--- LISTA DE FINCAS ---");
            for (Finca f : listaFincas) {
                System.out.println(f.toString()); 
            }
        }
    }

    @Override
    public boolean modificar(Finca obj) {
        int index = listaFincas.indexOf(obj);
        if (index != -1) {
            listaFincas.set(index, obj); 
            return true;
        }
        return false;
    }

    @Override
    public boolean borrar(Finca obj) {
        return listaFincas.remove(obj);
    }

    @Override
    public void ordenar() {
        Collections.sort(listaFincas); 
    }

    @Override
    public void ordenarBurbuja() {
        int n = listaFincas.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listaFincas.get(j).getIdFinca() > listaFincas.get(j + 1).getIdFinca()) {
                    Finca temporal = listaFincas.get(j);
                    listaFincas.set(j, listaFincas.get(j + 1));
                    listaFincas.set(j + 1, temporal);
                }
            }
        }
        System.out.println("-> Lista de Fincas ordenada (Burbuja).");
    }

    @Override
    public void ordenarQuicksort() {
        System.out.println("QuickSort en construcción...");
    }

    @Override
    public void ordenarMersort() {
        System.out.println("MergeSort en construcción...");
    }
    public java.util.ArrayList<Finca> getListaFincas() {
    return this.listaFincas; // Cambia "listaFincas" por el nombre exacto de tu variable
}
}
