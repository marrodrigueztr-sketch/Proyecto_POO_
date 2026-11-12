package controlador;

import modelo.*;

public class CargadorDatos {

    public static void cargarTodo() {
        ControladorPatologia conPatologia = new ControladorPatologia();
        conPatologia.insertar(new Patologia(1, "Roya", "Hemileia vastatrix"));
        conPatologia.insertar(new Patologia(2, "Broca", "Hypothenemus hampei"));
        // 1. Instanciamos los controladores estáticos
        ControladorPais conPais = new ControladorPais();
        ControladorCiudad conCiudad = new ControladorCiudad();
        ControladorEspecie conEspecie = new ControladorEspecie();
        ControladorUsuario conUsuario = new ControladorUsuario();
        ControladorFinca conFinca=new ControladorFinca();
        // ==========================================
        // 2. CREACIÓN DE PAÍSES
        // ==========================================
        Pais p1 = new Pais(1, "Colombia");
        Pais p2 = new Pais(2, "Brasil");
        Pais p3 = new Pais(3, "México");
        
        conPais.insertar(p1);
        conPais.insertar(p2);
        conPais.insertar(p3);

        // ==========================================
        // 3. CREACIÓN DE CIUDADES (Asignando el País)
        // ==========================================
        // Ciudades de Colombia (p1)
        Ciudad c1 = new Ciudad(101, "Manizales", p1);
        Ciudad c2 = new Ciudad(102, "Pereira", p1);
        Ciudad c3 = new Ciudad(103, "Armenia", p1);
        
        // Ciudades de Brasil (p2)
        Ciudad c4 = new Ciudad(201, "Sao Paulo", p2);
        Ciudad c5 = new Ciudad(202, "Minas Gerais", p2);
        
        // Ciudad de México (p3)
        Ciudad c6 = new Ciudad(301, "Veracruz", p3);
        
        conCiudad.insertar(c1);
        conCiudad.insertar(c2);
        conCiudad.insertar(c3);
        conCiudad.insertar(c4);
        conCiudad.insertar(c5);
        conCiudad.insertar(c6);

        // ==========================================
        // 4. CREACIÓN DE ESPECIES (Cultivos/Plantas)
        // ==========================================
        Especie e1 = new Especie(1, "Café Arábica");
        Especie e2 = new Especie(2, "Café Robusta");
        Especie e3 = new Especie(3, "cafe sello rojo");
        Especie e4 = new Especie(4, "cafe quindio");
        Especie e5 = new Especie(5, "Cacao Fino");
        
        conEspecie.insertar(e1);
        conEspecie.insertar(e2);
        conEspecie.insertar(e3);
        conEspecie.insertar(e4);
        conEspecie.insertar(e5);

        // ==========================================
        // CREACIÓN DE FINCAS (Constructor Completo)
        // ==========================================
        java.util.ArrayList<modelo.Especie> listaEspecies1 = new java.util.ArrayList<>();
        listaEspecies1.add(e1); 
        listaEspecies1.add(e2);

        java.util.ArrayList<modelo.Especie> listaEspecies2 = new java.util.ArrayList<>();
        listaEspecies2.add(e3);
        
        java.util.ArrayList<modelo.Especie> listaVacia = new java.util.ArrayList<>();

        // Constructor: Finca(idFinca, nombreFinca, direccionFinca, ciudad, arregloespecies)
        Finca f1 = new Finca(101, "La Esperanza", "Vereda El Carmen", c1, listaEspecies1);
        Finca f2 = new Finca(102, "El Mirador", "Km 5 Vía Principal", c2, listaEspecies2);
        Finca f3 = new Finca(103, "Villa Verde", "Sector Los Pinos", c3, listaEspecies1);
        Finca f4 = new Finca(104, "San Juan", "Vereda La Linda", c1, listaVacia);
        Finca f5 = new Finca(105, "El Ocaso", "Falda del Cerro", c2, listaEspecies2);
        Finca f6 = new Finca(106, "La Cumbre", "Alto de la Cruz", c4, listaEspecies1);
        
        // Asumiendo que tienes un ControladorFinca instanciado arriba como conFinca
        conFinca.insertar(f1);
        conFinca.insertar(f2);
        conFinca.insertar(f3);
        conFinca.insertar(f4);
        conFinca.insertar(f5);
        conFinca.insertar(f6);
        
        
        
        // ==========================================
        // 5. CREACIÓN DE USUARIOS (Roles: 1-Admin, 2-Trabajador, 3-Propietario)
      
        
        // -- 2 Administradores (Rol 1) --
        Usuario u1 = new Usuario(10001, "Carlos", "Gómez", 3101234567.0, "admin1@mail.com", "Calle 12 #3-4", 1, "123", f1);
        Usuario u2 = new Usuario(10002, "Diana", "López", 3209876543.0, "admin2@mail.com", "Carrera 5 #10-2", 1, "123", f2);
        
        // -- 2 Trabajadores (Rol 2) --
        Usuario u3 = new Usuario(20001, "José", "Martínez", 3154445555.0, "trabajador1@mail.com", "Vereda Sur", 2, "123", f1);
        Usuario u4 = new Usuario(20002, "María", "Pérez", 3001112222.0, "trabajador2@mail.com", "Vereda Norte", 2, "123", f3);
        
        // -- 2 Propietarios (Rol 3) --
        Usuario u5 = new Usuario(30001, "Luis", "Rodríguez", 3119998888.0, "propietario1@mail.com", "Avenida 8", 3, "123", f4);
        Usuario u6 = new Usuario(30002, "Ana", "García", 3125556666.0, "propietario2@mail.com", "Calle 50 #1-2", 3, "123", f5);
        
        conUsuario.insertar(u1);
        conUsuario.insertar(u2);
        conUsuario.insertar(u3);
        conUsuario.insertar(u4);
        conUsuario.insertar(u5);
        conUsuario.insertar(u6);
        System.out.println("--- Datos iniciales sembrados correctamente ---");
    }
}