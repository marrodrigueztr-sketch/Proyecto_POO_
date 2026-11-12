/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicios;
import modelo.Finca;
import modelo.Usuario;
import controlador.ControladorUsuario;
import modelo.Administrador;
import modelo.Trabajador;

/**
 *
 * @author sergi
 */
public class NewClass2 {
    public static void main(String[] args) {
        Finca f1 = new Finca(1, "Los Cacaos", "Norte");
        Finca f2 = new Finca(2, "La Esperanza", "Sur");
        
        ControladorUsuario cont= new ControladorUsuario();
        
        
       Usuario u1=new Administrador(111, "pedro", "el escamoso", 320, "pedrito@gmail.com", "allaaa", 1, "pasword1");
       cont.insertar(u1);
       Usuario u2=new Trabajador(999, "miguel", "castro", 311, "miguelitogmial.com", "cervantes", 2,"pasword2", f1, "cajero", 1000);
       cont.insertar(u2);
       Usuario u3=new Trabajador(555, "dani", "mejia", 321, "danieme@gmail.com", "carola", 2,"pasword3", f1, "recolector", 2000);
       cont.insertar(u3);
       Usuario u4=new Trabajador(222, "mateo", "buritica", 371, "teo@gmail.com", "aranjuez", 2,"pasword4", f2, "cotero", 5000);
       cont.insertar(u4);
       Usuario u5=new Trabajador(777, "sofia", "caicedo", 311, "sofi@gmail.com", "chipre", 2,"pasword5", f1, "conductora", 600);
        cont.insertar(u5);
        
        cont.ordenar();
        double nomina=0;
        for( Usuario u: cont.getListaUsuarios()){
            
            if (u instanceof Trabajador){
                
                Trabajador camellador= (Trabajador)u;
                
                Finca fincaobj=camellador.getFinca();
                if (fincaobj.getNombreFinca().equals("La Esperanza")){
                    
                    System.out.println("Trabajador"+camellador.getNombre()+"salario="+camellador.getSalario());
                    nomina+=camellador.getSalario();
                    }
            
              }
            
               
         } 
    }
    
}
