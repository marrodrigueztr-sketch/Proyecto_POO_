/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Usuarios;
import MODELO.OperarioBodega;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author Marcelo
 */

public class ControladorUsuario implements ICRUD {

    private ArrayList<Usuarios> listaUsuarios;

    public ControladorUsuario() {
        this.listaUsuarios = new ArrayList<>();
    }

    @Override
    public boolean insertar(Object obj) {
        Usuarios nuevoUsuario = (Usuarios) obj;
        for (Usuarios u : listaUsuarios) {
            if (u.equals(nuevoUsuario)) {
                return false; // El correo ya existe
            }
        }
        listaUsuarios.add(nuevoUsuario);
        this.ordenar(); // Ordenamiento nativo
        return true;
    }

    @Override
    public void consultarTodos() {
        if (listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("--- LISTA DE USUARIOS ---");
            for (Usuarios u : listaUsuarios) {
                System.out.println(u.toString());
            }
        }
    }

    @Override
    public Object consultarUno(Object obj) {
        int idBuscado = -1;
        if (obj instanceof Integer) {
            idBuscado = (Integer) obj;
        } else if (obj instanceof Usuarios) {
            idBuscado = ((Usuarios) obj).getId();
        }

        // Búsqueda Binaria
        int inicio = 0;
        int fin = listaUsuarios.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Usuarios u = listaUsuarios.get(medio);

            if (u.getId() == idBuscado) return u;
            if (u.getId() < idBuscado) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }

    @Override
    public void ordenar() {
        listaUsuarios.sort(Comparator.comparingInt(Usuarios::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Algoritmo Burbuja secundario (Ej. ordenar por Nombre)
        int n = listaUsuarios.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean intercambiar = false;
                if (descendente == 1) {
                    if (listaUsuarios.get(j).getNombre().compareToIgnoreCase(listaUsuarios.get(j + 1).getNombre()) < 0) {
                        intercambiar = true;
                    }
                } else {
                    if (listaUsuarios.get(j).getNombre().compareToIgnoreCase(listaUsuarios.get(j + 1).getNombre()) > 0) {
                        intercambiar = true;
                    }
                }
                if (intercambiar) {
                    Usuarios temp = listaUsuarios.get(j);
                    listaUsuarios.set(j, listaUsuarios.get(j + 1));
                    listaUsuarios.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public Object update(Object obj) {
        Usuarios entidadActualizada = (Usuarios) obj;
        Usuarios existente = (Usuarios) consultarUno(entidadActualizada.getId());
        if (existente != null) {
            existente.setNombre(entidadActualizada.getNombre());
            existente.setApellido(entidadActualizada.getApellido());
            existente.setTelefono(entidadActualizada.getTelefono());
            existente.setCargo(entidadActualizada.getCargo());
            return existente;
        }
        return null;
    }

    @Override
    public boolean delete(Object obj) {
        Usuarios u = (Usuarios) consultarUno(obj);
        if (u != null) {
            listaUsuarios.remove(u);
            return true;
        }
        return false;
    }

    // Método sobrecargado para aplicar la regla de negocio
    public boolean delete(Object obj, Usuarios usuarioEjecutor) {
        if (usuarioEjecutor instanceof OperarioBodega) {
            System.out.println("Acceso denegado: Los operarios no pueden eliminar registros.");
            return false;
        }
        return this.delete(obj);
    }
    
    public ArrayList<Usuarios> getListaUsuarios() {
        return this.listaUsuarios;
    }
}