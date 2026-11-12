/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJERCICIOS;

/**
 *
 * @author Marcelo
 */

/*Este código instancia directamente los movimientos logísticos con fechas completamente desordenadas 
para probar que el método ordenar(int) implementa correctamente el algoritmo Burbuja.*/

import CONTROLADOR.ControladorMovimiento;
import MODELO.Movimiento_logistico;

public class Ejercicio2 {
    
    public static void main(String[] args) {
        ControladorMovimiento cm = new ControladorMovimiento();

        // 1. Crear 4 movimientos con fechas cronologicamente desordenadas
        Movimiento_logistico m1 = new Movimiento_logistico(1, "2026-10-15", "Entrada", 50, null, null);
        Movimiento_logistico m2 = new Movimiento_logistico(2, "2026-08-01", "Salida", 20, null, null);
        Movimiento_logistico m3 = new Movimiento_logistico(3, "2026-12-20", "Traslado", 10, null, null);
        Movimiento_logistico m4 = new Movimiento_logistico(4, "2026-09-23", "Entrada", 100, null, null);

        // 2. Almacenarlos
        cm.insertar(m1);
        cm.insertar(m2);
        cm.insertar(m3);
        cm.insertar(m4);

        // 3. Mostrar el estado inicial (Estaran ordenados por ID gracias al insertar)
        System.out.println("--- ORDEN NATURAL POR ID ---");
        cm.consultarTodos();

        // 4. Llamar al metodo ordenar con criterio secundario (Burbuja)
        // Pasamos 1 para que el controlador lo ordene de forma descendente (las fechas mas recientes primero)
        cm.ordenar(1);

        // 5. Mostrar como quedaron los datos tras aplicar el algoritmo Burbuja
        System.out.println("\n--- ORDENADO POR FECHA (BURBUJA DESCENDENTE) ---");
        cm.consultarTodos();
    }
}
