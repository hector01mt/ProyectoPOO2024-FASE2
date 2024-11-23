/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.admin;

import controlador.ConfiguracionSistemaController;
import controlador.ItemController;
import controlador.PrestamosController;
import controlador.UsuariosController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Item;
import modelo.Prestamos;
import modelo.Usuarios;

/**
 *
 * @author Hector Marquez
 */
public class GestionPrestamos extends javax.swing.JFrame {

    private final PrestamosController prestamosController;
    private final ItemController itemController;
    private final UsuariosController usuariosController;
    private final ConfiguracionSistemaController configuracionController;
    private final DefaultTableModel modeloUsuarios;
    private final DefaultTableModel modeloEjemplares;
    private final DefaultTableModel modeloPrestamos;
    private int usuarioSeleccionadoId;
    private int ejemplarSeleccionadoId = 0;
    
    /**
     * Creates new form GestionPrestamos
     */
    public GestionPrestamos() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        prestamosController = new PrestamosController();
        itemController = new ItemController();
        usuariosController = new UsuariosController();
        configuracionController = new ConfiguracionSistemaController();
        
        modeloUsuarios = new DefaultTableModel(new Object[]{"ID Usuario", "Nombre", "Email", "Tipo"}, 0);
        tblUsuarios.setModel(modeloUsuarios);
        
        modeloEjemplares = new DefaultTableModel(new Object[]{"ID Item", "Título", "Autor", "Tipo"}, 0);
        tblEjemplar.setModel(modeloEjemplares);

        modeloPrestamos = new DefaultTableModel(new Object[]{"ID Prestamo", "Usuario", "Ejemplar", "Fecha Préstamo", "Fecha Devolución", "Estado"}, 0);
        tblPrestamos.setModel(modeloPrestamos);
        
