/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao.servicios;

import ec.edu.ups.modelo.DetallePoliza;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Starman
 */
public class PolizaRespuesta {

    private int codigoPol;
    private String estado;
    private double monto;
    private double tasa;
    private double interes;
    private Date fechaRegistro;
    private Date fechaVencimiento;
    private List<DetallePoliza> detalles;

    public int getCodigoPol() {
        return codigoPol;
    }

    public void setCodigoPol(int codigoPol) {
        this.codigoPol = codigoPol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<DetallePoliza> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePoliza> detalles) {
        this.detalles = detalles;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public double getTasa() {
        return tasa;
    }

}
