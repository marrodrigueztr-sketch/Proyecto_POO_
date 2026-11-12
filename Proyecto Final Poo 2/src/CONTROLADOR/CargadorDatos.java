package CONTROLADOR;

import MODELO.*;
import java.util.ArrayList;

/**
 * Carga los datos iniciales del sistema (en memoria) una sola vez al arrancar.
 * Incluye el usuario Administrador por defecto.
 *
 * @author Grupo 1
 */
public class CargadorDatos {

    private static boolean cargado = false;

    /** Carga los datos de ejemplo de todas las entidades (solo la primera vez). */
    public static void cargarTodo() {
        if (cargado) return;
        cargado = true;

        ControladorPais cPais = new ControladorPais();
        ControladorCiudad cCiudad = new ControladorCiudad();
        ControladorAlmacen cAlmacen = new ControladorAlmacen();
        ControladorZona cZona = new ControladorZona();
        ControladorUsuario cUsuario = new ControladorUsuario();
        ControladorProductos cProductos = new ControladorProductos();
        ControladorMovimiento cMov = new ControladorMovimiento();

        // ---------- Ubicaciones ----------
        Pais colombia = new Pais(1, "Colombia");
        Pais peru = new Pais(2, "Perú");
        cPais.insertar(colombia);
        cPais.insertar(peru);

        Ciudad bogota = new Ciudad(1, "Bogotá", colombia);
        Ciudad medellin = new Ciudad(2, "Medellín", colombia);
        Ciudad lima = new Ciudad(3, "Lima", peru);
        cCiudad.insertar(bogota);
        cCiudad.insertar(medellin);
        cCiudad.insertar(lima);

        // ---------- Usuarios (Administrador por defecto) ----------
        AdministradorSistema admin = new AdministradorSistema(1, "Admin", "Sistema", "3000000000", "admin@erp.com", "Administrador", 1, "Admin123*");
        GerenteGeneral gerente = new GerenteGeneral(2, "Carlos", "Ruiz", "3110000000", "gerente@erp.com", "Gerente", 5, "Gerente123*");
        cUsuario.insertar(admin);
        cUsuario.insertar(gerente);

        // ---------- Almacenes ----------
        Almacen central = new Almacen(1, "Almacén Central", bogota, gerente);
        Almacen norte = new Almacen(2, "Bodega Norte", medellin, gerente);
        cAlmacen.insertar(central);
        cAlmacen.insertar(norte);

        OperarioBodega operario = new OperarioBodega(3, "Juan", "Perez", "3004560000", "operario@erp.com", "Operario", 1200000f, "Diurno", central, "Operario123*");
        OperarioBodega operario2 = new OperarioBodega(4, "Luisa", "Diaz", "3007890000", "luisa@erp.com", "Operario", 1250000f, "Nocturno", norte, "Luisa123*");
        cUsuario.insertar(operario);
        cUsuario.insertar(operario2);

        // ---------- Productos (registrados por el operario o el admin) ----------
        Productos tv = new Productos(5, "ELT-4012", "Televisor 50 pulgadas", "Smart TV 4K", 1800000f, 12, "Samsung", "Electrónico", 3);
        Productos leche = new Productos(2, "PER-1001", "Leche entera", "Caja x 1 litro", 4500f, 200, "Alpina", "Perecedero", 3);
        Productos vajilla = new Productos(8, "FRA-2005", "Vajilla de vidrio", "Juego de 12 piezas", 95000f, 30, "Corona", "Frágil", 1);
        Productos audifonos = new Productos(1, "ELT-3300", "Audífonos bluetooth", "Inalámbricos", 120000f, 45, "Sony", "Electrónico", 3);
        Productos queso = new Productos(4, "PER-1500", "Queso campesino", "Bloque de 500 g", 12000f, 8, "Colanta", "Perecedero", 4);
        cProductos.insertar(tv);
        cProductos.insertar(leche);
        cProductos.insertar(vajilla);
        cProductos.insertar(audifonos);
        cProductos.insertar(queso);

        // ---------- Zonas / Pasillos (agregan productos) ----------
        ArrayList<Productos> productosA = new ArrayList<>();
        productosA.add(tv);
        productosA.add(audifonos);
        cZona.insertar(new ZonaPasillo(1, "A-01", 10, central, productosA));
        ArrayList<Productos> productosB = new ArrayList<>();
        productosB.add(leche);
        productosB.add(queso);
        cZona.insertar(new ZonaPasillo(2, "B-01", 5, central, productosB));
        cZona.insertar(new ZonaPasillo(3, "N-01", 8, norte, new ArrayList<>()));

        // ---------- Movimientos logísticos ----------
        cMov.insertar(new Movimiento_logistico(1, "02/11/2026", "Entrada", 50, leche, operario));
        cMov.insertar(new Movimiento_logistico(2, "05/11/2026", "Salida", 3, tv, operario));
        cMov.insertar(new Movimiento_logistico(3, "28/10/2026", "Entrada", 20, audifonos, operario2));
        cMov.insertar(new Movimiento_logistico(4, "08/11/2026", "Traslado", 5, vajilla, operario2));
        cMov.insertar(new Movimiento_logistico(5, "10/11/2026", "Salida", 10, leche, operario));
    }
}
