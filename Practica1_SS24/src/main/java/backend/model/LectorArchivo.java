/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import frontend.model.JIFIngresoArchivo;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Carlos Cotom
 */
public class LectorArchivo extends Thread {
    
    private File archivoEntrada;
    private JIFIngresoArchivo ingresoArchivoFront;
    private JLabel label;
    private Bancario bancario;
    private Tarjeta tarjeta;
    private ArrayList<Tarjeta> tarjetas;
    private Cliente cliente;
    private ArrayList<Cliente> clientes;
    private SolicitudTarjeta solicitud;
    private ArrayList<SolicitudTarjeta> solicitudes;
    private MovimientoTarjeta movimiento;
    private ArrayList<MovimientoTarjeta> movimientos;
    private ArrayList<String> consultas;
    private ArrayList<Integer> autorizaciones;
    private ArrayList<String> cancelaciones;
    
    @Override
    public void run(){
        
    }
    
    public void nuevoRegistroLeido(){
        
    }
    
}
