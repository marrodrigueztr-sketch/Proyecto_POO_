/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.Comparator;
import modelo.Ciudad;
import modelo.Pais;

/**
 *
 * @author sergi
 */
public class ControladorPais implements ICRUD<Pais>{
    private static ArrayList<Pais> listaPais;
    
    public ArrayList<Pais> getListaPais() {
        return listaPais;
    }
    public ControladorPais() {
        if (listaPais == null) {
            listaPais = new ArrayList<>();
             listaPais.add(new Pais(1, "Colombia"));
        }
    }
    
    @Override
    public boolean insertar(Pais obj) {
        for (Pais p : listaPais) {
                    if (p.getId() == obj.getId()) {
                        System.out.println("Error: Ya existe un País con ese ID.");
                        return false;
                    }
                }
                listaPais.add(obj);
                return true;   
    }

    @Override
    public Pais consultarUno(Pais obj) {
        ordenar(); 
        
        int inicio = 0;
        int fin = listaPais.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Pais paisMedio = listaPais.get(medio);

            if (paisMedio.getId() == obj.getId()) {
                return paisMedio; // ¡Encontrado!
            } else if (paisMedio.getId() < obj.getId()) {
                inicio = medio + 1; // Buscar en la mitad derecha
            } else {
                fin = medio - 1;    // Buscar en la mitad izquierda
            }
        }
        return null; // No existe

    }

    @Override
    public void consultarTodos() {
        for (Pais p : listaPais) {
            System.out.println("ID: " + p.getId() + " - Nombre: " + p.getNombre());
        }
    }

   
    @Override
    public boolean modificar(Pais obj) {
        int index = listaPais.indexOf(obj);
        if (index != -1) {
            listaPais.set(index, obj); // Modificamos los datos
            return true;
        }
        return false;
    }

    @Override
    public boolean borrar(Pais obj) {
         return listaPais.remove(obj);
         

    }

    @Override
    public void ordenar() {
        listaPais.sort(Comparator.comparingInt(Pais::getId));
    }

    @Override
    public void ordenarBurbuja() {
        int n = listaPais.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
               
                if (listaPais.get(j).getId() > listaPais.get(j + 1).getId()) {
                    Pais temporal = listaPais.get(j);
                    listaPais.set(j, listaPais.get(j + 1));
                    listaPais.set(j + 1, temporal);
                }
            }
        }
        System.out.println("-> Lista de paises ordenada (Burbuja).");

    }

    @Override
    public void ordenarQuicksort() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void ordenarMersort() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
