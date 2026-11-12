/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;
import MODELO.Usuarios;
import MODELO.OperarioBodega;
import MODELO.AdministradorSistema;
import MODELO.Almacen;
import MODELO.GerenteGeneral;
import UTILIDADES.Ordenamientos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Controlador CRUD de usuarios (Administrador, Gerente General y Operario de Bodega).
 *
 * @author Marcelo
 */

public class ControladorUsuario implements ICRUD {

    // static: todas las ventanas (login, menú, formularios) comparten la misma lista
    private static ArrayList<Usuarios> listaUsuarios = new ArrayList<>();

    public ControladorUsuario() {
    }

    @Override
    public boolean insertar(Object obj) {
        Usuarios nuevoUsuario = (Usuarios) obj;
        if (existeId(nuevoUsuario.getId())) {
            return false; // El ID ya existe
        }
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
        this.ordenar(); // la búsqueda binaria exige la lista ordenada por ID
        // Búsqueda Binaria
        return Ordenamientos.busquedaBinaria(listaUsuarios, idBuscado, Usuarios::getId);
    }

    @Override
    public void ordenar() {
        listaUsuarios.sort(Comparator.comparingInt(Usuarios::getId));
    }

    @Override
    public void ordenar(int descendente) {
        // Algoritmo Burbuja secundario (por Nombre), usado desde la consola
        List<Usuarios> ordenada = ordenarBurbujaNombre(descendente == 1);
        listaUsuarios.clear();
        listaUsuarios.addAll(ordenada);
    }

    /**
     * Actualiza los datos de un usuario existente. El rol no se puede cambiar.
     * Si la contraseña llega vacía, se conserva la anterior.
     *
     * @return el usuario actualizado, o null si no existe, cambió de rol o el correo ya lo usa otro
     */
    @Override
    public Object update(Object obj) {
        Usuarios datos = (Usuarios) obj;
        Usuarios existente = (Usuarios) consultarUno(datos.getId());
        if (existente == null || existente.getClass() != datos.getClass()) {
            return null;
        }
        if (datos.getCorreo() != null && !datos.getCorreo().isEmpty()) {
            if (correoEnUso(datos.getCorreo(), datos.getId())) return null;
            existente.setCorreo(datos.getCorreo());
        }
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setCargo(datos.getCargo());

        String nuevaClave = obtenerContrasena(datos);
        boolean cambiaClave = nuevaClave != null && !nuevaClave.isEmpty();

        if (existente instanceof AdministradorSistema a && datos instanceof AdministradorSistema d) {
            a.setNivel(d.getNivel());
            if (cambiaClave) a.setContrasena(nuevaClave);
        } else if (existente instanceof GerenteGeneral g && datos instanceof GerenteGeneral d) {
            g.setNivel(d.getNivel());
            if (cambiaClave) g.setContrasena(nuevaClave);
        } else if (existente instanceof OperarioBodega o && datos instanceof OperarioBodega d) {
            o.setSalario(d.getSalario());
            o.setTurno(d.getTurno());
            o.setAlmacenAsignado(d.getAlmacenAsignado());
            if (cambiaClave) o.setContrasena(nuevaClave);
        }
        return existente;
    }

    /**
     * Elimina el usuario y sus dependencias: si es gerente, sus almacenes quedan sin gerente;
     * si es operario, se eliminan los movimientos de los que era responsable.
     */
    @Override
    public boolean delete(Object obj) {
        Usuarios u = (Usuarios) consultarUno(obj);
        if (u == null) {
            return false;
        }
        if (u instanceof GerenteGeneral) {
            for (Almacen a : new ControladorAlmacen().getListaAlmacenes()) {
                if (a.getGerenteAsignado() != null && a.getGerenteAsignado().getId() == u.getId()) {
                    a.setGerenteAsignado(null);
                }
            }
        } else if (u instanceof OperarioBodega) {
            new ControladorMovimiento().getListaMovimientos().removeIf(
                    m -> m.getOperarioResponsable() != null && m.getOperarioResponsable().getId() == u.getId());
        }
        listaUsuarios.remove(u);
        return true;
    }

    // Método sobrecargado para aplicar la regla de negocio
    public boolean delete(Object obj, Usuarios usuarioEjecutor) {
        if (!puedeEliminar(usuarioEjecutor)) {
            return false; // Acceso denegado: solo el administrador elimina registros
        }
        return this.delete(obj);
    }

