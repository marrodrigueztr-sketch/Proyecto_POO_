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
import modelo.Administrador;
import modelo.Usuario;

// con "implements ICRUD<Usuario>" le decimos 
// que reemplace todas las 'T' por la clase 'Usuario'.
public class ControladorUsuario implements ICRUD<Usuario> {

  
    private static ArrayList<Usuario> listaUsuarios;

    // Constructor
    public ControladorUsuario() {
        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<>(); 
            Administrador adminDefault = new Administrador(12345, "Admin", "Principal", 0000, "admin@tienda.com", "Sede", 0, "1234");
            listaUsuarios.add(adminDefault);
        }
    }
    
    public ArrayList<Usuario>getListaUsuarios(){
        return this.listaUsuarios;
    }
    // --- MÉTODOS CRUD OBLIGATORIOS ---

    @Override
    public boolean insertar(Usuario obj) {
        // .contains() usa internamente el equals de 
        //  la clase Usuario para saber si esa cedula ya existe
        if (listaUsuarios.contains(obj)) {
            System.out.println("Error: Ya existe un usuario con esa cedula.");
            return false;
        }
        listaUsuarios.add(obj);
        return true;
    }

    @Override
    public Usuario consultarUno(Usuario obj) {
        ordenar(); 

        int inicio = 0;
        int fin = listaUsuarios.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Usuario usuarioMedio = listaUsuarios.get(medio);

            if (usuarioMedio.getIdCedula() == obj.getIdCedula()) {
                return usuarioMedio; // aqui lo encontro
            }
            if (usuarioMedio.getIdCedula() < obj.getIdCedula()) {
                inicio = medio + 1; // Buscar en la mitad derecha
            } else {
                fin = medio - 1; // Buscar en la mitad izquierda
            }
        }
        return null; // Si el bucle termina y no retornó,  no existe
    }

    @Override
    public void consultarTodos() {
        if (listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
        } else {
            System.out.println("--- LISTA DE USUARIOS ---");
            for (Usuario u : listaUsuarios) {
                
                System.out.println(u.toString()); 
            }
        }
    }

    @Override
    public boolean modificar(Usuario obj) {
        // buscamos la posicion del usuario en la lista usando su cedula
        int index = listaUsuarios.indexOf(obj);
        if (index != -1) {
            listaUsuarios.set(index, obj); // modificamos los datos
            return true;
        }
        return false;
    }

    @Override
    public boolean borrar(Usuario obj) {
        // el metodo remove usa el equals para buscar la cedula y eliminar todo el objeto
        return listaUsuarios.remove(obj);
    }

    // --- ALGORITMOS DE ORDENAMIENTO ------------

    @Override
    public void ordenar() {
        //  usa el 'compareTo' que  de la clase Usuario 
        Collections.sort(listaUsuarios); 
    }

    @Override
    public void ordenarBurbuja() {
        // ALGORITMO BURBUJA 
        int n = listaUsuarios.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                
                if (listaUsuarios.get(j).getIdCedula() > listaUsuarios.get(j + 1).getIdCedula()) {
                   
                    Usuario temporal = listaUsuarios.get(j);
                    listaUsuarios.set(j, listaUsuarios.get(j + 1));
                    listaUsuarios.set(j + 1, temporal);
                }
            }
        }
        System.out.println("La lista ha sido ordenada usando  Burbuja.");
    }

    @Override
    public void ordenarQuicksort() {
        System.out.println("Algoritmo QuickSort en construcción...");
    }

    @Override
    public void ordenarMersort() {
        System.out.println("Algoritmo MergeSort en construcción...");
    }
//--------------------------.-------------------------------------------------------    
    // metodo  para la validacion del Login
public Usuario validarLogin(String correo, String password) {

    for (Usuario user : listaUsuarios) {
        if (user.getCorreo().equals(correo) && user.getContrasena().equals(password)) {
            return user; // Lo encontro
        }
    }
    return null; // si termina el ciclo y no lo encuentra devuelve null
}
}
