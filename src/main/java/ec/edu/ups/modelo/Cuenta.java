/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Starman
 */
@Entity
public class Cuenta implements Serializable {

    //Atributos de la clase
    @Id
    @Column(name = "numero_cuenta")
    private int numeroCuenta;
    private double saldoAhorro;
    private Date fechaRegistro;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    /**
     * Constructor de la clase
     */
    public Cuenta() {

    }

    /**
     * Metodo que permite obtener el atributo numeroCuentaDeAhorro
     *
     * @return El atributo numeroCuentaDeAhorro de esta clase
     */
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Metodo que permite asignarle un valor al atributo numeroCuentaDeAhorro
     *
     * @param numeroCuentaDeAhorro Variable que se asigna al atributo
     */
    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * Metodo que permite obtener el atributo saldoCuentaDeAhorro
     *
     * @return El atributo saldoCuentaDeAhorro de esta clase
     */
    public double getSaldoAhorro() {
        return saldoAhorro;
    }

    /**
     * Metodo que permite asignarle un valor al atributo saldoCuentaDeAhorro
     *
     * @param saldoCuentaDeAhorro Variable que se asigna al atributo
     */
    public void setSaldoAhorro(double saldoAhorro) {
        this.saldoAhorro = saldoAhorro;
    }

    /**
     * Metodo que permite obtener el atributo fechaDeRegistro
     *
     * @return El atributo fechaDeRegistro de esta clase
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo que permite asignarle un valor al atributo fechaRegistro
     *
     * @param fechaDeRegistro Variable que se asigna al atributo
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Metodo que permite obtener el atributo cliente
     *
     * @return El atributo cliente de esta clase
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Metodo que permite asignarle un valor al atributo cliente
     *
     * @param cliente Variable que se asigna al atributo
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
