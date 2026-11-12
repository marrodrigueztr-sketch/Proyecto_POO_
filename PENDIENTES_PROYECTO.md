# Pendientes del proyecto ERP (Grupo 1)

Fuente de requisitos: `ProyectoPOO2026II-Grupo1.docx`. Los formularios se crean siguiendo `GUIA_FORMULARIOS_GUI.md`.

## Ya hecho
- `FrmLogin`: login por correo y contraseña, usuarios por defecto y validación de correo.
- `FrmMenuPrincipal`: menús Sesión, Gestión y Consultas, permisos por rol y cerrar sesión.
- `FrmProductos`: CRUD completo, validaciones, Quicksort, Mergesort, Burbuja, `sort`, filtros de existencias y roles.
- Clase principal del proyecto: `VistaGUI.FrmLogin`.

## Entrega 2 (GUI): falta
- [ ] Formularios: Almacenes, Zonas/Pasillos, Movimientos, Países, Ciudades y Usuarios (ver la tabla en la guía).
- [ ] Controladores nuevos: `ControladorPais`, `ControladorCiudad`, `ControladorZona`.
- [ ] Pasar a `static` las listas de `ControladorAlmacen` y `ControladorMovimiento`.
- [ ] Consultas filtradas de **movimientos logísticos** (por tipo, producto y fecha).
- [ ] Quicksort (ID descendente) y Mergesort (nombre) en todas las entidades, lanzados desde la GUI.
- [ ] Almacenes ordenados con `List.sort` por ID ascendente.
- [ ] **Diagrama UML** final en ArgoUML o StarUML.

## Entrega 3 (final): falta
- [ ] **Persistencia**: guardar y cargar todas las listas en archivos (por ejemplo, serialización o CSV) o en una base de datos. Cargar al iniciar y guardar en cada operación CRUD.
- [ ] **Validaciones con Regex y excepciones propias** (lanzar una excepción si no se cumplen):
  - Correo con formato corporativo.
  - Fecha `DD/MM/AAAA`.
  - SKU `AAA-0000`.
  - Password: mínimo 8 caracteres, con al menos 1 mayúscula, 1 dígito y 1 carácter especial.
  - Nombre de usuario: mínimo 3 caracteres, letras y espacios, sin caracteres especiales.
- [ ] CRUD de usuarios desde el panel del Administrador. Las contraseñas se muestran ocultas.
- [ ] Javadoc (HTML) de todas las clases, métodos y constructores.
- [ ] Ejecutable o acceso directo en el escritorio (el `.jar` de `dist/` más un script). Además, PDF de documentación y video.

## Decisiones tomadas
- **Productos sin herencia**: la profesora confirmó que no es necesaria. Se usa `String tipo` (Perecedero/Frágil/Electrónico) elegido en un combo. La herencia del proyecto se demuestra con `Usuarios` → `AdministradorSistema`, `GerenteGeneral` y `OperarioBodega`. **No proponer subclases de Productos.**

## Detalles a corregir
- `Usuarios` no sobrescribe `toString()`, así que las subclases muestran `MODELO.X@hash`.
- `OperarioBodega.toString()` no incluye los datos heredados.
- `ControladorUsuario.update` no actualiza la contraseña ni el correo.
- La consola (`VISTA/`) y la GUI crean controladores distintos. Ahora comparten datos porque las listas son `static`.
