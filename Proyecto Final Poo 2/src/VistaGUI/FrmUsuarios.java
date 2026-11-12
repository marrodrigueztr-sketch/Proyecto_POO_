/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VistaGUI;

import CONTROLADOR.ControladorUsuario;
import MODELO.GerenteGeneral;
import MODELO.Usuarios;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import CONTROLADOR.ControladorAlmacen;
import MODELO.AdministradorSistema;
import MODELO.Almacen;
import MODELO.OperarioBodega;

/**
 * Formulario CRUD de usuarios del sistema (Administrador, Gerente General y
 * Operario de Bodega). Las contraseñas se escriben ocultas y nunca se muestran.
 *
 * @author Grupo 1
 */
public class FrmUsuarios extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmUsuarios.class.getName());

    private final ControladorUsuario controlador = new ControladorUsuario();
    private Usuarios usuarioLogueado;
    private boolean soloLectura;
    private DefaultTableModel modeloTabla;

    // ==================================================================================
    // Inicialización y permisos
    // ==================================================================================

    /**
     * Creates new form FrmUsuarios (lo usa el diseñador de NetBeans; abre en solo lectura).
     */
    public FrmUsuarios() {
        this(null, true);
    }

    /**
     * Abre el formulario para el usuario que inició sesión.
     *
     * @param usuario usuario logueado
     */
    public FrmUsuarios(Usuarios usuario) {
        this(usuario, false);
    }

    /**
     * @param usuario usuario logueado
     * @param modoConsulta true para abrir en modo solo consulta (menú Consultas)
     */
    public FrmUsuarios(Usuarios usuario, boolean modoConsulta) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.soloLectura = modoConsulta || usuario == null || usuario instanceof GerenteGeneral;
        modeloTabla = FormUtil.configurarTabla(tblDatos, "ID", "Rol", "Nombre", "Apellido", "Teléfono", "Correo", "Cargo", "Detalle");
        cargarCombos();
        aplicarPermisos();
        llenarTabla(controlador.getListaUsuarios());
    }

    private void aplicarPermisos() {
        if (soloLectura) {
            setTitle("Consulta de Usuarios");
            lblTitulo.setText("Consulta de Usuarios");
            btnGuardar.setVisible(false);
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            // Solo se deja escribir el ID para poder buscar
            cmbRol.setEnabled(false);
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtTelefono.setEditable(false);
            txtCorreo.setEditable(false);
            txtCargo.setEditable(false);
            txtContrasena.setEditable(false);
            txtNivel.setEditable(false);
            txtSalario.setEditable(false);
            cmbTurno.setEnabled(false);
            cmbAlmacen.setEnabled(false);
        } else if (!ControladorUsuario.puedeEliminar(usuarioLogueado)) {
            // Operario de bodega: crea, consulta y actualiza, pero no elimina
            btnEliminar.setEnabled(false);
            btnEliminar.setToolTipText("Solo el administrador puede eliminar registros.");
        }
        permisosExtra();
    }

    /** Muestra en la tabla la lista recibida (completa, ordenada o filtrada). */
    private void llenarTabla(List<Usuarios> lista) {
        modeloTabla.setRowCount(0);
        for (Usuarios e : lista) {
            modeloTabla.addRow(fila(e));
        }
        lblResultado.setText("Mostrando " + lista.size() + " registro(s)");
    }

    private void limpiarCampos() {
        txtId.setText("");
        if (cmbRol.getItemCount() > 0) cmbRol.setSelectedIndex(0);
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCargo.setText("");
        txtContrasena.setText("");
        txtNivel.setText("");
        txtSalario.setText("");
        if (cmbTurno.getItemCount() > 0) cmbTurno.setSelectedIndex(0);
        if (cmbAlmacen.getItemCount() > 0) cmbAlmacen.setSelectedIndex(0);
        despuesDeLimpiar();
        tblDatos.clearSelection();
        txtId.requestFocus();
    }

    // ==================================================================================
    // Lógica propia de Usuarios
    // ==================================================================================

    private void cargarCombos() {
        cmbAlmacen.removeAllItems();
        cmbAlmacen.addItem(FormUtil.item(0, "Sin asignar"));
        for (Almacen a : new ControladorAlmacen().getListaAlmacenes()) {
            cmbAlmacen.addItem(FormUtil.item(a.getId(), a.getNombre()));
        }
        actualizarCamposRol();
    }

    /** Habilita solo los campos que aplican al rol seleccionado. */
    private void actualizarCamposRol() {
        if (soloLectura) return;
        boolean operario = cmbRol.getSelectedIndex() == 2;
        txtNivel.setEnabled(!operario);
        txtSalario.setEnabled(operario);
        cmbTurno.setEnabled(operario);
        cmbAlmacen.setEnabled(operario);
    }

    private Object[] fila(Usuarios u) {
        String detalle;
        if (u instanceof AdministradorSistema a) {
            detalle = "Nivel " + a.getNivel();
        } else if (u instanceof GerenteGeneral g) {
            detalle = "Nivel " + g.getNivel();
        } else if (u instanceof OperarioBodega o) {
            String almacen = o.getAlmacenAsignado() != null ? o.getAlmacenAsignado().getNombre() : "sin almacén";
            detalle = String.format("$%.0f, %s, %s", o.getSalario(), o.getTurno(), almacen);
        } else {
            detalle = "";
        }
        return new Object[]{u.getId(), ControladorUsuario.obtenerRol(u), u.getNombre(), u.getApellido(),
            u.getTelefono(), u.getCorreo(), u.getCargo(), detalle};
    }

    private void mostrar(Usuarios u) {
        txtId.setText(String.valueOf(u.getId()));
        cmbRol.setSelectedItem(ControladorUsuario.obtenerRol(u));
        txtNombre.setText(u.getNombre());
        txtApellido.setText(u.getApellido());
        txtTelefono.setText(u.getTelefono());
        txtCorreo.setText(u.getCorreo());
        txtCargo.setText(u.getCargo());
        txtContrasena.setText(""); // la contraseña nunca se muestra
        txtNivel.setText("");
        txtSalario.setText("");
        if (u instanceof AdministradorSistema a) {
            txtNivel.setText(String.valueOf(a.getNivel()));
        } else if (u instanceof GerenteGeneral g) {
            txtNivel.setText(String.valueOf(g.getNivel()));
        } else if (u instanceof OperarioBodega o) {
            txtSalario.setText(String.valueOf(o.getSalario()));
            cmbTurno.setSelectedItem(o.getTurno());
            FormUtil.seleccionarPorId(cmbAlmacen, o.getAlmacenAsignado() != null ? o.getAlmacenAsignado().getId() : 0);
        }
        if (!soloLectura) {
            cmbRol.setEnabled(false); // el rol de un usuario existente no se cambia
            txtContrasena.setToolTipText("Deje la contraseña vacía para conservar la actual.");
        }
    }

    private Usuarios leerFormulario() {
        int id = FormUtil.entero(txtId, "ID", 1);
        String nombre = FormUtil.nombre(txtNombre, "Nombre");
        String apellido = FormUtil.nombre(txtApellido, "Apellido");
        String telefono = FormUtil.texto(txtTelefono, "Teléfono", 7);
        String correo = FormUtil.correo(txtCorreo);
        String cargo = FormUtil.texto(txtCargo, "Cargo", 3);
        String clave = new String(txtContrasena.getPassword());
        if (clave.isEmpty() && !controlador.existeId(id)) {
            txtContrasena.requestFocus();
            throw new IllegalArgumentException("La contraseña es obligatoria para un usuario nuevo.");
        }
        if (!clave.isEmpty() && clave.length() < 8) {
            txtContrasena.requestFocus();
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        switch (cmbRol.getSelectedIndex()) {
            case 0:
                return new AdministradorSistema(id, nombre, apellido, telefono, correo, cargo,
                        FormUtil.entero(txtNivel, "Nivel", 1), clave);
            case 1:
                return new GerenteGeneral(id, nombre, apellido, telefono, correo, cargo,
                        FormUtil.entero(txtNivel, "Nivel", 1), clave);
            default:
                float salario = FormUtil.decimal(txtSalario, "Salario", 0);
                String turno = (String) cmbTurno.getSelectedItem();
                int idAlmacen = FormUtil.idSeleccionado(cmbAlmacen);
                Almacen almacen = idAlmacen == 0 ? null : (Almacen) new ControladorAlmacen().consultarUno(idAlmacen);
                return new OperarioBodega(id, nombre, apellido, telefono, correo, cargo, salario, turno, almacen, clave);
        }
    }

    private String nombreDe(Usuarios u) {
        return "al usuario \"" + u.getNombre() + " " + u.getApellido() + "\"";
    }

    private void validarExtra(Usuarios u, boolean nuevo) {
        if (controlador.correoEnUso(u.getCorreo(), nuevo ? -1 : u.getId())) {
            txtCorreo.requestFocus();
            throw new IllegalArgumentException("El correo " + u.getCorreo() + " ya está registrado por otro usuario.");
        }
        if (!nuevo) {
            Usuarios actual = (Usuarios) controlador.consultarUno(u.getId());
            if (actual != null && actual.getClass() != u.getClass()) {
                throw new IllegalArgumentException("No se puede cambiar el rol de un usuario existente.");
            }
        }
    }

    private void validarEliminar(Usuarios u) {
        if (usuarioLogueado != null && u.getId() == usuarioLogueado.getId()) {
            throw new IllegalArgumentException("No puede eliminar su propio usuario mientras tiene la sesión iniciada.");
        }
        if (u instanceof AdministradorSistema && controlador.contarAdministradores() <= 1) {
            throw new IllegalArgumentException("Debe existir al menos un usuario Administrador.");
        }
    }

    private void permisosExtra() {
        if (soloLectura) {
            txtContrasena.setEnabled(false);
        }
    }

    private void despuesDeLimpiar() {
        if (!soloLectura) {
            cmbRol.setEnabled(true);
            txtContrasena.setToolTipText(null);
        }
        actualizarCamposRol();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblCargo = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblNivel = new javax.swing.JLabel();
        txtNivel = new javax.swing.JTextField();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        lblTurno = new javax.swing.JLabel();
        cmbTurno = new javax.swing.JComboBox<>();
        lblAlmacen = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox<>();
        panelBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        panelInferior = new javax.swing.JPanel();
        panelOrdenar = new javax.swing.JPanel();
        lblOrdenar = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();
        panelFiltrar = new javax.swing.JPanel();
        lblFiltroRol = new javax.swing.JLabel();
        cmbFiltroRol = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnMostrarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Usuarios");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gestión de Usuarios");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del usuario"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblId.setText("ID:");
        panelDatos.add(lblId);

        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        panelDatos.add(txtId);

        lblRol.setText("Rol:");
        panelDatos.add(lblRol);

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Gerente General", "Operario de Bodega" }));
        cmbRol.addActionListener(this::cmbRolActionPerformed);
        panelDatos.add(cmbRol);

        lblNombre.setText("Nombre:");
        panelDatos.add(lblNombre);

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panelDatos.add(txtNombre);

        lblApellido.setText("Apellido:");
        panelDatos.add(lblApellido);

        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        panelDatos.add(txtApellido);

        lblTelefono.setText("Teléfono:");
        panelDatos.add(lblTelefono);

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        panelDatos.add(txtTelefono);

        lblCorreo.setText("Correo:");
        panelDatos.add(lblCorreo);

        panelDatos.add(txtCorreo);

        lblCargo.setText("Cargo:");
        panelDatos.add(lblCargo);

        panelDatos.add(txtCargo);

        lblContrasena.setText("Contraseña:");
        panelDatos.add(lblContrasena);

        txtContrasena.setEchoChar('*');
        panelDatos.add(txtContrasena);

        lblNivel.setText("Nivel (admin/gerente):");
        panelDatos.add(lblNivel);

        txtNivel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNivelKeyTyped(evt);
            }
        });
        panelDatos.add(txtNivel);

        lblSalario.setText("Salario (operario):");
        panelDatos.add(lblSalario);

        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalarioKeyTyped(evt);
            }
        });
        panelDatos.add(txtSalario);

        lblTurno.setText("Turno (operario):");
        panelDatos.add(lblTurno);

        cmbTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diurno", "Nocturno", "Mixto" }));
        panelDatos.add(cmbTurno);

        lblAlmacen.setText("Almacén (operario):");
        panelDatos.add(lblAlmacen);

        panelDatos.add(cmbAlmacen);

        panelSuperior.add(panelDatos, java.awt.BorderLayout.CENTER);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        panelBotones.add(btnGuardar);

        btnBuscar.setText("Buscar por ID");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        panelBotones.add(btnBuscar);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);
        panelBotones.add(btnModificar);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        panelBotones.add(btnEliminar);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        panelBotones.add(btnLimpiar);

        panelSuperior.add(panelBotones, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        scrollTabla.setPreferredSize(new java.awt.Dimension(1000, 250));

        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tblDatos);

        getContentPane().add(scrollTabla, java.awt.BorderLayout.CENTER);

        panelInferior.setLayout(new java.awt.GridLayout(2, 1));

        panelOrdenar.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenar"));
        panelOrdenar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblOrdenar.setText("Ordenar por:");
        panelOrdenar.add(lblOrdenar);

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID descendente (Quicksort)", "Apellido A-Z (Mergesort)", "Nombre A-Z (Burbuja)", "ID ascendente (sort)" }));
        panelOrdenar.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOrdenar.add(btnOrdenar);

        lblResultado.setText(" ");
        panelOrdenar.add(lblResultado);

        panelInferior.add(panelOrdenar);

        panelFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar usuarios (filtro)"));
        panelFiltrar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblFiltroRol.setText("Rol:");
        panelFiltrar.add(lblFiltroRol);

        cmbFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Administrador", "Gerente General", "Operario de Bodega" }));
        panelFiltrar.add(cmbFiltroRol);

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(this::btnFiltrarActionPerformed);
        panelFiltrar.add(btnFiltrar);

        btnMostrarTodos.setText("Mostrar todos");
        btnMostrarTodos.addActionListener(this::btnMostrarTodosActionPerformed);
        panelFiltrar.add(btnMostrarTodos);

        panelInferior.add(panelFiltrar);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            Usuarios nuevo = leerFormulario();
            if (controlador.existeId(nuevo.getId())) {
                FormUtil.aviso(this, "Ya existe un registro con el ID " + nuevo.getId() + ".");
                return;
            }
            validarExtra(nuevo, true);
            if (controlador.insertar(nuevo)) {
                FormUtil.info(this, "Registro guardado correctamente.");
                llenarTabla(controlador.getListaUsuarios());
                limpiarCampos();
            } else {
                FormUtil.aviso(this, "No se pudo guardar: el ID o el correo ya están registrados.");
            }
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            int id = FormUtil.entero(txtId, "ID", 1);
            Usuarios encontrado = (Usuarios) controlador.consultarUno(id); // búsqueda binaria por ID
            if (encontrado == null) {
                FormUtil.info(this, "No se encontró ningún registro con el ID " + id + ".");
                return;
            }
            mostrar(encontrado);
            llenarTabla(java.util.Collections.singletonList(encontrado));
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            Usuarios datos = leerFormulario();
            if (!controlador.existeId(datos.getId())) {
                FormUtil.aviso(this, "No existe un registro con el ID " + datos.getId() + ". Búsquelo primero.");
                return;
            }
            validarExtra(datos, false);
            if (controlador.update(datos) != null) {
                FormUtil.info(this, "Registro actualizado correctamente.");
                llenarTabla(controlador.getListaUsuarios());
                limpiarCampos();
            } else {
                FormUtil.aviso(this, "No se pudo actualizar: verifique que el correo no esté repetido y que el rol no haya cambiado.");
            }
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            int id = FormUtil.entero(txtId, "ID", 1);
            Usuarios registro = (Usuarios) controlador.consultarUno(id);
            if (registro == null) {
                FormUtil.aviso(this, "No existe un registro con el ID " + id + ".");
                return;
            }
            validarEliminar(registro);
            if (!FormUtil.confirmar(this, "¿Eliminar " + nombreDe(registro) + "?" + "\nSi es operario se eliminarán sus movimientos; si es gerente, sus almacenes quedarán sin gerente.")) {
                return;
            }
            // El controlador vuelve a verificar el rol (solo el administrador elimina)
            if (controlador.delete(id, usuarioLogueado)) {
                FormUtil.info(this, "Registro eliminado.");
                llenarTabla(controlador.getListaUsuarios());
                limpiarCampos();
            } else {
                FormUtil.aviso(this, "No tiene permisos para eliminar registros.");
            }
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        llenarTabla(controlador.getListaUsuarios());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        Usuarios registro = (Usuarios) controlador.consultarUno(id);
        if (registro != null) {
            mostrar(registro);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        List<Usuarios> lista;
        switch (cmbOrden.getSelectedIndex()) {
            case 0: lista = controlador.ordenarQuicksortIdDesc(); break;
            case 1: lista = controlador.ordenarMergesortApellido(); break;
            case 2: lista = controlador.ordenarBurbujaNombre(false); break;
            default: lista = controlador.ordenarSortIdAsc(); break;
        }
        llenarTabla(lista);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        llenarTabla(controlador.filtrarPorRol((String) cmbFiltroRol.getSelectedItem()));
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        if (cmbFiltroRol.getItemCount() > 0) cmbFiltroRol.setSelectedIndex(0);
        llenarTabla(controlador.getListaUsuarios());
    }//GEN-LAST:event_btnMostrarTodosActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        FormUtil.soloLetras(evt);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        FormUtil.soloLetras(evt);
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtNivelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNivelKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtNivelKeyTyped

    private void txtSalarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyTyped
        FormUtil.soloDecimal(evt, txtSalario);
    }//GEN-LAST:event_txtSalarioKeyTyped

    private void cmbRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRolActionPerformed
        actualizarCamposRol();
    }//GEN-LAST:event_cmbRolActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        CONTROLADOR.CargadorDatos.cargarTodo();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmUsuarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnMostrarTodos;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JComboBox<String> cmbAlmacen;
    private javax.swing.JComboBox<String> cmbFiltroRol;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JComboBox<String> cmbTurno;
    private javax.swing.JLabel lblAlmacen;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblFiltroRol;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOrdenar;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNivel;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
