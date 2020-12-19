/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Starman
 */
@Entity
public class SolicitudPoliza {

    @Id
    private int codigoPoliza;
    @OneToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente clienteCredito;
    private double montoPoliza;
    private String mesesPoliza;
    private double tasaPoliza;

    public int getCodigoPoliza() {
        return codigoPoliza;
    }

    public void setCodigoPoliza(int codigoPoliza) {
        this.codigoPoliza = codigoPoliza;
    }

    public Cliente getClienteCredito() {
        return clienteCredito;
    }

    public void setClienteCredito(Cliente clienteCredito) {
        this.clienteCredito = clienteCredito;
    }

    public double getMontoPoliza() {
        return montoPoliza;
    }

    public void setMontoPoliza(double montoPoliza) {
        this.montoPoliza = montoPoliza;
    }

    public String getMesesPoliza() {
        return mesesPoliza;
    }

    public void setMesesPoliza(String mesesPoliza) {
        this.mesesPoliza = mesesPoliza;
    }

    public double getTasaPoliza() {
        return tasaPoliza;
    }

    public void setTasaPoliza(double tasaPoliza) {
        this.tasaPoliza = tasaPoliza;
    }

    @Override
    public String toString() {
        return "SolicitudPoliza{" + "codigoPoliza=" + codigoPoliza + ", clienteCredito=" + clienteCredito + ", montoPoliza=" + montoPoliza + ", mesesPoliza=" + mesesPoliza + ", tasaPoliza=" + tasaPoliza + '}';
    }

}
