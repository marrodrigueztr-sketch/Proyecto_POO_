/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sergio martinez y camilo londoño
 */

import controlador.ControladorCiudad;
import java.util.Scanner;
import modelo.Ciudad;
import modelo.Pais;

public class VistaCiudad {
    
    private Scanner leerDatos;
    public ControladorCiudad contCiudad;
    
    public VistaCiudad() {
        leerDatos = new Scanner(System.in);
        contCiudad = new ControladorCiudad();
        insertarDefault();
    }
    
    private void insertarDefault() {
        Pais colombia = new Pais(1, "Colombia");
        Pais mexico = new Pais(2, "México");

        
        contCiudad.insertar(new Ciudad(5, "Manizales", colombia));
        contCiudad.insertar(new Ciudad(1, "Bogota", colombia));
        contCiudad.insertar(new Ciudad(3, "Guadalajara", mexico));
        ;
    }
    
    private void insertar() {
        System.out.println("\n--- REGISTRO DE NUEVA CIUDAD ---");
        
        System.out.println("Digite el ID de la Ciudad:");
        int idCiudad = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar buffer
        
        System.out.println("Digite el nombre de la Ciudad:");
        String nombreCiudad = leerDatos.nextLine();
        
       
        System.out.println("\n--- Datos del Pais al que pertenece ---");
        System.out.println("Digite el ID del Pais:");
        int idPais = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar buffer
        
        System.out.println("Digite el nombre del Pais:");
        String nombrePais = leerDatos.nextLine();
        
       
        Pais paisTemp = new Pais(idPais, nombrePais);
        
       
        Ciudad nuevaCiudad = new Ciudad(idCiudad, nombreCiudad, paisTemp);
        
        if(contCiudad.insertar(nuevaCiudad)) {
            System.out.println("-> ¡Ciudad Insertada con exito!");
        } else {
            System.out.println("-> Error: Ciudad NO Insertada. ID repetido.");
        }
    }
    
    private void consultarUno() {
        System.out.println("Digite el ID de la ciudad a buscar:");
        int id = leerDatos.nextInt();
        leerDatos.nextLine(); 
        
        Ciudad ciuTemp = new Ciudad();
        ciuTemp.setId(id); 
        
        Ciudad encontrada = contCiudad.consultarUno(ciuTemp);
        
        if (encontrada != null) {
            System.out.println("-> Ciudad encontrada: " + encontrada.toString());
        } else {
            System.out.println("-> No existe ninguna ciudad con el ID: " + id);
        }
    }
    
    public void menu() {
        int val = -1;
        do {
            System.out.println("\n=== MENU CIUDADES ===");
            System.out.println("1. Insertar nueva ciudad");
            System.out.println("2. Consultar todas");
            System.out.println("3. Ordenar ciudades (Metodo Nativo)");
            System.out.println("4. Buscar una ciudad (Busqueda Binaria)");
            System.out.println("5. Ordenar ciudades (Burbuja)");
            System.out.println("0. Volver al menu principal"); 
            System.out.print("Digite su opcion: ");
            
            val = leerDatos.nextInt();
            leerDatos.nextLine(); 
            
            switch (val) {
                case 1: insertar(); break;
                case 2: contCiudad.consultarTodos(); break;
                case 3: 
                    contCiudad.ordenar(); 
                    System.out.println("-> Ciudades ordenadas correctamente.");
                    break;    
                case 4: consultarUno(); break;   
                case 5: contCiudad.ordenarBurbuja(); break;
                case 0: System.out.println("-> Saliendo al menu principal..."); break;
                default: System.out.println("-> Opcion no valida."); break;
            }
        } while (val != 0);
    }
}
