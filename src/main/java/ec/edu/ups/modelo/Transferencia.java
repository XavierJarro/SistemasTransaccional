/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Starman
 */
@Entity
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoTransferenciaLocal;
    private double monto;
    @OneToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "numero_cuenta")
    private CuentaDeAhorro cuentaDeAhorroDestino;

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public int getCodigoTransferenciaLocal() {
        return codigoTransferenciaLocal;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCodigoTransferenciaLocal(int codigoTransferenciaLocal) {
        this.codigoTransferenciaLocal = codigoTransferenciaLocal;
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
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Metodo que permite obtener el atributo
     *
     *
     */
    public CuentaDeAhorro getCuentaDeAhorroDestino() {
        return cuentaDeAhorroDestino;
    }

    /**
     * Metodo que permite asignarle un valor al atributo
     *
     *
     */
    public void setCuentaDeAhorroDestino(CuentaDeAhorro cuentaDeAhorroDestino) {
        this.cuentaDeAhorroDestino = cuentaDeAhorroDestino;
    }

}
