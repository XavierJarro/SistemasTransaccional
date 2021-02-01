/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao.servicios;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import java.util.List;

/**
 *
 * @author Starman
 */
public class Respuesta {

    private int codigo;
    private String descripcion;
    private @JsonProperty("Cliente")Cliente cliente;
    private @JsonProperty("Cuenta")CuentaDeAhorro cuentaDeAhorro;
    private @JsonProperty("Poliza")List<PolizaRespuesta> listaCreditos;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CuentaDeAhorro getCuentaDeAhorro() {
        return cuentaDeAhorro;
    }

    public void setCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
        this.cuentaDeAhorro = cuentaDeAhorro;
    }

    public List<PolizaRespuesta> getListaCreditos() {
        return listaCreditos;
    }

    public void setListaCreditos(List<PolizaRespuesta> listaCreditos) {
        this.listaCreditos = listaCreditos;
    }
}
