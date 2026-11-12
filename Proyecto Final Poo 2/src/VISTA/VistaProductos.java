/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package VISTA;
import CONTROLADOR.ControladorProductos;
import MODELO.Productos;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */


public class VistaProductos {
    
    private ControladorProductos controlador;
    private Scanner scanner;

    public VistaProductos(ControladorProductos controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("--- GESTION DE PRODUCTOS ---");
            System.out.println("1. Crear Producto");
            System.out.println("2. Consultar Todos");
            System.out.println("3. Buscar Producto por ID");
            System.out.println("4. Actualizar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Ordenar por ID (Descendente)");
            System.out.println("7. Ordenar por Stock (Ascendente)");
            System.out.println("8. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1: crearProducto(); break;
                    case 2: consultarProductos(); break;
                    case 3: buscarProducto(); break;
                    case 4: actualizarProducto(); break;
                    case 5: eliminarProducto(); break;
                    case 6: 
                        controlador.ordenar(1); 
                        System.out.println("Productos ordenados por Stock");
                        consultarProductos();
                        break;
                    case 7:
                        controlador.ordenar(); 
                        System.out.println("Productos ordenados por ID");
                        consultarProductos();
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

    private void crearProducto() {
        try {
            System.out.print("Ingrese ID unico: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese Codigo SKU: ");
            String sku = scanner.nextLine();
            System.out.print("Ingrese Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese Descripcion: ");
            String desc = scanner.nextLine();
            System.out.print("Ingrese Precio Unitario: ");
            float precio = Float.parseFloat(scanner.nextLine());
            System.out.print("Ingrese Stock Actual: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese Proveedor: ");
            String prov = scanner.nextLine();
            System.out.print("Ingrese Tipo (Perecedero/Fragil/Electronico): ");
            String tipo = scanner.nextLine();

            Productos nuevo = new Productos(id, sku, nombre, desc, precio, stock, prov, tipo);
            
            if (controlador.insertar(nuevo)) {
                System.out.println("!Producto guardado exitosamente!");
            } else {
                System.out.println("Error: El codigo SKU ya existe en el sistema.");
            }
        } catch (Exception e) {
            System.out.println("Error al capturar datos. Verifique los formatos.");
        }
    }

    private void consultarProductos() {
        controlador.consultarTodos();
    }

    private void buscarProducto() {
        try {
            System.out.print("Ingrese el ID a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Productos p = (Productos) controlador.consultarUno(id);
            if (p != null) {
                System.out.println("Producto encontrado: " + p.toString());
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico.");
        }
    }

    private void actualizarProducto() {
        try {
            System.out.print("Ingrese el ID del producto a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nuevo Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva Descripcion: ");
            String desc = scanner.nextLine();
            System.out.print("Nuevo Precio Unitario: ");
            float precio = Float.parseFloat(scanner.nextLine());
            System.out.print("Nuevo Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Nuevo Proveedor: ");
            String prov = scanner.nextLine();

            Productos temp = new Productos(id, "", nombre, desc, precio, stock, prov, "");
            
            if (controlador.update(temp) != null) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar. Verifique que el ID exista.");
            }
        } catch (Exception e) {
            System.out.println("Error en los formatos de entrada.");
        }
    }

    private void eliminarProducto() {
        try {
            System.out.print("Ingrese el ID del producto a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            if (controlador.delete(id)) {
                System.out.println("Producto eliminado exitosamente.");
            } else {
                System.out.println("Error: Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numerico valido.");
        }
    }
}