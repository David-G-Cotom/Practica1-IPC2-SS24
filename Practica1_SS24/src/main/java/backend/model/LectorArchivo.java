/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.TipoMovimientos;
import backend.enums.TipoTarjetas;
import backend.data.AutorizacionTarjetaDB;
import backend.data.CancelacionTarjetaDB;
import backend.data.EstadoCuentaDB;
import backend.data.ListadoSolicitudesDB;
import backend.data.ListadoTarjetasDB;
import backend.enums.EstadosSolicitud;
import backend.enums.EstadosTarjeta;
import frontend.model.JIFIngresoArchivo;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Carlos Cotom
 */
public class LectorArchivo extends Thread {

    private final File archivoEntrada;
    private JIFIngresoArchivo ingresoArchivoFront;
    private JLabel descripcion;
    private JLabel proceso;
    private Bancario bancario;
    private final int velocidadPorcesamiento;

    public LectorArchivo(File archivoEntrada, JIFIngresoArchivo ingresoArchivoFront, JLabel descripcion, JLabel proceso, int velocidadProcesamiento) {
        this.archivoEntrada = archivoEntrada;
        this.ingresoArchivoFront = ingresoArchivoFront;
        this.descripcion = descripcion;
        this.proceso = proceso;
        this.velocidadPorcesamiento = velocidadProcesamiento;
        this.bancario = new Bancario();
        this.bancario.setPathCarpeta(this.ingresoArchivoFront.getPathCarpeta());
    }

