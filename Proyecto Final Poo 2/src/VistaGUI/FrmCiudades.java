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
import CONTROLADOR.ControladorPais;
import MODELO.Ciudad;
import MODELO.Pais;

/**
 * Formulario CRUD de ciudades (cada ciudad pertenece a un país).
 *
 * @author Grupo 1
 */
public class FrmCiudades extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmCiudades.class.getName());

    private final ControladorCiudad controlador = new ControladorCiudad();
    private Usuarios usuarioLogueado;
    private boolean soloLectura;
    private DefaultTableModel modeloTabla;

    // ==================================================================================
    // Inicialización y permisos
    // ==================================================================================

    /**
     * Creates new form FrmCiudades (lo usa el diseñador de NetBeans; abre en solo lectura).
     */
    public FrmCiudades() {
        this(null, true);
    }

    /**
     * Abre el formulario para el usuario que inició sesión.
     *
     * @param usuario usuario logueado
     */
    public FrmCiudades(Usuarios usuario) {
        this(usuario, false);
    }

    /**
     * @param usuario usuario logueado
     * @param modoConsulta true para abrir en modo solo consulta (menú Consultas)
     */
    public FrmCiudades(Usuarios usuario, boolean modoConsulta) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.soloLectura = modoConsulta || usuario == null || usuario instanceof GerenteGeneral;
        modeloTabla = FormUtil.configurarTabla(tblDatos, "ID", "Nombre", "País", "Almacenes");
        cargarCombos();
        aplicarPermisos();
        llenarTabla(controlador.getListaCiudades());
    }

    private void aplicarPermisos() {
        if (soloLectura) {
            setTitle("Consulta de Ciudades");
            lblTitulo.setText("Consulta de Ciudades");
            btnGuardar.setVisible(false);
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            // Solo se deja escribir el ID para poder buscar
            txtNombre.setEditable(false);
            cmbPais.setEnabled(false);
        } else if (!ControladorUsuario.puedeEliminar(usuarioLogueado)) {
            // Operario de bodega: crea, consulta y actualiza, pero no elimina
            btnEliminar.setEnabled(false);
            btnEliminar.setToolTipText("Solo el administrador puede eliminar registros.");
        }
        permisosExtra();
    }

    /** Muestra en la tabla la lista recibida (completa, ordenada o filtrada). */
    private void llenarTabla(List<Ciudad> lista) {
        modeloTabla.setRowCount(0);
        for (Ciudad e : lista) {
            modeloTabla.addRow(fila(e));
        }
        lblResultado.setText("Mostrando " + lista.size() + " registro(s)");
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        if (cmbPais.getItemCount() > 0) cmbPais.setSelectedIndex(0);
        despuesDeLimpiar();
        tblDatos.clearSelection();
        txtId.requestFocus();
    }

    // ==================================================================================
    // Lógica propia de Ciudad
    // ==================================================================================

    private final ControladorPais controlPaises = new ControladorPais();

    private void cargarCombos() {
        cmbPais.removeAllItems();
        cmbFiltroPais.removeAllItems();
        cmbFiltroPais.addItem("Todos");
        for (Pais p : controlPaises.getListaPaises()) {
            String item = FormUtil.item(p.getId(), p.getNombre());
            cmbPais.addItem(item);
            cmbFiltroPais.addItem(item);
        }
    }

    private Object[] fila(Ciudad c) {
        String pais = c.getPaisReferencia() != null ? c.getPaisReferencia().getNombre() : "-";
        int almacenes = new ControladorAlmacen().filtrarPorCiudad(c.getId()).size();
        return new Object[]{c.getId(), c.getNombre(), pais, almacenes};
    }

    private void mostrar(Ciudad c) {
        txtId.setText(String.valueOf(c.getId()));
        txtNombre.setText(c.getNombre());
        FormUtil.seleccionarPorId(cmbPais, c.getPaisReferencia() != null ? c.getPaisReferencia().getId() : 0);
    }

    private Ciudad leerFormulario() {
        int id = FormUtil.entero(txtId, "ID", 1);
        String nombre = FormUtil.nombre(txtNombre, "Nombre");
        Pais pais = (Pais) controlPaises.consultarUno(FormUtil.idSeleccionado(cmbPais));
        if (pais == null) {
            throw new IllegalArgumentException("Debe seleccionar un país. Si no hay, regístrelo en Gestionar Países.");
        }
        return new Ciudad(id, nombre, pais);
    }

    private String nombreDe(Ciudad c) {
        return "la ciudad \"" + c.getNombre() + "\"";
    }

    private void validarExtra(Ciudad registro, boolean nuevo) {
        // sin validaciones adicionales
    }

    private void validarEliminar(Ciudad registro) {
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
        lblPais = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox<>();
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
        lblFiltroPais = new javax.swing.JLabel();
        cmbFiltroPais = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnMostrarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Ciudades");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gestión de Ciudades");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la ciudad"));
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

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panelDatos.add(txtNombre);

        lblPais.setText("País:");
        panelDatos.add(lblPais);

        panelDatos.add(cmbPais);

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

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID descendente (Quicksort)", "Nombre A-Z (Mergesort)", "ID ascendente (sort)" }));
        panelOrdenar.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOrdenar.add(btnOrdenar);

        lblResultado.setText(" ");
        panelOrdenar.add(lblResultado);

        panelInferior.add(panelOrdenar);

        panelFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar ciudades (filtro)"));
        panelFiltrar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblFiltroPais.setText("País:");
        panelFiltrar.add(lblFiltroPais);

        panelFiltrar.add(cmbFiltroPais);

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
            Ciudad nuevo = leerFormulario();
            if (controlador.existeId(nuevo.getId())) {
                FormUtil.aviso(this, "Ya existe un registro con el ID " + nuevo.getId() + ".");
                return;
            }
            validarExtra(nuevo, true);
            if (controlador.insertar(nuevo)) {
                FormUtil.info(this, "Registro guardado correctamente.");
                llenarTabla(controlador.getListaCiudades());
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
            Ciudad encontrado = (Ciudad) controlador.consultarUno(id); // búsqueda binaria por ID
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
            Ciudad datos = leerFormulario();
            if (!controlador.existeId(datos.getId())) {
                FormUtil.aviso(this, "No existe un registro con el ID " + datos.getId() + ". Búsquelo primero.");
                return;
            }
            validarExtra(datos, false);
            if (controlador.update(datos) != null) {
                FormUtil.info(this, "Registro actualizado correctamente.");
                llenarTabla(controlador.getListaCiudades());
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
            Ciudad registro = (Ciudad) controlador.consultarUno(id);
            if (registro == null) {
                FormUtil.aviso(this, "No existe un registro con el ID " + id + ".");
                return;
            }
            validarEliminar(registro);
            if (!FormUtil.confirmar(this, "¿Eliminar " + nombreDe(registro) + "?" + "\nTambién se eliminarán sus almacenes y zonas.")) {
                return;
            }
            // El controlador vuelve a verificar el rol (solo el administrador elimina)
            if (controlador.delete(id, usuarioLogueado)) {
                FormUtil.info(this, "Registro eliminado.");
                llenarTabla(controlador.getListaCiudades());
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
        llenarTabla(controlador.getListaCiudades());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        Ciudad registro = (Ciudad) controlador.consultarUno(id);
        if (registro != null) {
            mostrar(registro);
        }
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        List<Ciudad> lista;
        switch (cmbOrden.getSelectedIndex()) {
            case 0: lista = controlador.ordenarQuicksortIdDesc(); break;
            case 1: lista = controlador.ordenarMergesortNombre(); break;
            default: lista = controlador.ordenarSortIdAsc(); break;
        }
        llenarTabla(lista);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        llenarTabla(controlador.filtrarPorPais(FormUtil.idSeleccionado(cmbFiltroPais)));
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        if (cmbFiltroPais.getItemCount() > 0) cmbFiltroPais.setSelectedIndex(0);
        llenarTabla(controlador.getListaCiudades());
    }//GEN-LAST:event_btnMostrarTodosActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        FormUtil.soloEnteros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        FormUtil.soloLetras(evt);
    }//GEN-LAST:event_txtNombreKeyTyped

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
        java.awt.EventQueue.invokeLater(() -> new FrmCiudades().setVisible(true));
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
    private javax.swing.JComboBox<String> cmbFiltroPais;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JComboBox<String> cmbPais;
    private javax.swing.JLabel lblFiltroPais;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblPais;
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
