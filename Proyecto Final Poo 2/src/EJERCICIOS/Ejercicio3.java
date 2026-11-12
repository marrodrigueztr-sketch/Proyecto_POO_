/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJERCICIOS;

/**
 *
 * @author Marcelo
 */

import CONTROLADOR.ControladorProductos;
import MODELO.Productos;


/*Este ejercicio valida que el controlador bloquea productos con SKU duplicados 
(gracias al método equals() del modelo) y prueba la Búsqueda Binaria al momento de realizar la actualización.*/

public class Ejercicio3 {
    
    public static void main(String[] args) {
        ControladorProductos cp = new ControladorProductos();

        // 1. Crear 4 productos utilizando el constructor completo
        // Se repite intencionalmente el SKU "SKU-001" en el cuarto producto para probar la restriccion
        Productos p1 = new Productos(1, "SKU-001", "Laptop Dell", "Laptop 15 pulgadas", 1500.0f, 10, "Dell Inc", "Electronico");
        Productos p2 = new Productos(2, "SKU-002", "Mouse Inalambrico", "Mouse optico", 25.0f, 50, "Logitech", "Electronico");
        Productos p3 = new Productos(3, "SKU-003", "Teclado Mecanico", "Switches rojos", 45.0f, 30, "Redragon", "Electronico");
        Productos p4 = new Productos(4, "SKU-001", "Monitor LG", "Monitor 24 pulgadas", 200.0f, 15, "LG", "Electronico");

        // 2. Almacenarlos en el arreglo (ArrayList) validando repetidos
        System.out.println("Insertando P1: " + cp.insertar(p1));
        System.out.println("Insertando P2: " + cp.insertar(p2));
        System.out.println("Insertando P3: " + cp.insertar(p3));
        System.out.println("Insertando P4 (SKU repetido): " + cp.insertar(p4)); // Debe imprimir false

        // 3. Modificar un producto (Ejemplo: actualizar precio y stock del ID 2)
        // Se instancia un objeto temporal con el mismo ID para enviarlo al update
        System.out.println("\nActualizando producto con ID 2...");
        Productos pModificado = new Productos(2, "SKU-002", "Mouse Inalambrico Pro", "Mouse optico gamer", 35.0f, 40, "Logitech", "Electronico");
        cp.update(pModificado);

        // 4. Mostrar como quedaron los datos por pantalla
        System.out.println("\n--- RESULTADO FINAL DEL INVENTARIO ---");
        cp.consultarTodos();
    }
}
