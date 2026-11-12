/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJERCICIOS;

/**
 *
 * @author Marcelo
 */
/*
Los profesores suelen pedir que demuestres qué pasa cuando el sistema busca algo que no existe. Este ejercicio valida el comportamiento 
de la Búsqueda Binaria al usar consultarUno() evaluando tanto un caso exitoso como un caso de error.
*/


import CONTROLADOR.ControladorProductos;
import MODELO.Productos;

public class Ejercicio5{
    
    public static void main(String[] args) {
        ControladorProductos cp = new ControladorProductos();

        // 1. Crear 3 productos y almacenarlos
        cp.insertar(new Productos(100, "SKU-A", "Impresora", "Laser", 120.0f, 5, "HP", "Electronico"));
        cp.insertar(new Productos(105, "SKU-B", "Tinta", "Cartucho negro", 15.0f, 20, "HP", "Fragil"));
        cp.insertar(new Productos(110, "SKU-C", "Papel", "Resma 500 hojas", 5.0f, 100, "Norma", "Perecedero"));

        System.out.println("--- PRODUCTOS REGISTRADOS ---");
        cp.consultarTodos();

        // 2. Probar la busqueda binaria con un ID QUE NO EXISTE (Ej. 999)
        System.out.println("\nBuscando producto con ID 999...");
        Object resultadoNulo = cp.consultarUno(999);
        
        if (resultadoNulo == null) {
            System.out.println("Validacion correcta: El producto 999 no existe en el sistema.");
        }

        // 3. Probar la busqueda binaria con un ID QUE SI EXISTE (Ej. 105)
        System.out.println("\nBuscando producto con ID 105...");
        Productos pEncontrado = (Productos) cp.consultarUno(105); // Aplicamos el Casting
        
        if (pEncontrado != null) {
            System.out.println("Producto encontrado: " + pEncontrado.getNombre() + " - Stock actual: " + pEncontrado.getStockactual());
            
            // 4. Modificar el producto encontrado usando update()
            System.out.println("Descontando 5 unidades del stock...");
            pEncontrado.setStockactual(pEncontrado.getStockactual() - 5);
            cp.update(pEncontrado);
        }

        // 5. Mostrar como quedaron los datos por pantalla
        System.out.println("\n--- RESULTADO FINAL TRAS LA VENTA ---");
        cp.consultarTodos();
    }
}
