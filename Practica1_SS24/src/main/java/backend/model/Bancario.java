/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.data.AutorizacionTarjetaDB;
import backend.data.CancelacionTarjetaDB;
import backend.data.ConsultaTarjetaDB;
import backend.data.ListadoTarjetasDB;
import backend.data.MovimientoTarjetaDB;
import backend.data.SolicitudTarjetaDB;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class Bancario {

    private final String[] TIPO_TARJETAS = {"NACIONAL", "REGIONAL", "INTERNACIONAL"};
    private final String[] TIPO_MOVIMIENTOS = {"CARGO", "ABONO"};
    private final String[] ESTADO_TARJETA = {"AUTORIZADA", "ACTIVA", "CANCELADA"};
    private final String[] ESTADO_SOLICITUD = {"APROBADA", "AUTORIZADA", "RECHAZADA"};
    private String pathCarpeta;

    public String getPathCarpeta() {
        return pathCarpeta;
    }

    public void setPathCarpeta(String pathCarpeta) {
        this.pathCarpeta = pathCarpeta;
    }        

    public boolean isNumeroSolicitudRepetida(int numeroSolicitud) {
        SolicitudTarjetaDB datosSolicitud = new SolicitudTarjetaDB();
        ArrayList<Integer> solicitudesEnBS = datosSolicitud.getNumeroSolicitud();
        for (int i = 0; i < solicitudesEnBS.size(); i++) {
            if (solicitudesEnBS.get(i) == numeroSolicitud) {
                System.out.println("Numero de Solicitud Repetida!!!");
                return true;
            }
        }
        return false;
    }

    public String quitarComillas(String texto) {
        String textoModificado;
        if (texto.contains("\"")) {
            textoModificado = texto.substring(1, texto.length() - 1);
            return textoModificado;
        }
        return texto;
    }

    public boolean isFormatoFechaCorrecto(String fecha) {
        fecha = this.quitarComillas(fecha);
        if (fecha.contains("/")) {
            String[] datos = fecha.split("/");
            if (datos.length == 3) {
                String yyyy = datos[2];
                String mm = datos[1];
                String dd = datos[0];
                if (yyyy.length() == 4 && mm.length() == 2 && dd.length() == 2) {
                    if (Integer.parseInt(mm) < 13 && Integer.parseInt(dd) < 32) {
                        System.out.println("Formato de Fecha Correcta");
                        return true;
                    }
                }
            }
        }
        System.out.println("Formato de Fecha Incorrecta");
        return false;
    }

    public String transformarFormatoFecha(String fecha) {
        String[] datos = fecha.split("/");
        String yyyy = datos[2];
        String mm = datos[1];
        String dd = datos[0];
        return yyyy + "-" + mm + "-" + dd;
    }

    public boolean isTipoTarjetaValido(String tipoTarjeta) {
        for (int i = 0; i < TIPO_TARJETAS.length; i++) {
            if (TIPO_TARJETAS[i].equalsIgnoreCase(tipoTarjeta)) {
                System.out.println("Tipo de Tarjeta Valida");
                return true;
            }
        }
        System.out.println("Tipo de Tarjeta Invalida");
        return false;
    }

    public boolean isInteger(String texto) {
        try {
            Integer.valueOf(texto);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error en la Lectura de Archivo: Texto ingresado Invalido para ser Numero Entero");
            return false;
        }
    }

    public boolean isDouble(String texto) {
        try {
            Double.valueOf(texto);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error en la Lectura de Archivo: Texto ingresado Invalido para ser Numero Decimal");
            return false;
        }
    }

    public boolean isTipoMovimientoValido(String tipoMovimiento) {
        for (int i = 0; i < TIPO_MOVIMIENTOS.length; i++) {
            if (TIPO_MOVIMIENTOS[i].equalsIgnoreCase(tipoMovimiento)) {
                System.out.println("Tipo de Movimiento Valido");
                return true;
            }
        }
        System.out.println("Tipode Movimiento Invalido");
        return false;
    }

    public boolean isNumeroTarjetaValido(String numeroTarjeta) {
        String[] contenido = numeroTarjeta.split(" ");
        boolean isValido = false;
        if (contenido.length == 4) {
            for (int i = 0; i < contenido.length; i++) {
                if (contenido[i].length() == 4 && this.isInteger(contenido[i])) {
                    isValido = true;
                } else {
                    System.out.println("Numero de Tarjeta Invalida");
                    return false;
                }
            }
        }
        return isValido;
    }
    
    public String transformarNumeroTarjeta(String numeroSinFormato) {
        String numeroFormateado = "";
        String[] numero = numeroSinFormato.split("");
        if (numero.length == 16) {
            for (int i = 0; i < numero.length; i++) {
                if ((i%4) == 0 && i != 0) {
                    numeroFormateado += " ";
                }
                numeroFormateado += numero[i];
            }
        }
        return numeroFormateado;
    }

    public boolean isEstadoTarjetaValido(String estadoTarjeta) {
        for (int i = 0; i < ESTADO_TARJETA.length; i++) {
            if (ESTADO_TARJETA[i].equalsIgnoreCase(estadoTarjeta)) {
                System.out.println("Estado de Tarjeta Valido");
                return true;
            }
        }
        System.out.println("Estado de Tarjeta Invalido");
        return false;
    }

    public boolean isEstadoSolicitudValido(String estadoSolicitud) {
        for (int i = 0; i < ESTADO_SOLICITUD.length; i++) {
            if (ESTADO_SOLICITUD[i].equalsIgnoreCase(estadoSolicitud)) {
                System.out.println("Estado de Solicitud Valido");
                return true;
            }
        }
        System.out.println("Estado de Solicitud Invalido");
        return false;
    }

    public boolean verificarSolicitudLeida(SolicitudTarjeta solicitud) {
        if (!isNumeroSolicitudRepetida(solicitud.getNumeroSolicitud()) && isFormatoFechaCorrecto(solicitud.getFechaSolicitud())
                && isTipoTarjetaValido(solicitud.getTipoTarjetaSolicitada())) {
            solicitud.setNombreSolicitante(this.quitarComillas(solicitud.getNombreSolicitante()));
            solicitud.setDireccionSolicitante(this.quitarComillas(solicitud.getDireccionSolicitante()));
            solicitud.setFechaSolicitud(this.transformarFormatoFecha(this.quitarComillas(solicitud.getFechaSolicitud())));
            System.out.println("Solicitud de Tarjeta Valida para su Ejecucion\n");
            SolicitudTarjetaDB nuevaSolicitud = new SolicitudTarjetaDB(solicitud);
            nuevaSolicitud.crearCliente();
            nuevaSolicitud.crearSolicitud();
            return true;
        }
        System.out.println("Solicitud de Tarjeta NO Valida para su Ejecucion\n");
        return false;
    }

    public boolean verificarMovimientoLeido(MovimientoTarjeta movimiento) {
        if (isNumeroTarjetaValido(movimiento.getNumeroTarjeta()) && isFormatoFechaCorrecto(movimiento.getFechaOperacion())
                && isTipoMovimientoValido(movimiento.getTipoMovimiento())) {
            movimiento.setDescripcion(this.quitarComillas(movimiento.getDescripcion()));
            movimiento.setEstablecimiento(this.quitarComillas(movimiento.getEstablecimiento()));
            movimiento.setFechaOperacion(this.transformarFormatoFecha(this.quitarComillas(movimiento.getFechaOperacion())));
            MovimientoTarjetaDB movimientoTarjeta = new MovimientoTarjetaDB(movimiento);            
            if (movimientoTarjeta.isTarjetaActiva()) {
                movimientoTarjeta.crearRegistro();
                movimientoTarjeta.actualizarSaldoTarjeta();
                return true;
            } else {
                System.out.println("La Tarjeta esta Inactiva");
                return false;
            }
        } else {
            System.out.println("Movimiento de Tarjeta NO Valido para su Ejecucion\n");
            return false;
        }
    }

    public void verificarFiltroEstadoCuenta(FiltroEstadoCuenta filtro) {
        if (isNumeroTarjetaValido(filtro.getNumeroTarjeta()) && isTipoTarjetaValido(filtro.getTipoTarjeta())) {
            System.out.println("Filtro para Estado de Cuenta Valido para su Ejecucion\n");
            return;
        }
        System.out.println("Filtro para Estado de Cuenta NO Valido para su Ejecucion\n");
    }

    public boolean verificarFiltroListadoTarjetas(ListadoTarjetas filtro) {
        if (!filtro.getTipoTarjeta().equals("")) {
            if (!isTipoTarjetaValido(filtro.getTipoTarjeta())) {
                System.out.println("Numero de Tarjeta Invalida");
                return false;
            }
        }
        if (!filtro.getFechaInicio().equals("")) {
            if (!isFormatoFechaCorrecto(filtro.getFechaInicio())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaInicio(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaInicio())));
        }
        if (!filtro.getFechaFinal().equals("")) {
            if (!isFormatoFechaCorrecto(filtro.getFechaFinal())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaFinal(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaFinal())));
        }
        if (!filtro.getEstadoTarjeta().equals("")) {
            if (!isEstadoTarjetaValido(filtro.getEstadoTarjeta())) {
                System.out.println("Estado de Tarjeta Invalido");
                return false;
            }
        }
        System.out.println("Filtro de Listado de Tarjetas Valido para su Ejecucion\n");
        String restoQuery = filtro.filtrarDatos();
        ListadoTarjetasDB listadoTarjetas = new ListadoTarjetasDB();
        ArrayList<ListadoTarjetas> datos = listadoTarjetas.getListadoTarjetas(restoQuery);
        filtro.setDatosTarjetas(datos);        
        filtro.exportarReportes(pathCarpeta);
        return true;        
    }

    public void verificarFiltroListadoSolicitudes(FiltroListadoSolicitudes filtro) {
        if (isFormatoFechaCorrecto(filtro.getFechaInicio()) && isFormatoFechaCorrecto(filtro.getFechaFin())
                && isTipoTarjetaValido(filtro.getTipoTarjeta()) && isEstadoSolicitudValido(filtro.getEstadoSolicitud())) {
            System.out.println("Filtro de Listado de Solicitudes Valido para su Ejecucion\n");
            return;
        }
        System.out.println("Filtro de Listado de Solicitudes NO Valido para su Ejecucion\n");
    }

    public void verificarAutorizacionLeida(int numeroSolicitud) {
        if (this.isNumeroSolicitudRepetida(numeroSolicitud)) {
            AutorizacionTarjetaDB autorizacionDB = new AutorizacionTarjetaDB(numeroSolicitud);
            if (!autorizacionDB.isSolicitudAutorizada(numeroSolicitud)) {
                Autorizacion autorizacion = new Autorizacion(autorizacionDB.getCliente().getSalario(), autorizacionDB.getLimiteCreditoTipoTarjeta(),
                        autorizacionDB.getSolicitud().getTipoTarjetaSolicitada());
                if (autorizacion.autorizarTarjeta()) {
                    autorizacionDB.crearTarjeta(autorizacion.getTarjeta());
                    autorizacionDB.actualizarEstadoSolicitud(numeroSolicitud, true);
                    System.out.println("Autorizacion Aceptada");
                } else {
                    autorizacionDB.actualizarEstadoSolicitud(numeroSolicitud, false);
                    System.out.println("Autorizacion Rechazada por Limite de Credito");
                }
            } else {
                System.out.println("Solicitud ya se encontraba Autorizada");
            }
        } else {
            System.out.println("Numero de Solicitu NO encontrada en la DB");
        }        
    }
    
    public Consulta verificarConsultaTarjeta(String numeroTarjeta) {
        ConsultaTarjetaDB consultaDB = new ConsultaTarjetaDB();
        ArrayList<String> numerosRegistrados = consultaDB.getNumeroTarjetas();
        Consulta consulta = null;
        for (int i = 0; i < numerosRegistrados.size(); i++) {
            System.out.println("EXITO");
            if (numerosRegistrados.get(i).equals(numeroTarjeta)) {
                System.out.println("ENCONTRADA");
                consulta = consultaDB.getConsulta(numeroTarjeta);                               
                break;
            }
        }
        System.out.println("No se Encontro el Numero de la Tarjeta en la DB");
        return consulta;
    }
    
    public Cancelacion verificarCancelacionLeida(String numeroTarjeta) {
        CancelacionTarjetaDB cancelacionDB = new CancelacionTarjetaDB();
        ArrayList<String> numerosRegistrados = cancelacionDB.getNumeroTarjetas();
        Cancelacion cancelacion = null;
        for (int i = 0; i < numerosRegistrados.size(); i++) {           
            if (numerosRegistrados.get(i).equals(numeroTarjeta)) {
                cancelacion = cancelacionDB.getCancelacion(numeroTarjeta);                               
                break;
            }
        }
        return cancelacion;
    }

}
