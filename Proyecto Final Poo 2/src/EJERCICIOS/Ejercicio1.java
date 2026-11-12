/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJERCICIOS;
import CONTROLADOR.ControladorUsuario;
import MODELO.AdministradorSistema;
import MODELO.OperarioBodega;
import MODELO.Usuarios;
/**
 *
 * @author Marcelo
 */

/*Este ejercicio demuestra la funcionalidad de la sobrecarga del método delete 
y cómo el sistema protege la información basándose en la clase que ejecuta la acción (polimorfismo).*/

public class Ejercicio1 {
    
    public static void main(String[] args) {
        ControladorUsuario cu = new ControladorUsuario();

        // 1. Crear 3 usuarios usando el constructor completo de sus clases hijas
        AdministradorSistema admin = new AdministradorSistema(1, "Ana", "Gomez", "300123", "ana@empresa.com", "Admin", 1, "admin123");
        OperarioBodega op1 = new OperarioBodega(2, "Juan", "Perez", "300456", "juan@empresa.com", "Operario", 1200.0f, "Diurno", null);
        OperarioBodega op2 = new OperarioBodega(3, "Luis", "Diaz", "300789", "luis@empresa.com", "Operario", 1200.0f, "Nocturno", null);

        // 2. Almacenarlos
        cu.insertar(admin);
        cu.insertar(op1);
        cu.insertar(op2);

        System.out.println("--- LISTA INICIAL DE USUARIOS ---");
        cu.consultarTodos();

        // 3. Intento de eliminacion sin permisos (Un operario intenta borrar a otro)
        System.out.println("\nIntento de borrado: op1 intenta eliminar a op2 (ID 3)");
        boolean borradoFallo = cu.delete(3, op1);
        System.out.println("Resultado de la operacion: " + borradoFallo);

        // 4. Intento de eliminacion con permisos (El administrador borra al operario)
        System.out.println("\nIntento de borrado: admin intenta eliminar a op2 (ID 3)");
        boolean borradoExito = cu.delete(3, admin);
        System.out.println("Resultado de la operacion: " + borradoExito);

        // 5. Mostrar como quedaron los datos
        System.out.println("\n--- LISTA FINAL DE USUARIOS ---");
        cu.consultarTodos();
    }
}