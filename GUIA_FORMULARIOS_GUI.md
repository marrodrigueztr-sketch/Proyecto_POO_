# Guía para crear los formularios GUI (para una IA asistente)

Contexto: proyecto Java Swing en NetBeans, carpeta `Proyecto Final Poo 2/src`, patrón MVC.
Paquetes: `MODELO`, `CONTROLADOR` (implementan `ICRUD`), `VistaGUI` (formularios).
**Plantilla a copiar: `VistaGUI/FrmProductos.java` + `FrmProductos.form`** (ya funciona completo).

## Reglas obligatorias

1. Cada formulario es un `JFrame` con **dos archivos**: `FrmX.java` y `FrmX.form`. Si cambias componentes en el `.java`, actualiza también el `.form` (si no, NetBeans pierde los cambios).
2. El código dentro de `initComponents()` y entre `GEN-BEGIN/GEN-END` debe coincidir exactamente con el `.form`. La lógica propia va **fuera** de esos bloques.
3. `defaultCloseOperation = DISPOSE_ON_CLOSE` (value="2" en el .form). Nunca `EXIT_ON_CLOSE`: al cerrar debe volver al menú.
4. Las clases MODELO no imprimen ni usan Swing. Toda validación de GUI va en el formulario.
5. Usa `JOptionPane` para mensajes y `try/catch (NumberFormatException)` al convertir números. La app nunca debe cerrarse por un error.
6. Nombres claros: `txtNombre`, `cmbTipo`, `btnGuardar`, `tblAlmacenes`, `lblTitulo`.

## Paso a paso para un formulario nuevo (ej. `FrmAlmacenes`)

### 1. Preparar el controlador
- Cambia la lista a `static` para que todas las ventanas compartan los datos:
  `private static ArrayList<Almacen> listaAlmacenes = new ArrayList<>();` y quita el `new ArrayList<>()` del constructor.
- Opcional: carga datos de ejemplo en el constructor si la lista está vacía.
- En `consultarUno` llama a `ordenar()` (por ID ascendente) antes de la búsqueda binaria.
- Agrega `existeId(int id)`, que devuelve `consultarUno(id) != null`.
- Agrega `delete(Object obj, Usuarios ejecutor)`: devuelve `false` si el ejecutor es `OperarioBodega` o `GerenteGeneral`.
- Agrega los ordenamientos para la GUI. **Trabajan sobre una copia** (`new ArrayList<>(lista)`) y devuelven `List<T>`:
  - `ordenarQuicksortIdDesc()`: Quicksort por ID descendente.
  - `ordenarMergesortNombre()`: Mergesort por nombre.
  - Burbuja por un criterio secundario (fecha, capacidad, etc.).
  - `List.sort` por ID ascendente. **Almacenes debe usar este.**
- Copia los algoritmos de `ControladorProductos`.

### 2. Crear el `.form` y el `.java`
Copia la estructura de `FrmProductos`:
- `BorderLayout` en el JFrame:
  - **PAGE_START:** `panelSuperior`, que contiene el título, `panelDatos` (`GridLayout(0,4,10,8)` con pares etiqueta/campo) y `panelBotones` (`FlowLayout`).
  - **CENTER:** `scrollTabla` con `JTable`, `preferredSize` 900x250.
  - **PAGE_END:** `panelInferior` (`GridLayout(2,1)`) con `panelOrdenar` y `panelFiltrar`.
- Para elegir objetos relacionados (ciudad, gerente, producto, operario) usa un `JComboBox`. Llénalo en el constructor con la lista del controlador correspondiente.

### 3. Constructores (sobrecarga, como en FrmProductos)
```java
public FrmAlmacenes() { this(null, true); }                 // diseñador NetBeans
public FrmAlmacenes(Usuarios u) { this(u, false); }
public FrmAlmacenes(Usuarios u, boolean modoConsulta) {
    initComponents();
    usuarioLogueado = u;
    soloLectura = modoConsulta || u == null || u instanceof GerenteGeneral;
    configurarTabla(); aplicarPermisos(); llenarTabla(controlador.getListaAlmacenes());
}
```

### 4. Métodos de apoyo (fuera de initComponents)
- `configurarTabla()`: crea un `DefaultTableModel` con las columnas y `isCellEditable` en `false`.
- `llenarTabla(List<T>)`: hace `setRowCount(0)` y luego un `addRow` por cada elemento.
- `aplicarPermisos()`:
  - **Solo lectura:** oculta Guardar, Modificar y Eliminar, y pone los campos como no editables.
  - **Operario:** `btnEliminar.setEnabled(false)`.
- `leerFormulario()`: valida los campos. Si algo está mal, muestra el aviso y devuelve `null`; si todo está bien, devuelve el objeto.
- `limpiarCampos()`, `mostrarEntidad(T)`, `soloNumeros(KeyEvent)` (en el evento `keyTyped` de los campos numéricos).

### 5. Eventos de botones
| Botón | Acción |
|---|---|
| Guardar | `leerFormulario()` → `existeId` → `insertar` → `llenarTabla` → `limpiarCampos` |
| Buscar | `consultarUno(id)` (búsqueda binaria) → `mostrarEntidad` |
| Modificar | `leerFormulario()` → `update` |
| Eliminar | confirmar → `controlador.delete(id, usuarioLogueado)` |
| Ordenar | `switch` del combo → método de orden → `llenarTabla(resultado)` |
| Clic en tabla | carga la fila en el formulario |

### 6. Conectar al menú
En `FrmMenuPrincipal.java` reemplaza `enConstruccion("...")` por
`abrirVentana(new FrmAlmacenes(usuarioLogueado));`. Para la opción de Consultas usa `abrirVentana(new FrmAlmacenes(usuarioLogueado, true));`.

### 7. Probar
Haz Clean and Build y luego Run (arranca en `FrmLogin`). Prueba con los tres usuarios:
- `admin@erp.com` / `Admin123*`
- `operario@erp.com` / `Operario123*`
- `gerente@erp.com` / `Gerente123*`

## Datos de cada formulario pendiente

| Formulario | Campos | Combos (relaciones) | Extra |
|---|---|---|---|
| FrmAlmacenes | id, nombre | Ciudad, Gerente (GerenteGeneral) | Orden por ID ascendente con `List.sort` |
| FrmZonas | id, códigoÁrea, capacidadMáxima | Almacén | Lista de productos de la zona |
| FrmMovimientos | id, fecha (DD/MM/AAAA), cantidad | Tipo (Entrada/Salida/Traslado), Producto, Operario | **Consulta filtrada** por tipo, producto y rango de fechas; Burbuja por fecha |
| FrmPaises | id, nombre | — | Hace falta crear `ControladorPais` |
| FrmCiudades | id, nombre | País | Hace falta crear `ControladorCiudad` |
| FrmUsuarios | id, nombre, apellido, teléfono, correo, cargo, contraseña (`JPasswordField`) | Rol (Admin/Gerente/Operario) | Solo Admin. Campos extra según el rol (nivel; salario, turno, almacén) |
