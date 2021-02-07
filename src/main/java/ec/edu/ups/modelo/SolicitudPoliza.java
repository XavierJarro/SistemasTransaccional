/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private Cliente clientePoliza;
    private double montoPoliza;
    private int mesesPoliza;
    private double tasaPoliza;
    private String estado;
    private double saldoCuenta;
    @Column(length = 16777216)
    private byte[] arCedula;
    @Column(length = 16777216)
    private byte[] arPlanillaServicios;
    private int cantidadPolizas;

    public int getCodigoPoliza() {
        return codigoPoliza;
    }

    public void setCodigoPoliza(int codigoPoliza) {
        this.codigoPoliza = codigoPoliza;
    }

    public Cliente getClientePoliza() {
        return clientePoliza;
    }

    public void setClientePoliza(Cliente clientePoliza) {
        this.clientePoliza = clientePoliza;
    }

    public double getMontoPoliza() {
        return montoPoliza;
    }

    public void setMontoPoliza(double montoPoliza) {
        this.montoPoliza = montoPoliza;
    }

    public int getMesesPoliza() {
        return mesesPoliza;
    }

    public void setMesesPoliza(int mesesPoliza) {
        this.mesesPoliza = mesesPoliza;
    }

    public double getTasaPoliza() {
        return tasaPoliza;
    }

    public void setTasaPoliza(double tasaPoliza) {
        this.tasaPoliza = tasaPoliza;
    }

    public byte[] getArCedula() {
        return arCedula;
    }

    public void setArCedula(byte[] arCedula) {
        this.arCedula = arCedula;
    }

    public byte[] getArPlanillaServicios() {
        return arPlanillaServicios;
    }

    public void setArPlanillaServicios(byte[] arPlanillaServicios) {
        this.arPlanillaServicios = arPlanillaServicios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(double saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public int getCantidadPolizas() {
        return cantidadPolizas;
    }

    public void setCantidadPolizas(int cantidadPolizas) {
        this.cantidadPolizas = cantidadPolizas;
    }

}
