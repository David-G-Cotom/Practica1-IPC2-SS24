/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import frontend.model.JIFIngresoArchivo;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Cotom
 */
public class LectorArchivo extends Thread {
    
    private final File archivoEntrada;
    private JIFIngresoArchivo ingresoArchivoFront;
    private JLabel carga;
    private JLabel descripcionProceso;
    private Bancario bancario;
    private Tarjeta tarjeta;
    private ArrayList<Tarjeta> tarjetas;
    private Cliente cliente;
    private ArrayList<Cliente> clientes;
    private SolicitudTarjeta solicitud;
    private ArrayList<SolicitudTarjeta> solicitudes;
    private MovimientoTarjeta movimiento;
    private ArrayList<MovimientoTarjeta> movimientos;
    private ArrayList<String> consultas = new ArrayList<>();
    private ArrayList<Integer> autorizaciones = new ArrayList<>();
    private ArrayList<String> cancelaciones = new ArrayList<>();
    private final int velocidadPorcesamiento;
    private FiltroEstadoCuenta filtroEstadoCuenta;
    private FiltroListadoSolicitudes filtroListadoSolicitudes;
    private FiltroListadoTarjetas filtroListadoTarjetas;
    
    public LectorArchivo(File archivoEntrada, JIFIngresoArchivo ingresoArchivoFront, JLabel carga, JLabel descripcionProceso, int velocidadProcesamiento) {
        this.archivoEntrada = archivoEntrada;
        this.ingresoArchivoFront = ingresoArchivoFront;
        this.carga = carga;
        this.descripcionProceso = descripcionProceso;
        this.velocidadPorcesamiento = velocidadProcesamiento;
    }        
    
    @Override
    public void run(){
        int numeroLineas = this.contarLineasArchivo();
        BarraCarga barraCarga = new BarraCarga(this.carga, true, this.velocidadPorcesamiento * numeroLineas);
        barraCarga.start();
        try (BufferedReader lector = new BufferedReader(new FileReader(this.archivoEntrada))) {
            String lineaLeida = lector.readLine();
            while (lineaLeida != null) {
                if (!lineaLeida.isBlank()) {
                    String[] cadenaLeida = leerIntruccion(lineaLeida);
                    String[] datosRecolectados = cadenaLeida[1].split(",");
                    switch (cadenaLeida[0]) {
                        case "SOLICITUD":                            
                            if (datosRecolectados.length == 6) {
                                this.solicitud = new SolicitudTarjeta(Integer.parseInt(datosRecolectados[0]),
                                        datosRecolectados[1], datosRecolectados[2], datosRecolectados[3], Double.parseDouble(datosRecolectados[4]), datosRecolectados[5]);
                                this.descripcionProceso.setText("Procesando la Solictud de Tarjeta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Solicitud de Tarjeta!!!");
                            }
                            break;                        
                        case "MOVIMIENTO":
                            if (datosRecolectados.length == 6) {
                                this.movimiento = new MovimientoTarjeta(datosRecolectados[0], datosRecolectados[1],
                                        datosRecolectados[2], datosRecolectados[3], datosRecolectados[4], Double.parseDouble(datosRecolectados[5]));
                                this.descripcionProceso.setText("Procesando el Movimiento de Tarjeta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para el MOvimiento en Tarjeta!!!");
                            }
                            break;
                        case "CONSULTAR_TARJETA":
                            if (datosRecolectados.length == 1) {
                                this.consultas.add(datosRecolectados[0]);
                                this.descripcionProceso.setText("Procesando la Consulta de Tarjeta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Consulta de Tarjeta!!!");
                            }
                            break;
                        case "AUTORIZACION_TARJETA":
                            if (datosRecolectados.length == 1) {
                                this.autorizaciones.add(Integer.valueOf(datosRecolectados[0]));
                                this.descripcionProceso.setText("Procesando la Autorizacion de Tarjeta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Autorizacion de Tarjeta!!!");
                            }
                            break;
                        case "CANCELACION_TARJETA":
                            if (datosRecolectados.length == 1) {
                                this.cancelaciones.add(datosRecolectados[0]);
                                this.descripcionProceso.setText("Procesando la Cancelacion de Tarjeta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!");
                            }
                            break;
                        case "ESTADO_CUENTA":
                            if (datosRecolectados.length == 4) {
                                this.filtroEstadoCuenta = new FiltroEstadoCuenta(datosRecolectados[0], datosRecolectados[1],
                                        Double.parseDouble(datosRecolectados[2]), Double.parseDouble(datosRecolectados[3]));
                                this.descripcionProceso.setText("Procesando el Reporte para el Estado de Cuenta");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!");
                            }
                            break;
                        case "LISTADO_TARJETAS":
                            if (datosRecolectados.length == 5) {
                                this.filtroListadoTarjetas = new FiltroListadoTarjetas(datosRecolectados[0], Double.parseDouble(datosRecolectados[1]),
                                        datosRecolectados[2], datosRecolectados[3], datosRecolectados[4]);
                                this.descripcionProceso.setText("Procesando el Reporte para el Listado de Tarjetas");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!");
                            }
                            break;
                        case "LISTADO_SOLICITUDES":
                            if (datosRecolectados.length == 5) {
                                this.filtroListadoSolicitudes = new FiltroListadoSolicitudes(datosRecolectados[0], datosRecolectados[1],
                                        datosRecolectados[2], Integer.parseInt(datosRecolectados[3]), datosRecolectados[4]);
                                this.descripcionProceso.setText("Procesando el Reporte para el Listado de Solicitudes");
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!");
                            }
                            break;
                        default:
                            System.out.println("Error en la Lectura de Archivo: Intruccion invalida!!!");
                    }
                }
                lineaLeida = lector.readLine();
                Thread.sleep(this.velocidadPorcesamiento);
            }
            barraCarga.setExecute(false);
            this.ingresoArchivoFront.mostrarMensaje();
            this.ingresoArchivoFront.setClosed(true);            
        } catch (FileNotFoundException e) {
        } catch (IOException ex) {            
        } catch (InterruptedException ex) {
        } catch (PropertyVetoException ex) {
        }
        System.out.println("Hilo del Lector Finalizado");
    }
    
    private String[] leerIntruccion(String lineaLeida) {
        String[] resultadoLectura = new String[2];
        String[] cadena = lineaLeida.split("");
        String accion = "";
        String datos = "";
        boolean dentroParentesis = false;
        for (int i = 0; i < cadena.length; i++) {
            if (!cadena[i].equals(" ")) {
                if (cadena[i].equals("(")) {
                dentroParentesis = true;
                continue;
                }
                if (!dentroParentesis) {
                    accion+=cadena[i];                
                } else {
                    if (cadena[i].equals(")")) {
                        break;
                    }
                    datos+=cadena[i];
                }
            }            
        }
        resultadoLectura[0] = accion;
        resultadoLectura[1] = datos;
        return resultadoLectura;
    }
    
    private int contarLineasArchivo() {
        int numeroLineas = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(this.archivoEntrada))) {
            String lineaLeida = lector.readLine();
            while (lineaLeida != null) {
                numeroLineas++;
                lineaLeida = lector.readLine();
            }
        } catch (Exception e) {
        }
        return numeroLineas;
    }
    
    public void nuevoRegistroLeido(){
        
    }
    
}
