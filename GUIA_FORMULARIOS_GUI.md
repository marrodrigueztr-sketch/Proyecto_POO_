# Guía de los formularios GUI (para una IA asistente)

Contexto: proyecto Java Swing en NetBeans, carpeta `Proyecto Final Poo 2/src`, patrón MVC.

| Paquete | Contenido |
|---|---|
| `MODELO` | Entidades. Sin Swing ni impresiones. |
| `CONTROLADOR` | CRUD que implementa `ICRUD`. Listas `static` compartidas. `CargadorDatos` carga los datos de ejemplo. |
| `UTILIDADES` | `Ordenamientos`: Quicksort, Mergesort, Burbuja, `List.sort` y búsqueda binaria, genéricos. |
| `VistaGUI` | `FrmLogin`, `FrmMenuPrincipal`, un `Frm` por entidad y `FormUtil` (apoyo). |

**Todos los formularios ya existen.** Usa esta guía para modificarlos o crear uno nuevo. La plantilla más simple es `FrmPaises`; la más completa, `FrmUsuarios`.

## Reglas obligatorias
1. Cada formulario tiene **dos archivos**: `FrmX.java` y `FrmX.form`. Si agregas o quitas componentes, actualiza los dos (o hazlo desde el diseñador de NetBeans).
2. El contenido de `initComponents()` y de los bloques `GEN-BEGIN/GEN-END` lo genera NetBeans. La lógica propia va **fuera** de esos bloques.
3. Las ventanas secundarias usan `DISPOSE_ON_CLOSE`, para que al cerrarlas se vuelva al menú.
4. El MODELO no usa Swing. La validación de la GUI se hace con `FormUtil`, que lanza `IllegalArgumentException` con el mensaje; el formulario la atrapa y la muestra con `FormUtil.aviso(this, ex.getMessage())`.
5. Los ordenamientos para mostrar en la tabla se hacen **sobre copias**, usando `Ordenamientos`. La lista original queda ordenada por ID ascendente, que es lo que necesita la búsqueda binaria.

## Estructura común de cada formulario
- **Diseño**:
  - `panelSuperior`: título, `panelDatos` (`GridLayout` de 4 columnas) y `panelBotones`.
  - `scrollTabla` con `tblDatos`.
  - `panelInferior`: `panelOrdenar` y `panelFiltrar`.
- **Constructores**:
  - `FrmX()`: lo usa el diseñador; abre en solo lectura.
  - `FrmX(Usuarios u)`: modo gestión.
  - `FrmX(Usuarios u, boolean modoConsulta)`: lo usa el menú Consultas.
- **Métodos de apoyo genéricos**: `aplicarPermisos()`, `llenarTabla(List)`, `limpiarCampos()`.
- **Métodos propios de cada entidad**:

  | Método | Qué hace |
  |---|---|
  | `cargarCombos()` | Llena los combos. Cada ítem tiene el formato `"id - texto"`; se lee con `FormUtil.idSeleccionado`. |
  | `fila(E)` | Arma la fila de la tabla para una entidad. |
  | `mostrar(E)` | Carga los datos de la entidad en los campos. |
  | `leerFormulario()` | Lee y valida los campos; devuelve la entidad. |
  | `nombreDe(E)` | Texto que se muestra al confirmar la eliminación. |
  | `validarExtra(E, nuevo)` | Validaciones adicionales al guardar o modificar. |
  | `validarEliminar(E)` | Restricciones adicionales al eliminar. |
  | `permisosExtra()` | Permisos adicionales según el rol o el modo. |
  | `despuesDeLimpiar()` | Ajustes después de limpiar los campos. |

- **Botones**:

  | Botón | Acción |
  |---|---|
  | Guardar | `leerFormulario` → `existeId` → `validarExtra` → `insertar` |
  | Buscar | `consultarUno(id)` (búsqueda binaria) |
  | Modificar | `update` |
  | Eliminar | confirmar → `delete(id, usuarioLogueado)` (solo el Administrador) |
  | Ordenar | `switch` del combo → método del controlador |
  | Filtrar | método `filtrar...` del controlador |

## Roles
- **Administrador**: puede hacer todo.
- **Operario**: el botón Eliminar aparece desactivado y no ve la gestión de Usuarios.
- **Gerente** o modo consulta: se ocultan Guardar, Modificar y Eliminar, y los campos quedan de solo lectura.

La regla se aplica en `aplicarPermisos()` y, al eliminar, se vuelve a comprobar en `ControladorUsuario.puedeEliminar`.

## Paso a paso para agregar un formulario nuevo
1. **Controlador**:
   - Lista `static`.
   - `existeId`.
   - `delete(obj, Usuarios)` que usa `ControladorUsuario.puedeEliminar`.
   - Ordenamientos con `Ordenamientos.quicksort/mergesort/burbuja/nativo`.
   - Un método `filtrarPor...`.
2. Copia `FrmPaises.java` y `FrmPaises.form` con el nombre nuevo. Reemplaza el nombre de la clase en los dos archivos.
3. Ajusta los campos, las columnas y las opciones de ordenar y filtrar, y adapta los métodos propios de la entidad.
4. Agrega la opción al menú en `FrmMenuPrincipal`: `abrirVentana(new FrmX(usuarioLogueado))` en Gestión y `abrirVentana(new FrmX(usuarioLogueado, true))` en Consultas.
5. Haz Clean and Build y ejecuta (arranca en `FrmLogin`). Prueba con `admin@erp.com` / `Admin123*`, `operario@erp.com` / `Operario123*` y `gerente@erp.com` / `Gerente123*`.
