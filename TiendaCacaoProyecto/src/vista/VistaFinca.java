/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 * @author sergio martinez y camilo londoño
 */


import controlador.ControladorFinca;
import java.util.Scanner;
import modelo.Finca;

public class VistaFinca {
    
    private Scanner leerDatos;
    public ControladorFinca contFinca;
    
    public VistaFinca() {
        leerDatos = new Scanner(System.in);
        contFinca = new ControladorFinca();
        insertarDefault(); // Datos de prueba 
    }
    
    private void insertarDefault() {
        contFinca.insertar(new Finca(101, "El Paraiso", "Vereda Sur"));
        contFinca.insertar(new Finca(105, "La Esperanza", "Vereda Norte"));
        contFinca.insertar(new Finca(103, "Los Cacaos", "Vereda Central"));
    }
    
    private void insertar() {
        System.out.println("\n--- REGISTRO DE NUEVA FINCA ---");
        System.out.println("Digite el ID de la Finca:");
        int id = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar buffer
        
        System.out.println("Digite el nombre de la Finca:");
        String nombre = leerDatos.nextLine();
        
        System.out.println("Digite la direccion de la Finca:");
        String direccion = leerDatos.nextLine();
        
        // Creamos la finca y la enviamos al controlador
        Finca nuevaFinca = new Finca(id, nombre, direccion);
        
        if(contFinca.insertar(nuevaFinca)) {
            System.out.println("-> Finca Insertada con exito");
        } else {
            System.out.println("-> Error: Finca NO Insertada. ID repetido.");
        }
    }
    
    private void consultarUno() {
        System.out.println("Digite el ID de la finca a buscar:");
        int id = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar buffer
        
        Finca fincaTemp = new Finca();
        fincaTemp.setIdFinca(id); 
        
        Finca encontrada = contFinca.consultarUno(fincaTemp);
        
        if (encontrada != null) {
            System.out.println("-> Finca encontrada: " + encontrada.toString());
        } else {
            System.out.println("-> No existe ninguna finca con el ID: " + id);
        }
    }
    
    public void menu() {
        int val = -1;
        do {
            System.out.println("\n=== MENU FINCAS ===");
            System.out.println("1. Insertar nueva finca");
            System.out.println("2. Consultar todas");
            System.out.println("3. Ordenar fincas (Metodo Nativo)");
            System.out.println("4. Buscar una finca (Busqueda Binaria)");
            System.out.println("5. Ordenar fincas (Burbuja)");
            System.out.println("0. Volver al menu principal"); 
            System.out.print("Digite su opcion: ");
            
            val = leerDatos.nextInt();
            leerDatos.nextLine(); // Limpiar buffer
            
            switch (val) {
                case 1: 
                    insertar(); 
                    break;
                case 2: 
                    contFinca.consultarTodos(); 
                    break;
                case 3: 
                    contFinca.ordenar(); 
                    System.out.println("-> Fincas ordenadas correctamente.");
                    break;    
                case 4: 
                    consultarUno(); 
                    break;   
                case 5: 
                    contFinca.ordenarBurbuja(); 
                    break;
                case 0: System.out.println("-> Saliendo al menu principal..."); break;
                default: System.out.println("-> Opcion no valida."); break;
            }
        } while (val != 0);
    }
}
