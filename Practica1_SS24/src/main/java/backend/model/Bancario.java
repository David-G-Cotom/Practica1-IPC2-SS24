/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class Bancario {
    
    private final String[] TIPO_TARJETAS = {"NACIONAL", "REGIONAL", "INTERNACIONAL"};
    private final String[] TIPO_MOVIMIENTOS = {"CARGO", "BONO"};
    private final String[] ESTADO_TARJETA = {"AUTORIZADA", "ACTIVA", "CANCELADA"};
    private final String[] ESTADO_SOLICITUD = {"APROBADA", "AUTORIZADA", "RECHAZADA"};
    
    public boolean verificarNumeroSolicitudRepetida(int numeroSolicitud) {
        //Conectarse a la DB y extraer un arreglo que contenga todos los numeros de solicitud
        ArrayList<Integer> solicitudesEnBS = null;
        for (int i = 0; i < solicitudesEnBS.size(); i++) {
            if (solicitudesEnBS.get(i) == numeroSolicitud) {
                System.out.println("Error!!! Numero de Solicitud Repetida");
                return true;
            }
        }
        return false;
    }
    
    public String quitarComillas(String texto) {
        String textoModificado = "";
        if (texto.contains("\"")) {
            textoModificado = texto.substring(1, texto.length()-1);
        }
        return textoModificado;
    }
    
    public boolean verificarFormatoFechaCorrecto(String fecha) {
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
    
    public boolean verificarTipoTarjetaValido(String tipoTarjeta) {
        for (int i = 0; i < TIPO_TARJETAS.length; i++) {
            if (TIPO_TARJETAS[i].equalsIgnoreCase(tipoTarjeta)) {
                System.out.println("Tipo de Tarjeta Valida");
                return true;
            }
        }
        System.out.println("Tipo de Tarjeta Invalida");
        return false;        
    }
    
    public boolean verificarTipoMovimientoValido(String tipoMovimiento) {
        for (int i = 0; i < TIPO_MOVIMIENTOS.length; i++) {
            if (TIPO_MOVIMIENTOS[i].equalsIgnoreCase(tipoMovimiento)) {
                System.out.println("Tipo de Movimiento Valido");
                return true;
            }
        }
        System.out.println("Tipode Movimiento Invalido");
        return false;        
    }
    
    public boolean verificarNumeroTarjetaValido(String numeroTarjeta) {
        String[] contenido = numeroTarjeta.split(" ");
        if (contenido.length == 4) {
            for (int i = 0; i < contenido.length; i++) {
                if (contenido[i].length() != 4) {
                    System.out.println("Numero de Tarjeta Invalido");
                    return false;
                }
            }
        }
        System.out.println("Numero de Tarjeta Valido");
        return true;        
    }
    
    public boolean verificarEstadoTarjetaValido(String estadoTarjeta) {
        for (int i = 0; i < ESTADO_TARJETA.length; i++) {
            if (ESTADO_TARJETA[i].equalsIgnoreCase(estadoTarjeta)) {
                System.out.println("Estado de Tarjeta Valido");
                return true;
            }
        }
        System.out.println("Estado de Tarjeta Invalido");
        return false;
    }
    
    public boolean verificarEstadoSolicitudValido(String estadoSolicitud) {
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
        if (/*!verificarNumeroSolicitudRepetida(solicitud.getNumeroSolicitud()) && */verificarFormatoFechaCorrecto(solicitud.getFechaSolicitud())
                && verificarTipoTarjetaValido(solicitud.getTipoTarjetaSolicitada())) {
            solicitud.setNombreSolicitante(this.quitarComillas(solicitud.getNombreSolicitante()));
            solicitud.setDireccionSolicitante(this.quitarComillas(solicitud.getDireccionSolicitante()));
            System.out.println("Solicitud de Tarjeta Valida para su Ejecucion\n");
            return true;
        }
        System.out.println("Solicitud de Tarjeta NO Valida para su Ejecucion\n");
        return false;
    }
    
    public boolean verificarMovimientoLeido(MovimientoTarjeta movimiento) {
        if (verificarNumeroTarjetaValido(movimiento.getNumeroTarjeta()) && verificarFormatoFechaCorrecto(movimiento.getFechaOperacion())
                && verificarTipoMovimientoValido(movimiento.getTipoMovimiento())) {
            movimiento.setDescripcion(this.quitarComillas(movimiento.getDescripcion()));
            movimiento.setEstablecimiento(this.quitarComillas(movimiento.getEstablecimiento()));
            System.out.println("Movimiento de Tarjeta Valida para su Ejecucion\n");
            return true;
        }
        System.out.println("Movimiento de Tarjeta NO Valido para su Ejecucion\n");
        return false;
    }
    
    public boolean verificarFiltroEstadoCuenta(FiltroEstadoCuenta filtro) {
        if (verificarNumeroTarjetaValido(filtro.getNumeroTarjeta()) && verificarTipoTarjetaValido(filtro.getTipoTarjeta())) {
            System.out.println("Filtro para Estado de Cuenta Valido para su Ejecucion\n");
            return true;
        }
        System.out.println("Filtro para Estado de Cuenta NO Valido para su Ejecucion\n");
        return false;
    }
    
    public boolean verificarFiltroListadoTarjetas(FiltroListadoTarjetas filtro) {
        if (verificarTipoTarjetaValido(filtro.getTipoTarjeta()) && verificarFormatoFechaCorrecto(filtro.getFechaInicio())
                && verificarFormatoFechaCorrecto(filtro.getFechaFinal()) && verificarEstadoTarjetaValido(filtro.getEstadoTarjeta())) {
            System.out.println("Filtro de Listado de Tarjetas Valido para su Ejecucion\n");
            return true;
        }
        System.out.println("Filtro de Listado de Tarjetas NO Valido para su Ejecucion\n");
        return false;
    }
    
    public boolean verificarFiltroListadoSolicitudes(FiltroListadoSolicitudes filtro) {
        if (verificarFormatoFechaCorrecto(filtro.getFechaInicio()) && verificarFormatoFechaCorrecto(filtro.getFechaFin())
                && verificarTipoTarjetaValido(filtro.getTipoTarjeta()) && verificarEstadoSolicitudValido(filtro.getEstadoSolicitud())) {
            System.out.println("Filtro de Listado de Solicitudes Valido para su Ejecucion\n");
            return true;
        }
        System.out.println("Filtro de Listado de Solicitudes NO Valido para su Ejecucion\n");
        return false;
    }
    
}
