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
import javax.swing.JLabel;

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
    private SolicitudTarjeta solicitud;
    private MovimientoTarjeta movimiento;
    private final int velocidadPorcesamiento;
    private FiltroEstadoCuenta filtroEstadoCuenta;
    private FiltroListadoSolicitudes filtroListadoSolicitudes;
    private FiltroListadoTarjetas filtroListadoTarjetas;
    private BarraCarga barraCarga;
    private boolean suspended;
    
    public LectorArchivo(File archivoEntrada, JIFIngresoArchivo ingresoArchivoFront, JLabel carga, JLabel descripcionProceso, int velocidadProcesamiento) {
        this.archivoEntrada = archivoEntrada;
        this.ingresoArchivoFront = ingresoArchivoFront;
        this.carga = carga;
        this.descripcionProceso = descripcionProceso;
        this.velocidadPorcesamiento = velocidadProcesamiento;
        this.bancario = new Bancario();
        this.bancario.setPathCarpeta(this.ingresoArchivoFront.getPathCarpeta());
    }        

    public BarraCarga getBarraCarga() {
        return barraCarga;
    }

    public void setBarraCarga(BarraCarga barraCarga) {
        this.barraCarga = barraCarga;
    }         
    
    @Override
    public void run(){
        int numeroLineas = this.contarLineasArchivo();
        barraCarga = new BarraCarga(this.carga, true, this.velocidadPorcesamiento * numeroLineas);
        barraCarga.start();
        try (BufferedReader lector = new BufferedReader(new FileReader(this.archivoEntrada))) {
            String lineaLeida = lector.readLine();
            while (lineaLeida != null) {
                this.enSuspencion();
                if (!lineaLeida.isBlank()) {
                    String[] cadenaLeida = leerIntruccion(lineaLeida);
                    String[] datosRecolectados = cadenaLeida[1].split(",");
                    switch (cadenaLeida[0]) {
                        case "SOLICITUD":                            
                            this.descripcionProceso.setText("Procesando la Solictud de Tarjeta");
                            if (datosRecolectados.length == 6) {
                                if (this.bancario.isInteger(datosRecolectados[0]) && this.bancario.isDouble(datosRecolectados[4])) {
                                    this.solicitud = new SolicitudTarjeta(Integer.parseInt(datosRecolectados[0]),
                                            datosRecolectados[1], datosRecolectados[2], datosRecolectados[3], Double.parseDouble(datosRecolectados[4]), datosRecolectados[5]);
                                    this.bancario.verificarSolicitudLeida(solicitud);
                                }                               
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Solicitud de Tarjeta!!!\n");
                            }
                            break;                        
                        case "MOVIMIENTO":
                            this.descripcionProceso.setText("Procesando el Movimiento de Tarjeta");
                            if (datosRecolectados.length == 6) {
                                String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
                                if (this.bancario.isDouble(datosRecolectados[5])) {
                                    this.movimiento = new MovimientoTarjeta(numeroTarjeta, datosRecolectados[1],
                                            datosRecolectados[2], datosRecolectados[3], datosRecolectados[4], Double.parseDouble(datosRecolectados[5]));
                                    this.bancario.verificarMovimientoLeido(movimiento);                                    
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para el MOvimiento en Tarjeta!!!\n");
                            }
                            break;
                        case "CONSULTAR_TARJETA":
                            this.descripcionProceso.setText("Procesando la Consulta de Tarjeta");
                            if (datosRecolectados.length == 1) {
                                String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
                                if (this.bancario.isNumeroTarjetaValido(numeroTarjeta)) {                                    
                                    System.out.println("Consulta de Tarjeta Valida para su Ejecucion");
                                    Consulta consulta = this.bancario.verificarConsultaTarjeta(numeroTarjeta);
                                    if (consulta != null) {
                                        consulta.setPathCarpeta(this.ingresoArchivoFront.getPathCarpeta());
                                        consulta.exportarReportes();                                        
                                    }
                                } else {
                                    System.out.println("Numero de Tarjeta Invalido para Hacer la Consulta de Tarjeta");
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Consulta de Tarjeta!!!\n");
                            }
                            break;
                        case "AUTORIZACION_TARJETA":
                            this.descripcionProceso.setText("Procesando la Autorizacion de Tarjeta");
                            if (datosRecolectados.length == 1) {
                                if (this.bancario.isInteger(datosRecolectados[0])) {
                                    System.out.println("Autorizacion de Tarjeta Valida para su Ejecucion");
                                    this.bancario.verificarAutorizacionLeida(Integer.parseInt(datosRecolectados[0]));                                  
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Autorizacion de Tarjeta!!!\n");
                            }
                            break;
                        case "CANCELACION_TARJETA":
                            this.descripcionProceso.setText("Procesando la Cancelacion de Tarjeta");
                            if (datosRecolectados.length == 1) {
                                String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
                                if (this.bancario.isNumeroTarjetaValido(numeroTarjeta)) {
                                    System.out.println("Cancelacion de Tarjeta Valida para Ejecutarse");                                    
                                    Cancelacion cancelacion = this.bancario.verificarCancelacionLeida(numeroTarjeta);
                                    if (cancelacion != null) {
                                        if (cancelacion.getEstadoTarjeta()) {
                                            if (cancelacion.getSaldoTarjeta() >= 0) {
                                                barraCarga.suspender();
                                                this.suspender();
                                                this.ingresoArchivoFront.getLblNumeroTarjeta().setText(cancelacion.getNumeroTarjeta());
                                                this.ingresoArchivoFront.getLblNombre().setText(cancelacion.getNombrePropietario());
                                                this.ingresoArchivoFront.getLblDireccion().setText(cancelacion.getDireccionPropietario());
                                                this.ingresoArchivoFront.getLblSalario().setText(cancelacion.getSalarioPropietario());
                                                this.ingresoArchivoFront.getConfirmacionDialog().setVisible(true);
                                                this.ingresoArchivoFront.getConfirmacionDialog().setLocationRelativeTo(this.ingresoArchivoFront);
                                                this.ingresoArchivoFront.getConfirmacionDialog().setResizable(false);
                                            } else {
                                                System.out.println("No se puede Cancelar la Tarjeta porque hay Saldo Pendiente");
                                            }                                            
                                        } else {
                                            System.out.println("La Tarjeta ya se encontraba Cancelada");
                                        }                                        
                                    }
                                } else {
                                    System.out.println("Numero de Tarjeta Invalido para Hacer la Cancelacion de Tarjeta");
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!\n");
                            }
                            break;
                        case "ESTADO_CUENTA":
                            this.descripcionProceso.setText("Procesando el Reporte para el Estado de Cuenta");
                            if (datosRecolectados.length == 4) {
                                if (this.bancario.isDouble(datosRecolectados[2]) && this.bancario.isDouble(datosRecolectados[3])) {
                                    this.filtroEstadoCuenta = new FiltroEstadoCuenta(datosRecolectados[0], datosRecolectados[1],
                                            Double.parseDouble(datosRecolectados[2]), Double.parseDouble(datosRecolectados[3]));
                                    this.bancario.verificarFiltroEstadoCuenta(filtroEstadoCuenta);                                    
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!\n");
                            }
                            break;
                        case "LISTADO_TARJETAS":
                            this.descripcionProceso.setText("Procesando el Reporte para el Listado de Tarjetas");
                            if (datosRecolectados.length == 5) {
                                if (this.bancario.isDouble(datosRecolectados[1])) {
                                    this.filtroListadoTarjetas = new FiltroListadoTarjetas(datosRecolectados[0], Double.parseDouble(datosRecolectados[1]),
                                            datosRecolectados[2], datosRecolectados[3], datosRecolectados[4]);
                                    this.bancario.verificarFiltroListadoTarjetas(filtroListadoTarjetas);                                    
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!\n");
                            }
                            break;
                        case "LISTADO_SOLICITUDES":
                            this.descripcionProceso.setText("Procesando el Reporte para el Listado de Solicitudes");
                            if (datosRecolectados.length == 5) {
                                if (this.bancario.isDouble(datosRecolectados[3])) {
                                    this.filtroListadoSolicitudes = new FiltroListadoSolicitudes(datosRecolectados[0], datosRecolectados[1],
                                            datosRecolectados[2], Double.parseDouble(datosRecolectados[3]), datosRecolectados[4]);
                                    this.bancario.verificarFiltroListadoSolicitudes(filtroListadoSolicitudes);                                    
                                }
                            } else {
                                System.out.println("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta!!!\n");
                            }
                            break;
                        default:
                            this.descripcionProceso.setText("");
                            System.out.println("Error en la Lectura de Archivo: Intruccion invalida!!!\n");
                    }
                }
                lineaLeida = lector.readLine();
                Thread.sleep(this.velocidadPorcesamiento);
            }
            barraCarga.setExecute(false);
            this.ingresoArchivoFront.mostrarMensaje("Archivo Leido Exitosamente!!!");
            this.ingresoArchivoFront.setClosed(true);            
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de Texto no Encontrado para Lectura");
        } catch (IOException ex) {            
        } catch (InterruptedException exc) {
            System.out.println("Error en el Thread para slepear la laectura de instruccion");
        } catch (PropertyVetoException exce) {
            System.out.println("Error al cerrar automaticamente el Front de Ingreso de Archivo");
        }
        System.out.println("Hilo del Lector Finalizado\n");
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
    
    public synchronized void suspender() {
        System.out.println("Hilo de Lector de Carga Pausada");
        this.suspended = true;
    }
    
    public synchronized void reanudar() {
        System.out.println("Hilo de Lector de Carga Reanudada");
        this.suspended = false;
        notifyAll();
    }
    
    public synchronized void enSuspencion() {
        while (this.suspended) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Error al Pausar el Hilo del Lector");
            }
        }
    }
    
}
