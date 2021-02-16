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

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getCodigoPoliza() {
        return codigoPoliza;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCodigoPoliza(int codigoPoliza) {
        this.codigoPoliza = codigoPoliza;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Cliente getClientePoliza() {
        return clientePoliza;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setClientePoliza(Cliente clientePoliza) {
        this.clientePoliza = clientePoliza;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getMontoPoliza() {
        return montoPoliza;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setMontoPoliza(double montoPoliza) {
        this.montoPoliza = montoPoliza;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getMesesPoliza() {
        return mesesPoliza;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setMesesPoliza(int mesesPoliza) {
        this.mesesPoliza = mesesPoliza;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getTasaPoliza() {
        return tasaPoliza;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setTasaPoliza(double tasaPoliza) {
        this.tasaPoliza = tasaPoliza;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public byte[] getArCedula() {
        return arCedula;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setArCedula(byte[] arCedula) {
        this.arCedula = arCedula;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public byte[] getArPlanillaServicios() {
        return arPlanillaServicios;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setArPlanillaServicios(byte[] arPlanillaServicios) {
        this.arPlanillaServicios = arPlanillaServicios;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getSaldoCuenta() {
        return saldoCuenta;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setSaldoCuenta(double saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getCantidadPolizas() {
        return cantidadPolizas;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCantidadPolizas(int cantidadPolizas) {
        this.cantidadPolizas = cantidadPolizas;
    }

}
