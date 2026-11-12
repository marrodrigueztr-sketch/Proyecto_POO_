/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;
import CONTROLADOR.ControladorMovimiento;
import MODELO.Movimiento_logistico;
import java.util.Scanner;
/**
 *
 * @author Marcelo
 */

public class VistaMovimientos {

    private ControladorMovimiento controlador;
    private Scanner scanner;

    public VistaMovimientos(ControladorMovimiento controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("--- GESTION DE MOVIMIENTOS LOGISTICOS ---");
            System.out.println("1. Registrar Movimiento");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Buscar Movimiento por ID");
            System.out.println("4. Actualizar Movimiento");
            System.out.println("5. Eliminar Movimiento");
            System.out.println("6. Ordenar por fecha movimiento(Descendente)");
            System.out.println("7. Ordenar por ID(Ascendente)");
            System.out.println("8. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: crearMovimiento(); break;
                    case 2: consultarMovimientos(); break;
                    case 3: buscarMovimiento(); break;
                    case 4: actualizarMovimiento(); break;
                    case 5: eliminarMovimiento(); break;
                   case 6: 
                        controlador.ordenar(1); 
                        System.out.println("Productos ordenados por fecha movimiento");
                        consultarMovimientos();
                        break;
                    case 7:
                        controlador.ordenar(); 
                        System.out.println("Productos ordenados por ID");
                        consultarMovimientos();
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

    private void crearMovimiento() {
        try {
            System.out.print("Ingrese ID unico del movimiento: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese Fecha (Ej. 2026-09-23): ");
            String fecha = scanner.nextLine();
            System.out.print("Ingrese Tipo (Entrada/Salida/Traslado): ");
            String tipo = scanner.nextLine();
            System.out.print("Ingrese Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            Movimiento_logistico nuevo = new Movimiento_logistico(id, fecha, tipo, cantidad, null, null);

            if (controlador.insertar(nuevo)) {
                System.out.println("!Movimiento registrado exitosamente!");
            }
        } catch (Exception e) {
            System.out.println("Error al capturar datos.");
        }
    }

    private void consultarMovimientos() {
        controlador.consultarTodos();
    }

    private void buscarMovimiento() {
        try {
            System.out.print("Ingrese el ID a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Movimiento_logistico m = (Movimiento_logistico) controlador.consultarUno(id);
            if (m != null) {
                System.out.println("Movimiento encontrado: " + m.toString());
            } else {
                System.out.println("Movimiento no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }

    private void actualizarMovimiento() {
        try {
            System.out.print("Ingrese el ID del movimiento a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nueva Fecha: ");
            String fecha = scanner.nextLine();
            System.out.print("Nuevo Tipo: ");
            String tipo = scanner.nextLine();
            System.out.print("Nueva Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            Movimiento_logistico temp = new Movimiento_logistico(id, fecha, tipo, cantidad, null, null);

            if (controlador.update(temp) != null) {
                System.out.println("Movimiento actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar. ID no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error en los formatos de entrada.");
        }
    }

    private void eliminarMovimiento() {
        try {
            System.out.print("Ingrese el ID del movimiento a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controlador.delete(id)) {
                System.out.println("Movimiento eliminado exitosamente.");
            } else {
                System.out.println("Error: Movimiento no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }
}