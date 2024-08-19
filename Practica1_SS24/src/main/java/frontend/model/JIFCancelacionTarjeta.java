/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package frontend.model;

import backend.data.CancelacionTarjetaDB;
import backend.model.Bancario;
import backend.model.Cancelacion;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Cotom
 */
public class JIFCancelacionTarjeta extends javax.swing.JInternalFrame {

    private Bancario bancario;

    /**
     * Creates new form JIFCancelacionTarjeta
     */
    public JIFCancelacionTarjeta() {
        initComponents();
        this.bancario = new Bancario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        confirmacionDialog = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNumeroTarjeta = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroTarjeta = new javax.swing.JTextField();
        btnCancelacion = new javax.swing.JButton();

        confirmacionDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        confirmacionDialog.setTitle("Confirmacion para Cancelar Tarjeta");
        confirmacionDialog.setSize(new java.awt.Dimension(450, 280));

        jLabel4.setText("¿Seguro que quiere Cancelar la Tarjeta del Propietario siguiente?");

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel5.setText("Nombre del Propietario:");

        jLabel6.setText("Direccion del Propietario:");

        jLabel7.setText("Salario del Propietario:");

        lblDireccion.setText(".");

        lblNombre.setText(".");

        lblSalario.setText(".");

        jLabel8.setText("Numero Tarjeta:");

        lblNumeroTarjeta.setText(".");

        javax.swing.GroupLayout confirmacionDialogLayout = new javax.swing.GroupLayout(confirmacionDialog.getContentPane());
        confirmacionDialog.getContentPane().setLayout(confirmacionDialogLayout);
        confirmacionDialogLayout.setHorizontalGroup(
            confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmacionDialogLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnConfirmar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(54, 54, 54))
            .addGroup(confirmacionDialogLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(confirmacionDialogLayout.createSequentialGroup()
                        .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumeroTarjeta)
                            .addComponent(lblSalario)
                            .addComponent(lblNombre)
                            .addComponent(lblDireccion))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        confirmacionDialogLayout.setVerticalGroup(
            confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, confirmacionDialogLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblNumeroTarjeta))
                .addGap(18, 18, 18)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNombre))
                .addGap(18, 18, 18)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblDireccion))
                .addGap(18, 18, 18)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSalario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(confirmacionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addGap(43, 43, 43))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cancelacion de Tarjeta");

        jLabel1.setText("Numero de Tarjeta:");

        txtNumeroTarjeta.setText("0000 0000 0000 0000");

        btnCancelacion.setText("Cancelar Tarjeta");
        btnCancelacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnCancelacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCancelacion)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //---------------------------------------- METODOS DE EVENTO ----------------------------------------//
    private void btnCancelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelacionActionPerformed
        if (this.isCamposValidos()) {
            Cancelacion cancelacion = bancario.verificarCancelacionLeida(this.txtNumeroTarjeta.getText());
            if (cancelacion != null) {
                if (cancelacion.getEstadoTarjeta().toString().equals("CANCELADA")) {
                    JOptionPane.showMessageDialog(this, "No se puede Cancelar la Tarjeta porque ya esta Cancelada");
                    return;
                }
                if (!(cancelacion.getSaldoTarjeta() >= 0)) {
                    JOptionPane.showMessageDialog(this, "No se puede Cancelar la Tarjeta porque hay Saldo Pendiente");
                    return;
                }
                this.lblNumeroTarjeta.setText(cancelacion.getNumeroTarjeta());
                this.lblNombre.setText(cancelacion.getNombrePropietario());
                this.lblDireccion.setText(cancelacion.getDireccionPropietario());
                this.lblSalario.setText(cancelacion.getSalarioPropietario());
                this.confirmacionDialog.setVisible(true);
                this.confirmacionDialog.setLocationRelativeTo(this);
                this.confirmacionDialog.setResizable(false);
            }
            this.txtNumeroTarjeta.setText("");
        }
    }//GEN-LAST:event_btnCancelacionActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        CancelacionTarjetaDB cancelacion = new CancelacionTarjetaDB();
        cancelacion.actualizarEstadoTarjeta(this.lblNumeroTarjeta.getText());
        JOptionPane.showMessageDialog(this, "Tarjeta Cancelada Exitosamente");
        this.confirmacionDialog.dispose();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JOptionPane.showMessageDialog(this, "Accion Cancelada");
        this.confirmacionDialog.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que evalua cada campo del formulario para verificar que esten
     * completos y de ser asi verificar que sean datos correctos
     *
     * @return verdadero si los campos del formulario son validos, de los
     * contrario retorna falso
     */
    private boolean isCamposValidos() {
        if (this.txtNumeroTarjeta.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debe Completar el Campo del Formulario", "Error!!!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!this.bancario.isNumeroTarjetaValido(this.txtNumeroTarjeta.getText())) {
            JOptionPane.showMessageDialog(this, "El Numero de Tarjeta ingresado NO es Valido", "Error!!!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!this.bancario.isNumeroTarjetaRepetida(this.txtNumeroTarjeta.getText())) {
            JOptionPane.showMessageDialog(this, "El Numero de Tarjeta NO esta Registrada en el Sistema", "Error!!!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelacion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JDialog confirmacionDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumeroTarjeta;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JTextField txtNumeroTarjeta;
    // End of variables declaration//GEN-END:variables
}
