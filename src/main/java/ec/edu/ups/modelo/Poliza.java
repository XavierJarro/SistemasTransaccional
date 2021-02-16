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
    private int codigoPol;//
    private String estado;
    private double monto;//
    private double tasa;//
    private double interes;//
    private double total;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @OneToOne
    private Empleado jefeC;
    @OneToOne
    private SolicitudPoliza solicitud;

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getCodigoPol() {
        return codigoPol;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCodigoPol(int codigoPol) {
        this.codigoPol = codigoPol;
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
    public double getMonto() {
        return monto;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getTasa() {
        return tasa;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setTasa(double tasa) {
        this.tasa = tasa;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getInteres() {
        return interes;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setInteres(double interes) {
        this.interes = interes;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public Empleado getJefeC() {
        return jefeC;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setJefeC(Empleado jefeC) {
        this.jefeC = jefeC;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public SolicitudPoliza getSolicitud() {
        return solicitud;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setSolicitud(SolicitudPoliza solicitud) {
        this.solicitud = solicitud;
    }
    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public double getTotal() {
        return total;
    }
    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setTotal(double total) {
        this.total = total;
    }

}
