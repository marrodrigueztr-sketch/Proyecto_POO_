/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.Patologia;
import java.util.ArrayList ;
import java.util.Collections;

/**
 *
 * @author sergi
 */
public class ControladorPatologia implements ICRUD<Patologia>{
    private static ArrayList<Patologia> listaPatologias = new ArrayList<>();

    public ControladorPatologia() {
        if (listaPatologias==null){
            listaPatologias=new ArrayList<>();
        }
    }

    public static ArrayList<Patologia> getListaPatologias() {
        return listaPatologias;
    }

    public static void setListaPatologias(ArrayList<Patologia> listaPatologias) {
        ControladorPatologia.listaPatologias = listaPatologias;
    }
    
    @Override
    public boolean insertar(Patologia obj) {
       if (listaPatologias.contains(obj)) {
            System.out.println("Error: Ya existe una Patologia con ese ID.");
            return false;
        }
        listaPatologias.add(obj);
        return true;
    }

    @Override
    public Patologia consultarUno(Patologia obj) {
        ordenar();
        int inicio = 0;
        int fin = listaPatologias.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Patologia patomedio = listaPatologias.get(medio);

            if (patomedio.getId() == obj.getId()) {
                return patomedio;
            }
            if (patomedio.getId() < obj.getId()) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;

    }

    @Override
    public void consultarTodos() {
        if (listaPatologias.isEmpty()) {
            System.out.println("No hay patologias registradas.");
        } else {
            System.out.println("--- LISTA DE PATOLOGIAS ---");
            for (Patologia e : listaPatologias) {
                System.out.println(e.toString());
            }
        }

    }

    @Override
    public boolean modificar(Patologia obj) {
        int index = listaPatologias.indexOf(obj);
        if (index != -1) {
            listaPatologias.set(index, obj);
            return true;
        }
        return false;

    }

    @Override
    public boolean borrar(Patologia obj) {
          return listaPatologias.remove(obj);

    }

    @Override
    public void ordenar() {
        Collections.sort(listaPatologias);
    }

    @Override
    public void ordenarBurbuja() {
        int n = listaPatologias.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listaPatologias.get(j).getId() > listaPatologias.get(j + 1).getId()) {
                    Patologia temporal = listaPatologias.get(j);
                    listaPatologias.set(j, listaPatologias.get(j + 1));
                    listaPatologias.set(j + 1, temporal);
                }
            }
        }
        System.out.println("-> Lista de patologia ordenada (Burbuja).");

    }

    @Override
    public void ordenarQuicksort() {
        System.out.println("en construccion");
    }

    @Override
    public void ordenarMersort() {
         System.out.println("en construccion");

    }
    
}
