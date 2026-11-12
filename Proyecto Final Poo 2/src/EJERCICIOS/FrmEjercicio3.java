/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package EJERCICIOS;

import CONTROLADOR.ControladorAlmacen;
import CONTROLADOR.ControladorUsuario;
import MODELO.AdministradorSistema;
import MODELO.Almacen;
import MODELO.Ciudad;
import MODELO.GerenteGeneral;
import MODELO.OperarioBodega;
import MODELO.Pais;
import MODELO.Usuarios;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * EJERCICIO 3: almacenes.
 * <p>
 * Demuestra: constructores sobrecargados (vacío + setters), relaciones entre clases
 * (Almacén - Ciudad - País - Gerente), insertar/actualizar/eliminar, sobrecarga de delete
 * según el rol (polimorfismo), sort nativo por ID y Burbuja por nombre.
 *
 * @author Grupo 1
 */
public class FrmEjercicio3 extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEjercicio3.class.getName());

    private final ControladorAlmacen controlador = new ControladorAlmacen();
    private DefaultTableModel modeloTabla;
    private final ArrayList<Ciudad> ciudades = new ArrayList<>();
    private final ArrayList<GerenteGeneral> gerentes = new ArrayList<>();
    private AdministradorSistema administrador;
    private OperarioBodega operario;

    /**
     * Creates new form FrmEjercicio3
     */
    public FrmEjercicio3() {
        initComponents();
        modeloTabla = crearModelo("ID", "Nombre", "Ciudad", "País", "Gerente");
        crearDatos();
        llenarCombos();
        llenarTabla();
        reporte("ALMACENES INICIALES (sort nativo por ID)");
    }

    /** 1. Se crean los objetos relacionados y los almacenes. */
    private void crearDatos() {
        // Constructor con parámetros
        Pais colombia = new Pais(1, "Colombia");
        // Constructor vacío + setters (sobrecarga de constructores)
        Pais peru = new Pais();
        peru.setId(2);
        peru.setNombre("Perú");

        // Asociación: cada Ciudad conoce su País
        ciudades.add(new Ciudad(1, "Bogotá", colombia));
        ciudades.add(new Ciudad(2, "Medellín", colombia));
        ciudades.add(new Ciudad(3, "Lima", peru));

        gerentes.add(new GerenteGeneral(10, "Carlos", "Ruiz", "3111111111", "carlos@erp.com", "Gerente", 5, "Gerente123*"));
        gerentes.add(new GerenteGeneral(11, "Diana", "Perez", "3122222222", "diana@erp.com", "Gerente", 4, "Gerente123*"));

        // Usuarios que intentarán eliminar (polimorfismo: ambos son Usuarios)
        administrador = new AdministradorSistema(1, "Ana", "Gomez", "3001112233", "ana@erp.com", "Administrador", 1, "Admin123*");
        operario = new OperarioBodega(2, "Juan", "Perez", "3004445566", "juan@erp.com", "Operario", 1200000f, "Diurno", null);

        // El Almacén contiene una Ciudad y un Gerente
        imprimir("Insertar Zeta Bodegas: " + controlador.insertar(new Almacen(45, "Zeta Bodegas", ciudades.get(0), gerentes.get(0))));
        imprimir("Insertar Alfa Logística: " + controlador.insertar(new Almacen(12, "Alfa Logística", ciudades.get(1), gerentes.get(1))));
        imprimir("Insertar Delta Storage: " + controlador.insertar(new Almacen(89, "Delta Storage", ciudades.get(2), null)));
        imprimir("");
    }

    private void llenarCombos() {
        cmbCiudad.removeAllItems();
        for (Ciudad c : ciudades) {
            cmbCiudad.addItem(c.getNombre() + " (" + c.getPaisReferencia().getNombre() + ")");
        }
        cmbGerente.removeAllItems();
        cmbGerente.addItem("Sin asignar");
        for (GerenteGeneral g : gerentes) {
            cmbGerente.addItem(g.getNombre() + " " + g.getApellido());
        }
    }

    /** Reporte de todos los almacenes usando toString(). */
    private void reporte(String titulo) {
        imprimir("=== " + titulo + " ===");
        for (Almacen a : controlador.getListaAlmacenes()) {
            imprimir(a.toString());
        }
        imprimir("");
    }

    private void llenarTabla() {
        modeloTabla.setRowCount(0);
        for (Almacen a : controlador.getListaAlmacenes()) {
            String ciudad = a.getCiudad() != null ? a.getCiudad().getNombre() : "-";
            String pais = (a.getCiudad() != null && a.getCiudad().getPaisReferencia() != null)
                    ? a.getCiudad().getPaisReferencia().getNombre() : "-";
            String gerente = a.getGerenteAsignado() != null ? a.getGerenteAsignado().getNombre() : "Sin asignar";
            modeloTabla.addRow(new Object[]{a.getId(), a.getNombre(), ciudad, pais, gerente});
        }
    }

    /** Lee el formulario y arma el Almacén con su Ciudad y su Gerente; null si hay datos inválidos. */
    private Almacen leerFormulario() {
        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            aviso("El ID debe ser un número entero.");
            return null;
        }
        String nombre = txtNombre.getText().trim();
        if (nombre.length() < 3) {
            aviso("El nombre del almacén debe tener al menos 3 caracteres.");
            return null;
        }
        Ciudad ciudad = ciudades.get(cmbCiudad.getSelectedIndex());
        int indiceGerente = cmbGerente.getSelectedIndex();
        GerenteGeneral gerente = indiceGerente == 0 ? null : gerentes.get(indiceGerente - 1);
        return new Almacen(id, nombre, ciudad, gerente);
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        cmbCiudad.setSelectedIndex(0);
        cmbGerente.setSelectedIndex(0);
        tblDatos.clearSelection();
        txtId.requestFocus();
    }

    /** Escribe una línea en el reporte de la ventana y en la consola de NetBeans. */
    private void imprimir(String linea) {
        txtReporte.append(linea + "\n");
        txtReporte.setCaretPosition(txtReporte.getDocument().getLength());
        System.out.println(linea);
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    /** Bloquea cualquier tecla que no sea un dígito. */
    private void soloNumeros(java.awt.event.KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            evt.consume();
        }
    }

    /** Crea un modelo de tabla de solo lectura. */
    private DefaultTableModel crearModelo(String... columnas) {
        DefaultTableModel m = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tblDatos.setModel(m);
        return m;
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
        panelEncabezado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblEnunciado = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCiudad = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox<>();
        lblGerente = new javax.swing.JLabel();
        cmbGerente = new javax.swing.JComboBox<>();
        lblEjecutor = new javax.swing.JLabel();
        cmbEjecutor = new javax.swing.JComboBox<>();
        panelBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        panelInferior = new javax.swing.JPanel();
        panelOpciones = new javax.swing.JPanel();
        lblOrden = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        scrollReporte = new javax.swing.JScrollPane();
        txtReporte = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejercicio 3 - Almacenes: relaciones y permisos");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        panelEncabezado.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ejercicio 3 - Almacenes: relaciones y permisos");
        panelEncabezado.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        lblEnunciado.setBorder(javax.swing.BorderFactory.createTitledBorder("Enunciado"));
        lblEnunciado.setText("<html><body style='width:820px'>Se crean países (con el constructor con parámetros y con el <b>constructor vacío + setters</b>), ciudades y gerentes. Cada <b>Almacén contiene una Ciudad y un Gerente</b> (relaciones entre clases). Se registran y modifican almacenes eligiendo su ciudad y gerente. Se demuestra la <b>sobrecarga del método delete</b>: eliminar \"como Operario\" es rechazado y \"como Administrador\" es permitido (<b>polimorfismo</b>). Los almacenes se ordenan por ID con el <b>sort nativo</b> y por nombre con <b>Burbuja</b> (A-Z y Z-A).</body></html>");
        panelEncabezado.add(lblEnunciado, java.awt.BorderLayout.CENTER);

        panelSuperior.add(panelEncabezado, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblId.setText("ID:");
        panelDatos.add(lblId);

        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        panelDatos.add(txtId);

        lblNombre.setText("Nombre del almacén:");
        panelDatos.add(lblNombre);

        panelDatos.add(txtNombre);

        lblCiudad.setText("Ciudad:");
        panelDatos.add(lblCiudad);

        panelDatos.add(cmbCiudad);

        lblGerente.setText("Gerente asignado:");
        panelDatos.add(lblGerente);

        panelDatos.add(cmbGerente);

        lblEjecutor.setText("Eliminar como:");
        panelDatos.add(lblEjecutor);

        cmbEjecutor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador (Ana)", "Operario de Bodega (Juan)" }));
        panelDatos.add(cmbEjecutor);

        panelSuperior.add(panelDatos, java.awt.BorderLayout.CENTER);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        panelBotones.add(btnGuardar);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);
        panelBotones.add(btnModificar);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        panelBotones.add(btnEliminar);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        panelBotones.add(btnLimpiar);

        btnReporte.setText("Ver reporte (toString)");
        btnReporte.addActionListener(this::btnReporteActionPerformed);
        panelBotones.add(btnReporte);

        panelSuperior.add(panelBotones, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        scrollTabla.setPreferredSize(new java.awt.Dimension(900, 190));
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tblDatos);

        getContentPane().add(scrollTabla, java.awt.BorderLayout.CENTER);

        panelInferior.setLayout(new java.awt.BorderLayout());

        panelOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenar almacenes"));
        panelOpciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblOrden.setText("Ordenar por:");
        panelOpciones.add(lblOrden);

        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID ascendente (sort nativo)", "Nombre A-Z (Burbuja)", "Nombre Z-A (Burbuja)" }));
        panelOpciones.add(cmbOrden);

        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);
        panelOpciones.add(btnOrdenar);

        panelInferior.add(panelOpciones, java.awt.BorderLayout.PAGE_START);

        scrollReporte.setBorder(javax.swing.BorderFactory.createTitledBorder("Reporte (toString) / consola"));
        scrollReporte.setPreferredSize(new java.awt.Dimension(900, 170));
        txtReporte.setEditable(false);
        txtReporte.setColumns(20);
        txtReporte.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtReporte.setRows(8);
        scrollReporte.setViewportView(txtReporte);

        panelInferior.add(scrollReporte, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtIdKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Almacen nuevo = leerFormulario();
        if (nuevo == null) return;
        if (controlador.consultarUno(nuevo.getId()) != null) {
            aviso("Ya existe un almacén con el ID " + nuevo.getId() + ".");
            return;
        }
        imprimir("Insertar " + nuevo.getNombre() + ": " + controlador.insertar(nuevo));
        llenarTabla();
        reporte("ALMACENES DESPUÉS DE INSERTAR");
        limpiar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        Almacen datos = leerFormulario();
        if (datos == null) return;
        if (controlador.consultarUno(datos.getId()) == null) {
            aviso("No existe un almacén con el ID " + datos.getId() + ".");
            return;
        }
        controlador.update(datos); // se reemplazan el nombre, la Ciudad y el Gerente
        imprimir("Almacén " + datos.getId() + " actualizado (nombre, ciudad y gerente).");
        llenarTabla();
        reporte("ALMACENES DESPUÉS DE MODIFICAR");
        limpiar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int id;
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            aviso("Escriba el ID del almacén a eliminar.");
            return;
        }
        if (controlador.consultarUno(id) == null) {
            aviso("No existe un almacén con el ID " + id + ".");
            return;
        }
        // Polimorfismo: el ejecutor puede ser un Administrador o un Operario (ambos son Usuarios)
        Usuarios ejecutor = cmbEjecutor.getSelectedIndex() == 0 ? administrador : operario;
        boolean eliminado = controlador.delete(id, ejecutor); // método delete sobrecargado
        imprimir("Eliminar almacén " + id + " como " + ControladorUsuario.obtenerRol(ejecutor) + ": " + eliminado);
        if (eliminado) {
            llenarTabla();
            reporte("ALMACENES DESPUÉS DE ELIMINAR");
            limpiar();
        } else {
            aviso("Acceso denegado: solo el Administrador puede eliminar registros.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        reporte("REPORTE ACTUAL DE ALMACENES (toString)");
    }//GEN-LAST:event_btnReporteActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        Almacen a = (Almacen) controlador.consultarUno((Integer) modeloTabla.getValueAt(fila, 0));
        if (a == null) return;
        txtId.setText(String.valueOf(a.getId()));
        txtNombre.setText(a.getNombre());
        cmbCiudad.setSelectedIndex(Math.max(0, ciudades.indexOf(a.getCiudad())));
        cmbGerente.setSelectedIndex(a.getGerenteAsignado() == null ? 0 : gerentes.indexOf(a.getGerenteAsignado()) + 1);
        llenarTabla(); // consultarUno reordena por ID para la búsqueda binaria
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        switch (cmbOrden.getSelectedIndex()) {
            case 0:
                controlador.ordenar();   // sort nativo (List.sort) por ID ascendente
                reporte("ORDENADOS POR ID (sort nativo)");
                break;
            case 1:
                controlador.ordenar(0);  // Burbuja por nombre A-Z
                reporte("ORDENADOS POR NOMBRE A-Z (Burbuja)");
                break;
            default:
                controlador.ordenar(1);  // Burbuja por nombre Z-A
                reporte("ORDENADOS POR NOMBRE Z-A (Burbuja)");
                break;
        }
        llenarTabla();
    }//GEN-LAST:event_btnOrdenarActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmEjercicio3().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cmbCiudad;
    private javax.swing.JComboBox<String> cmbEjecutor;
    private javax.swing.JComboBox<String> cmbGerente;
    private javax.swing.JComboBox<String> cmbOrden;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblEjecutor;
    private javax.swing.JLabel lblEnunciado;
    private javax.swing.JLabel lblGerente;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOrden;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollReporte;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtReporte;
    // End of variables declaration//GEN-END:variables
}
