/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista.usuario;

import controlador.ItemController;
import controlador.PrestamosController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Item;
import modelo.Prestamos;

/**
 *
 * @author Hector Marquez
 */
public class UsuarioDashboard extends javax.swing.JFrame {
    
    private final PrestamosController prestamosController;
    private final ItemController itemController;
    private final DefaultTableModel tableModelLibros;
    private final DefaultTableModel tableModelPrestamos;
    private int libroSeleccionadoId = 0;
    private int prestamoSeleccionadoId = 0;
    private int usuarioId; // ID del usuario logueado

    /**
     * Creates new form AlumnoDashboard
     */
    public UsuarioDashboard(int usuarioId) {
        this.usuarioId = usuarioId;
        initComponents();
        
        prestamosController = new PrestamosController();
        itemController = new ItemController();

        tableModelLibros = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "Tipo"}, 0);
        tblLibros.setModel(tableModelLibros);

        tableModelPrestamos = new DefaultTableModel(new Object[]{"ID", "Título", "Fecha Préstamo", "Fecha Devolución", "Estado"}, 0);
        tblPrestamos.setModel(tableModelPrestamos);

        cargarLibros();
        cargarPrestamos(usuarioId);
       
    }
    
    private void cargarLibros() {
        tableModelLibros.setRowCount(0);
        List<Item> items = itemController.listarItems();
        System.out.println("Libros encontrados: " + items.size());
        for (Item item : items) {
            tableModelLibros.addRow(new Object[]{
                    item.getIdItem(),
                    item.getTitulo(),
                    item.getAutor(),
                    item.getTipoItem()
            });
        }
    }
    
    private void cargarPrestamos(int idUsuario) {
        tableModelPrestamos.setRowCount(0); // Limpiar tabla
        List<Prestamos> prestamos = prestamosController.listarPrestamosPorUsuario(idUsuario); // Reutilizando el método existente con detalles
        System.out.println("Préstamos encontrados: " + prestamos.size());
        for (Prestamos prestamo : prestamos) {
            tableModelPrestamos.addRow(new Object[]{
                prestamo.getIdPrestamo(),
                prestamo.getTituloEjemplar(), // Incluido desde listarPrestamosConDetalles
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion(),
                prestamo.isDevuelto() ? "Devuelto" : "No Devuelto"
            });
        }
    }
    
    private void filtrarLibros(String criterio) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelLibros);
        tblLibros.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + criterio));
    }

    private void filtrarPrestamos(String criterio) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelPrestamos);
        tblPrestamos.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + criterio));
    }
    
    private void prestarLibro() {
        if (libroSeleccionadoId == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para prestar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!prestamosController.validarLimitePrestamos(usuarioId)) {
            JOptionPane.showMessageDialog(this, "Ha alcanzado el límite máximo de préstamos activos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date fechaPrestamo = new java.sql.Date(System.currentTimeMillis()); // Fecha actual
        java.sql.Date fechaDevolucion = new java.sql.Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)); // Fecha de devolución (7 días después)

        Prestamos nuevoPrestamo = new Prestamos(
                0, usuarioId, libroSeleccionadoId,
                fechaPrestamo,
                fechaDevolucion,
                false, 0.0
        );

        if (prestamosController.registrarPrestamo(nuevoPrestamo)) {
            JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPrestamos(usuarioId);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void devolverLibro() {
        if (prestamoSeleccionadoId == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un préstamo para devolver.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (prestamosController.marcarDevuelto(prestamoSeleccionadoId, 0.0)) {
            JOptionPane.showMessageDialog(this, "Libro devuelto exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPrestamos(usuarioId);
        } else {
            JOptionPane.showMessageDialog(this, "Error al devolver el libro.", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        txtBuscarLibro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLibros = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrestamos = new javax.swing.JTable();
        txtBuscarPrestamo = new javax.swing.JTextField();
        btnPrestarLibro = new javax.swing.JButton();
        btnDevolverLibro = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("BUSCAR");

        txtBuscarLibro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarLibroKeyReleased(evt);
            }
        });

        tblLibros.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLibrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLibros);

        jLabel2.setText("MIS PRESTAMOS");

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
        tblPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPrestamosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPrestamos);

        txtBuscarPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestamoKeyReleased(evt);
            }
        });

        btnPrestarLibro.setBackground(new java.awt.Color(153, 204, 255));
        btnPrestarLibro.setText("PRESTAR LIBRO");
        btnPrestarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestarLibroActionPerformed(evt);
            }
        });

        btnDevolverLibro.setBackground(new java.awt.Color(153, 204, 255));
        btnDevolverLibro.setText("DEVOLVER");
        btnDevolverLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverLibroActionPerformed(evt);
            }
        });

        jLabel3.setText("BUSCAR");

        jLabel4.setText("LIBRO A PRESTAR");

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Hector Marquez\\Documents\\NetBeansProjects\\AmigosDeDonBosco24\\src\\main\\java\\Imagenes\\Sin título.jpg")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrestarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDevolverLibro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtBuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrestarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDevolverLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarLibroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarLibroKeyReleased
      filtrarLibros(txtBuscarLibro.getText());
    }//GEN-LAST:event_txtBuscarLibroKeyReleased

    private void txtBuscarPrestamoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestamoKeyReleased
      filtrarPrestamos(txtBuscarPrestamo.getText());
    }//GEN-LAST:event_txtBuscarPrestamoKeyReleased

    private void tblLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLibrosMouseClicked
        int selectedRow = tblLibros.getSelectedRow();
        if (selectedRow != -1) {
            libroSeleccionadoId = (int) tableModelLibros.getValueAt(selectedRow, 0);
        }
    }//GEN-LAST:event_tblLibrosMouseClicked

    private void tblPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPrestamosMouseClicked
        int selectedRow = tblPrestamos.getSelectedRow();
        if (selectedRow != -1) {
            prestamoSeleccionadoId = (int) tableModelPrestamos.getValueAt(selectedRow, 0);
        }
    }//GEN-LAST:event_tblPrestamosMouseClicked

    private void btnPrestarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestarLibroActionPerformed
        prestarLibro();
    }//GEN-LAST:event_btnPrestarLibroActionPerformed

    private void btnDevolverLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolverLibroActionPerformed
        devolverLibro();
    }//GEN-LAST:event_btnDevolverLibroActionPerformed

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
            java.util.logging.Logger.getLogger(UsuarioDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            int usuarioId = 1;
            public void run() {
                new UsuarioDashboard(usuarioId).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDevolverLibro;
    private javax.swing.JButton btnPrestarLibro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLibros;
    private javax.swing.JTable tblPrestamos;
    private javax.swing.JTextField txtBuscarLibro;
    private javax.swing.JTextField txtBuscarPrestamo;
    // End of variables declaration//GEN-END:variables
}
