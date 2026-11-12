/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package EJERCICIOS;

import CONTROLADOR.ControladorUsuario;
import MODELO.AdministradorSistema;
import MODELO.GerenteGeneral;
import MODELO.OperarioBodega;
import MODELO.Usuarios;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * EJERCICIO 1 (estilo sustentación de la 1ª entrega): modificar un usuario.
 * <p>
 * Demuestra: constructores sobrecargados, insertar en el controlador, equals() por correo,
 * búsqueda binaria (consultarUno), getters/setters, update() y reportes con toString().
 *
 * @author Grupo 1
 */
public class FrmEjercicio1 extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEjercicio1.class.getName());

    private final ControladorUsuario controlador = new ControladorUsuario();
    private DefaultTableModel modeloTabla;
    private Usuarios usuarioEncontrado; // usuario cargado con el botón Buscar

    /**
     * Creates new form FrmEjercicio1
     */
    public FrmEjercicio1() {
        initComponents();
        modeloTabla = crearModelo("ID", "Rol", "Nombre", "Apellido", "Teléfono", "Correo", "Cargo");
        crearUsuarios();
        llenarTabla();
        reporte("USUARIOS ANTES DE MODIFICAR");
    }

    /** 1. Se crean los usuarios con los constructores de las clases hijas y se insertan. */
    private void crearUsuarios() {
        // Constructor completo de cada clase hija
        AdministradorSistema u1 = new AdministradorSistema(111, "Pedro", "Gomez", "3201234567", "pedro@erp.com", "Administrador", 1, "Admin123*");
        GerenteGeneral u2 = new GerenteGeneral(999, "Miguel", "Castro", "3111234567", "miguel@erp.com", "Gerente", 5, "Gerente123*");
        // Constructor de OperarioBodega SIN contraseña (9 parámetros)
        OperarioBodega u3 = new OperarioBodega(555, "Daniela", "Mejia", "3211234567", "daniela@erp.com", "Operario", 1300000f, "Diurno", null);
        // Constructor SOBRECARGADO de OperarioBodega CON contraseña (10 parámetros)
        OperarioBodega u4 = new OperarioBodega(222, "Mateo", "Buritica", "3711234567", "mateo@erp.com", "Operario", 1250000f, "Nocturno", null, "Mateo123*");

        imprimir("Insertar " + u1.getNombre() + ": " + controlador.insertar(u1));
        imprimir("Insertar " + u2.getNombre() + ": " + controlador.insertar(u2));
        imprimir("Insertar " + u3.getNombre() + ": " + controlador.insertar(u3));
        imprimir("Insertar " + u4.getNombre() + ": " + controlador.insertar(u4));

        // equals() compara por correo: este usuario NO se debe insertar
        OperarioBodega repetido = new OperarioBodega(777, "Copia", "Repetida", "3000000000", "pedro@erp.com", "Operario", 0f, "Diurno", null);
        imprimir("Insertar usuario con correo repetido (pedro@erp.com): " + controlador.insertar(repetido));
        imprimir("");
    }

    /** Reporte de todos los usuarios usando toString(). */
    private void reporte(String titulo) {
        imprimir("=== " + titulo + " ===");
        for (Usuarios u : controlador.getListaUsuarios()) {
            imprimir(u.toString());
        }
        imprimir("");
    }

    private void llenarTabla() {
        modeloTabla.setRowCount(0);
        for (Usuarios u : controlador.getListaUsuarios()) {
            modeloTabla.addRow(new Object[]{u.getId(), ControladorUsuario.obtenerRol(u), u.getNombre(),
                u.getApellido(), u.getTelefono(), u.getCorreo(), u.getCargo()});
        }
    }

    private void limpiar() {
        txtIdBuscar.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCargo.setText("");
        usuarioEncontrado = null;
        tblDatos.clearSelection();
        txtIdBuscar.requestFocus();
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
        lblIdBuscar = new javax.swing.JLabel();
        txtIdBuscar = new javax.swing.JTextField();
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
        panelBotones = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        panelInferior = new javax.swing.JPanel();
        scrollReporte = new javax.swing.JScrollPane();
        txtReporte = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejercicio 1 - Modificar un usuario");

        panelSuperior.setLayout(new java.awt.BorderLayout());

        panelEncabezado.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 153, 204));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ejercicio 1 - Modificar un usuario");
        panelEncabezado.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        lblEnunciado.setBorder(javax.swing.BorderFactory.createTitledBorder("Enunciado"));
        lblEnunciado.setText("<html><body style='width:820px'>Se crean 4 usuarios con los <b>constructores sobrecargados</b> de las clases hijas (Administrador, Gerente y dos Operarios: uno con contraseña y otro sin ella) y se insertan en el controlador. Se intenta insertar un usuario con un correo repetido (<b>equals</b> lo impide). El usuario digita el ID del usuario a modificar (111, 222, 555 o 999), se busca con <b>búsqueda binaria</b>, se cambian sus datos con los <b>setters</b> y se muestra la lista <b>ANTES y DESPUÉS</b> de modificar con <b>toString()</b>.</body></html>");
        panelEncabezado.add(lblEnunciado, java.awt.BorderLayout.CENTER);

        panelSuperior.add(panelEncabezado, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        panelDatos.setLayout(new java.awt.GridLayout(0, 4, 10, 8));

        lblIdBuscar.setText("ID del usuario a modificar:");
        panelDatos.add(lblIdBuscar);

        txtIdBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdBuscarKeyTyped(evt);
            }
        });
        panelDatos.add(txtIdBuscar);

        lblNombre.setText("Nombre:");
        panelDatos.add(lblNombre);

        panelDatos.add(txtNombre);

        lblApellido.setText("Apellido:");
        panelDatos.add(lblApellido);

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

        panelSuperior.add(panelDatos, java.awt.BorderLayout.CENTER);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btnBuscar.setText("Buscar (búsqueda binaria)");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        panelBotones.add(btnBuscar);

        btnModificar.setText("Modificar (setters + update)");
        btnModificar.addActionListener(this::btnModificarActionPerformed);
        panelBotones.add(btnModificar);

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

    private void txtIdBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdBuscarKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtIdBuscarKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            int id = Integer.parseInt(txtIdBuscar.getText().trim());
            usuarioEncontrado = (Usuarios) controlador.consultarUno(id); // búsqueda binaria por ID
            if (usuarioEncontrado == null) {
                aviso("No existe un usuario con el ID " + id + ". Pruebe con 111, 222, 555 o 999.");
                return;
            }
            // Se muestran los datos actuales usando los getters
            txtNombre.setText(usuarioEncontrado.getNombre());
            txtApellido.setText(usuarioEncontrado.getApellido());
            txtTelefono.setText(usuarioEncontrado.getTelefono());
            txtCorreo.setText(usuarioEncontrado.getCorreo());
            txtCargo.setText(usuarioEncontrado.getCargo());
            imprimir("Búsqueda binaria del ID " + id + ": encontrado -> " + usuarioEncontrado.getNombre());
        } catch (NumberFormatException e) {
            aviso("El ID debe ser un número entero.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (usuarioEncontrado == null) {
            aviso("Primero busque el usuario que desea modificar.");
            return;
        }
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String cargo = txtCargo.getText().trim();
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty() || cargo.isEmpty()) {
            aviso("Todos los campos son obligatorios.");
            return;
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            aviso("El correo debe tener la forma usuario@dominio.com");
            return;
        }

        // equals(): el nuevo correo no puede pertenecer a OTRO usuario
        Usuarios temporal = new OperarioBodega(); // constructor vacío
        temporal.setCorreo(correo);
        for (Usuarios u : controlador.getListaUsuarios()) {
            if (u != usuarioEncontrado && u.equals(temporal)) {
                aviso("El correo " + correo + " ya lo tiene " + u.getNombre() + " (equals compara por correo).");
                return;
            }
        }

        // Se modifican los datos con los setters y se actualiza en el controlador
        usuarioEncontrado.setNombre(nombre);
        usuarioEncontrado.setApellido(apellido);
        usuarioEncontrado.setTelefono(telefono);
        usuarioEncontrado.setCorreo(correo);
        usuarioEncontrado.setCargo(cargo);
        controlador.update(usuarioEncontrado);

        imprimir("Usuario " + usuarioEncontrado.getId() + " modificado con los setters.");
        llenarTabla();
        reporte("USUARIOS DESPUÉS DE MODIFICAR");
        JOptionPane.showMessageDialog(this, "Usuario modificado. Revise el reporte ANTES / DESPUÉS.");
        limpiar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        reporte("REPORTE ACTUAL DE USUARIOS (toString)");
    }//GEN-LAST:event_btnReporteActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        int fila = tblDatos.getSelectedRow();
        if (fila == -1) return;
        txtIdBuscar.setText(String.valueOf(modeloTabla.getValueAt(fila, 0)));
        btnBuscarActionPerformed(null);
    }//GEN-LAST:event_tblDatosMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new FrmEjercicio1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEnunciado;
    private javax.swing.JLabel lblIdBuscar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollReporte;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdBuscar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtReporte;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
