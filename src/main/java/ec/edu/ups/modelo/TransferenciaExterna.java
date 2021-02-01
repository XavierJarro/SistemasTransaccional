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

    public int getCodigoTransferenciaExterna() {
        return codigoTransferenciaExterna;
    }

    public void setCodigoTransferenciaExterna(int codigoTransferenciaExterna) {
        this.codigoTransferenciaExterna = codigoTransferenciaExterna;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public double getMontoTransferencia() {
        return montoTransferencia;
    }

    public void setMontoTransferencia(double montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    public String getNombreInstitucionExterna() {
        return nombreInstitucionExterna;
    }

    public void setNombreInstitucionExterna(String nombreInstitucionExterna) {
        this.nombreInstitucionExterna = nombreInstitucionExterna;
    }

    public String getCuentaPersonaLocal() {
        return cuentaPersonaLocal;
    }

    public void setCuentaPersonaLocal(String cuentaPersonaLocal) {
        this.cuentaPersonaLocal = cuentaPersonaLocal;
    }

    public String getCuentaPersonaExterna() {
        return cuentaPersonaExterna;
    }

    public void setCuentaPersonaExterna(String cuentaPersonaExterna) {
        this.cuentaPersonaExterna = cuentaPersonaExterna;
    }

    public String getNombrePersonaExterna() {
        return nombrePersonaExterna;
    }

    public void setNombrePersonaExterna(String nombrePersonaExterna) {
        this.nombrePersonaExterna = nombrePersonaExterna;
    }

    public String getApellidoPersonaExterna() {
        return apellidoPersonaExterna;
    }

    public void setApellidoPersonaExterna(String apellidoPersonaExterna) {
        this.apellidoPersonaExterna = apellidoPersonaExterna;
    }
    
    
}
