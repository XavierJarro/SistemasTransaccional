/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Starman
 */
@Entity
public class Poliza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoPol;
    private String estado;
    private double monto;
    private double tasa;
    private double interes;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @OneToOne
    private Empleado jefeC;
    @OneToOne
    private SolicitudPoliza solicitud;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
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

    public Empleado getJefeC() {
        return jefeC;
    }

    public void setJefeC(Empleado jefeC) {
        this.jefeC = jefeC;
    }

    public SolicitudPoliza getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudPoliza solicitud) {
        this.solicitud = solicitud;
    }

    public List<DetallePoliza> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePoliza> detalles) {
        this.detalles = detalles;
    }

}
