/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Starman
 */
@Entity
public class SesionCliente implements Serializable {

    //Atributos de la clase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoSesion;
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSesion;
    @OneToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    public int getCodigoSesion() {
        return codigoSesion;
    }

    public void setCodigoSesion(int codigoSesion) {
        this.codigoSesion = codigoSesion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "SesionCliente{" + "codigoSesion=" + codigoSesion + ", estado=" + estado + ", fechaSesion=" + fechaSesion + ", cliente=" + cliente + '}';
    }

}
