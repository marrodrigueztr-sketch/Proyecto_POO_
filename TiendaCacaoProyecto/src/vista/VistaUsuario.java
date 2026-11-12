/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sergio martinez y camilo londoño
 */

import controlador.ControladorUsuario;
import java.util.Scanner;
import modelo.Finca;
import modelo.Usuario;
import modelo.Administrador;
import modelo.Propietario;
import modelo.Trabajador;

public class VistaUsuario {
    
    private Scanner leerDatos;
    public ControladorUsuario contusu;
    
    public VistaUsuario() {
        leerDatos = new Scanner(System.in);
        contusu = new ControladorUsuario();
        insertarDefault(); 
    }
    
    //con esto pruebo las cosas sin tener q estar insertando a mano a cada rato
    private void insertarDefault() {
        /*
        Usuario usu = new Usuario(5, "checho", "galvez", 1110, "checho@gmail.com", "Carrera", 0, "checho123");
        contusu.insertar(usu);
        Usuario usu2 = new Usuario(1, "Ana", "Hurtado", 1110, "ana@gmail.com", "Carrera", 0, "ana123");
        contusu.insertar(usu2);
        Usuario usu3 = new Usuario(3, "camilo", "londoño", 1110, "cami@gmail.com", "Carrera", 0, "milo444");
        contusu.insertar(usu3);
       
        Finca fin = new Finca(12, "La Maria", "Vereda el Sancudo");
        Usuario usu4 = new Usuario(2, "Angela", "Jaramillo", 11113, "angela@hotmail.com", "Avenida", 1, "angela123", fin);
        contusu.insertar(usu4);
        
        */
        Finca miFinca = new Finca(12, "La Maria", "Vereda el Sancudo");
        contusu.insertar(new Administrador(111, "Juan Admin", "perez",321444,"ajsjsa@gmail.com","alli cerquita ",1,"111"));
        contusu.insertar(new Trabajador(222, "pepe", "grillo", 320554778, "pepegrillo@gmail.com", "alli cerca", 2, "222", miFinca, "camellodactilo", 500000));
        contusu.insertar(new Propietario(333, "pepe", "cadena", 311456897, "cadenap@gmailcom", "mas para alla", 3, "333", miFinca));
                
    }
    
    private void insertar() {
        Usuario usu = new Usuario(); //  constructor vacío
        
        System.out.println("Digite el nombre del Usuario:");
        usu.setNombre(leerDatos.nextLine()); // nextLine() lee todo el texto con espacios
        
        System.out.println("Digite la cedula del Usuario:");
        usu.setIdCedula(leerDatos.nextInt());
        leerDatos.nextLine(); //  Limpia el "Enter" oculto que deja el nextInt()
        
        System.out.println("Digite el telefono del Usuario:");
        usu.setTelefono(leerDatos.nextDouble());
        leerDatos.nextLine(); // limpiar el buffer de nuevo
        
        if(contusu.insertar(usu)) {
            System.out.println("-> Usuario Insertado con exito");
        } else {
            System.out.println("-> Error: Usuario NO Insertado. Cedula repetida.");
        }
    }
    
    private void consultarUno() {
        System.out.println("Digite la cedula del usuario a buscar:");
        int cedula = leerDatos.nextInt();
        leerDatos.nextLine(); // Limpiar el buffer
        
        Usuario usutem = new Usuario();
        usutem.setIdCedula(cedula);
        
        // aqui con el  <T>java ya sabe que devolverá un objeto de tipo Usuario.
        Usuario encontrado = contusu.consultarUno(usutem);
        
        if (encontrado != null) {
            System.out.println("-> Usuario encontrado: " + encontrado.toString());
        } else {
            System.out.println("-> No existe ningun usuario con la cedula: " + cedula);
        }
    }
    
    public void menu() {
        int val = -1; // Iniciamos en -1 para que entre al bucle
        
        
        do {
            System.out.println("\n=== MENU USUARIOS ===");
            System.out.println("1. Insertar nuevo usuario");
            System.out.println("2. Consultar todos");
            System.out.println("3. Ordenar lista (Metodo normal)");
            System.out.println("4. Buscar un Usuario (Busqueda Binaria)");
            System.out.println("5. Ordenar Usuarios (Burbuja)");
            System.out.println("6.Modificar");
            System.out.println("0. Salir"); 
            System.out.print("Digite su opcion: ");
            
            val = leerDatos.nextInt();
            leerDatos.nextLine(); // Limpiar buffer
            
            switch (val) {
                case 1:
                    insertar();        
                    break;
                case 2:
                    contusu.consultarTodos();
                    break;
                case 3:
                    contusu.ordenar();
                    System.out.println("-> Lista ordenada correctamente.");
                    break;    
                case 4:
                    consultarUno();
                    break;   
                case 5:
                    contusu.ordenarBurbuja();
                    break;
                case 6:
                    
                    System.out.println("Ingrese el ID del usuario que desea modificar");
                    int cedulaBuscar=leerDatos.nextInt();
                    leerDatos.nextLine();
                    
                    Usuario usuariobuscar=new Usuario();
                    usuariobuscar.setIdCedula(cedulaBuscar);
                    
                    Usuario usuarioOriginal=contusu.consultarUno(usuariobuscar);
                    if(usuarioOriginal!=null){
                        
                        System.out.println("Ingrese el nuevo ID: "+usuarioOriginal.getIdCedula());
                        int nuevaCedula=leerDatos.nextInt();
                        usuarioOriginal.setIdCedula(nuevaCedula);
                        
                        boolean exito = contusu.modificar(usuarioOriginal);
                        if(exito)
                            System.out.println("se modifico correctamente");
                        else
                            System.out.println("error: no se pudo guardar la modificacion");
                        
                        
                    }
                    
                    break;
                case 0:
                    System.out.println("-> Saliendo del menu de usuarios...");
                    break;
                default:
                    System.out.println("-> Opcion no válida. Intente de nuevo.");
                    break;
            }
        } while (val != 0); 
    }
}
