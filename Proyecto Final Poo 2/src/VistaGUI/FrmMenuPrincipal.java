/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VistaGUI;

import CONTROLADOR.ControladorUsuario;
import MODELO.AdministradorSistema;
import MODELO.GerenteGeneral;
import MODELO.OperarioBodega;
import MODELO.Usuarios;
import javax.swing.JOptionPane;

/**
 * Menú principal del ERP. Muestra u oculta opciones según el rol del usuario
 * que inició sesión:
 * <ul>
 * <li>Administrador: gestión completa (incluye usuarios) y consultas.</li>
 * <li>Operario de Bodega: crea, actualiza y consulta (no borra, no gestiona usuarios).</li>
 * <li>Gerente General: solo consultas y ordenamientos.</li>
 * </ul>
 *
 * @author Marcelo
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMenuPrincipal.class.getName());

    // Usuario que inició sesión; se usa para habilitar/ocultar opciones según su rol
    private Usuarios usuarioLogueado;

    /**
     * Creates new form FrmMenuPrincipal (lo usa el diseñador de NetBeans).
     */
    public FrmMenuPrincipal() {
        initComponents();
        aplicarPermisos();
    }

    /**
     * Abre el menú principal con el usuario autenticado en el login.
     *
     * @param usuario usuario que inició sesión
     */
    public FrmMenuPrincipal(Usuarios usuario) {
        initComponents();
        this.usuarioLogueado = usuario;
        aplicarPermisos();
    }

    /**
     * @return el usuario que inició sesión (para pasarlo a los formularios)
     */
    public Usuarios getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // ------------------------------------------------------------------------------------
    // Permisos por rol
    // ------------------------------------------------------------------------------------
    private void aplicarPermisos() {
        if (usuarioLogueado == null) {
            // Sin sesión no se permite usar el sistema
            menuGestion.setVisible(false);
            menuConsultas.setVisible(false);
            lblBienvenida.setText("No hay una sesión iniciada");
            lblRol.setText("Use Sesión > Cerrar sesión para volver al login.");
            lblPermisos.setText("");
            return;
        }

        String rol = ControladorUsuario.obtenerRol(usuarioLogueado);
        setTitle("ERP Corporativo - " + rol + ": " + usuarioLogueado.getNombre());
        lblBienvenida.setText("Bienvenido, " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido());
        lblRol.setText("Rol: " + rol);

        if (usuarioLogueado instanceof AdministradorSistema) {
            // El Administrador tiene acceso a todo
            menuGestion.setVisible(true);
            menuConsultas.setVisible(true);
            itemGestionUsuarios.setVisible(true);
            itemConsultarUsuarios.setVisible(true);
            lblPermisos.setText("<html><center>Acceso total: crear, consultar, actualizar y eliminar registros,"
                    + "<br>incluida la gestión de usuarios del sistema.</center></html>");

        } else if (usuarioLogueado instanceof OperarioBodega) {
            // El Operario crea, actualiza y consulta, pero no borra ni gestiona usuarios
            menuGestion.setVisible(true);
            menuConsultas.setVisible(true);
            itemGestionUsuarios.setVisible(false);
            jSeparator2.setVisible(false);
            itemConsultarUsuarios.setVisible(false);
            lblPermisos.setText("<html><center>Puede crear, consultar y actualizar registros."
                    + "<br>No puede eliminar registros ni gestionar usuarios.</center></html>");

        } else if (usuarioLogueado instanceof GerenteGeneral) {
            // El Gerente solo hace consultas y ordenamientos
            menuGestion.setVisible(false);
            menuConsultas.setVisible(true);
            itemConsultarUsuarios.setVisible(true);
            lblPermisos.setText("<html><center>Modo de solo lectura: consultas y ordenamientos.</center></html>");
        }
    }

    /**
     * Abre una ventana secundaria centrada. Al cerrarla se vuelve a este menú,
     * por eso las ventanas secundarias deben usar DISPOSE_ON_CLOSE.
     */
    private void abrirVentana(javax.swing.JFrame ventana) {
        ventana.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }

    /** Aviso temporal para las opciones cuyo formulario aún no existe. */
    private void enConstruccion(String nombre) {
        JOptionPane.showMessageDialog(this, "El formulario de " + nombre + " aún está en construcción.",
                "Próximamente", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBienvenida = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblPermisos = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuSesion = new javax.swing.JMenu();
        itemCerrarSesion = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();
        menuGestion = new javax.swing.JMenu();
        itemGestionProductos = new javax.swing.JMenuItem();
        itemGestionAlmacenes = new javax.swing.JMenuItem();
        itemGestionZonas = new javax.swing.JMenuItem();
        itemGestionMovimientos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemGestionPaises = new javax.swing.JMenuItem();
        itemGestionCiudades = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemGestionUsuarios = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        itemConsultarProductos = new javax.swing.JMenuItem();
        itemConsultarMovimientos = new javax.swing.JMenuItem();
        itemConsultarAlmacenes = new javax.swing.JMenuItem();
        itemConsultarUsuarios = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ERP Corporativo - Menú Principal");

        lblBienvenida.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblBienvenida.setForeground(new java.awt.Color(0, 153, 204));
        lblBienvenida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBienvenida.setText("Bienvenido al ERP Corporativo");

        lblRol.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblRol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRol.setText("Rol:");

        lblPermisos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPermisos.setText("Permisos:");
        lblPermisos.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        menuSesion.setText("Sesión");

        itemCerrarSesion.setText("Cerrar sesión");
        itemCerrarSesion.addActionListener(this::itemCerrarSesionActionPerformed);
        menuSesion.add(itemCerrarSesion);

        itemSalir.setText("Salir");
        itemSalir.addActionListener(this::itemSalirActionPerformed);
        menuSesion.add(itemSalir);

        jMenuBar1.add(menuSesion);

        menuGestion.setText("Gestión");

        itemGestionProductos.setText("Gestionar Productos");
        itemGestionProductos.addActionListener(this::itemGestionProductosActionPerformed);
        menuGestion.add(itemGestionProductos);

        itemGestionAlmacenes.setText("Gestionar Almacenes");
        itemGestionAlmacenes.addActionListener(this::itemGestionAlmacenesActionPerformed);
        menuGestion.add(itemGestionAlmacenes);

        itemGestionZonas.setText("Gestionar Zonas / Pasillos");
        itemGestionZonas.addActionListener(this::itemGestionZonasActionPerformed);
        menuGestion.add(itemGestionZonas);

        itemGestionMovimientos.setText("Gestionar Movimientos Logísticos");
        itemGestionMovimientos.addActionListener(this::itemGestionMovimientosActionPerformed);
        menuGestion.add(itemGestionMovimientos);
        menuGestion.add(jSeparator1);

        itemGestionPaises.setText("Gestionar Países");
        itemGestionPaises.addActionListener(this::itemGestionPaisesActionPerformed);
        menuGestion.add(itemGestionPaises);

        itemGestionCiudades.setText("Gestionar Ciudades");
        itemGestionCiudades.addActionListener(this::itemGestionCiudadesActionPerformed);
        menuGestion.add(itemGestionCiudades);
        menuGestion.add(jSeparator2);

        itemGestionUsuarios.setText("Gestionar Usuarios");
        itemGestionUsuarios.addActionListener(this::itemGestionUsuariosActionPerformed);
        menuGestion.add(itemGestionUsuarios);

        jMenuBar1.add(menuGestion);

        menuConsultas.setText("Consultas");

        itemConsultarProductos.setText("Consultar Productos y Existencias");
        itemConsultarProductos.addActionListener(this::itemConsultarProductosActionPerformed);
        menuConsultas.add(itemConsultarProductos);

        itemConsultarMovimientos.setText("Consultar Movimientos Logísticos");
        itemConsultarMovimientos.addActionListener(this::itemConsultarMovimientosActionPerformed);
        menuConsultas.add(itemConsultarMovimientos);

        itemConsultarAlmacenes.setText("Consultar Almacenes");
        itemConsultarAlmacenes.addActionListener(this::itemConsultarAlmacenesActionPerformed);
        menuConsultas.add(itemConsultarAlmacenes);

        itemConsultarUsuarios.setText("Consultar Usuarios");
        itemConsultarUsuarios.addActionListener(this::itemConsultarUsuariosActionPerformed);
        menuConsultas.add(itemConsultarUsuarios);

        jMenuBar1.add(menuConsultas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(lblRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblBienvenida)
                .addGap(20, 20, 20)
                .addComponent(lblRol)
                .addGap(30, 30, 30)
                .addComponent(lblPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            new FrmLogin().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_itemSalirActionPerformed

    private void itemGestionProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionProductosActionPerformed
        abrirVentana(new FrmProductos(usuarioLogueado));
    }//GEN-LAST:event_itemGestionProductosActionPerformed

    private void itemGestionAlmacenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionAlmacenesActionPerformed
        enConstruccion("Almacenes");
    }//GEN-LAST:event_itemGestionAlmacenesActionPerformed

    private void itemGestionZonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionZonasActionPerformed
        enConstruccion("Zonas / Pasillos");
    }//GEN-LAST:event_itemGestionZonasActionPerformed

    private void itemGestionMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionMovimientosActionPerformed
        enConstruccion("Movimientos Logísticos");
    }//GEN-LAST:event_itemGestionMovimientosActionPerformed

    private void itemGestionPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionPaisesActionPerformed
        enConstruccion("Países");
    }//GEN-LAST:event_itemGestionPaisesActionPerformed

    private void itemGestionCiudadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionCiudadesActionPerformed
        enConstruccion("Ciudades");
    }//GEN-LAST:event_itemGestionCiudadesActionPerformed

    private void itemGestionUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionUsuariosActionPerformed
        enConstruccion("Usuarios");
    }//GEN-LAST:event_itemGestionUsuariosActionPerformed

    private void itemConsultarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultarProductosActionPerformed
        abrirVentana(new FrmProductos(usuarioLogueado, true)); // modo solo consulta
    }//GEN-LAST:event_itemConsultarProductosActionPerformed

    private void itemConsultarMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultarMovimientosActionPerformed
        enConstruccion("Consulta de Movimientos Logísticos");
    }//GEN-LAST:event_itemConsultarMovimientosActionPerformed

    private void itemConsultarAlmacenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultarAlmacenesActionPerformed
        enConstruccion("Consulta de Almacenes");
    }//GEN-LAST:event_itemConsultarAlmacenesActionPerformed

    private void itemConsultarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultarUsuariosActionPerformed
        enConstruccion("Consulta de Usuarios");
    }//GEN-LAST:event_itemConsultarUsuariosActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemConsultarAlmacenes;
    private javax.swing.JMenuItem itemConsultarMovimientos;
    private javax.swing.JMenuItem itemConsultarProductos;
    private javax.swing.JMenuItem itemConsultarUsuarios;
    private javax.swing.JMenuItem itemGestionAlmacenes;
    private javax.swing.JMenuItem itemGestionCiudades;
    private javax.swing.JMenuItem itemGestionMovimientos;
    private javax.swing.JMenuItem itemGestionPaises;
    private javax.swing.JMenuItem itemGestionProductos;
    private javax.swing.JMenuItem itemGestionUsuarios;
    private javax.swing.JMenuItem itemGestionZonas;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblRol;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuSesion;
    // End of variables declaration//GEN-END:variables
}
