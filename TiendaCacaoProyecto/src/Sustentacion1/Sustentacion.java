/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sustentacion1;

/**
 *
 * @author TRABAJE CON CAMILO LONDOÑO
 * MI NOMBRE: SERGIO MARTINEZ
 */

import modelo.Usuario;
import controlador.ControladorUsuario;
import modelo.Finca;
import java.util.Scanner;

public class Sustentacion {
    public static void main (String[] args){
        
        Finca f1 = new Finca(1, "Los Cacaos", "Norte");
        Finca f2 = new Finca(2, "La Esperanza", "Sur");
        ControladorUsuario cont=new ControladorUsuario();
        Scanner leer= new Scanner(System.in);
        
       //Usuario(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena,Finca finca)
       Usuario u1=new Usuario(111, "pedro", "el escamoso", 320, "pedrito@gmail.com", "allaaa", 1, "pasword1",f1);
       cont.insertar(u1);
       Usuario u2=new Usuario(999, "miguel", "castro", 311, "miguelitogmial.com", "cervantes", 2,"pasword2",f1 );
       cont.insertar(u2);
       Usuario u3=new Usuario (555, "dani", "mejia", 321, "danieme@gmail.com", "carola", 2,"pasword3",f1);
       cont.insertar(u3);
       Usuario u4=new Usuario(222, "mateo", "buritica", 371, "teo@gmail.com", "aranjuez", 2,"pasword4",f1);
       cont.insertar(u4);
       
       // el arreglo ya esta implicito en el controlador usuario no se tiene q volver a declarar
       System.out.println("digite la cedula del usuario a modificar (111)-(999)-(555)-(222)");
       int idBuscar= leer.nextInt();
       
       System.out.println("USUARIOS ANTES DE MODIFICAR UNO");
       for (Usuario u:cont.getListaUsuarios()){
           
           if(u.getIdCedula()==idBuscar){
               System.out.println("Ingrese su nuevo id:");
               int idNuevo=leer.nextInt();
               u.setIdCedula(idNuevo);
               
               System.out.println("Ingrese su nuevo nombre");
               String nombreNuevo= leer.nextLine();
               u.setNombre(nombreNuevo);
               
               System.out.println("Ingrese su apellido");
               String apellidoNuevo= leer.nextLine();
               u.setApellido(apellidoNuevo);
               
               System.out.println("Ingrese su numero telefonico");
               int numNuevo= leer.nextInt();
               u.setTelefono(numNuevo);
               
               System.out.println("Ingrese su nuevo correo");
               String correoNuevo= leer.nextLine();
               u.setCorreo(correoNuevo);
               
               System.out.println("Ingrese su nueva direccion");
               String direccionNuevo= leer.nextLine();
               u.setDireccion(direccionNuevo);
               
               System.out.println("ingrese su nuevo roll(1-2-3)");
               int rollNuevo= leer.nextInt();
               u.setRoll(rollNuevo);
               
               System.out.println("Ingrese su nueva contraseña");
               String contraNuevo= leer.nextLine();
               u.setContrasena(contraNuevo);
               
               System.out.println("Desea modificar su finca1  por finca 2?"+u.getFinca()+"finca 2"+f2.getNombreFinca()+"1 para si y 2 para no");
               int resp= leer.nextInt();
               if (resp==1)
                   u.setFinca(f2);
               else
                   System.out.println(" no se modifico nada");
               
               cont.modificar(u);
               
           }
       }
       System.out.println("MOSTRANDO USUARIOS DESPUES DE MODIFICAR UNO"); 
       cont.consultarTodos();
        
    }
}
