/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicios;
import modelo.Finca;
import controlador.ControladorUsuario;
import modelo.Propietario;
import modelo.Administrador;
import modelo.Propietario;
//import java.util.ArrayList;
import modelo.Trabajador;
import modelo.Usuario;
/**
 *
 * @author sergi
 */
public class NewClass {
    public static void main(String[] args) {
        
        Finca f1=new Finca(1, "naranja", "alla");
        Finca f2=new Finca(2, "cuchilla", "aca");
        
        ControladorUsuario cont=new ControladorUsuario();
        
        Usuario u1=new Administrador(15, "pedro", "sanchez", 3215, "pedro@gmail.com", "su casa", 1, "pasword1");
        cont.insertar(u1);
        
        Usuario u2=new Administrador(20, "pepe", "grillo", 315, "pepegmail.com", "el apartamento", 1, "pasword2");
        cont.insertar(u2);
        
        Usuario u3=new Trabajador(10, "albeiro", "mesa", 312, "alb@gmail.com", "por alla", 2, "pasword3", f1, "recolector", 100000000);
        cont.insertar(u3);
        
        Usuario u4= new Trabajador(7, "checho", "galvez", 314, "checho@gmail.com", "mi casa", 2, "pasword4", f1, "cajero", 500000000);
        cont.insertar(u4);
        
        Usuario u5=new Propietario(88, "camilo", "perez", 300, "milo12@gmailcom", "la casa de el", 3, "pasword5", f2);
        cont.insertar(u5);
        
        Usuario Trampa=new Usuario(88, "sancho", "son", 231, "hola@gmail.com", "nose donde vive", 2, "777");
        
        boolean se_pudo=cont.insertar(Trampa);
        if(!se_pudo){
            System.out.println(" se acabo de bloquear correctamente");
        }
        
        for( Usuario u: cont.getListaUsuarios()){
            if(u instanceof Trabajador){
                System.out.println(u.toString());
            }
        }
        
    }
}
