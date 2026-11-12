/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sergio martinez y camilo londoño
 */


import controlador.ControladorEspecie;
import java.util.Scanner;
import modelo.Especie;

public class VistaEspecie {
    
    private Scanner leerDatos;
    public ControladorEspecie contEspecie;
    
    public VistaEspecie() {
        leerDatos = new Scanner(System.in);
        contEspecie = new ControladorEspecie();
        insertarDefault();
    }
    
    private void insertarDefault() {
        contEspecie.insertar(new Especie(30, "Cacao Criollo", "Theobroma cacao ssp. cacao"));
        contEspecie.insertar(new Especie(10, "Cacao Forastero", "Theobroma cacao ssp. sphaerocarpum"));
        contEspecie.insertar(new Especie(20, "Cacao Trinitario", "Theobroma cacao ssp. trinitario"));
    }
    
    private void insertar() {
        System.out.println("\n--- REGISTRO DE NUEVA ESPECIE ---");
        
        System.out.println("Digite el ID de la Especie:");
        int id = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar buffer
        
        System.out.println("Digite el nombre común de la Especie:");
        String nombre = leerDatos.nextLine();
        
        System.out.println("Digite el nombre cientifico de la Especie:");
        String nombreCientifico = leerDatos.nextLine();
        
        Especie nuevaEspecie = new Especie(id, nombre, nombreCientifico);
        
        if(contEspecie.insertar(nuevaEspecie)) {
            System.out.println("-> Especie Insertada con exito");
        } else {
            System.out.println("-> Error: Especie NO Insertada. ID repetido.");
        }
    }
    
    private void consultarUno() {
        System.out.println("Digite el ID de la especie a buscar:");
        int id = leerDatos.nextInt();
        leerDatos.nextLine(); 
        
        Especie espTemp = new Especie();
        espTemp.setId(id); 
        
        Especie encontrada = contEspecie.consultarUno(espTemp);
        
        if (encontrada != null) {
            System.out.println("-> Especie encontrada: " + encontrada.toString());
        } else {
            System.out.println("-> No existe ninguna especie con el ID: " + id);
        }
    }
    
    public void menu() {
        int val = -1;
        do {
            System.out.println("\n=== MENU ESPECIES ===");
            System.out.println("1. Insertar nueva especie");
            System.out.println("2. Consultar todas");
            System.out.println("3. Ordenar especies (Metodo Nativo)");
            System.out.println("4. Buscar una especie (Busqueda Binaria)");
            System.out.println("5. Ordenar especies (Burbuja)");
            System.out.println("0. Volver al menu principal"); 
            System.out.print("Digite su opcion: ");
            
            val = leerDatos.nextInt();
            leerDatos.nextLine(); 
            
            switch (val) {
                case 1: insertar(); break;
                case 2: contEspecie.consultarTodos(); break;
                case 3: 
                    contEspecie.ordenar(); 
                    System.out.println("-> Especies ordenadas correctamente.");
                    break;    
                case 4: consultarUno(); break;   
                case 5: contEspecie.ordenarBurbuja(); break;
                case 0: System.out.println("-> Saliendo al menu principal..."); break;
                default: System.out.println("-> Opción no valida."); break;
            }
        } while (val != 0);
    }
}