    /**
     * Regla de negocio de todo el sistema: solo el Administrador puede eliminar registros.
     *
     * @param usuario usuario que intenta eliminar
     * @return true si puede eliminar
     */
    public static boolean puedeEliminar(Usuarios usuario) {
        return usuario instanceof AdministradorSistema;
    }

    public ArrayList<Usuarios> getListaUsuarios() {
        return this.listaUsuarios;
    }

    public boolean existeId(int id) {
        return consultarUno(id) != null;
    }

    /** @return true si otro usuario (con ID distinto a idExcluido) ya usa ese correo */
    public boolean correoEnUso(String correo, int idExcluido) {
        for (Usuarios u : listaUsuarios) {
            if (u.getId() != idExcluido && u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }

    /** @return cuántos administradores hay registrados */
    public int contarAdministradores() {
        int n = 0;
        for (Usuarios u : listaUsuarios) {
            if (u instanceof AdministradorSistema) n++;
        }
        return n;
    }

    /** @return lista de gerentes (para asignarlos a almacenes) */
    public List<GerenteGeneral> getGerentes() {
        List<GerenteGeneral> r = new ArrayList<>();
        for (Usuarios u : listaUsuarios) if (u instanceof GerenteGeneral g) r.add(g);
        return r;
    }

    /** @return lista de operarios (para los movimientos) */
    public List<OperarioBodega> getOperarios() {
        List<OperarioBodega> r = new ArrayList<>();
        for (Usuarios u : listaUsuarios) if (u instanceof OperarioBodega o) r.add(o);
        return r;
    }

    /**
     * Valida el inicio de sesión buscando un usuario por correo y contraseña.
     *
     * @param correo correo ingresado
     * @param password contraseña ingresada
     * @return el usuario encontrado, o null si los datos no coinciden
     */
    public Usuarios validarLogin(String correo, String password) {
        for (Usuarios u : listaUsuarios) {
            if (u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(correo)
                    && password.equals(obtenerContrasena(u))) {
                return u;
            }
        }
        return null;
    }

    /** Devuelve la contraseña según el tipo de usuario (polimorfismo con instanceof). */
    private String obtenerContrasena(Usuarios u) {
        if (u instanceof AdministradorSistema a) return a.getContrasena();
        if (u instanceof GerenteGeneral g) return g.getContrasena();
        if (u instanceof OperarioBodega o) return o.getContrasena();
        return null;
    }

    /**
     * Nombre del rol de un usuario, usado para mostrar y aplicar permisos.
     *
     * @param u usuario
     * @return "Administrador", "Gerente General" u "Operario de Bodega"
     */
    public static String obtenerRol(Usuarios u) {
        if (u instanceof AdministradorSistema) return "Administrador";
        if (u instanceof GerenteGeneral) return "Gerente General";
        if (u instanceof OperarioBodega) return "Operario de Bodega";
        return "Desconocido";
    }

    // ---------------- Ordenamientos para la GUI (sobre copias) ----------------
    public List<Usuarios> ordenarQuicksortIdDesc() {
        return Ordenamientos.quicksort(listaUsuarios, Comparator.comparingInt(Usuarios::getId).reversed());
    }

    /** Mergesort por apellido (y nombre si el apellido es igual). */
    public List<Usuarios> ordenarMergesortApellido() {
        return Ordenamientos.mergesort(listaUsuarios,
                Comparator.comparing(Usuarios::getApellido, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Usuarios::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    public List<Usuarios> ordenarBurbujaNombre(boolean descendente) {
        Comparator<Usuarios> c = Comparator.comparing(Usuarios::getNombre, String.CASE_INSENSITIVE_ORDER);
        return Ordenamientos.burbuja(listaUsuarios, descendente ? c.reversed() : c);
    }

    public List<Usuarios> ordenarSortIdAsc() {
        return Ordenamientos.nativo(listaUsuarios, Comparator.comparingInt(Usuarios::getId));
    }

    /** Consulta filtrada por rol ("Todos" para no filtrar). */
    public List<Usuarios> filtrarPorRol(String rol) {
        List<Usuarios> r = new ArrayList<>();
        for (Usuarios u : listaUsuarios) {
            if (rol == null || rol.equals("Todos") || rol.equals(obtenerRol(u))) r.add(u);
        }
        return r;
    }
}