    @Override
    public void run() {
        try (BufferedReader lector = new BufferedReader(new FileReader(this.archivoEntrada))) {
            String lineaLeida = lector.readLine();
            while (lineaLeida != null) {
                if (!lineaLeida.isBlank()) {
                    String[] cadenaLeida = leerIntruccion(lineaLeida);
                    String[] datosRecolectados = obtenerDatos(cadenaLeida[1]);
                    switch (cadenaLeida[0]) {
                        case "SOLICITUD":
                            ejecutarSolicitud(datosRecolectados);
                            break;
                        case "MOVIMIENTO":
                            ejecutarMovimiento(datosRecolectados);
                            break;
                        case "CONSULTAR_TARJETA":
                            ejecutarConsulta(datosRecolectados);
                            break;
                        case "AUTORIZACION_TARJETA":
                            ejecutarAutorizacion(datosRecolectados);
                            break;
                        case "CANCELACION_TARJETA":
                            ejecutarCancelacion(datosRecolectados);
                            break;
                        case "ESTADO_CUENTA":
                            ejecutarEstadoCuenta(datosRecolectados);
                            break;
                        case "LISTADO_TARJETAS":
                            ejecutarListadoTarjetas(datosRecolectados);
                            break;
                        case "LISTADO_SOLICITUDES":
                            ejecutarListadoSolicitudes(datosRecolectados);
                            break;
                        default:
                            this.proceso.setText("");
                            this.descripcion.setText("Error en la Lectura de Archivo: Intruccion invalida");
                    }
                }
                lineaLeida = lector.readLine();
                Thread.sleep(this.velocidadPorcesamiento);
                this.descripcion.setText("");
            }
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
    }

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que recibe la linea leida del archivo de texto y la separa en dos
     * para poder obtener la instruccion a ejecutar y sus valores
     *
     * @param lineaLeida es la linea que se acaba de leer del archivo de texto
     * @return un Arreglo de dos posiciones en donde la primer posicion esta la
     * instruccion y en la segunda posicion estan los valores de la misma
     */
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
                    accion += cadena[i];
                } else {
                    if (cadena[i].equals(")")) {
                        break;
                    }
                    datos += cadena[i];
                }
            }
        }
        resultadoLectura[0] = accion;
        resultadoLectura[1] = datos;
        return resultadoLectura;
    }

    /**
     * Metodo que recibe los datos de la linea leida en el archivo de texto, en
     * donde los separa para tener una mejor manipulacion
     *
     * @param datosAgrupados son los datos pero de forma en que estan juntos
     * @return un Arreglo con los datos de forma separada
     */
    private String[] obtenerDatos(String datosAgrupados) {
        String[] datos = datosAgrupados.split("");
        ArrayList<String> datosArray = new ArrayList<>();
        String dato = "";
        for (int i = 0; i < datos.length; i++) {
            if (!datos[i].equals(",")) {
                dato += datos[i];
            } else {
                datosArray.add(dato);
                dato = "";
            }
            if (i == datos.length - 1) {
                datosArray.add(dato);
            }
        }
        String[] datosIndividuales = new String[datosArray.size()];
        for (int i = 0; i < datosArray.size(); i++) {
            datosIndividuales[i] = datosArray.get(i);
        }
        return datosIndividuales;
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la Solicitud para Tarjeta
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Solicitud de la Tarjeta
     */
    private void ejecutarSolicitud(String[] datosRecolectados) {
        this.proceso.setText("Procesando la Solictud de Tarjeta");
        if (datosRecolectados.length == 6) {
            if (!this.bancario.isInteger(datosRecolectados[0])) {
                this.descripcion.setText("Error en Lectura de Solicitud: Texto ingresado NO es Numero Entero Positivo");
                return;
            }
            if (!this.bancario.isDouble(datosRecolectados[4])) {
                this.descripcion.setText("Error en Lectura de Solicitud: Texto ingresado NO es Numero Decimal Positivo");
                return;
            }
            if (!this.bancario.isTipoTarjetaValido(datosRecolectados[2])) {
                this.descripcion.setText("Error en Lectura de Solicitud: Texto ingresado NO valido para Tipo de Tarjeta");
                return;
            }
            SolicitudTarjeta solicitud = new SolicitudTarjeta(Integer.parseInt(datosRecolectados[0]), datosRecolectados[1],
                    TipoTarjetas.valueOf(datosRecolectados[2]), datosRecolectados[3], Double.parseDouble(datosRecolectados[4]), datosRecolectados[5]);
            if (this.bancario.verificarSolicitudLeida(solicitud)) {
                this.descripcion.setText("Solicitud de Tarjeta Valida para su Ejecucion");
            } else {
                this.descripcion.setText("Solicitud de Tarjeta NO Valida para su Ejecucion");
            }
        } else {
            this.descripcion.setText("Error en Lectura de Archivo: Cantidad de datos invalidos para la Solicitud de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua el Movimiento de Tarjeta
     *
     * @param datosRecolectados son los datos que serviran para ejecutar el
     * Movimiento de la Tarjeta
     */
    private void ejecutarMovimiento(String[] datosRecolectados) {
        this.proceso.setText("Procesando el Movimiento de Tarjeta");
        if (datosRecolectados.length == 6) {
            String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
            if (!this.bancario.isDouble(datosRecolectados[5])) {
                this.descripcion.setText("Error en Lectura de Movimientos: Texto ingresado NO es Numero Decimal Positivo");
                return;
            }
            if (!this.bancario.isTipoMovimientoValido(datosRecolectados[2])) {
                this.descripcion.setText("Error en Lectura de Movimientos: Texto ingresado NO valido para Tipo de Movimiento");
                return;
            }
            MovimientoTarjeta movimiento = new MovimientoTarjeta(numeroTarjeta, datosRecolectados[1],
                    TipoMovimientos.valueOf(datosRecolectados[2]), datosRecolectados[3], datosRecolectados[4], Double.parseDouble(datosRecolectados[5]));
            if (this.bancario.verificarMovimientoLeido(movimiento)) {
                this.descripcion.setText("Movimiento de Tarjeta Valida para su Ejecucion");
            } else {
                this.descripcion.setText("Movimiento de Tarjeta NO Valida para su Ejecucion");
            }
        } else {
            this.descripcion.setText("Error en Lectura de Archivo: Cantidad de datos invalidos para el Movimiento de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la Consulta de Tarjeta
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Consulta de la Tarjeta
     */
    private void ejecutarConsulta(String[] datosRecolectados) {
        this.proceso.setText("Procesando la Consulta de Tarjeta");
        if (datosRecolectados.length == 1) {
            String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
            if (!this.bancario.isNumeroTarjetaValido(numeroTarjeta)) {
                this.descripcion.setText("Numero de Tarjeta NO Valido para Hacer la Consulta de Tarjeta");
                return;
            }
            this.descripcion.setText("Consulta de Tarjeta Valida para su Ejecucion");
            Consulta consulta = this.bancario.verificarConsultaTarjeta(numeroTarjeta);
            if (consulta != null) {
                consulta.setPathCarpeta(this.ingresoArchivoFront.getPathCarpeta());
                consulta.exportarReportes();
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Consulta de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la Autorizacion de una Tarjeta
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Autorizacion de la Tarjeta
     */
    private void ejecutarAutorizacion(String[] datosRecolectados) {
        this.proceso.setText("Procesando la Autorizacion de Tarjeta");
        if (datosRecolectados.length == 1) {
            if (!this.bancario.isInteger(datosRecolectados[0])) {
                this.descripcion.setText("Numero de Solicitud NO Valido");
                return;
            }
            this.descripcion.setText("Autorizacion de Tarjeta Valida para su Ejecucion");
            if (this.bancario.verificarAutorizacionLeida(Integer.parseInt(datosRecolectados[0]))) {
                AutorizacionTarjetaDB data = new AutorizacionTarjetaDB();
                String numeroTarjeta = data.getNumeroTarjeta(Integer.parseInt(datosRecolectados[0]));
                this.descripcion.setText("Se Autorizo la Tarjeta con Numero " + numeroTarjeta + " para la Solicitud " + Integer.valueOf(datosRecolectados[0]));
            } else {
                this.descripcion.setText("No se pudo realizar la Autorizacion");
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Autorizacion de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la Cancelacion de una Tarjeta
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Cancelacion de la Tarjeta
     */
    private void ejecutarCancelacion(String[] datosRecolectados) {
        this.proceso.setText("Procesando la Cancelacion de Tarjeta");
        if (datosRecolectados.length == 1) {
            String numeroTarjeta = this.bancario.transformarNumeroTarjeta(datosRecolectados[0]);
            if (!this.bancario.isNumeroTarjetaValido(numeroTarjeta)) {
                this.descripcion.setText("Numero de Tarjeta NO Valido");
                return;
            }
            this.descripcion.setText("Cancelacion de Tarjeta Valida para Ejecutarse");
            Cancelacion cancelacion = this.bancario.verificarCancelacionLeida(numeroTarjeta);
            if (cancelacion != null) {
                if (cancelacion.getEstadoTarjeta().toString().equals("CANCELADA")) {
                    this.descripcion.setText("La Tarjeta NO se puede Cancelar porque ya Estaba Cancelada");
                    return;
                }
                if (!(cancelacion.getSaldoTarjeta() >= 0)) {
                    this.descripcion.setText("La Tarjeta NO se puede Cancelar porque Hay Saldo Pendiente");
                    return;
                }
                CancelacionTarjetaDB cancelacionDB = new CancelacionTarjetaDB();
                cancelacionDB.actualizarEstadoTarjeta(numeroTarjeta);
                this.descripcion.setText("Tarjeta Cancelada Exitosamente");
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la creacion de Reporte para el Estado de
     * Cuenta para Tarjetas
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Creacion del Reporte para el Estado de Cuenta de Tarjetas
     */
    private void ejecutarEstadoCuenta(String[] datosRecolectados) {
        this.proceso.setText("Procesando el Reporte para el Estado de Cuenta");
        if (datosRecolectados.length == 4) {
            double saldoMinimo;
            double interesMinimo;
            FiltroEstadoCuenta filtroEstadoCuenta;
            if (this.bancario.isDouble(datosRecolectados[2])) {
                saldoMinimo = Double.parseDouble(datosRecolectados[2]);
            } else {
                saldoMinimo = -1;
            }
            if (this.bancario.isDouble(datosRecolectados[3])) {
                interesMinimo = Double.parseDouble(datosRecolectados[3]);
            } else {
                interesMinimo = -1;
            }
            if (this.bancario.isTipoTarjetaValido(datosRecolectados[1])) {
                filtroEstadoCuenta = new FiltroEstadoCuenta(datosRecolectados[0], TipoTarjetas.valueOf(datosRecolectados[1]), saldoMinimo, interesMinimo);
            } else {
                filtroEstadoCuenta = new FiltroEstadoCuenta(datosRecolectados[0], saldoMinimo, interesMinimo);
            }
            if (this.bancario.verificarFiltroEstadoCuenta(filtroEstadoCuenta)) {
                this.descripcion.setText("Filtro para Estado de Cuenta Valido para su Ejecucion");
                String restoQuery = filtroEstadoCuenta.filtrarDatos();
                EstadoCuentaDB estadoCuenta = new EstadoCuentaDB();
                ArrayList<EstadoCuenta> datos = estadoCuenta.getEstadosCuenta(restoQuery);
                if (datos.isEmpty()) {
                    this.descripcion.setText("No se pudo crear Archivo porque No hay Datos por Mostrar");
                    return;
                }
                filtroEstadoCuenta.setDatosEstadosCuenta(datos);
                filtroEstadoCuenta.exportarReportes(this.ingresoArchivoFront.getPathCarpeta());
            } else {
                this.descripcion.setText("Filtro para Estado de Cuenta NO Valido para su Ejecucion");
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para Consultar el Estado de Cuenta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la creacion del Reporte para el Listado de
     * Tarjetas
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * Creacion del Reporte para el Listado de Tarjetas
     */
    private void ejecutarListadoTarjetas(String[] datosRecolectados) {
        this.proceso.setText("Procesando el Reporte para el Listado de Tarjetas");
        if (datosRecolectados.length == 5) {
            double montoLimite;
            ListadoTarjetas filtroListadoTarjetas;
            if (this.bancario.isDouble(datosRecolectados[1])) {
                montoLimite = Double.parseDouble(datosRecolectados[1]);
            } else {
                montoLimite = -1;
            }
            filtroListadoTarjetas = new ListadoTarjetas(montoLimite, datosRecolectados[2], datosRecolectados[3]);
            if (this.bancario.isTipoTarjetaValido(datosRecolectados[0])) {
                filtroListadoTarjetas.setTipoTarjeta(TipoTarjetas.valueOf(datosRecolectados[0]));
                filtroListadoTarjetas.setHayTipoTarjeta(true);
            } else {
                filtroListadoTarjetas.setHayTipoTarjeta(false);
            }
            if (this.bancario.isEstadoTarjetaValido(datosRecolectados[4])) {
                filtroListadoTarjetas.setEstadoTarjeta(EstadosTarjeta.valueOf(datosRecolectados[4]));
                filtroListadoTarjetas.setHayEstadoTarjeta(true);
            } else {
                filtroListadoTarjetas.setHayEstadoTarjeta(false);
            }
            if (this.bancario.verificarFiltroListadoTarjetas(filtroListadoTarjetas)) {
                this.descripcion.setText("Filtro de Listado de Tarjetas Valido para su Ejecucion");
                String restoQuery = filtroListadoTarjetas.filtrarDatos();
                ListadoTarjetasDB listadoTarjetas = new ListadoTarjetasDB();
                ArrayList<ListadoTarjetas> datos = listadoTarjetas.getListadoTarjetas(restoQuery);
                if (datos.isEmpty()) {
                    this.descripcion.setText("No se pudo crear Archivo porque No hay Datos por Mostrar");
                    return;
                }
                filtroListadoTarjetas.setDatosTarjetas(datos);
                filtroListadoTarjetas.exportarReportes(this.ingresoArchivoFront.getPathCarpeta());
            } else {
                this.descripcion.setText("Filtro de Listado de Tarjetas NO Valido para su Ejecucion");
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta");
        }
    }

    /**
     * Metodo que verifica que los datos recibidos como parametro sean
     * correctos, de ser asi efectua la creacion del Reporte para el Listado de
     * Solicitudes
     *
     * @param datosRecolectados son los datos que serviran para ejecutar la
     * creacion del Reporte para el Listado de Solicitudes
     */
    private void ejecutarListadoSolicitudes(String[] datosRecolectados) {
        this.proceso.setText("Procesando el Reporte para el Listado de Solicitudes");
        if (datosRecolectados.length == 5) {
            double montoLimite;
            ListadoSolicitudes filtroListadoSolicitudes;
            if (this.bancario.isDouble(datosRecolectados[3])) {
                montoLimite = Double.parseDouble(datosRecolectados[3]);
            } else {
                montoLimite = -1;
            }
            filtroListadoSolicitudes = new ListadoSolicitudes(datosRecolectados[0], datosRecolectados[1], montoLimite);
            if (this.bancario.isTipoTarjetaValido(datosRecolectados[2])) {
                filtroListadoSolicitudes.setTipoTarjeta(TipoTarjetas.valueOf(datosRecolectados[2]));
                filtroListadoSolicitudes.setHayTipoTarjeta(true);
            } else {
                filtroListadoSolicitudes.setHayTipoTarjeta(false);
            }
            if (this.bancario.isEstadoSolicitudValido(datosRecolectados[4])) {
                filtroListadoSolicitudes.setEstadoSolicitud(EstadosSolicitud.valueOf(datosRecolectados[4]));
                filtroListadoSolicitudes.setHayEstadoSolicitud(true);
            } else {
                filtroListadoSolicitudes.setHayEstadoSolicitud(false);
            }
            if (this.bancario.verificarFiltroListadoSolicitudes(filtroListadoSolicitudes)) {
                System.out.println("Filtro de Listado de Solicitudes Valido para su Ejecucion");
                String restoQuery = filtroListadoSolicitudes.filtrarDatos();
                ListadoSolicitudesDB listadoSolicitudes = new ListadoSolicitudesDB();
                ArrayList<ListadoSolicitudes> datos = listadoSolicitudes.getListadoSolicitudes(restoQuery);
                if (datos.isEmpty()) {
                    this.descripcion.setText("No se pudo crear Archivo porque No hay Datos por Mostrar");
                    return;
                }
                filtroListadoSolicitudes.setDatosSolicitudes(datos);
                filtroListadoSolicitudes.exportarReportes(this.ingresoArchivoFront.getPathCarpeta());
            } else {
                this.descripcion.setText("Filtro de Listado de Solicitudes NO Valido para su Ejecucion");
            }
        } else {
            this.descripcion.setText("Error en la Lectura de Archivo: Cantidad de datos invalidos para la Cancelacion de Tarjeta");
        }
    }

}
