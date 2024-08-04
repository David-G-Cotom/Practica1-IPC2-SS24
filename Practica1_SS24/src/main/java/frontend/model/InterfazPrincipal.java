/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend.model;

/**
 *
 * @author Carlos Cotom
 */
public class InterfazPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form InterfazPrincipal
     */
    public InterfazPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuRegistrar = new javax.swing.JMenu();
        itmenuSolicitudTarjeta = new javax.swing.JMenuItem();
        itmenuMovimientoTarjeta = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        itmenuConsultarTarjeta = new javax.swing.JMenuItem();
        itmenuEstadoCuenta = new javax.swing.JMenuItem();
        itmenuListadoTarjetas = new javax.swing.JMenuItem();
        itmenuListadoSolicitudes = new javax.swing.JMenuItem();
        menuOtros = new javax.swing.JMenu();
        itmenuAutorizarTarjeta = new javax.swing.JMenuItem();
        itmenuCancelarTarjeta = new javax.swing.JMenuItem();
        menuImportarDatos = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Bancario");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 988, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );

        menuRegistrar.setText("Registrar");

        itmenuSolicitudTarjeta.setText("Solicitud de Tarjeta");
        menuRegistrar.add(itmenuSolicitudTarjeta);

        itmenuMovimientoTarjeta.setText("Movimiento de Tarjeta");
        menuRegistrar.add(itmenuMovimientoTarjeta);

        jMenuBar1.add(menuRegistrar);

        menuConsultas.setText("Consultar");

        itmenuConsultarTarjeta.setText("Tarjeta");
        menuConsultas.add(itmenuConsultarTarjeta);

        itmenuEstadoCuenta.setText("Estado de Cuenta");
        menuConsultas.add(itmenuEstadoCuenta);

        itmenuListadoTarjetas.setText("Listado de Tarjetas");
        menuConsultas.add(itmenuListadoTarjetas);

        itmenuListadoSolicitudes.setText("Listado de Solicitudes");
        menuConsultas.add(itmenuListadoSolicitudes);

        jMenuBar1.add(menuConsultas);

        menuOtros.setText("Otros");

        itmenuAutorizarTarjeta.setText("Autorizar Tarjeta");
        menuOtros.add(itmenuAutorizarTarjeta);

        itmenuCancelarTarjeta.setText("Cancelar Tarjeta");
        menuOtros.add(itmenuCancelarTarjeta);

        jMenuBar1.add(menuOtros);

        menuImportarDatos.setText("Importar Datos");
        jMenuBar1.add(menuImportarDatos);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itmenuAutorizarTarjeta;
    private javax.swing.JMenuItem itmenuCancelarTarjeta;
    private javax.swing.JMenuItem itmenuConsultarTarjeta;
    private javax.swing.JMenuItem itmenuEstadoCuenta;
    private javax.swing.JMenuItem itmenuListadoSolicitudes;
    private javax.swing.JMenuItem itmenuListadoTarjetas;
    private javax.swing.JMenuItem itmenuMovimientoTarjeta;
    private javax.swing.JMenuItem itmenuSolicitudTarjeta;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuImportarDatos;
    private javax.swing.JMenu menuOtros;
    private javax.swing.JMenu menuRegistrar;
    // End of variables declaration//GEN-END:variables
}
