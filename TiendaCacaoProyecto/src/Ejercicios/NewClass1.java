/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicios;

import controlador.ControladorUsuario;
import modelo.Finca;
import modelo.Trabajador;
import modelo.Usuario;

/**
 *
 * @author sergi
 */
public  class NewClass1 {

        public static void main(String[] args) {
            ControladorUsuario cont = new ControladorUsuario();
            Finca f1 = new Finca(14, "Haciendacacao", "mas alla");
            Usuario u1 = new Trabajador(333, "pepe", "grillo", 320, "ahsdha@gmail.com", "nose", 2, "pasword2", f1, "cotero", 200);
            cont.insertar(u1);
            Usuario u2 = new Trabajador(13, "manolo", "mu\u00f1oz", 150, "ahsdha@gmail.com", "nose", 2, "pasword1", f1, "recolector", 100);
            cont.insertar(u2);
            Usuario u3 = new Trabajador(100, "papo", "escalante", 314, "ahsdha@gmail.com", "nose", 2, "pasword3", f1, "conductor", 4000);
            cont.insertar(u3);
            System.out.println(u1.toString());
            for (Usuario u : cont.getListaUsuarios()) {
                if (u.getIdCedula() == 333) {
                    u.setNombre("trabajador ascendido");
                    if (u instanceof Trabajador) {
                        Trabajador camelladorReal = (Trabajador) u;
                        camelladorReal.setSalario(999);
                    }
                }
            }
        }
    }
