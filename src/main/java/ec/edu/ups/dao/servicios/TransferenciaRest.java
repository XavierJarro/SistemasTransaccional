/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import java.util.List;

/**
 *
 * @author Starman
 */
public class TransferenciaRest {

    private String cedula;
    private String cuentaDeAhorro;
    private double monto;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCuentaDeAhorro() {
        return cuentaDeAhorro;
    }

    public void setCuentaDeAhorro(String cuentaDeAhorro) {
        this.cuentaDeAhorro = cuentaDeAhorro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}
