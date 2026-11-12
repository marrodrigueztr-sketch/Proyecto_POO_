/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Marcelo
 */

/*
Este ejercicio evalúa tu capacidad para manejar relaciones entre clases (Composición/Agregación). Demuestra cómo un objeto principal (Almacen) 
contiene a otros objetos (Ciudad y GerenteGeneral), y cómo se actualizan estos objetos anidados.
*/
package EJERCICIOS;

import CONTROLADOR.ControladorAlmacen;
import MODELO.Almacen;
import MODELO.Ciudad;
import MODELO.GerenteGeneral;
import MODELO.Pais;

public class Ejercicio4 {
    
    public static void main(String[] args) {
        ControladorAlmacen ca = new ControladorAlmacen();

        // 1. Crear objetos complementarios (Dependencias)
        Pais colombia = new Pais(1, "Colombia");
        Ciudad bogota = new Ciudad(1, "Bogota", colombia);
        Ciudad medellin = new Ciudad(2, "Medellin", colombia);
        
        GerenteGeneral gerente1 = new GerenteGeneral(1, "Carlos", "Ruiz", "311", "carlos@erp.com", "Gerente", 5, "pass123");
        GerenteGeneral gerente2 = new GerenteGeneral(2, "Diana", "Perez", "312", "diana@erp.com", "Gerente", 5, "pass456");

        // 2. Crear 3 almacenes usando el constructor completo (inyectando las dependencias)
        Almacen a1 = new Almacen(10, "Almacen Central", bogota, gerente1);
        Almacen a2 = new Almacen(20, "Almacen Norte", medellin, gerente2);
        Almacen a3 = new Almacen(30, "Almacen Sur", bogota, null); // Sin gerente asignado aun

        // 3. Almacenarlos en el arreglo
        ca.insertar(a1);
        ca.insertar(a2);
        ca.insertar(a3);

        System.out.println("--- ESTADO INICIAL DE ALMACENES ---");
        ca.consultarTodos();

        // 4. Modificar un almacen: Asignarle gerente al Almacen Sur y cambiarle el nombre
        System.out.println("\nActualizando Almacen Sur (ID 30)...");
        Almacen aModificado = new Almacen(30, "Almacen Sur - Sede Principal", bogota, gerente1);
        ca.update(aModificado);

        // 5. Mostrar como quedaron los datos por pantalla
        System.out.println("\n--- RESULTADO FINAL DE ALMACENES ---");
        ca.consultarTodos();
    }
}
