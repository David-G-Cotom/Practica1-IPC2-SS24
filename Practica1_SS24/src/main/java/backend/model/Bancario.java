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

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que verifica si el numero de solicitud ingresada como parametro
     * existe dentro de la Base de Datos del sistema
     *
     * @param numeroSolicitud es el numero de solicitud que se quiere saber si
     * existe en el sistema
     * @return verdadero si el numero de solicitud ingresado como parametro esta
     * repetida dentro del registro sel sistema, de los contrario retorna falso
     */
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

    /**
     * Metodo que verifica si el numero de tarjeta ingresada como parametro
     * existe dentro de la Base de Datos del sistema
     *
     * @param numeroTarjeta es el numero de tarjeta que se quiere saber si
     * existe en el sistema
     * @return verdadero si el numero de tarjeta ingresado como parametro esta
     * repetida dentro del registro sel sistema, de los contrario retorna falso
     */
    public boolean isNumeroTarjetaRepetida(String numeroTarjeta) {
        MovimientoTarjetaDB datosSolicitud = new MovimientoTarjetaDB();
        ArrayList<String> tarjetasEnBS = datosSolicitud.getNumerosTarjeta();
        for (int i = 0; i < tarjetasEnBS.size(); i++) {
            if (tarjetasEnBS.get(i).equals(numeroTarjeta)) {
                System.out.println("Numero de Tarjeta Repetida");
                return true;
            }
        }
        System.out.println("No se encontro el Numero de Tarjeta Ingresado");
        return false;
    }

    /**
     * Metodo que quita las comillas del texto ingresado como parametro si es
     * que las tiene
     *
     * @param texto es una cadena que tiene comillas dentro de si
     * @return el texto ingresado como parametro pero ahora sin comillas
     */
    public String quitarComillas(String texto) {
        String textoModificado;
        if (texto.contains("\"")) {
            textoModificado = texto.substring(1, texto.length() - 1);
            return textoModificado;
        }
        return texto;
    }

    /**
     * Metodo que verifica si la fecha ingresada como parametro tiene el formato
     * adecuado de ingreso
     *
     * @param fecha es la fecha con un determinado formato
     * @return verdadero si la fecha ingersada como parametro tiene el formato
     * correcto, de lo contario retorna falso
     */
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

    /**
     * Metodo que transforma la fecha ingresada como parametro a un formato
     * correcto para sus posteriores procesamientos
     *
     * @param fecha es el valor de la fecha con un determinado formato
     * @return el formato de fecha correcto par su proceso en la Base de Datos
     */
    public String transformarFormatoFecha(String fecha) {
        String[] datos = fecha.split("/");
        String yyyy = datos[2];
        String mm = datos[1];
        String dd = datos[0];
        return yyyy + "-" + mm + "-" + dd;
    }

    /**
     * Metodo que evalua si el valor de la cadena tipoTarjeta pertenece a los
     * tipos de tarjeta validos para una tarjeta
     *
     * @param tipoTarjeta es el valor del Tipo de Tarjeta para una Tarjeta
     * @return verdadero si la cadena tipoTarjeta es valido, de lo contario
     * retorna falso
     */
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

    /**
     * Metodo que evalua si el texto ingresado como parametro es valido para ser
     * un numero entero positivo
     *
     * @param texto es la cadena que se quiere saber si puede ser un numero
     * entero positivo
     * @return verdadero si el texto ingresado como parametro cumple para ser un
     * numero entero positivo, de los contrario retorna falso
     */
    public boolean isInteger(String texto) {
        try {
            int numero = Integer.parseInt(texto);
            return numero >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Texto ingresado NO puede ser Numero Entero");
            return false;
        }
    }

    /**
     * Metodo que evalua si el texto ingresado como parametro es valido para ser
     * un numero decimal positivo
     *
     * @param texto es la cadena que se quiere saber si puede ser un numero
     * decimal positivo
     * @return verdadero si el texto ingresado como parametro cumple para ser un
     * numero decimal positivo, de los contrario retorna falso
     */
    public boolean isDouble(String texto) {
        try {
            double numero = Double.parseDouble(texto);
            return numero >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Texto ingresado NO puede ser Numero Decimal");
            return false;
        }
    }

    /**
     * Metodo que evalua si el valor de la cadena tipoMovimiento pertenece a los
     * tipos de movimiento valido para una Tarjeta
     *
     * @param tipoMovimiento es el valor del Tipo de Movimiento para una Tarjeta
     * @return verdadero si la cadena tipoMovimiento es valido, de lo contario
     * retorna falso
     */
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

    /**
     * Metodo que verifica si el valor de la cadena numeroTarjeta recibido como
     * parametro tiene el formato correcto para considerarse un numero de
     * tarjeta valido
     *
     * @param numeroTarjeta es el posible numero para una tarjeta
     * @return verdadero si el valor de numeroTarjeta es valido como un numero
     * de tarjeta, de los contrario retorna falso
     */
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

    /**
     * Metodo que transforma el valor de la cadena numeroSinFormato recibida
     * como parametro al formato correcto que debe tener el numero de las
     * tarjetas para su procesamiento
     *
     * @param numeroSinFormato es el numero de tarjeta pero sin el formato
     * adecuado
     * @return la cadena del numero de Tarjeta recibido como parametro con el
     * formato adecuado
     */
    public String transformarNumeroTarjeta(String numeroSinFormato) {
        String numeroFormateado = "";
        String[] numero = numeroSinFormato.split("");
        if (numero.length == 16) {
            for (int i = 0; i < numero.length; i++) {
                if (((i % 4) == 0) && (i != 0)) {
                    numeroFormateado += " ";
                }
                numeroFormateado += numero[i];
            }
        }
        return numeroFormateado;
    }

    /**
     * Metodo que evalua si el valor de la cadena estadoTarjeta pertenece a los
     * estados validos para una Tarjeta
     *
     * @param estadoTarjeta es el valor del Estado de la Tarjeta
     * @return verdadero si la cadena estadoTarjeta es valido, de lo contario
     * retorna falso
     */
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

    /**
     * Metodo que evalua si el valor de la cadena estadoSolicitud pertenece a
     * los estados validos para una Solicitud
     *
     * @param estadoSolicitud es el valor del Estado de la Solicitud
     * @return verdadero si la cadena estadoSolicitud es valido, de lo contario
     * retorna falso
     */
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

    /**
     * Metodo que evalua si la longitud de cadena de texto ingresada como
     * parametro es menor al valor longitud ingresado como parametro
     *
     * @param cadena es la cadena de texto que se quiere evaluar su longitud
     * @param longitud es la longitu con la que debe cumplir la cadena recibida
     * @return verdadero si la cadena es menor a la longitud especificada, de
     * los contrario retorna falso
     */
    public boolean isLongitudCadenaValida(String cadena, int longitud) {
        if (cadena.length() < longitud) {
            System.out.println("Longitud de Cadena Valida");
            return true;
        }
        System.out.println("Longitud de Cadena Muy Larga");
        return false;
    }

    /**
     * Metodo que realiza la SOlicitud de una Tarjeta siempre que los atributos
     * de la Instancia Solicitud Tarjeta recibido de parametro sean validos
     *
     * @param solicitud es una Instancia de de Solicitud Tarjeta que contiene la
     * informacion necesaria para efectuar una Solicitud de Tarjeta
     * @return verdadero si la Solicitud de Tarjeta se realiza con exito, de los
     * contrario se retorna falso
     */
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

    /**
     * Metodo que realiza el Movimeinto de una Tarjeta siempre que los atributos
     * de la Instancia Movimiento Tarjeta recibido de parametro sean validos
     *
     * @param movimiento es una Instancia de Movimiento Tarjeta que contiene la
     * informacion necesaria para poder efectuar un Movimiento de Tarjeta
     * @return verdadero si el Movimiento de Tarjeta se realiza de forma
     * exitosa, de lo contrario retorna falso
     */
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

    /**
     * Metodo que verifica si los atributos de la Instancia Filtro de Estado de
     * Cuenta ingresado como parametro es valido para poder hacer la ejecucion
     * de la consulta en Base de Datos
     *
     * @param filtro es una Instancia de Filtro Estado Cuenta que contiene los
     * valores para filtrar consulta
     * @return verdadero si los valores del filtro ingresado de parametro son
     * Validos, de los contrario retorna falso
     */
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

    /**
     * Metodo que verifica si los atributos de la Instancia Listado de Tarjetas
     * ingresado como parametro es valido para poder hacer la ejecucion de la
     * consulta en Base de Datos
     *
     * @param filtro es una Instancia de Listado Tarjetas que contiene los
     * valores para filtrar consulta
     * @return verdadero si los valores del filtro ingresado de parametro son
     * Validos, de los contrario retorna falso
     */
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

    /**
     * Metodo que verifica si los atributos de la Instancia Listado de
     * Solicitudes ingresado como parametro es valido para poder hacer la
     * ejecucion de la consulta en Base de Datos
     *
     * @param filtro es una Instancia de Listado Solicitudes que contiene los
     * valores para filtrar consulta
     * @return verdadero si los valores del filtro ingresado de parametro son
     * Validos, de los contrario retorna falso
     */
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

    /**
     * Metodo que retorna un boolean dependiendo de si la Autorizacion de la
     * Solicitud con Numero ingresado como parametro se ha realizado bien
     *
     * @param numeroSolicitud es el Numero de Solicitud que se quiere Autorizar
     * @return verdadero si existe la Solicitud, de los contrario retornra falso
     */
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

    /**
     * Metodo que Retorna una COnsulta de Tarjeta para el Numero de Tarjeta
     * ingresado como parametro siempre que exista la misma
     *
     * @param numeroTarjeta es el Numero de Tarjeta que se quiere Consultar
     * @return una Instancia de Consulta
     */
    public Consulta verificarConsultaTarjeta(String numeroTarjeta) {
        ConsultaTarjetaDB consultaDB = new ConsultaTarjetaDB();
        Consulta consulta;
        if (!isNumeroTarjetaRepetida(numeroTarjeta)) {
            return null;
        }
        consulta = consultaDB.getConsulta(numeroTarjeta);
        return consulta;
    }

    /**
     * Metodo que Retorna una Cancelacion de Tarjeta para el Numero de Tarjeta
     * ingresado como parametro siempre que exista la misma
     *
     * @param numeroTarjeta es el Numero de Tarjeta que se quiere Cancelar
     * @return una Instancioa de Cancelacion
     */
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
