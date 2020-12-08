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
public class TransferenciaLocal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoTransferenciaLocal;
    private double monto;
    @OneToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "numero_cuenta")
    private Cuenta cuentaDeAhorroDestino;

    public int getCodigoTransferenciaLocal() {
        return codigoTransferenciaLocal;
    }

    public void setCodigoTransferenciaLocal(int codigoTransferenciaLocal) {
        this.codigoTransferenciaLocal = codigoTransferenciaLocal;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cuenta getCuentaDeAhorroDestino() {
        return cuentaDeAhorroDestino;
    }

    public void setCuentaDeAhorroDestino(Cuenta cuentaDeAhorroDestino) {
        this.cuentaDeAhorroDestino = cuentaDeAhorroDestino;
    }

}
