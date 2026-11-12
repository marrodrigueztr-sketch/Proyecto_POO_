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
import CONTROLADOR.ControladorMovimiento;
import CONTROLADOR.ControladorProductos;
import MODELO.Movimiento_logistico;
import MODELO.OperarioBodega;
import MODELO.Productos;
import java.time.LocalDate;

/**
 * Formulario CRUD de movimientos logísticos (Entrada, Salida, Traslado), con
 * ordenamientos y consulta filtrada por tipo, producto y rango de fechas.
 *
 * @author Grupo 1
 */
public class FrmMovimientos extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMovimientos.class.getName());

    private final ControladorMovimiento controlador = new ControladorMovimiento();
    private Usuarios usuarioLogueado;
    private boolean soloLectura;
    private DefaultTableModel modeloTabla;

    // ==================================================================================
    // Inicialización y permisos
    // ==================================================================================

    /**
     * Creates new form FrmMovimientos (lo usa el diseñador de NetBeans; abre en solo lectura).
     */
    public FrmMovimientos() {
        this(null, true);
    }

    /**
     * Abre el formulario para el usuario que inició sesión.
     *
     * @param usuario usuario logueado
     */
    public FrmMovimientos(Usuarios usuario) {
        this(usuario, false);
    }

    /**
     * @param usuario usuario logueado
     * @param modoConsulta true para abrir en modo solo consulta (menú Consultas)
     */
    public FrmMovimientos(Usuarios usuario, boolean modoConsulta) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.soloLectura = modoConsulta || usuario == null || usuario instanceof GerenteGeneral;
        modeloTabla = FormUtil.configurarTabla(tblDatos, "ID", "Fecha", "Tipo", "Cantidad", "Producto", "Operario");
        cargarCombos();
        aplicarPermisos();
        llenarTabla(controlador.getListaMovimientos());
    }

    private void aplicarPermisos() {
        if (soloLectura) {
            setTitle("Consulta de Movimientos Logísticos");
            lblTitulo.setText("Consulta de Movimientos Logísticos");
            btnGuardar.setVisible(false);
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            // Solo se deja escribir el ID para poder buscar
            txtFecha.setEditable(false);
            cmbTipo.setEnabled(false);
            txtCantidad.setEditable(false);
            cmbProducto.setEnabled(false);
            cmbOperario.setEnabled(false);
        } else if (!ControladorUsuario.puedeEliminar(usuarioLogueado)) {
            // Operario de bodega: crea, consulta y actualiza, pero no elimina
            btnEliminar.setEnabled(false);
            btnEliminar.setToolTipText("Solo el administrador puede eliminar registros.");
        }
        permisosExtra();
    }

    /** Muestra en la tabla la lista recibida (completa, ordenada o filtrada). */
    private void llenarTabla(List<Movimiento_logistico> lista) {
        modeloTabla.setRowCount(0);
        for (Movimiento_logistico e : lista) {
            modeloTabla.addRow(fila(e));
        }
        lblResultado.setText("Mostrando " + lista.size() + " registro(s)");
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtFecha.setText("");
        if (cmbTipo.getItemCount() > 0) cmbTipo.setSelectedIndex(0);
        txtCantidad.setText("");
        if (cmbProducto.getItemCount() > 0) cmbProducto.setSelectedIndex(0);
        if (cmbOperario.getItemCount() > 0) cmbOperario.setSelectedIndex(0);
        despuesDeLimpiar();
        tblDatos.clearSelection();
        txtId.requestFocus();
    }

    // ==================================================================================
    // Lógica propia de Movimiento_logistico
    // ==================================================================================

    private final ControladorProductos controlProductos = new ControladorProductos();
    private final ControladorUsuario controlUsuarios = new ControladorUsuario();

    private void cargarCombos() {
        cmbProducto.removeAllItems();
        cmbFiltroProducto.removeAllItems();
        cmbFiltroProducto.addItem("Todos");
        for (Productos p : controlProductos.getListaProductos()) {
            String item = FormUtil.item(p.getId(), p.getSku() + " " + p.getNombre());
            cmbProducto.addItem(item);
            cmbFiltroProducto.addItem(item);
        }
        cmbOperario.removeAllItems();
        for (OperarioBodega o : controlUsuarios.getOperarios()) {
            cmbOperario.addItem(FormUtil.item(o.getId(), o.getNombre() + " " + o.getApellido()));
        }
        seleccionarOperarioActual();
    }

    /** Si quien registra es un operario, se propone él mismo como responsable. */
    private void seleccionarOperarioActual() {
        if (usuarioLogueado instanceof OperarioBodega) {
            FormUtil.seleccionarPorId(cmbOperario, usuarioLogueado.getId());
        }
    }

    private Object[] fila(Movimiento_logistico m) {
        String producto = m.getProductoInvolucrado() != null
                ? m.getProductoInvolucrado().getSku() + " " + m.getProductoInvolucrado().getNombre() : "-";
        String operario = m.getOperarioResponsable() != null
                ? m.getOperarioResponsable().getNombre() + " " + m.getOperarioResponsable().getApellido() : "-";
        return new Object[]{m.getId(), m.getFecha(), m.getTipo(), m.getCantidad(), producto, operario};
    }

    private void mostrar(Movimiento_logistico m) {
        txtId.setText(String.valueOf(m.getId()));
        txtFecha.setText(m.getFecha());
        cmbTipo.setSelectedItem(m.getTipo());
        txtCantidad.setText(String.valueOf(m.getCantidad()));
        FormUtil.seleccionarPorId(cmbProducto, m.getProductoInvolucrado() != null ? m.getProductoInvolucrado().getId() : 0);
        FormUtil.seleccionarPorId(cmbOperario, m.getOperarioResponsable() != null ? m.getOperarioResponsable().getId() : 0);
    }

    private Movimiento_logistico leerFormulario() {
        int id = FormUtil.entero(txtId, "ID", 1);
        String fecha = FormUtil.fecha(txtFecha, "Fecha");
        String tipo = (String) cmbTipo.getSelectedItem();
        int cantidad = FormUtil.entero(txtCantidad, "Cantidad", 1);
        Productos producto = (Productos) controlProductos.consultarUno(FormUtil.idSeleccionado(cmbProducto));
        if (producto == null) {
            throw new IllegalArgumentException("Debe seleccionar un producto. Si no hay, regístrelo en Gestionar Productos.");
        }
        Usuarios u = (Usuarios) controlUsuarios.consultarUno(FormUtil.idSeleccionado(cmbOperario));
        if (!(u instanceof OperarioBodega operario)) {
            throw new IllegalArgumentException("Debe seleccionar el operario responsable.");
        }
        return new Movimiento_logistico(id, fecha, tipo, cantidad, producto, operario);
    }

    private String nombreDe(Movimiento_logistico m) {
        return "el movimiento " + m.getId() + " (" + m.getTipo() + " del " + m.getFecha() + ")";
    }

    private void validarExtra(Movimiento_logistico m, boolean nuevo) {
        // sin validaciones adicionales
    }

    private void validarEliminar(Movimiento_logistico m) {
        // sin restricciones adicionales
    }

    private void permisosExtra() {
    }

    private void despuesDeLimpiar() {
        seleccionarOperarioActual();
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
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblOperario = new javax.swing.JLabel();
        cmbOperario = new javax.swing.JComboBox<>();
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
        lblFiltroTipo = new javax.swing.JLabel();
        cmbFiltroTipo = new javax.swing.JComboBox<>();
        lblFiltroProducto = new javax.swing.JLabel();
        cmbFiltroProducto = new javax.swing.JComboBox<>();
        lblDesde = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        lblHasta = new javax.swing.JLabel();
        txtHasta = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnMostrarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Movimientos Logísticos");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gestión de Movimientos Logísticos");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del movimiento"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblId.setText("ID:");
        panelDatos.add(lblId);

        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        panelDatos.add(txtId);

        lblFecha.setText("Fecha (DD/MM/AAAA):");
        panelDatos.add(lblFecha);

        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });
        panelDatos.add(txtFecha);

        lblTipo.setText("Tipo de movimiento:");
        panelDatos.add(lblTipo);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Salida", "Traslado" }));
        panelDatos.add(cmbTipo);

        lblCantidad.setText("Cantidad:");
        panelDatos.add(lblCantidad);

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        panelDatos.add(txtCantidad);

        lblProducto.setText("Producto:");
        panelDatos.add(lblProducto);

        panelDatos.add(cmbProducto);

        lblOperario.setText("Operario responsable:");
        panelDatos.add(lblOperario);

        panelDatos.add(cmbOperario);

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

        scrollTabla.setPreferredSize(new java.awt.Dimension(1050, 250));

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

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID descendente (Quicksort)", "Producto A-Z (Mergesort)", "Fecha más antigua primero (Burbuja)", "Fecha más reciente primero (Burbuja)", "ID ascendente (sort)" }));
        panelOrdenar.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOrdenar.add(btnOrdenar);

        lblResultado.setText(" ");
        panelOrdenar.add(lblResultado);

        panelInferior.add(panelOrdenar);

        panelFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar movimientos (filtros)"));
        panelFiltrar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblFiltroTipo.setText("Tipo:");
        panelFiltrar.add(lblFiltroTipo);

        cmbFiltroTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Entrada", "Salida", "Traslado" }));
        panelFiltrar.add(cmbFiltroTipo);

        lblFiltroProducto.setText("Producto:");
        panelFiltrar.add(lblFiltroProducto);

        panelFiltrar.add(cmbFiltroProducto);

        lblDesde.setText("Desde:");
        panelFiltrar.add(lblDesde);

        txtDesde.setColumns(8);
        txtDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDesdeKeyTyped(evt);
            }
        });
        panelFiltrar.add(txtDesde);

        lblHasta.setText("Hasta:");
        panelFiltrar.add(lblHasta);

        txtHasta.setColumns(8);
        txtHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHastaKeyTyped(evt);
            }
        });
        panelFiltrar.add(txtHasta);

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
            Movimiento_logistico nuevo = leerFormulario();
            if (controlador.existeId(nuevo.getId())) {
                FormUtil.aviso(this, "Ya existe un registro con el ID " + nuevo.getId() + ".");
                return;
            }
            validarExtra(nuevo, true);
            if (controlador.insertar(nuevo)) {
                FormUtil.info(this, "Registro guardado correctamente.");
                llenarTabla(controlador.getListaMovimientos());
                limpiarCampos();
            } else {
                FormUtil.aviso(this, "No se pudo guardar el registro.");
            }
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            int id = FormUtil.entero(txtId, "ID", 1);
            Movimiento_logistico encontrado = (Movimiento_logistico) controlador.consultarUno(id); // búsqueda binaria por ID
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
            Movimiento_logistico datos = leerFormulario();
            if (!controlador.existeId(datos.getId())) {
                FormUtil.aviso(this, "No existe un registro con el ID " + datos.getId() + ". Búsquelo primero.");
                return;
            }
            validarExtra(datos, false);
            if (controlador.update(datos) != null) {
                FormUtil.info(this, "Registro actualizado correctamente.");
                llenarTabla(controlador.getListaMovimientos());
                limpiarCampos();
            } else {
                FormUtil.aviso(this, "No se pudo actualizar el registro.");
            }
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            int id = FormUtil.entero(txtId, "ID", 1);
            Movimiento_logistico registro = (Movimiento_logistico) controlador.consultarUno(id);
            if (registro == null) {
                FormUtil.aviso(this, "No existe un registro con el ID " + id + ".");
                return;
            }
            validarEliminar(registro);
            if (!FormUtil.confirmar(this, "¿Eliminar " + nombreDe(registro) + "?" + "")) {
                return;
            }
            // El controlador vuelve a verificar el rol (solo el administrador elimina)
            if (controlador.delete(id, usuarioLogueado)) {
                FormUtil.info(this, "Registro eliminado.");
                llenarTabla(controlador.getListaMovimientos());
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
        llenarTabla(controlador.getListaMovimientos());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        Movimiento_logistico registro = (Movimiento_logistico) controlador.consultarUno(id);
        if (registro != null) {
            mostrar(registro);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        List<Movimiento_logistico> lista;
        switch (cmbOrden.getSelectedIndex()) {
            case 0: lista = controlador.ordenarQuicksortIdDesc(); break;
            case 1: lista = controlador.ordenarMergesortProducto(); break;
            case 2: lista = controlador.ordenarBurbujaFecha(false); break;
            case 3: lista = controlador.ordenarBurbujaFecha(true); break;
            default: lista = controlador.ordenarSortIdAsc(); break;
        }
        llenarTabla(lista);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        try {
            String tipo = (String) cmbFiltroTipo.getSelectedItem();
            int idProducto = FormUtil.idSeleccionado(cmbFiltroProducto);
            LocalDate desde = txtDesde.getText().isBlank() ? null : FormUtil.fechaLocal(txtDesde, "Desde");
            LocalDate hasta = txtHasta.getText().isBlank() ? null : FormUtil.fechaLocal(txtHasta, "Hasta");
            if (desde != null && hasta != null && desde.isAfter(hasta)) {
                throw new IllegalArgumentException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
            }
            llenarTabla(controlador.filtrar(tipo, idProducto, desde, hasta));
        } catch (IllegalArgumentException ex) {
            FormUtil.aviso(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        if (cmbFiltroTipo.getItemCount() > 0) cmbFiltroTipo.setSelectedIndex(0);
        if (cmbFiltroProducto.getItemCount() > 0) cmbFiltroProducto.setSelectedIndex(0);
        txtDesde.setText("");
        txtHasta.setText("");
        llenarTabla(controlador.getListaMovimientos());
    }//GEN-LAST:event_btnMostrarTodosActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        FormUtil.soloFecha(evt, txtFecha);
    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtDesdeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDesdeKeyTyped
        FormUtil.soloFecha(evt, txtDesde);
    }//GEN-LAST:event_txtDesdeKeyTyped

    private void txtHastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHastaKeyTyped
        FormUtil.soloFecha(evt, txtHasta);
    }//GEN-LAST:event_txtHastaKeyTyped

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
        java.awt.EventQueue.invokeLater(() -> new FrmMovimientos().setVisible(true));
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
    private javax.swing.JComboBox<String> cmbFiltroProducto;
    private javax.swing.JComboBox<String> cmbFiltroTipo;
    private javax.swing.JComboBox<String> cmbOperario;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFiltroProducto;
    private javax.swing.JLabel lblFiltroTipo;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblOperario;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOrdenar;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHasta;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
