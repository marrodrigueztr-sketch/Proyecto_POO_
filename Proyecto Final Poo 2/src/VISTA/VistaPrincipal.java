/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
package VISTA;

import CONTROLADOR.ControladorAlmacen;
import CONTROLADOR.ControladorMovimiento;
import CONTROLADOR.ControladorProductos;
import CONTROLADOR.ControladorUsuario;
import java.util.Scanner;

public class VistaPrincipal {
    
    private ControladorProductos controladorProductos;
    private ControladorUsuario controladorUsuario;
    private ControladorAlmacen controladorAlmacen;
    private ControladorMovimiento controladorMovimiento;
    private Scanner scanner;

    public VistaPrincipal() {
        this.controladorProductos = new ControladorProductos();
        this.controladorUsuario = new ControladorUsuario();
        this.controladorAlmacen = new ControladorAlmacen();
        this.controladorMovimiento = new ControladorMovimiento();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("=== ERP CORPORATIVO - GRUPO 1 ===");
            System.out.println("1. Gestionar Productos (Inventario)");
            System.out.println("2. Gestionar Usuarios");
            System.out.println("3. Gestionar Almacenes (Infraestructura)");
            System.out.println("4. Gestionar Movimientos (Logistica)");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        VistaProductos vp = new VistaProductos(controladorProductos, scanner);
                        vp.mostrarMenu();
                        break;
                    case 2:
                        VistaUsuarios vu = new VistaUsuarios(controladorUsuario, scanner);
                        vu.mostrarMenu();
                        break;
                    case 3:
                        VistaAlmacenes va = new VistaAlmacenes(controladorAlmacen, scanner);
                        va.mostrarMenu();
                        break;
                    case 4:
                        VistaMovimientos vm = new VistaMovimientos(controladorMovimiento, scanner);
                        vm.mostrarMenu();
                        break;
                    case 5:
                        System.out.println("Cerrando el sistema ERP...");
                        break;
                    default:
                        System.out.println("Error: Ingrese una opcion valida entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
                opcion = 0; 
            }
        } while (opcion != 5);
    }

    public static void main(String[] args) {
        VistaPrincipal app = new VistaPrincipal();
        app.iniciar();
    }
}