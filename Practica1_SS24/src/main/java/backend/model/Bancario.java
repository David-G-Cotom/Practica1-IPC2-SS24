/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.TipoMovimientos;
import backend.enums.TipoTarjetas;
import backend.data.AutorizacionTarjetaDB;
import backend.data.CancelacionTarjetaDB;
import backend.data.ConsultaTarjetaDB;
import backend.data.MovimientoTarjetaDB;
import backend.data.SolicitudTarjetaDB;
import backend.enums.EstadosSolicitud;
import backend.enums.EstadosTarjeta;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class Bancario {

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
                System.out.println("Numero de Solicitud Repetida");
                return true;
            }
        }
        System.out.println("No se encontro el Numero de Solicitud Ingresado");
        return false;
    }
    
    public boolean isNumeroTarjetaRepetida(String numeroSolicitud) {
        MovimientoTarjetaDB datosSolicitud = new MovimientoTarjetaDB();
        ArrayList<String> tarjetasEnBS = datosSolicitud.getNumerosTarjeta();
        for (int i = 0; i < tarjetasEnBS.size(); i++) {
            if (tarjetasEnBS.get(i).equals(numeroSolicitud)) {
                System.out.println("Numero de Tarjeta Repetida");
                return true;
            }
        }
        System.out.println("No se encontro el Numero de Tarjeta Ingresado");
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
                    if (this.isInteger(yyyy) && this.isInteger(mm) && this.isInteger(dd)) {
                        if (Integer.parseInt(mm) < 13 && Integer.parseInt(mm) > 0 && Integer.parseInt(dd) < 32 && Integer.parseInt(dd) > 0) {
                            System.out.println("Formato de Fecha Correcta");
                            return true;
                        }                        
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
        TipoTarjetas[] tipos = TipoTarjetas.values();
        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].toString().equals(tipoTarjeta)) {
                System.out.println("Tipo de Tarjeta Valida");
                return true;
            }
        }
        System.out.println("Tipo de Tarjeta Invalida");
        return false;
    }

    public boolean isInteger(String texto) {
        try {
            int numero = Integer.parseInt(texto);
            return numero >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Texto ingresado NO puede ser Numero Entero");
            return false;
        }
    }

    public boolean isDouble(String texto) {
        try {
            double numero = Double.parseDouble(texto);
            return numero >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Texto ingresado NO puede ser Numero Decimal");
            return false;
        }
    }

    public boolean isTipoMovimientoValido(String tipoMovimiento) {
        TipoMovimientos[] tipos = TipoMovimientos.values();
        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].toString().equals(tipoMovimiento)) {
                System.out.println("Tipo de Movimiento Valido");
                return true;
            }
        }
        System.out.println("Tipo de Movimiento Invalido");
        return false;
    }

    public boolean isNumeroTarjetaValido(String numeroTarjeta) {
        if (numeroTarjeta.equals("")) {
            return false;
        }
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
        EstadosTarjeta[] estados = EstadosTarjeta.values();
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].toString().equals(estadoTarjeta)) {
                System.out.println("Estado de Tarjeta Valido");
                return true;
            }
        }
        System.out.println("Estado de Tarjeta Invalido");
        return false;
    }

    public boolean isEstadoSolicitudValido(String estadoSolicitud) {
        EstadosSolicitud[] estados = EstadosSolicitud.values();
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].toString().equals(estadoSolicitud)) {
                System.out.println("Estado de Solicitud Valido");
                return true;
            }
        }
        System.out.println("Estado de Solicitud Invalido");
        return false;
    }
    
    public boolean isLongitudCadenaValida(String cadena, int longitud) {
        if (cadena.length() < longitud) {
            System.out.println("Longitud de Cadena Valida");
            return true;
        }
        System.out.println("Longitud de Cadena Muy Larga");
        return false;
    }

    public boolean verificarSolicitudLeida(SolicitudTarjeta solicitud) {
        if (!isNumeroSolicitudRepetida(solicitud.getNumeroSolicitud()) && isFormatoFechaCorrecto(solicitud.getFechaSolicitud())) {
            solicitud.setNombreSolicitante(this.quitarComillas(solicitud.getNombreSolicitante()));
            solicitud.setDireccionSolicitante(this.quitarComillas(solicitud.getDireccionSolicitante()));
            solicitud.setFechaSolicitud(this.transformarFormatoFecha(this.quitarComillas(solicitud.getFechaSolicitud())));
            if (isLongitudCadenaValida(solicitud.getNombreSolicitante(), 100) && isLongitudCadenaValida(solicitud.getDireccionSolicitante(), 150)) {
                System.out.println("Solicitud de Tarjeta Valida para su Ejecucion\n");
                SolicitudTarjetaDB nuevaSolicitud = new SolicitudTarjetaDB(solicitud);
                nuevaSolicitud.crearCliente();
                nuevaSolicitud.crearSolicitud();
                return true;                
            }
        }
        System.out.println("Solicitud de Tarjeta NO Valida para su Ejecucion\n");
        return false;
    }

    public boolean verificarMovimientoLeido(MovimientoTarjeta movimiento) {
        if (isNumeroTarjetaValido(movimiento.getNumeroTarjeta()) && isFormatoFechaCorrecto(movimiento.getFechaOperacion())) {
            movimiento.setDescripcion(this.quitarComillas(movimiento.getDescripcion()));
            movimiento.setEstablecimiento(this.quitarComillas(movimiento.getEstablecimiento()));
            movimiento.setFechaOperacion(this.transformarFormatoFecha(this.quitarComillas(movimiento.getFechaOperacion())));
            MovimientoTarjetaDB movimientoTarjeta = new MovimientoTarjetaDB(movimiento);
            if (!isNumeroTarjetaRepetida(movimiento.getNumeroTarjeta())) {
                return false;
            }
            if (!isLongitudCadenaValida(movimiento.getDescripcion(), 200)) {
                return false;
            }
            if (movimientoTarjeta.isTarjetaActiva()) {
                movimientoTarjeta.crearRegistro();
                movimientoTarjeta.actualizarSaldoTarjeta();
                return true;
            } else {
                System.out.println("Tarjeta Inactiva");
                return false;
            }
        }
        System.out.println("Movimiento de Tarjeta NO Valido para su Ejecucion\n");
        return false;
    }

    public boolean verificarFiltroEstadoCuenta(FiltroEstadoCuenta filtro) {
        if (!filtro.getNumeroTarjeta().equals("")) {
            if (!isNumeroTarjetaValido(filtro.getNumeroTarjeta())) {
                System.out.println("Numero de Tarjeta Invalida");
                return false;
            }
            if (!isNumeroTarjetaRepetida(filtro.getNumeroTarjeta())) {
                return false;
            }
        }        
        return true;        
    }

    public boolean verificarFiltroListadoTarjetas(ListadoTarjetas filtro) {
        if (!filtro.getFechaInicio().equals("")) {
            /*if (!isFormatoFechaCorrecto(filtro.getFechaInicio())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaInicio(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaInicio())));*/
            filtro.setFechaInicio("");
        }
        if (!filtro.getFechaFinal().equals("")) {
            /*if (!isFormatoFechaCorrecto(filtro.getFechaFinal())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaFinal(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaFinal())));*/
            filtro.setFechaFinal("");
        }        
        return true;        
    }

    public boolean verificarFiltroListadoSolicitudes(ListadoSolicitudes filtro) {
        if (!filtro.getFechaInicio().equals("")) {
            /*if (!isFormatoFechaCorrecto(filtro.getFechaInicio())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaInicio(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaInicio())));*/
            filtro.setFechaInicio("");
        }
        if (!filtro.getFechaFin().equals("")) {
            /*if (!isFormatoFechaCorrecto(filtro.getFechaFin())) {
                System.out.println("Formato de Fecha Invalido");
                return false;
            }
            filtro.setFechaFin(this.transformarFormatoFecha(this.quitarComillas(filtro.getFechaFin())));*/
            filtro.setFechaFin("");
        }        
        return true;
    }

    public boolean verificarAutorizacionLeida(int numeroSolicitud) {
        if (this.isNumeroSolicitudRepetida(numeroSolicitud)) {
            AutorizacionTarjetaDB autorizacionDB = new AutorizacionTarjetaDB(numeroSolicitud);
            if (!autorizacionDB.isSolicitudAutorizada(numeroSolicitud)) {
                Autorizacion autorizacion = new Autorizacion(autorizacionDB.getCliente().getSalario(), autorizacionDB.getLimiteCreditoTipoTarjeta(),
                        autorizacionDB.getSolicitud().getTipoTarjetaSolicitada());
                if (autorizacion.exitoEnAutorizarTarjeta()) {
                    autorizacionDB.crearTarjeta(autorizacion.getTarjeta());
                    autorizacionDB.actualizarEstadoSolicitud(numeroSolicitud, true);
                    return true;
                } else {
                    autorizacionDB.actualizarEstadoSolicitud(numeroSolicitud, false);                    
                }
            } else {
                System.out.println("Solicitud ya se encontraba Autorizada");
            }
        }
        return false;
    }
    
    public Consulta verificarConsultaTarjeta(String numeroTarjeta) {
        ConsultaTarjetaDB consultaDB = new ConsultaTarjetaDB();
        Consulta consulta;
        if (!isNumeroTarjetaRepetida(numeroTarjeta)) {
            return null;
        }
        consulta = consultaDB.getConsulta(numeroTarjeta);
        return consulta;
    }
    
    public Cancelacion verificarCancelacionLeida(String numeroTarjeta) {
        CancelacionTarjetaDB cancelacionDB = new CancelacionTarjetaDB();
        Cancelacion cancelacion;
        if (!isNumeroTarjetaRepetida(numeroTarjeta)) {
            return null;
        }
        cancelacion = cancelacionDB.getCancelacion(numeroTarjeta);
        return cancelacion;
    }

}
