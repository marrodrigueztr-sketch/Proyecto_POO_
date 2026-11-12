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
import CONTROLADOR.ControladorCiudad;
import MODELO.Almacen;
import MODELO.Ciudad;

/**
 * Formulario CRUD de almacenes/bodegas. Por requisito, el ordenamiento principal
 * de almacenes es el nativo de Java (List.sort) por ID ascendente.
 *
 * @author Grupo 1
 */
public class FrmAlmacenes extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmAlmacenes.class.getName());

    private final ControladorAlmacen controlador = new ControladorAlmacen();
    private Usuarios usuarioLogueado;
    private boolean soloLectura;
    private DefaultTableModel modeloTabla;

    // ==================================================================================
    // Inicialización y permisos
    // ==================================================================================

    /**
     * Creates new form FrmAlmacenes (lo usa el diseñador de NetBeans; abre en solo lectura).
     */
    public FrmAlmacenes() {
        this(null, true);
    }

    /**
     * Abre el formulario para el usuario que inició sesión.
     *
     * @param usuario usuario logueado
     */
    public FrmAlmacenes(Usuarios usuario) {
        this(usuario, false);
    }

    /**
     * @param usuario usuario logueado
     * @param modoConsulta true para abrir en modo solo consulta (menú Consultas)
     */
    public FrmAlmacenes(Usuarios usuario, boolean modoConsulta) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.soloLectura = modoConsulta || usuario == null || usuario instanceof GerenteGeneral;
        modeloTabla = FormUtil.configurarTabla(tblDatos, "ID", "Nombre", "Ciudad", "País", "Gerente", "Zonas");
        cargarCombos();
        aplicarPermisos();
        llenarTabla(controlador.getListaAlmacenes());
    }

    private void aplicarPermisos() {
        if (soloLectura) {
            setTitle("Consulta de Almacenes");
            lblTitulo.setText("Consulta de Almacenes");
            btnGuardar.setVisible(false);
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            // Solo se deja escribir el ID para poder buscar
            txtNombre.setEditable(false);
            cmbCiudad.setEnabled(false);
            cmbGerente.setEnabled(false);
        } else if (!ControladorUsuario.puedeEliminar(usuarioLogueado)) {
            // Operario de bodega: crea, consulta y actualiza, pero no elimina
            btnEliminar.setEnabled(false);
            btnEliminar.setToolTipText("Solo el administrador puede eliminar registros.");
        }
        permisosExtra();
    }

    /** Muestra en la tabla la lista recibida (completa, ordenada o filtrada). */
    private void llenarTabla(List<Almacen> lista) {
        modeloTabla.setRowCount(0);
        for (Almacen e : lista) {
            modeloTabla.addRow(fila(e));
        }
        lblResultado.setText("Mostrando " + lista.size() + " registro(s)");
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        if (cmbCiudad.getItemCount() > 0) cmbCiudad.setSelectedIndex(0);
        if (cmbGerente.getItemCount() > 0) cmbGerente.setSelectedIndex(0);
        despuesDeLimpiar();
        tblDatos.clearSelection();
        txtId.requestFocus();
    }

    // ==================================================================================
    // Lógica propia de Almacen
    // ==================================================================================

    private final ControladorCiudad controlCiudades = new ControladorCiudad();
    private final ControladorUsuario controlUsuarios = new ControladorUsuario();

    private void cargarCombos() {
        cmbCiudad.removeAllItems();
        cmbFiltroCiudad.removeAllItems();
        cmbFiltroCiudad.addItem("Todos");
        for (Ciudad c : controlCiudades.getListaCiudades()) {
            String item = FormUtil.item(c.getId(), c.getNombre());
            cmbCiudad.addItem(item);
            cmbFiltroCiudad.addItem(item);
        }
        cmbGerente.removeAllItems();
        cmbGerente.addItem(FormUtil.item(0, "Sin asignar"));
        for (GerenteGeneral g : controlUsuarios.getGerentes()) {
            cmbGerente.addItem(FormUtil.item(g.getId(), g.getNombre() + " " + g.getApellido()));
        }
    }

    private Object[] fila(Almacen a) {
        String ciudad = a.getCiudad() != null ? a.getCiudad().getNombre() : "-";
        String pais = (a.getCiudad() != null && a.getCiudad().getPaisReferencia() != null)
                ? a.getCiudad().getPaisReferencia().getNombre() : "-";
        String gerente = a.getGerenteAsignado() != null
                ? a.getGerenteAsignado().getNombre() + " " + a.getGerenteAsignado().getApellido() : "Sin asignar";
        return new Object[]{a.getId(), a.getNombre(), ciudad, pais, gerente, controlador.contarZonas(a.getId())};
    }

    private void mostrar(Almacen a) {
        txtId.setText(String.valueOf(a.getId()));
        txtNombre.setText(a.getNombre());
        FormUtil.seleccionarPorId(cmbCiudad, a.getCiudad() != null ? a.getCiudad().getId() : 0);
        FormUtil.seleccionarPorId(cmbGerente, a.getGerenteAsignado() != null ? a.getGerenteAsignado().getId() : 0);
    }

    private Almacen leerFormulario() {
        int id = FormUtil.entero(txtId, "ID", 1);
        String nombre = FormUtil.texto(txtNombre, "Nombre", 3);
        Ciudad ciudad = (Ciudad) controlCiudades.consultarUno(FormUtil.idSeleccionado(cmbCiudad));
        if (ciudad == null) {
            throw new IllegalArgumentException("Debe seleccionar una ciudad. Si no hay, regístrela en Gestionar Ciudades.");
        }
        int idGerente = FormUtil.idSeleccionado(cmbGerente);
        GerenteGeneral gerente = idGerente == 0 ? null : (GerenteGeneral) controlUsuarios.consultarUno(idGerente);
        return new Almacen(id, nombre, ciudad, gerente);
    }

    private String nombreDe(Almacen a) {
        return "el almacén \"" + a.getNombre() + "\"";
    }

    private void validarExtra(Almacen registro, boolean nuevo) {
        // sin validaciones adicionales
    }

    private void validarEliminar(Almacen registro) {
        // sin restricciones adicionales
    }

    private void permisosExtra() {
    }

    private void despuesDeLimpiar() {
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
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox<>();
        lblGerente = new javax.swing.JLabel();
        cmbGerente = new javax.swing.JComboBox<>();
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
        lblFiltroCiudad = new javax.swing.JLabel();
        cmbFiltroCiudad = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnMostrarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Almacenes");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gestión de Almacenes");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del almacén"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblId.setText("ID:");
        panelDatos.add(lblId);

        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        panelDatos.add(txtId);

        lblNombre.setText("Nombre:");
        panelDatos.add(lblNombre);

        panelDatos.add(txtNombre);

        lblCiudad.setText("Ciudad:");
        panelDatos.add(lblCiudad);

        panelDatos.add(cmbCiudad);

        lblGerente.setText("Gerente asignado:");
        panelDatos.add(lblGerente);

        panelDatos.add(cmbGerente);

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

        scrollTabla.setPreferredSize(new java.awt.Dimension(900, 250));

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

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID ascendente (List.sort)", "ID descendente (Quicksort)", "Nombre A-Z (Mergesort)" }));
        panelOrdenar.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOrdenar.add(btnOrdenar);

        lblResultado.setText(" ");
        panelOrdenar.add(lblResultado);

        panelInferior.add(panelOrdenar);

        panelFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar almacenes (filtro)"));
        panelFiltrar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblFiltroCiudad.setText("Ciudad:");
        panelFiltrar.add(lblFiltroCiudad);

        panelFiltrar.add(cmbFiltroCiudad);

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
            Almacen nuevo = leerFormulario();
            if (controlador.existeId(nuevo.getId())) {
                FormUtil.aviso(this, "Ya existe un registro con el ID " + nuevo.getId() + ".");
                return;
            }
            validarExtra(nuevo, true);
            if (controlador.insertar(nuevo)) {
                FormUtil.info(this, "Registro guardado correctamente.");
                llenarTabla(controlador.getListaAlmacenes());
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
            Almacen encontrado = (Almacen) controlador.consultarUno(id); // búsqueda binaria por ID
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
            Almacen datos = leerFormulario();
            if (!controlador.existeId(datos.getId())) {
                FormUtil.aviso(this, "No existe un registro con el ID " + datos.getId() + ". Búsquelo primero.");
                return;
            }
            validarExtra(datos, false);
            if (controlador.update(datos) != null) {
                FormUtil.info(this, "Registro actualizado correctamente.");
                llenarTabla(controlador.getListaAlmacenes());
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
            Almacen registro = (Almacen) controlador.consultarUno(id);
            if (registro == null) {
                FormUtil.aviso(this, "No existe un registro con el ID " + id + ".");
                return;
            }
            validarEliminar(registro);
            if (!FormUtil.confirmar(this, "¿Eliminar " + nombreDe(registro) + "?" + "\nTambién se eliminarán sus zonas y los operarios quedarán sin almacén asignado.")) {
                return;
            }
            // El controlador vuelve a verificar el rol (solo el administrador elimina)
            if (controlador.delete(id, usuarioLogueado)) {
                FormUtil.info(this, "Registro eliminado.");
                llenarTabla(controlador.getListaAlmacenes());
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
        llenarTabla(controlador.getListaAlmacenes());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        Almacen registro = (Almacen) controlador.consultarUno(id);
        if (registro != null) {
            mostrar(registro);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        List<Almacen> lista;
        switch (cmbOrden.getSelectedIndex()) {
            case 0: lista = controlador.ordenarSortIdAsc(); break;
            case 1: lista = controlador.ordenarQuicksortIdDesc(); break;
            default: lista = controlador.ordenarMergesortNombre(); break;
        }
        llenarTabla(lista);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        llenarTabla(controlador.filtrarPorCiudad(FormUtil.idSeleccionado(cmbFiltroCiudad)));
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        if (cmbFiltroCiudad.getItemCount() > 0) cmbFiltroCiudad.setSelectedIndex(0);
        llenarTabla(controlador.getListaAlmacenes());
    }//GEN-LAST:event_btnMostrarTodosActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

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
        java.awt.EventQueue.invokeLater(() -> new FrmAlmacenes().setVisible(true));
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
    private javax.swing.JComboBox<String> cmbCiudad;
    private javax.swing.JComboBox<String> cmbFiltroCiudad;
    private javax.swing.JComboBox<String> cmbGerente;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblFiltroCiudad;
    private javax.swing.JLabel lblGerente;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOrdenar;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
