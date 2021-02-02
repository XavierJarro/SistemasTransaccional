/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.dao.servicios;

import ec.edu.ups.modelo.TransferenciaExterna;
import ec.edu.ups.negocio.GestionUsuarioLocal;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Starman
 */
@Path("/starbank")
public class ServiciosBancoRest {

    @Inject
    private GestionUsuarioLocal on;

    @POST
    @Path("/transaccion")
    @Produces("application/json")
    @Consumes("application/json")
    public String transaccionBancaria(TransaccionRest transaccionRest) {
        return on.realizarTransaccion(transaccionRest.getCuenta(), transaccionRest.getMonto(),
                transaccionRest.getTipo());
    }

    @POST
    @Path("/transferencia")
    @Produces("application/json")
    @Consumes("application/json")
    public Respuesta transferencia(TransferenciaRest transferenciaRest) {
        return on.realizarTransferencia(transferenciaRest.getCedula(), transferenciaRest.getCuentaDeAhorro(), transferenciaRest.getMonto());
    }

    @GET
    @Path("/obtenerCliente")
    @Produces("application/json")
    public Respuesta obtenerClientes(@QueryParam("numeroCuenta") String numeroCuenta) {
        return on.obtenerClienteCuentaAhorro(numeroCuenta);
    }

    @POST
    @Path("/login")
    @Produces("application/json;charset=utf-8")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Respuesta loginCliente(@FormParam("username") String username, @FormParam("password") String password) {
        Respuesta respuesta = on.loginServicio(username, password);
        return respuesta;
    }

    @POST
    @Path("/cambiopassword")
    @Produces("application/json;charset=utf-8")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Respuesta cambioPassword(@FormParam("correo") String correo, @FormParam("contraAntigua") String contraAntigua, @FormParam("contraActual") String contraActual) {
        Respuesta respuesta = on.cambioContraseña(correo, contraAntigua, contraActual);
        return respuesta;
    }

    @POST
    @Path("/transferenciaExterna")
    @Produces("application/json")
    @Consumes("application/json")
    public RespuestaTransferenciaExterna transferenciaExterna(TransferenciaExterna transferenciaExterna) {
        return on.realizarTransferenciaExterna(transferenciaExterna);
    }
}