        cargarUsuarios();
        cargarEjemplares();
        cargarPrestamos();
    }
    
    private void cargarUsuarios() {
        modeloUsuarios.setRowCount(0);
        List<Usuarios> usuarios = usuariosController.listarUsuarios();
        for (Usuarios usuario : usuarios) {
            modeloUsuarios.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getNombreUsuario(),
                    usuario.getEmail(),
                    usuario.getTipoUsuario()
            });
        }
    }
    
    private void filtrarUsuarios(String criterio) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloUsuarios);
        tblUsuarios.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + criterio));
    }
    
    private void filtrarPrestamos(String criterio){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloPrestamos);
        tblPrestamos.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + criterio));
    }
    
     private void filtrarEjemplares(String criterio) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloEjemplares);
        tblEjemplar.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + criterio));
    }
     
    private void cargarEjemplares() {
        modeloEjemplares.setRowCount(0);
        List<Item> items = itemController.listarItems();
        for (Item item : items) {
            modeloEjemplares.addRow(new Object[]{
                item.getIdItem(),
                item.getTitulo(),
                item.getAutor(),
                item.getTipoItem()
            });
        }
    }
    
    private void seleccionarUsuario() {
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioSeleccionadoId = (int) tblUsuarios.getValueAt(filaSeleccionada, 0);
        String nombreUsuario = (String) tblUsuarios.getValueAt(filaSeleccionada, 1);
        JOptionPane.showMessageDialog(this, "Usuario seleccionado: " + nombreUsuario, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void cargarPrestamos() {
        modeloPrestamos.setRowCount(0);
        List<Prestamos> prestamos = prestamosController.listarPrestamosConDetalles(); // 0 para listar todos
        for (Prestamos prestamo : prestamos) {
            modeloPrestamos.addRow(new Object[]{
                    prestamo.getIdPrestamo(),
                    prestamo.getNombreUsuario(), // Obtener el nombre si es necesario
                    prestamo.getTituloEjemplar(), // Obtener el título si es necesario
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    prestamo.isDevuelto() ? "Devuelto" : "No Devuelto"
            });
        }
    }
    
    private boolean confirmarDetallesPrestamo(String usuario, String ejemplar, java.util.Date fechaPrestamo, java.util.Date fechaDevolucion) {
        String mensaje = "Por favor, confirme los detalles del préstamo:\n\n"
                + "Usuario: " + usuario + "\n"
                + "Ejemplar: " + ejemplar + "\n"
                + "Fecha de Préstamo: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaPrestamo) + "\n"
                + "Fecha de Devolución: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaDevolucion) + "\n\n"
                + "¿Desea proceder con el registro?";

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar Préstamo", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }
    
     private void registrarPrestamo() {
       if (usuarioSeleccionadoId == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (ejemplarSeleccionadoId == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un ejemplar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
         if (!prestamosController.validarLimitePrestamos(usuarioSeleccionadoId)) {
             JOptionPane.showMessageDialog(this, "El usuario ha alcanzado el límite de préstamos permitidos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
         }

        java.util.Date fechaPrestamo = datePrestamo.getDate();
        java.util.Date fechaDevolucion = dateDevolucion.getDate();

        if (fechaPrestamo == null || fechaDevolucion == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione las fechas de préstamo y devolución.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
         String nombreUsuario = (String) tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 1);
         String tituloEjemplar = (String) tblEjemplar.getValueAt(tblEjemplar.getSelectedRow(), 1);
         
          if (!confirmarDetallesPrestamo(nombreUsuario, tituloEjemplar, fechaPrestamo, fechaDevolucion)) {
             return; // Cancelar si el usuario no confirma
         }

        Prestamos nuevoPrestamo = new Prestamos(
            0,
            usuarioSeleccionadoId,
            ejemplarSeleccionadoId,
            new java.sql.Date(fechaPrestamo.getTime()),
            new java.sql.Date(fechaDevolucion.getTime()),
            false,
            0.0
        );

        if (prestamosController.registrarPrestamo(nuevoPrestamo)) {
            JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPrestamos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        datePrestamo = new com.toedter.calendar.JDateChooser();
        dateDevolucion = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrestamos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        txtBuscarUsuario = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblEjemplar = new javax.swing.JTable();
        txtBuscarEjemplar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBuscarPrestamo = new javax.swing.JTextField();
        btnGestionarDevolucion = new javax.swing.JButton();
        btnConfiguracionSistema = new javax.swing.JButton();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("USUARIO");

        jLabel2.setText("EJEMPLAR");

        jLabel3.setText("FECHA PRESTAMO");

        jLabel4.setText("FECHA DEVOLUCION");

        btnRegistrar.setText("REGISTRAR PRESTAMO");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnActualizar.setText("ACTUALIZAR TABLA");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        tblPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblPrestamos);

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUsuarios);

        txtBuscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarUsuarioKeyReleased(evt);
            }
        });

        tblEjemplar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEjemplar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEjemplarMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblEjemplar);

        txtBuscarEjemplar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarEjemplarKeyReleased(evt);
            }
        });

        jLabel5.setText("FILTRAR PRESTAMOS");

        txtBuscarPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestamoKeyReleased(evt);
            }
        });

        btnGestionarDevolucion.setText("GESTIONAR DEVOLUCION");
        btnGestionarDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarDevolucionActionPerformed(evt);
            }
        });

        btnConfiguracionSistema.setText("CONFIGURACION SISTEMA");
        btnConfiguracionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionSistemaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfiguracionSistema)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(datePrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                                .addComponent(jScrollPane4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(85, 85, 85)
                                .addComponent(txtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(81, 81, 81)
                                .addComponent(txtBuscarEjemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRegistrar)
                                .addGap(18, 18, 18)
                                .addComponent(btnGestionarDevolucion)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtBuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtBuscarEjemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datePrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(dateDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGestionarDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnConfiguracionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registrarPrestamo();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarPrestamos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtBuscarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuarioKeyReleased
        filtrarUsuarios(txtBuscarUsuario.getText());
    }//GEN-LAST:event_txtBuscarUsuarioKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            usuarioSeleccionadoId = (int) tblUsuarios.getValueAt(filaSeleccionada, 0);
            String nombreUsuario = (String) tblUsuarios.getValueAt(filaSeleccionada, 1);
            JOptionPane.showMessageDialog(this, "Usuario seleccionado: " + nombreUsuario, "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void tblEjemplarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEjemplarMouseClicked
        int filaSeleccionada = tblEjemplar.getSelectedRow();
        if (filaSeleccionada != -1) {
            ejemplarSeleccionadoId = (int) tblEjemplar.getValueAt(filaSeleccionada, 0);
            String tituloEjemplar = (String) tblEjemplar.getValueAt(filaSeleccionada, 1);
            JOptionPane.showMessageDialog(this, "Ejemplar seleccionado: " + tituloEjemplar, "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tblEjemplarMouseClicked

    private void txtBuscarEjemplarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEjemplarKeyReleased
        filtrarEjemplares(txtBuscarEjemplar.getText());
    }//GEN-LAST:event_txtBuscarEjemplarKeyReleased

    private void txtBuscarPrestamoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestamoKeyReleased
        filtrarPrestamos(txtBuscarPrestamo.getText());
    }//GEN-LAST:event_txtBuscarPrestamoKeyReleased

    private void btnGestionarDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarDevolucionActionPerformed
        new GestionDevoluciones().setVisible(true);
    }//GEN-LAST:event_btnGestionarDevolucionActionPerformed

    private void btnConfiguracionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionSistemaActionPerformed
        new ConfiguracionSistemaVista().setVisible(true);
    }//GEN-LAST:event_btnConfiguracionSistemaActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionPrestamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionPrestamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionPrestamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionPrestamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionPrestamos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnConfiguracionSistema;
    private javax.swing.JButton btnGestionarDevolucion;
    private javax.swing.JButton btnRegistrar;
    private com.toedter.calendar.JDateChooser dateDevolucion;
    private com.toedter.calendar.JDateChooser datePrestamo;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblEjemplar;
    private javax.swing.JTable tblPrestamos;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscarEjemplar;
    private javax.swing.JTextField txtBuscarPrestamo;
    private javax.swing.JTextField txtBuscarUsuario;
    // End of variables declaration//GEN-END:variables
}
