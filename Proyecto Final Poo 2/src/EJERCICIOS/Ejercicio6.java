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
Este ejercicio evalúa directamente tu comprensión técnica de la interfaz CRUD. Demuestra la diferencia entre utilizar el método ordenar() 
(que usa el orden natural nativo por ID) y el ordenar(int) 
(que usa tu propio algoritmo Burbuja para organizar los textos alfabéticamente).
*/
import CONTROLADOR.ControladorAlmacen;
import MODELO.Almacen;

public class Ejercicio6{
    
    public static void main(String[] args) {
        ControladorAlmacen ca = new ControladorAlmacen();

        // 1. Crear 4 almacenes insertados de manera totalmente desordenada por ID y por Nombre
        ca.insertar(new Almacen(45, "Zeta Bodegas", null, null));
        ca.insertar(new Almacen(12, "Alfa Logistica", null, null));
        ca.insertar(new Almacen(89, "Delta Storage", null, null));
        ca.insertar(new Almacen(3, "Beta Inversiones", null, null));

        // 2. El metodo insertar llama a ordenar() por defecto. Verificamos el Orden Natural (Por ID ascendente)
        System.out.println("--- ORDEN NATURAL POR ID (Sort nativo de Java) ---");
        System.out.println("Deberia verse: ID 3, ID 12, ID 45, ID 89");
        ca.consultarTodos();

        // 3. Ejecutar algoritmo de Burbuja Alfabetico Ascendente (0)
        System.out.println("\n--- ORDENAMIENTO BURBUJA ALFABETICO (Ascendente) ---");
        System.out.println("Deberia verse: Alfa, Beta, Delta, Zeta");
        ca.ordenar(0);
        ca.consultarTodos();

        // 4. Ejecutar algoritmo de Burbuja Alfabetico Descendente (1)
        System.out.println("\n--- ORDENAMIENTO BURBUJA ALFABETICO (Descendente) ---");
        System.out.println("Deberia verse: Zeta, Delta, Beta, Alfa");
        ca.ordenar(1);
        ca.consultarTodos();
    }
}