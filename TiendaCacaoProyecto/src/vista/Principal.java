
package vista;

/**
 * @author sergio martinez y camilo londoño                                                  
 */



import java.util.Scanner;
import modelo.Usuario;
import modelo.Administrador;
import modelo.Trabajador;
import modelo.Propietario;

public class Principal {
    
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        // Carga todas las vistas
        VistaUsuario menuUsuarios = new VistaUsuario();
        VistaFinca menuFincas = new VistaFinca();
        VistaEspecie menuEspecies = new VistaEspecie();
        VistaCiudad menuCiudades = new VistaCiudad();
        
        System.out.println("==========================================");
        System.out.println("  BIENVENIDO AL SISTEMA DE TIENDA CACAO");
        System.out.println("==========================================");
        
        // --- EL LOGIN REAL ---
        System.out.println("\n--- INICIO DE SESION ---");
        System.out.print("Digite su ID (Cedula) para ingresar: ");
        int idLogin = entrada.nextInt();
        entrada.nextLine(); // Limpiar buffer
        
        // Creo un usuario temporal 
        Usuario usuTemp = new Usuario();
        usuTemp.setIdCedula(idLogin);
        
        // Busco si existe
        Usuario usuarioLogueado = menuUsuarios.contusu.consultarUno(usuTemp);
        
        if (usuarioLogueado == null) {
            System.out.println("-> Error: Usuario no encontrado...");
            return; 
        }
        
        System.out.println("\nBienvenido, " + usuarioLogueado.getNombre() ); 
       
        // el rol
        int rol = 0;
        if (usuarioLogueado instanceof Administrador) { 
            rol = 1;
            System.out.println("Rol detectado: ADMINISTRADOR (Acceso Total)");
        } else if (usuarioLogueado instanceof Trabajador) {
            rol = 2;
            System.out.println("Rol detectado: TRABAJADOR (Gestión de Cacao)");
        } else if (usuarioLogueado instanceof Propietario) {
            rol = 3;
            System.out.println("Rol detectado: PROPIETARIO (Solo Lectura)");
        } else {
            System.out.println("Rol detectado: USUARIO BASE");
        }
        
        int opcion = -1;
        
        
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            
            if (rol == 1) {
                System.out.println("1. Gestionar Usuarios");
            }
            
            else if (rol == 1 || rol == 2) {
                System.out.println("2. Gestionar Fincas");
                System.out.println("3. Gestionar Especies");
                System.out.println("4. Gestionar Ciudades");
            }
            
            else if (rol == 3) {
                System.out.println("5. Consultar todas las Fincas");
                System.out.println("6. Consultar todas las Especies");
                System.out.println("7. Consultar todas las Ciudades");
            }
            
            System.out.println("0. Salir del Sistema");
            System.out.print("Digite su opcion: ");
            
            opcion = entrada.nextInt();
            entrada.nextLine(); // Limpiar el buffer
            
            switch (opcion) {
                case 1:
                    if (rol == 1) menuUsuarios.menu(); 
                    else System.out.println("-> Acceso Denegado.");
                    break;
                case 2:
                    if (rol == 1 || rol == 2) menuFincas.menu();
                    else System.out.println("-> Opcion no valida.");
                    break;
                case 3:
                    if (rol == 1 || rol == 2) menuEspecies.menu();
                    else System.out.println("-> Opcion no valida.");
                    break;
                case 4:
                    if (rol == 1 || rol == 2) menuCiudades.menu();
                    else System.out.println("-> Opcion no valida.");
                    break;
                
                case 5:
                    if (rol == 3) menuFincas.contFinca.consultarTodos();
                    break;
                case 6:
                    if (rol == 3) menuEspecies.contEspecie.consultarTodos();
                    break;
                case 7:
                    if (rol == 3) menuCiudades.contCiudad.consultarTodos();
                    break;
                    
                case 0:
                    System.out.println("-> Cerrando sesion... ¡Chaoo!");
                    break;
                default:
                    System.out.println("-> Opcion incorrecta.");
            }
            
        } while (opcion != 0);
        
        entrada.close();
    }
}