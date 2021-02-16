/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Starman
 */
@Entity
public class TransferenciaExterna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoTransferenciaExterna;
    private Date fechaTransaccion;
    private double montoTransferencia;
    private String nombreInstitucionExterna;
    private String cuentaPersonaLocal;
    private String cuentaPersonaExterna;
    private String nombrePersonaExterna;
    private String apellidoPersonaExterna;

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getCodigoTransferenciaExterna() {
        return codigoTransferenciaExterna;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCodigoTransferenciaExterna(int codigoTransferenciaExterna) {
        this.codigoTransferenciaExterna = codigoTransferenciaExterna;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getMontoTransferencia() {
        return montoTransferencia;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setMontoTransferencia(double montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getNombreInstitucionExterna() {
        return nombreInstitucionExterna;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setNombreInstitucionExterna(String nombreInstitucionExterna) {
        this.nombreInstitucionExterna = nombreInstitucionExterna;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getCuentaPersonaLocal() {
        return cuentaPersonaLocal;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCuentaPersonaLocal(String cuentaPersonaLocal) {
        this.cuentaPersonaLocal = cuentaPersonaLocal;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getCuentaPersonaExterna() {
        return cuentaPersonaExterna;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCuentaPersonaExterna(String cuentaPersonaExterna) {
        this.cuentaPersonaExterna = cuentaPersonaExterna;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getNombrePersonaExterna() {
        return nombrePersonaExterna;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setNombrePersonaExterna(String nombrePersonaExterna) {
        this.nombrePersonaExterna = nombrePersonaExterna;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getApellidoPersonaExterna() {
        return apellidoPersonaExterna;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setApellidoPersonaExterna(String apellidoPersonaExterna) {
        this.apellidoPersonaExterna = apellidoPersonaExterna;
    }

}
