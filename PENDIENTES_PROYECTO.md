# Estado del proyecto ERP (Grupo 1)

Requisitos: `ProyectoPOO2026II-Grupo1.docx`. Guía para modificar o crear formularios: `GUIA_FORMULARIOS_GUI.md`.

## Entrega 2 (GUI): hecho
- **Login** (`FrmLogin`): inicia sesión con correo y contraseña. Usuarios de prueba:
  - `admin@erp.com` / `Admin123*`
  - `gerente@erp.com` / `Gerente123*`
  - `operario@erp.com` / `Operario123*`
- **Menú principal** (`FrmMenuPrincipal`): tiene los menús Sesión, Gestión y Consultas, y permite cerrar sesión. Las ventanas secundarias se cierran y vuelven al menú.
- **Formularios CRUD**: Productos, Almacenes, Zonas/Pasillos, Movimientos, Países, Ciudades y Usuarios.
  - Cada uno tiene Guardar, Buscar por ID (búsqueda binaria), Modificar, Eliminar (con confirmación), Limpiar, y una tabla en la que un clic carga la fila.
- **Roles**:
  - **Administrador**: puede hacer todo.
  - **Operario**: crea, consulta y modifica, pero no elimina ni gestiona usuarios.
  - **Gerente**: solo consulta y ordena.
  - El controlador vuelve a validar el permiso al eliminar.
- **Ordenamientos lanzados desde la GUI** (`UTILIDADES/Ordenamientos`):
  - Quicksort por ID descendente, en todas las entidades.
  - Mergesort por nombre o apellido.
  - Burbuja por criterios secundarios: stock, fecha, capacidad, nombre.
  - `List.sort` por ID ascendente. Es el orden principal de Almacenes.
- **Consultas filtradas**:
  - Movimientos por tipo, producto y rango de fechas.
  - Existencias por tipo y stock máximo.
  - Además, filtros por país, ciudad, almacén y rol.
- **Validaciones en la GUI**:
  - Bloquea teclas inválidas: solo números, decimales, letras o fechas, según el campo.
  - Muestra mensajes claros: campos obligatorios, formato DD/MM/AAAA, IDs y correos repetidos.
  - Las contraseñas se escriben ocultas.
- **Eliminación con dependencias**:
  - País → ciudades → almacenes → zonas.
  - Al borrar un producto, se quita de las zonas y se borran sus movimientos.
  - Al borrar un operario, se borran sus movimientos.
  - Al borrar un gerente, sus almacenes quedan sin gerente.
- **Datos de ejemplo** en memoria (`CONTROLADOR/CargadorDatos`), que se cargan al iniciar desde el login.
- **UML**: `UML/diagrama_clases.puml` (PlantUML).

## Entrega 2: falta (lo hacen ustedes)
- [ ] Pasar el diagrama UML a **StarUML o ArgoUML**, como exige la guía. El `.puml` sirve de referencia: se puede ver en plantuml.com.

## Entrega 3 (final): pendiente
- [ ] **Persistencia**: guardar y cargar los datos en archivos o en una base de datos. Hoy todo vive en memoria y se reinicia al cerrar el programa.
- [ ] **Regex + excepciones propias**:
  - Correo corporativo.
  - Fecha DD/MM/AAAA.
  - SKU `AAA-0000`.
  - Contraseña: mínimo 8 caracteres, con al menos 1 mayúscula, 1 dígito y 1 carácter especial.
  - Nombre de usuario: mínimo 3 caracteres, solo letras y espacios.
  - Hoy las validaciones son básicas y usan `IllegalArgumentException`.
- [ ] Javadoc HTML completo.
- [ ] Ejecutable o acceso directo en el escritorio, más el PDF y el video.

## Decisiones tomadas
- **Productos sin herencia**: la profesora confirmó que no es necesaria. Se usa `String tipo` (Perecedero, Frágil, Electrónico) elegido en un combo. La herencia del proyecto se demuestra con `Usuarios` → `AdministradorSistema`, `GerenteGeneral` y `OperarioBodega`. **No proponer subclases de Productos.**
- Los ordenamientos de la GUI trabajan sobre copias de las listas, así la lista original sigue ordenada por ID y la búsqueda binaria funciona siempre.
- El rol de un usuario no se puede cambiar después de creado. Al modificar, si la contraseña se deja vacía, se conserva la anterior.
