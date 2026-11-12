/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;
import CONTROLADOR.ControladorAlmacen;
import MODELO.Almacen;
import java.util.Scanner;
/**
 *
 * @author Marcelo
 */


public class VistaAlmacenes {

    private ControladorAlmacen controlador;
    private Scanner scanner;

    public VistaAlmacenes(ControladorAlmacen controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("--- GESTION DE ALMACENES ---");
            System.out.println("1. Crear Almacen");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Buscar Almacen por ID");
            System.out.println("4. Actualizar Almacen");
            System.out.println("5. Eliminar Almacen");
            System.out.println("6. Ordenar por Nombre(Descendente)");
            System.out.println("7. Ordenar por ID(Ascendente)");
            System.out.println("8. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: crearAlmacen(); break;
                    case 2: consultarAlmacenes(); break;
                    case 3: buscarAlmacen(); break;
                    case 4: actualizarAlmacen(); break;
                    case 5: eliminarAlmacen(); break;
                    case 6: 
                        controlador.ordenar(1); 
                        System.out.println("Productos ordenados por Nombre");
                        consultarAlmacenes();
                        break;
                    case 7:
                        controlador.ordenar(); 
                        System.out.println("Productos ordenados por ID");
                        consultarAlmacenes();
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

    private void crearAlmacen() {
        try {
            System.out.print("Ingrese ID unico del almacen: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese Nombre del almacen: ");
            String nombre = scanner.nextLine();

            Almacen nuevo = new Almacen(id, nombre, null, null);

            if (controlador.insertar(nuevo)) {
                System.out.println("!Almacen creado dinamicamente!");
            }
        } catch (Exception e) {
            System.out.println("Error al capturar datos.");
        }
    }

    private void consultarAlmacenes() {
        controlador.consultarTodos();
    }

    private void buscarAlmacen() {
        try {
            System.out.print("Ingrese el ID a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Almacen a = (Almacen) controlador.consultarUno(id);
            if (a != null) {
                System.out.println("Almacen encontrado: " + a.toString());
            } else {
                System.out.println("Almacen no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }

    private void actualizarAlmacen() {
        try {
            System.out.print("Ingrese el ID del almacen a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nuevo Nombre del almacen: ");
            String nombre = scanner.nextLine();

            Almacen temp = new Almacen(id, nombre, null, null);

            if (controlador.update(temp) != null) {
                System.out.println("Almacen actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar. ID no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error en los formatos de entrada.");
        }
    }

    private void eliminarAlmacen() {
        try {
            System.out.print("Ingrese el ID del almacen a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controlador.delete(id)) {
                System.out.println("Almacen eliminado exitosamente.");
            } else {
                System.out.println("Error: Almacen no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }
}