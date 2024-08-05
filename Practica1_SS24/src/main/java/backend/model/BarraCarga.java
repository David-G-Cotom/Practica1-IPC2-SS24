/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import javax.swing.JLabel;

/**
 *
 * @author Carlos Cotom
 */
public class BarraCarga extends Thread {

    private JLabel lblCarga;
    private boolean execute;
    private int tiempoEjecucion;

    public BarraCarga(JLabel lblCarga, boolean execute, int tiempoEjecucion) {
        this.lblCarga = lblCarga;
        this.execute = execute;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public JLabel getLblCarga() {
        return lblCarga;
    }

    public void setLblCarga(JLabel lblCarga) {
        this.lblCarga = lblCarga;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    @Override
    public void run() {
        try {
            String textoCarga = "";
            int contador = 0;
            while (execute) {
                contador++;
                textoCarga = textoCarga + (contador + "") + "%";
                this.lblCarga.setText(textoCarga);
                textoCarga = "";
                Thread.sleep(this.tiempoEjecucion / 100);
                if (contador == 100) {
                    contador = 0;
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Error con el Thread de la barra");
        }
        System.out.println("Hilo de la Barra de Carga Finalizado");
    }

}
