/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.negocio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.modelo.Empleado;
import ec.edu.ups.modelo.SesionCliente;
import ec.edu.ups.modelo.Transaccion;
import ec.edu.ups.modelo.Transferencia;

import javax.ejb.Local;

/**
 *
 * @author Starman
 */
@Local
public interface GestionUsuarioLocal {

    public String generarNumeroDeCuenta();

    public String getUsuario(String cedula, String nombre, String apellido);

    public String getContraseña();

    public void enviarCorreo(String destinatario, String asunto, String cuerpo);

    public String fecha();

    public String obtenerFecha(Date fecha);

    public void guardarCliente(Cliente c);

    public Cliente buscarCliente(String cedulaCliente);

    public Cliente buscarClienteUsuarioContraseña(String usuario, String contraseña);

    public void eliminarCliente(String cedulaCliente);

    public void actualizarCliente(Cliente cliente);

    public List<Cliente> listaClientes();

    public void guardarCuentaDeAhorros(CuentaDeAhorro c);

    public CuentaDeAhorro buscarCuentaDeAhorro(String numeroCuentaDeAhorro);

    public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente);

    public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro);

    public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro);

    public List<CuentaDeAhorro> listaCuentaDeAhorros();

    public void guardarSesion(SesionCliente sesionCliente);

    public SesionCliente buscarSesionCliente(int codigoSesionCliente);

    public List<SesionCliente> obtenerSesionesCliente(String cedulaCliente);

    public boolean validadorDeCedula(String cedula) throws Exception;

    public void guardarEmpleado(Empleado empleado) throws SQLException, Exception;

    public Empleado usuarioRegistrado(String cedula);

    public List<Empleado> listadoEmpleados();

    public Empleado usuario(String usuario, String contra) throws Exception;

    public List<Transaccion> listadeTransacciones(String cedula);

    public void guardarTransaccion(Transaccion t) throws Exception;

    public List<Transaccion> obtenerTransaccionesFechaHora(String cedula, String fechaI, String fechaF);

    public void guardarTransferenciaLocal(Transferencia transfereciaLocal);

    // public String realizarTransaccion(String cuenta, double monto, String tipoTransaccion);
    public byte[] toByteArray(InputStream in) throws IOException;

    public String obtenerFecha2(Date fecha);

    public void cambioContrasena(Cliente cliente);

    public int obtenerEdad(Date fechaNacimiento);

    public double valorDecimalCr(double valor);

    public boolean verificarSolicitudSolicitando(String cedulaCliente);

}
