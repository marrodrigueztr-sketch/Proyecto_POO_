/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VistaGUI;

import CONTROLADOR.ControladorProductos;
import CONTROLADOR.ControladorUsuario;
import MODELO.GerenteGeneral;
import MODELO.OperarioBodega;
import MODELO.Productos;
import MODELO.Usuarios;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario CRUD de productos (inventario), con ordenamientos y consultas
 * filtradas de existencias. Los permisos dependen del rol:
 * <ul>
 * <li>Administrador: crear, buscar, modificar y eliminar.</li>
 * <li>Operario de Bodega: crear, buscar y modificar (no elimina).</li>
 * <li>Gerente General o modo consulta: solo buscar, ordenar y filtrar.</li>
 * </ul>
 *
 * @author Marcelo
 */
public class FrmProductos extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmProductos.class.getName());

    /** SKU: 3 letras mayúsculas, guion y 4 números (ej. ELT-4012). */
    private static final String REGEX_SKU = "^[A-Z]{3}-\\d{4}$";

    private final ControladorProductos controlador = new ControladorProductos();
    private Usuarios usuarioLogueado;
    private boolean soloLectura;
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form FrmProductos (lo usa el diseñador de NetBeans; abre en solo lectura).
     */
    public FrmProductos() {
        this(null, true);
    }

    /**
     * Abre la gestión de productos para el usuario que inició sesión.
     *
     * @param usuario usuario logueado
     */
    public FrmProductos(Usuarios usuario) {
        this(usuario, false);
    }

    /**
     * @param usuario usuario logueado
     * @param modoConsulta true para abrir en modo solo consulta (Consultas > Productos y Existencias)
     */
    public FrmProductos(Usuarios usuario, boolean modoConsulta) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.soloLectura = modoConsulta || usuario == null || usuario instanceof GerenteGeneral;
        configurarTabla();
        aplicarPermisos();
        llenarTabla(controlador.getListaProductos());
    }

    // ------------------------------------------------------------------------------------
    // Configuración
    // ------------------------------------------------------------------------------------
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "SKU", "Nombre", "Descripción", "Precio", "Stock", "Proveedor", "Tipo", "Registrado por"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // la tabla es solo para ver; se edita desde el formulario
            }
        };
        tblProductos.setModel(modeloTabla);
        tblProductos.getTableHeader().setReorderingAllowed(false);
        tblProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void aplicarPermisos() {
        if (soloLectura) {
            setTitle("Consulta de Productos y Existencias");
            lblTitulo.setText("Consulta de Productos y Existencias");
            btnGuardar.setVisible(false);
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            // Solo se deja escribir el ID para poder buscar
            txtSku.setEditable(false);
            txtNombre.setEditable(false);
            txtProveedor.setEditable(false);
            txtPrecio.setEditable(false);
            txtStock.setEditable(false);
            txtDescripcion.setEditable(false);
            cmbTipo.setEnabled(false);
        } else if (usuarioLogueado instanceof OperarioBodega) {
            btnEliminar.setEnabled(false);
            btnEliminar.setToolTipText("Los operarios de bodega no pueden eliminar productos.");
        }
    }

    /** Muestra en la tabla la lista recibida (ordenada o filtrada). */
    private void llenarTabla(List<Productos> productos) {
        modeloTabla.setRowCount(0);
        ControladorUsuario controlUsuarios = new ControladorUsuario();
        for (Productos p : productos) {
            Usuarios registro = (Usuarios) controlUsuarios.consultarUno(p.getIdUsuarioRegistro());
            String registradoPor = registro != null
                    ? registro.getId() + " - " + registro.getNombre()
                    : String.valueOf(p.getIdUsuarioRegistro());
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getSku(), p.getNombre(), p.getDescripcion(),
                String.format("%.2f", p.getPreciouniitario()), p.getStockactual(),
                p.getProveedor(), p.getTipo(), registradoPor
            });
        }
        lblResultado.setText("Mostrando " + productos.size() + " producto(s)");
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtSku.setText("");
        txtNombre.setText("");
        txtProveedor.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtDescripcion.setText("");
        cmbTipo.setSelectedIndex(0);
        if (!soloLectura) {
            txtSku.setEditable(true); // el SKU solo se escribe al crear
        }
        tblProductos.clearSelection();
        txtId.requestFocus();
    }

    private void mostrarProducto(Productos p) {
        txtId.setText(String.valueOf(p.getId()));
        txtSku.setText(p.getSku());
        txtNombre.setText(p.getNombre());
        txtProveedor.setText(p.getProveedor());
        txtPrecio.setText(String.valueOf(p.getPreciouniitario()));
        txtStock.setText(String.valueOf(p.getStockactual()));
        txtDescripcion.setText(p.getDescripcion());
        cmbTipo.setSelectedItem(p.getTipo());
        txtSku.setEditable(false); // el SKU identifica al producto y no se modifica
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Lee y valida los campos del formulario.
     *
     * @return el producto construido, o null si algún dato es inválido (ya se mostró el mensaje)
     */
    private Productos leerFormulario() {
        String idTexto = txtId.getText().trim();
        String sku = txtSku.getText().trim().toUpperCase();
        String nombre = txtNombre.getText().trim();
        String proveedor = txtProveedor.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String stockTexto = txtStock.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        if (idTexto.isEmpty() || sku.isEmpty() || nombre.isEmpty() || proveedor.isEmpty()
                || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            aviso("Todos los campos son obligatorios, excepto la descripción.");
            return null;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            aviso("El ID debe ser un número entero mayor que 0.");
            txtId.requestFocus();
            return null;
        }

        if (!sku.matches(REGEX_SKU)) {
            aviso("El SKU debe tener 3 letras, un guion y 4 números (ej. ELT-4012).");
            txtSku.requestFocus();
            return null;
        }

        if (nombre.length() < 3) {
            aviso("El nombre debe tener al menos 3 caracteres.");
            txtNombre.requestFocus();
            return null;
        }

        float precio;
        try {
            precio = Float.parseFloat(precioTexto);
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            aviso("El precio debe ser un número mayor o igual a 0 (use punto para decimales).");
            txtPrecio.requestFocus();
            return null;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockTexto);
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            aviso("El stock debe ser un número entero mayor o igual a 0.");
            txtStock.requestFocus();
            return null;
        }

        return new Productos(id, sku, nombre, descripcion, precio, stock, proveedor, tipo);
    }

    /** Lee el ID escrito en el formulario; null si está vacío o no es válido. */
    private Integer leerId() {
        try {
            return Integer.valueOf(txtId.getText().trim());
        } catch (NumberFormatException e) {
            aviso("Ingrese un ID numérico válido.");
            txtId.requestFocus();
            return null;
        }
    }

    /** Solo permite escribir dígitos en un campo. */
    private void soloNumeros(java.awt.event.KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            evt.consume();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
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
        lblSku = new javax.swing.JLabel();
        txtSku = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblProveedor = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblStock = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        panelBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        panelInferior = new javax.swing.JPanel();
        panelOrdenar = new javax.swing.JPanel();
        lblOrdenar = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        panelFiltrar = new javax.swing.JPanel();
        lblFiltroTipo = new javax.swing.JLabel();
        cmbFiltroTipo = new javax.swing.JComboBox<>();
        lblStockMax = new javax.swing.JLabel();
        txtStockMax = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnMostrarTodos = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Productos");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gestión de Productos");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panelSuperior.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del producto"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblId.setText("ID:");
        panelDatos.add(lblId);

        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        panelDatos.add(txtId);

        lblSku.setText("SKU (ej. ELT-4012):");
        panelDatos.add(lblSku);

        txtSku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSkuKeyTyped(evt);
            }
        });
        panelDatos.add(txtSku);

        lblNombre.setText("Nombre:");
        panelDatos.add(lblNombre);
        panelDatos.add(txtNombre);

        lblProveedor.setText("Proveedor:");
        panelDatos.add(lblProveedor);
        panelDatos.add(txtProveedor);

        lblPrecio.setText("Precio unitario:");
        panelDatos.add(lblPrecio);

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });
        panelDatos.add(txtPrecio);

        lblStock.setText("Stock actual:");
        panelDatos.add(lblStock);

        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });
        panelDatos.add(txtStock);

        lblTipo.setText("Tipo:");
        panelDatos.add(lblTipo);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Perecedero", "Frágil", "Electrónico" }));
        panelDatos.add(cmbTipo);

        lblDescripcion.setText("Descripción:");
        panelDatos.add(lblDescripcion);
        panelDatos.add(txtDescripcion);

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

        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tblProductos);

        getContentPane().add(scrollTabla, java.awt.BorderLayout.CENTER);

        panelInferior.setLayout(new java.awt.GridLayout(2, 1));

        panelOrdenar.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenar productos"));
        panelOrdenar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblOrdenar.setText("Ordenar por:");
        panelOrdenar.add(lblOrdenar);

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID descendente (Quicksort)", "Nombre A-Z (Mergesort)", "Stock mayor a menor (Burbuja)", "Stock menor a mayor (Burbuja)", "ID ascendente (sort)" }));
        panelOrdenar.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOrdenar.add(btnOrdenar);

        panelInferior.add(panelOrdenar);

        panelFiltrar.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar existencias (filtros)"));
        panelFiltrar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblFiltroTipo.setText("Tipo:");
        panelFiltrar.add(lblFiltroTipo);

        cmbFiltroTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Perecedero", "Frágil", "Electrónico" }));
        panelFiltrar.add(cmbFiltroTipo);

        lblStockMax.setText("Stock máximo:");
        panelFiltrar.add(lblStockMax);

        txtStockMax.setColumns(6);
        txtStockMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockMaxKeyTyped(evt);
            }
        });
        panelFiltrar.add(txtStockMax);

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(this::btnFiltrarActionPerformed);
        panelFiltrar.add(btnFiltrar);

        btnMostrarTodos.setText("Mostrar todos");
        btnMostrarTodos.addActionListener(this::btnMostrarTodosActionPerformed);
        panelFiltrar.add(btnMostrarTodos);

        lblResultado.setText(" ");
        panelFiltrar.add(lblResultado);

        panelInferior.add(panelFiltrar);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Productos nuevo = leerFormulario();
        if (nuevo == null) return;

        if (controlador.existeId(nuevo.getId())) {
            aviso("Ya existe un producto con el ID " + nuevo.getId() + ".");
            return;
        }
        if (controlador.existeSku(nuevo.getSku())) {
            aviso("Ya existe un producto con el SKU " + nuevo.getSku() + ".");
            return;
        }

        // Se guarda el id de quien registró el producto
        nuevo.setIdUsuarioRegistro(usuarioLogueado != null ? usuarioLogueado.getId() : 0);

        if (controlador.insertar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
            llenarTabla(controlador.getListaProductos());
            limpiarCampos();
        } else {
            aviso("No se pudo guardar el producto.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Integer id = leerId();
        if (id == null) return;

        // Búsqueda binaria por ID en el controlador
        Productos encontrado = (Productos) controlador.consultarUno(id);
        if (encontrado != null) {
            mostrarProducto(encontrado);
            llenarTabla(java.util.Collections.singletonList(encontrado));
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún producto con el ID " + id + ".",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        Productos datos = leerFormulario();
        if (datos == null) return;

        if (!controlador.existeId(datos.getId())) {
            aviso("No existe un producto con el ID " + datos.getId() + ". Búsquelo primero.");
            return;
        }

        if (controlador.update(datos) != null) {
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            llenarTabla(controlador.getListaProductos());
            limpiarCampos();
        } else {
            aviso("No se pudo actualizar el producto.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Integer id = leerId();
        if (id == null) return;

        Productos p = (Productos) controlador.consultarUno(id);
        if (p == null) {
            aviso("No existe un producto con el ID " + id + ".");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Eliminar el producto \"" + p.getNombre() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) return;

        // El controlador vuelve a verificar el rol (los operarios no pueden borrar)
        if (controlador.delete(id, usuarioLogueado)) {
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
            llenarTabla(controlador.getListaProductos());
            limpiarCampos();
        } else {
            aviso("No tiene permisos para eliminar productos.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        llenarTabla(controlador.getListaProductos());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        int fila = tblProductos.getSelectedRow();
        if (fila == -1) return;
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        Productos p = (Productos) controlador.consultarUno(id);
        if (p != null) {
            mostrarProducto(p);
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        List<Productos> ordenados;
        switch (cmbOrden.getSelectedIndex()) {
            case 0: ordenados = controlador.ordenarQuicksortIdDesc(); break;
            case 1: ordenados = controlador.ordenarMergesortNombre(); break;
            case 2: ordenados = controlador.ordenarBurbujaStock(true); break;
            case 3: ordenados = controlador.ordenarBurbujaStock(false); break;
            default: ordenados = controlador.ordenarSortIdAsc(); break;
        }
        llenarTabla(ordenados);
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        String tipo = (String) cmbFiltroTipo.getSelectedItem();
        Integer stockMaximo = null;
        String texto = txtStockMax.getText().trim();
        if (!texto.isEmpty()) {
            try {
                stockMaximo = Integer.valueOf(texto);
            } catch (NumberFormatException e) {
                aviso("El stock máximo debe ser un número entero.");
                return;
            }
        }
        llenarTabla(controlador.filtrar(tipo, stockMaximo));
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodosActionPerformed
        cmbFiltroTipo.setSelectedIndex(0);
        txtStockMax.setText("");
        llenarTabla(controlador.getListaProductos());
    }//GEN-LAST:event_btnMostrarTodosActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtStockKeyTyped

    private void txtStockMaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockMaxKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtStockMaxKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        // Solo dígitos y un único punto decimal
        char c = evt.getKeyChar();
        boolean esPuntoValido = c == '.' && !txtPrecio.getText().contains(".");
        if (!Character.isDigit(c) && !esPuntoValido && !Character.isISOControl(c)) {
            evt.consume();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtSkuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSkuKeyTyped
        // Convierte a mayúsculas y limita a 8 caracteres (AAA-0000)
        char c = evt.getKeyChar();
        if (Character.isISOControl(c)) return;
        if (txtSku.getText().length() >= 8 || !(Character.isLetterOrDigit(c) || c == '-')) {
            evt.consume();
            java.awt.Toolkit.getDefaultToolkit().beep();
            return;
        }
        evt.setKeyChar(Character.toUpperCase(c));
    }//GEN-LAST:event_txtSkuKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
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
        java.awt.EventQueue.invokeLater(() -> new FrmProductos().setVisible(true));
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
    private javax.swing.JComboBox<String> cmbFiltroTipo;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFiltroTipo;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrdenar;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblSku;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblStockMax;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelFiltrar;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOrdenar;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtSku;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtStockMax;
    // End of variables declaration//GEN-END:variables
}
