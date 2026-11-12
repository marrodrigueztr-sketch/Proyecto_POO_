/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;
import CONTROLADOR.ControladorUsuario;
import MODELO.OperarioBodega;
import MODELO.Usuarios;
import java.util.Scanner;
/**
 *
 * @author Marcelo
 */

public class VistaUsuarios {

    private ControladorUsuario controlador;
    private Scanner scanner;

    public VistaUsuarios(ControladorUsuario controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("--- GESTION DE USUARIOS ---");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Buscar Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Eliminar Usuario");
            System.out.println("6. Ordenar por Nombre(Descendente)");
            System.out.println("7. Ordenar por ID(Ascendente)");
            System.out.println("8. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: crearUsuario(); break;
                    case 2: consultarUsuarios(); break;
                    case 3: buscarUsuario(); break;
                    case 4: actualizarUsuario(); break;
                    case 5: eliminarUsuario(); break;
                    case 6: 
                        controlador.ordenar(1); 
                        System.out.println("Productos ordenados por Nombre");
                        consultarUsuarios();
                        break;
                    case 7:
                        controlador.ordenar(); 
                        System.out.println("Productos ordenados por ID");
                        consultarUsuarios();
                        break;
                    case 8: System.out.println("Retornando..."); 
                        break;
                    default: System.out.println("Error: Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada invalida. Ingrese un numero.");
            }
        } while (opcion != 8);
    }

    private void crearUsuario() {
        try {
            System.out.print("Ingrese ID unico: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese Telefono: ");
            String telefono = scanner.nextLine();
            System.out.print("Ingrese Correo Corporativo: ");
            String correo = scanner.nextLine();
            System.out.print("Ingrese Cargo (Admin/Gerente/Operario): ");
            String cargo = scanner.nextLine();

            Usuarios nuevo = new OperarioBodega(id, nombre, apellido, telefono, correo, cargo, 0.0f, "Diurno", null);

            if (controlador.insertar(nuevo)) {
                System.out.println("!Usuario creado exitosamente!");
            } else {
                System.out.println("Error: El correo ingresado ya pertenece a otro usuario.");
            }
        } catch (Exception e) {
            System.out.println("Error al capturar datos.");
        }
    }

    private void consultarUsuarios() {
        controlador.consultarTodos();
    }

    private void buscarUsuario() {
        try {
            System.out.print("Ingrese el ID a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Usuarios u = (Usuarios) controlador.consultarUno(id);
            if (u != null) {
                System.out.println("Usuario encontrado: " + u.toString());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }

    private void actualizarUsuario() {
        try {
            System.out.print("Ingrese el ID del usuario a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nuevo Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Nuevo Telefono: ");
            String telefono = scanner.nextLine();
            System.out.print("Nuevo Cargo: ");
            String cargo = scanner.nextLine();

            Usuarios temp = new OperarioBodega(id, nombre, apellido, telefono, "", cargo, 0.0f, "", null);

            if (controlador.update(temp) != null) {
                System.out.println("Usuario actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar. ID no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error en los formatos de entrada.");
        }
    }

    private void eliminarUsuario() {
        try {
            System.out.print("Ingrese el ID del usuario a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controlador.delete(id)) {
                System.out.println("Usuario eliminado exitosamente.");
            } else {
                System.out.println("Error: Usuario no encontrado o no se pudo eliminar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }
}