/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;
import modelo.AuditoriaControl;
import modelo.ProveedorControl;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class VistaAuditoria extends javax.swing.JInternalFrame {

    private final AuditoriaControl AUC = new AuditoriaControl();
    private final ProveedorControl PROC = new ProveedorControl();
    /**
     * Creates new form VistaAuditoria
     */
    public VistaAuditoria() {
        initComponents(); 
        jComboBoxProveedorAuditoria.setModel(PROC.seleccionar());
        listarArticulos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelDescripcion = new javax.swing.JLabel();
        jTextFieldDescripcionAuditoria = new javax.swing.JTextField();
        jComboBoxProveedorAuditoria = new javax.swing.JComboBox<>();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPaneArticulosAuditoria = new javax.swing.JScrollPane();
        jTableArticulosAuditoria = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Auditoría Artículos");

        jLabelDescripcion.setText("Descripción:");

        jComboBoxProveedorAuditoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ibili", "Lifestyle", "Metaltex" }));
        jComboBoxProveedorAuditoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProveedorAuditoriaActionPerformed(evt);
            }
        });

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jTableArticulosAuditoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Referencia", "Código EAN", "Descripción", "Pedido Mínimo", "Precio", "Proveedor", "Precio Anterior", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneArticulosAuditoria.setViewportView(jTableArticulosAuditoria);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldDescripcionAuditoria, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxProveedorAuditoria, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBuscar)
                        .addGap(370, 370, 370))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneArticulosAuditoria)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDescripcionAuditoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxProveedorAuditoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar)
                    .addComponent(jLabelDescripcion))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneArticulosAuditoria, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxProveedorAuditoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProveedorAuditoriaActionPerformed
        listarArticulos();
    }//GEN-LAST:event_jComboBoxProveedorAuditoriaActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        listarArticulos();
        jTextFieldDescripcionAuditoria.setText("");
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void listarArticulos() {
        String nombre = jTextFieldDescripcionAuditoria.getText();
        String pro = jComboBoxProveedorAuditoria.getSelectedItem().toString();
        jTableArticulosAuditoria.setModel(AUC.listar(nombre, pro));
        jTableArticulosAuditoria.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        TableRowSorter orden = new TableRowSorter(jTableArticulosAuditoria.getModel());
        jTableArticulosAuditoria.setRowSorter(orden);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JComboBox<String> jComboBoxProveedorAuditoria;
    private javax.swing.JLabel jLabelDescripcion;
    private javax.swing.JScrollPane jScrollPaneArticulosAuditoria;
    private javax.swing.JTable jTableArticulosAuditoria;
    private javax.swing.JTextField jTextFieldDescripcionAuditoria;
    // End of variables declaration//GEN-END:variables
}
